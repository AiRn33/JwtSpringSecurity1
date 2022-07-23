package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Security 설정에서 loginProcessingUrl("/login")
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는
// loadUserByUsername 함수가 실행된다
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 시큐리티 Session(내부 Authentication(내부 UserDetails)) 의 형태를 띄게 된다다
   @Override
    // 매개변수의 username은 페이지에서 넘어오는 id값과 동일해야한다
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);

        if(userEntity != null){
            // user가 있을 때 디테일 클래스를 리턴한다
            return new PrincipalDetails(userEntity);
        }

        return null;
    }
}
