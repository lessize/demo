package com.kh.demo.domain.member.svc;

import com.kh.demo.domain.entity.Member;

import java.util.Optional;

public interface MemberSVC {
  // 회원 가입
  Long joinMember(Member member);

  // 회원 아이디 조회
  boolean existMemberId(String email);

  // 회원 수정
  Optional<Member> findByEmailAndPasswd(String email, String passwd);

  // 회원 탈퇴
}
