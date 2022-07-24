package com.cos.security1.config;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// secured 어노테이션 활성화, // preAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // 비활성화
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 해당 url은 인증이 필요하다
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll() // 나머지 페이지는 전부 권한없이 로그인 가능
                .and().formLogin().loginPage("/loginForm") // login 페이지로 이동할 url 설정
                .loginProcessingUrl("/login") // login주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다
                .defaultSuccessUrl("/")
                .and()// 여기서부터 구글 로그인 관련
                .oauth2Login()
                .loginPage("/loginForm")  // 여기까지가 로그인 페이지 이동까지의 처리
        // 기존 1. 코드받기(인증) / 2. 엑세스토큰(권한) / 3. 사용자 프로필 정보를 가져옴 / 4. 그 정보를 토대로 회원가입을 자동 진행
        // oauth 라이브러리를 사용하면 2,3번을 한번에 처리하게됨
                .userInfoEndpoint()
                .userService(principalOauth2UserService);




        // url에서 user, manager, admin을 요청해서 login으로 이동시 해당 페이지로 이동해주고
        // 아래의 defaultSuccessUrl로 인해서 나머지 url에서 login으로 들어갈 시 / 페이지로 이동하게 된다
    }
}
