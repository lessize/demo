package com.kh.demo;

import com.kh.demo.web.interceptor.LoginCheckInterCeptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // 설정
public class AppConfig implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 인증 체크
    registry.addInterceptor(new LoginCheckInterCeptor())
            .order(1)                 // 인터셉터 실행 순서 지정
            .addPathPatterns("/**")   // 인터셉터에 포함시키는 url 패턴, 루트부터 하위 경로 모두 지정
            .excludePathPatterns(     // 화이트리스트 등록
                    "/",              // 초기화면
                    "/login",         // 로그인 화면
                    "/logout",        // 로그아웃
                    "/members/join",  // 회원 가입
                    "/css/**",
                    "/js/**",
                    "/img/**",
//                    "/test/**",
                    "/api/**"         // rest api
            );  // 인터셉터 제외 url 패턴
  }
}
