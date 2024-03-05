package com.kh.demo.web;

import com.kh.demo.domain.member.svc.MemberSVC;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.api.ResCode;
import com.kh.demo.web.req.member.ReqExistEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class ApiMemberController {

  private final MemberSVC memberSVC;

  // 회원 중복 체크
  @PostMapping("/dupchk")
  public ApiResponse<?> dupchk(@RequestBody ReqExistEmail reqExistEmail) {
    ApiResponse<?> res = null;
    log.info("reqExistEmail={}", reqExistEmail);
    boolean existEmail = memberSVC.existEmail(reqExistEmail.getEmail());
    if (existEmail) {
      res = ApiResponse.createApiResponse(ResCode.EXIST.getCode(), ResCode.EXIST.name(), null);
    } else {
      res = ApiResponse.createApiResponse(ResCode.NONE.getCode(), ResCode.NONE.name(), null);
    }

    return res;
  }
}
