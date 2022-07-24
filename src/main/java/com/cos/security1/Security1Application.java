package com.cos.security1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Security1Application {

	@Bean // 해당 메서드의 리턴 되는 오브젝트 값을 IoC에 등록해준다 간단하게 스프링 컨테이너에 올라감
	public BCryptPasswordEncoder encodePwd(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(Security1Application.class, args);
	}

}
