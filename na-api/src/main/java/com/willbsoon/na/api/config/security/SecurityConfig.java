package com.willbsoon.na.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.willbsoon.na.api.advice.exception.CAuthenticationEntryPointException;

import lombok.RequiredArgsConstructor;

//어노테이션을 활용한 권한 부여
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// 컨트롤러 메서드 단 or 컨트롤러에 권한 부여 가능
// in controller // @PreAuthorize("hasRole('ROLE_USER')") // @Secured("ROLE_USER")
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

//	hasIpAddress(ip) – 접근자의 IP주소가 매칭 하는지 확인합니다.        
//	hasRole(role) – 역할이 부여된 권한(Granted Authority)과 일치하는지 확인합니다.        
//	hasAnyRole(role) – 부여된 역할 중 일치하는 항목이 있는지 확인합니다.	ex) access = “hasAnyRole(‘ROLE_USER’,’ROLE_ADMIN’)”        
//	permitAll – 모든 접근자를 항상 승인합니다.        
//	denyAll – 모든 사용자의 접근을 거부합니다.        
//	anonymous – 사용자가 익명 사용자인지 확인합니다.        
//	authenticated – 인증된 사용자인지 확인합니다.        
//	rememberMe – 사용자가 remember me를 사용해 인증했는지 확인합니다.        
//	fullyAuthenticated – 사용자가 모든 크리덴셜을 갖춘 상태에서 인증했는지 확인합니다.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable() // restapi이므로 사용안함. 기본설정은 로그인 폼으로 이동
				.csrf().disable() // 테스트일땐 csrf 보안이 필요없으므로 사용안함
				// jwt 토큰으로 인증하므로 세션 필요없음
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
					.authorizeRequests()
					// 가입및 인증 주소는 누구나 접근
						.antMatchers("/*/signin", "/*/signup").permitAll()
						// helloworld로 시작하는 GET 리소스요청은 누구나 가능
						.antMatchers(HttpMethod.GET, "helloworld/**").permitAll()
	                    .antMatchers(HttpMethod.GET, "/exception/**").permitAll() // exception 추가
//	                    .antMatchers(HttpMethod.GET, "/*/users").hasRole("ADMIN") //?
					.anyRequest().hasRole("USER") // 그외 요청은 인증된 회원만 가능
				.and() // jwt없이 api를 호출한 경우
					.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
				.and() // api를 호출하였으나 접근 권한이 없는 경우
					.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
				.and()
				// id, password 인증 전에 jwt token 필터를 넣음
					.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
							UsernamePasswordAuthenticationFilter.class);

	}

	// ignore swagger resource
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**",
				"/swagger/**");
	}

}
