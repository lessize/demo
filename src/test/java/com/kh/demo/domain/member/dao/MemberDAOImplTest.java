package com.kh.demo.domain.member.dao;

import com.kh.demo.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class MemberDAOImplTest {

  @Autowired
//  @Qualifier("memberDAOImpl2")
  MemberDAO memberDAO;

  @Test
  void test() {
    log.info("memberDAO={}", memberDAO.getClass().getName());
  }

  @Test
  @DisplayName("회원가입")
  @Transactional
  void insertMember() {
    Member member = new Member();
    member.setEmail("user3@kh.com");
    member.setPasswd("user3");
    member.setNickname("사용자3");
//    Long memberId = memberDAO.insertMember(member);
//    log.info("memberId={}", memberId);
  }

  @Test
  @DisplayName("이메일(o)")
  void existMemberId() {
    boolean exist = memberDAO.existMemberId("user1@kh.com");
    assertThat(exist).isEqualTo(true);
  }

  @Test
  @DisplayName("이메일(x)")
  void dontExistMemberId() {
    boolean exist = memberDAO.existMemberId("1535@kh.com");
    assertThat(exist).isEqualTo(false);
  }

  @Test
  @DisplayName("회원조회(o)")
  void findByEmailAndPasswd() {
    Optional<Member> optionalMember = memberDAO.findByEmailAndPasswd("user1@kh.com", "user1");

    assertThat(optionalMember).isPresent(); // optionalMember가 존재하는지 확인
    Member member = optionalMember.get(); // optionalMember에서 실제 member 객체를 가져옴
    assertThat(member.getEmail()).isEqualTo("user1@kh.com"); // 회원의 이메일이 올바른지 확인
    assertThat(member.getPasswd()).isEqualTo("user1"); // 회원의 비밀번호가 올바른지 확인
  }

  @Test
  @DisplayName("회원조회(x)")
  void notFindByEmailAndPasswd() {
    Optional<Member> optionalMember = memberDAO.findByEmailAndPasswd("aaaaaa@kh.com", "aaaa");

    Assertions.assertThat(optionalMember).isEmpty();    // optional이 없어야 함
  }
}