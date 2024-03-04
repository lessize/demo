package com.kh.demo.domain.member.svc;

import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.member.dao.MemberDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC {


  private final MemberDAO memberDAO;

//  @Autowired
//  public MemberSVCImpl(MemberDAO memberDAO) {
//    this.memberDAO = memberDAO;
//    log.info("memberDAO={}", memberDAO.getClass().getName());
//  }

  @Override
  public Long joinMember(Member member) {
    return memberDAO.insertMember(member);
  }

  @Override
  public boolean existMemberId(String email) {
    return memberDAO.existMemberId(email);
  }

  @Override
  public Optional<Member> findByEmailAndPasswd(String email, String passwd) {
    return memberDAO.findByEmailAndPasswd(email, passwd);
  }
}
