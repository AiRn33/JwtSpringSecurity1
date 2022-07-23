package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.Authenticator;

@Controller
public class IndexController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String loginTest(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oAuth){
        System.out.println("/test/oauth/login =======================================");
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        System.out.println("authentication : " + oAuth2User);
        System.out.println("oauth2User : " + oAuth);
        return "OAuth 세션 정보 확인하기";
    }
    @PostMapping("/join")
    public String join(User user){

        user.setRole("ROLE_USER");

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 패스워드 암호화
        user.setPassword(encPassword);
        userRepository.save(user);
        // 비밀번호를 암호화 해줘야할 필요성이 있음

        return "redirect:/loginForm";
    }

    @GetMapping({"","/"})
    public String index(){
        // 머스테치로 이동
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(){

        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){

        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){

        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm(){

        return "loginForm";
    }


    @GetMapping("/joinForm")
    public String joinForm(){

        return "joinForm";
    }

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc(){

        return "회원가입완료됨";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }
    // 여러개를 쓸때는 PreAuthorize로 밑의 방식으로 , 하나만 걸때는 위의 Secured 방식으로하면 좋음
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터정보";
    }

}
