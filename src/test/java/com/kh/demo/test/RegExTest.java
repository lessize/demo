package com.kh.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class RegExTest {

  @Test
  @DisplayName("숫자 3자리 이상 10자리 이하")
  void t1() {
    String str = "12345";
    String pattern = "[0-9]{3,10}";

    // if(Pattern.matches(pattern,str)){
    if (Pattern.matches("\\d{3,10}", "1232")) {
      log.info("패턴일치");
    } else {
      log.info("패턴불일치");
    }
  }

  @Test
  @DisplayName("전화번호 패턴 체크")
  void t2() {
    String str = "12345";
    String pattern = "[0-9]{3,10}";

    if (Pattern.matches("\\d{3}-\\d{4}-\\d{4}", "01012345678")) {
      log.info("패턴일치");
    } else {
      log.info("패턴불일치");
    }
  }

  @Test
  @DisplayName("상품명 패턴 체크")
  void t3() {
    String input = "abc123가_문-test"; // 여기에 체크할 문자열을 넣어주세요

    // 정규표현식 패턴
    String pattern = "^[a-zA-Z0-9가-힣_-]{3,10}$";

    // 패턴을 컴파일
    Pattern p = Pattern.compile(pattern);

    // 입력값과 패턴 매칭 여부 확인
    Matcher m = p.matcher(input);

    // 매칭 결과 출력
    if (m.matches()) {
      log.info("영문자, 숫자, 한글, _, - 문자를 포함하며 전체 자릿수는 최소 3자리 최대 10자리입니다.");
    } else {
      log.info("조건에 맞지 않습니다.");
    }
  }

  @Test
  @DisplayName("이메일 패턴 체크")
  void t4(){
    String email = "example@example.com"; // 여기에 체크할 이메일을 넣어주세요

    // 정규표현식 패턴
    String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // 패턴을 컴파일
    Pattern p = Pattern.compile(pattern);

    // 입력값과 패턴 매칭 여부 확인
    Matcher m = p.matcher(email);

    // 매칭 결과 출력
    if (m.matches()) {
      log.info("올바른 이메일 형식입니다.");
    } else {
      log.info("올바르지 않은 이메일 형식입니다.");
    }
  }
}
