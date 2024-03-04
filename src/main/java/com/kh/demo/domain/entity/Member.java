package com.kh.demo.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {
  private Long memberId;              // number(10)
  private String email;               // varchar2(50)
  private String passwd;              // varchar2(12)
  private String nickname;            // varchar2(30)
  private String gubun;               // varchar2(11)
  private byte[] pic;                 // blob
  private LocalDateTime cdate;        // timestamp
  private LocalDateTime udate;        // timestamp
}
