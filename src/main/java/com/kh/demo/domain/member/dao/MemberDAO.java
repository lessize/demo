package com.kh.demo.domain.member.dao;

import com.kh.demo.domain.entity.Member;

import java.util.Optional;

public interface MemberDAO {
  // 회원 가입
  Long insertMember(Member member);

  // 회원 아이디 조회
  boolean existEmail(String email);

  // 회원 조회
  Optional<Member> findByEmailAndPasswd(String email, String passwd);

  // 회원 수정

  // 회원 탈퇴
}
