package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// CURD 함수를 들고 있는 JpaRepository 인터페이스
// @Respository라는 어노테이션이 없어도 IoC가 되는데 상속했기때문에 가능하다 자동 빈 등록
public interface UserRepository extends JpaRepository<User, Integer> {

    // findBy 까지 적는건 규칙
    // Username은 문법이다
    // select * from user where username = ? > 호출
    public User findByUsername(String username);

    // select * from user where email = ? > 호출
    // public User findByEmail(String email);
    Optional<User> findByProviderAndProviderId(String provider, String providerId);

}
