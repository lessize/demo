package com.kh.demo.web;

import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.member.svc.MemberSVC;
import com.kh.demo.web.form.member.LoginForm;
import com.kh.demo.web.form.member.LoginMember;
import com.kh.demo.web.form.member.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final MemberSVC memberSVC;

  // 로그인 양식
  @GetMapping("/login")
  public String loginForm() {
    return "login";
  }

  // 로그인 처리
  @PostMapping("/login")    // login?redirectUrl=사용자가 요청한 url
  public String login(LoginForm loginForm,
                      HttpServletRequest request,
                      @RequestParam(value = "redirectUrl", required = false) String redirectUrl) {
    log.info("loginForm={}", loginForm);

    // 1) 유효성 체크

    // 2) 회원 유무 체크
    // 2-1) 회원 아이디가 존재 유무 체크
    if (memberSVC.existEmail(loginForm.getEmail())) {
      Optional<Member> optionalMember = memberSVC.findByEmailAndPasswd(loginForm.getEmail(), loginForm.getPasswd());
      // 회원 정보가 있는 경우
      if (optionalMember.isPresent()) {
        // 세션 생성 : 세션 정보가 있다면 있는 세션 정보를, 없다면 새로운 세션을 생성함 getSession(true)
        HttpSession session = request.getSession(true);
        // 회원 정보를 세션에 저장
        Member member = optionalMember.get();
        LoginMember loginMember = new LoginMember(member.getMemberId(), member.getEmail(), member.getNickname(), member.getGubun());
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
      } else {
        // 회원 정보가 없는 경우
        return "login";
      }
    } else {
      return "login";
    }
    return redirectUrl != null ? "redirect:"+redirectUrl : "redirect:/";
  }

  // 로그아웃
  @ResponseBody
  @PostMapping("/logout")
  public String logout(HttpServletRequest request) {
    String flag = "NOK";
    // 세션이 있으면 가져오고 없으면 생성하지 않는다.
    HttpSession session = request.getSession(false);
    // 세션 제거
    if (session != null) {
      session.invalidate();
      flag = "OK";
    }
    return flag;
  }
}
