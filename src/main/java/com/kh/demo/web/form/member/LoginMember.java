package com.kh.demo.web.form.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginMember {
  private Long memberId;              // number(10)
  private String email;               // varchar2(50)
  private String nickname;            // varchar2(30)
  private String gubun;               // varchar2(11)

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public void setGubun(String gubun) {
    this.gubun = gubun;
  }
}
