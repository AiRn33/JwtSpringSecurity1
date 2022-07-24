package com.cos.security1.config.auth;

// 시큐리티가 /login을 낚아채서 로그인을 진행시키고
// 로그인 진행이 완료되면 session을 만들어준다(Security ContextHolder 에 저장)
// 오브젝트 => Authentication 객체만 들어갈 수 있음
// Authentication 안에 User 정보가 있어야 되는데
// User 오브젝트 타입 = > UserDetails 타입 객체

import com.cos.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// Security Session => Authentication 객체만 들어갈 수 있다는 걸 중요시하자
// Authentication => UserDetails 객체만 들어갈 수 있다
// 밑의 메소드와 같이 상속받으면 UserDetails의 역활을 할 수 있다
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String,Object> attributes;

    // 일반 로그인 생성자
    public PrincipalDetails(User user){
        this.user = user;
    }

    // Oauth 관련 로그인 생성자
    public PrincipalDetails(User user, Map<String,Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }
    // OAuth2User 오버라이딩 메소드
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 해당 User의 권한을 리턴하는 곳!!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        // 우리사이트에서 1년동안 회원이 로그인을 안할때 휴면회원을 할꺼면 entity에 로그인 데이터를 놔두고 해결할 수 있다
        // 현재시간 - 로그인 시간 = > 1년을 초과할 시 false로 리턴값을 받으면 된다
        return true;
    }


    @Override
    public String getName() {
        return null;
    }
}
