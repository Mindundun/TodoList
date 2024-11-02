package com.in28minutes.springboot.myfirstwebapp.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 직접 Bean 생성
public class SpringSecurityConfiguration {
	//LDAP or Datebase
	//In Memory
	
	/*
	 * InMemoryUserDetailsManager 
	 * InMemoryUserDetailsManager(UserDetails... users)
	 */
	
	@Bean
	public InMemoryUserDetailsManager createUserDetailManeger() {
		UserDetails userDetails1 = createNewUser("Mindun", "dummy");
		UserDetails userDetails2 = createNewUser("YM", "Babo1");
		
		return new InMemoryUserDetailsManager(userDetails1,userDetails2);			
	}

	private UserDetails createNewUser(String username, String password) {
		Function<String, String> passwordEncoder
		= input -> passwordEncoder().encode(input); // 어떤 input이 와도 이 passwordEncoder()는 인코딩한 다음 사용자 세부정보를 설정할 것임
	
		UserDetails userDetails = User.builder()
									.passwordEncoder(passwordEncoder)
								 	.username(username)
									.password(password)
									.roles("USER","ADMIN")
									.build();
		return userDetails;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	//All URLs are protect
	//A login form is shown for unauthrized requests
	//CSRF disable
	//Frames
	
	//로그아웃 후 무조건 로그인 페이지로 가는 기능인데 이 기능으로 인해 H2 매핑 시 오류 발생 . .
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/index.jsp").permitAll() // index.jsp 접근 허용
	            .anyRequest().authenticated() // 그 외 요청은 인증 필요
	        )
	        .formLogin(withDefaults())
	        .csrf().disable()
	        .headers().frameOptions().disable();

	    return http.build();
	
	}

}
