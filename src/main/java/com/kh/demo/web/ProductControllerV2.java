package com.kh.demo.web;

import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.svc.ProductSVC;
import com.kh.demo.web.form.product.AddForm;
import com.kh.demo.web.form.product.UpdateForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Controller // Controller 역할을 하는 클래스
@RequestMapping("/products")  // http://localhost:9080/products
public class ProductControllerV2 {

  private ProductSVC productSVC;

  ProductControllerV2(ProductSVC productSVC) {
    this.productSVC = productSVC;
  }

  // 상품 등록 양식
  @GetMapping("/add")     // http://localhost:9080/products/add
  public String addForm(Model model) {
    model.addAttribute("addForm", new AddForm());
    return "productV2/add"; // view 이름 상품등록화면
  }

  // 상품 등록 처리
  // controller 레벨에서 유효성 체크
  @PostMapping("/add")    // Post, http://localhost:9080/products/add
  public String add(AddForm addForm,      // form 객체 : 양식과 매핑되는 객체
                    Model model,
                    RedirectAttributes redirectAttributes
  ) {

    log.info("addForm={}", addForm);
    // 유효성 체크
    // 필드레벨
    // 1-1) 상품명
    String pattern = "^[a-zA-Z0-9가-힣_-]{3,10}$";

    if (!Pattern.matches(pattern, addForm.getPname())) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_pname", "영문,숫자,한글,_/-입력 가능, 3~10자리 입력");
      return "productV2/add";
    }
    // 1-2) 수량
    pattern = "^\\d{1,10}$";
    if (!Pattern.matches(pattern, String.valueOf(addForm.getQuantity()))) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_quantity", "숫자 0~9 사이 입력 가능, 1~10자리 입력");
      return "productV2/add";
    }
    // 1-3) 가격
    pattern = "^\\d{1,10}$";
    if (!Pattern.matches(pattern, String.valueOf(addForm.getPrice()))) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_price", "숫자 0~9 사이 입력 가능, 1~10자리 입력");
      return "productV2/add";
    }

    // 글로벌 레벨 체크
    if (addForm.getQuantity() * addForm.getPrice() > 10_000_000) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_global", "총액이 1000만원을 초과합니다.");
      return "productV2/add";
    }


    // 상품 등록
    Product product = new Product();
    product.setPname(addForm.getPname());
    product.setQuantity(addForm.getQuantity());
    product.setPrice(addForm.getPrice());

    Long productId = productSVC.save(product);
    log.info("상품번호={}", productId);

    redirectAttributes.addAttribute("pid", productId);
    return "redirect:/products/{pid}/detail"; // 상품조회화면  302 GET http://서버:9080/products/상품번호/detail
  }

  // 조회
  @GetMapping("/{pid}/detail")       // get http://localhost:9080/products/상품번호/detail
  public String findById(@PathVariable("pid") Long productId, Model model) {
    Optional<Product> finededProduct = productSVC.findById(productId);

    Product product = finededProduct.orElseThrow();
    model.addAttribute("product", product);

    return "productV2/detailForm";
  }

  // 단건 삭제
  @ResponseBody     // 응답 메세지 바디에 직접 작성
  @DeleteMapping("/{pid}/del")
  public ResponseEntity<?> deleteById(@PathVariable("pid") Long productId) {
    log.info("deleteById={}", productId);

    // 1) 상품 삭제 -> 상품 테이블에서 삭제
    int deletedRowCnt = productSVC.deleteById(productId);
    if (deletedRowCnt == 1) {
      return ResponseEntity.ok().build();   // 응답코드 200 OK
    } else {
      return ResponseEntity.notFound().build();   // 응답코드 404 NotFond
    }
//    return "redirect:/products";      // GET http://localhost:9080/products
  }

  // 다중 삭제
  @PostMapping("/del")                // POST http://localhost:9080/products/del
  public String deleteByIds(@RequestParam("pids") List<Long> pids) {

    log.info("deleteByIds={}", pids);
    int deletedRowCnt = productSVC.deleteByIds(pids);

    return "redirect:/products";      // GET http://localhost:9080/products
  }

  // 수정 양식
  @GetMapping("/{pid}/edit")          // GET http://localhost:9080/products/상품번호/edit
  public String updateForm(@PathVariable("pid") Long productId,
                           Model model){
    Optional<Product> optionalProduct = productSVC.findById(productId);
    Product findedProduct = optionalProduct.orElseThrow();
    model.addAttribute("product", findedProduct);

    return "productV2/updateForm";
  }

  // 수정 처리
  @PostMapping("/{pid}/edit")
  public String update(// 경로변수 pid로부터 상품번호를 읽어온다
                       @PathVariable("pid") Long productId,
                       // 요청 메세지 바디로부터 대응되는 상품 정보를 읽어온다
                       UpdateForm updateForm,
                       // 리다이렉트 시 경로변수에 값을 설정하기 위해 사용
                       RedirectAttributes redirectAttributes,
                       Model model){
    // 유효성 체크
    // 필드 레벨
    // 1-1) 상품명
    String pattern = "^[a-zA-Z0-9가-힣_-]{3,10}$";

    if (!Pattern.matches(pattern, updateForm.getPname())) {
      model.addAttribute("product", updateForm);
      model.addAttribute("s_err_pname", "영문,숫자,한글,_/-입력 가능, 3~10자리 입력");
      return "productV2/updateForm";
    }
    // 1-2) 수량
    pattern = "^\\d{1,10}$";
    if (!Pattern.matches(pattern, String.valueOf(updateForm.getQuantity()))) {
      model.addAttribute("product", updateForm);
      model.addAttribute("s_err_quantity", "숫자 0~9 사이 입력 가능, 1~10자리 입력");
      return "productV2/updateForm";
    }
    // 1-3) 가격
    pattern = "^\\d{1,10}$";
    if (!Pattern.matches(pattern, String.valueOf(updateForm.getPrice()))) {
      model.addAttribute("product", updateForm);
      model.addAttribute("s_err_price", "숫자 0~9 사이 입력 가능, 1~10자리 입력");
      return "productV2/updateForm";
    }

    // 글로벌 레벨 체크
    if (updateForm.getQuantity() * updateForm.getPrice() > 10_000_000) {
      model.addAttribute("product", updateForm);
      model.addAttribute("s_err_global", "총액이 1000만원을 초과합니다.");
      return "productV2/updateForm";
    }

    // 정상 처리
    Product product = new Product();
    product.setPname(updateForm.getPname());
    product.setQuantity(updateForm.getQuantity());
    product.setPrice(updateForm.getPrice());

    productSVC.updateById(productId, product);

    redirectAttributes.addAttribute("pid", productId);

    return "redirect:/products/{pid}/detail";
  }

  // 목록
  @GetMapping     // get http://localhost:9080/products
  public String findAll(Model model, HttpServletRequest request) {
    // 세션 체크
    HttpSession session = request.getSession(false);  // 새로운 세션을 생성하지 않음
    if (session != null) {
      Member member = (Member) session.getAttribute("loginOK");
    } else {
      return "redirect:/";
    }

    List<Product> list = productSVC.findAll();
    model.addAttribute("list", list);

    return "productV2/all";
  }
}
