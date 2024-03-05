package com.kh.demo.web;

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.svc.ProductSVC;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.api.ResCode;
import com.kh.demo.web.req.product.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
//@Controller
@RestController   // @controller + @ResponseBody
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ApiProductController {

  private final ProductSVC productSVC;

  // 등록
  @PostMapping      // http://localhost:9080/api/products
  public ApiResponse<?> add(@RequestBody ReqSave reqSave) {   // @RequestBody : 요청메세지 바디의 json 포맷 문자열 -> java 객체로 매핑
    log.info("reqSave={}", reqSave);

    // 1) 유효성 검증

    // 2) 상품 등록 처리
//    Product product = new Product();
//    product.setPname(reqSave.getPname());
//    product.setQuantity(reqSave.getQuantity());
//    product.setPrice(reqSave.getPrice());
    // 같은 필드가 존재하면 자동으로 매핑해주는 유틸리티
    Product product = new Product();
    BeanUtils.copyProperties(reqSave, product);
    Long productId = productSVC.save(product);

    ResSave resSave = new ResSave(productId, reqSave.getPname());

    String rtDetail = "상품번호 " + productId + "가 등록되었습니다.";
    ApiResponse<ResSave> res = ApiResponse.createApiResponseDetail(ResCode.OK.getCode(), ResCode.OK.name(), rtDetail, resSave);

    return res;
  }

  // 조회
  @GetMapping("/{pid}")
  public ApiResponse<?> findById(@PathVariable("pid") Long pid) {
    log.info("pid={}", pid);
    Optional<Product> optionalProduct = productSVC.findById(pid);

    ApiResponse<ResFindById> res = null;
    // 상품을 찾은 경우
    if (optionalProduct.isPresent()) {
      Product findedProduct = optionalProduct.get();

      ResFindById resFindById = new ResFindById();
      BeanUtils.copyProperties(findedProduct, resFindById);
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), resFindById);
    } else {  // 상품을 못 찾은 경우
      String rtDetail = "상품번호 : " + "찾으시는 상품이 존재하지 않습니다.";
      res = ApiResponse.createApiResponseDetail(ResCode.FAIL.getCode(), ResCode.FAIL.name(), rtDetail, null);
    }
    return res;
  }

  // 수정
  @PatchMapping("/{pid}")
  public ApiResponse<?> update(@PathVariable("pid") Long pid,
                               @RequestBody ReqUpdate reqUpdate) {
    log.info("pid={}", pid);
    log.info("reqUpdate={}", reqUpdate);
    // 1) 유효성 체크
    
    // 2) 수정
    Product product = new Product();
    BeanUtils.copyProperties(reqUpdate, product);
    int updatedCnt = productSVC.updateById(pid, product);

    ApiResponse<ResUpdate> res = null;
    if (updatedCnt == 1) {
      ResUpdate resUpdate = new ResUpdate();
      BeanUtils.copyProperties(reqUpdate, resUpdate);
      resUpdate.setProductId(pid);

      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), resUpdate);
    } else {
      res = ApiResponse.createApiResponse(ResCode.FAIL.getCode(), ResCode.FAIL.name(), null);
    }
    return res;
  }

  // 삭제
  @DeleteMapping("/{pid}")
  public ApiResponse<?> delete(@PathVariable("pid") Long pid) {
    int deletedCnt = productSVC.deleteById(pid);

    ApiResponse<ResUpdate> res = null;
    if (deletedCnt == 1) {
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), null);
    } else {
      res = ApiResponse.createApiResponse(ResCode.FAIL.getCode(), ResCode.FAIL.name(), null);
    }
    return res;
  }

//  // 목록
//  @GetMapping
//  public ApiResponse<?> list() {
//
//    try {
//      Thread.sleep(3000); // 3초 지연
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//
//    List<Product> list = productSVC.findAll();
//
//    ApiResponse<List<Product>> res = null;
//    if (list.size() > 0) {
//      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), list);
//      res.setTotalCnt(productSVC.totalCnt());
//    } else {
//      res = ApiResponse.createApiResponseDetail(ResCode.OK.getCode(), ResCode.OK.name(), "등록된 상품이 존재하지 않습니다.", list);
//    }
//
//    return res;
//  }
  // 목록 (페이징)
  @GetMapping
  public ApiResponse<?> list(@RequestParam("reqPage") Long reqPage,
                             @RequestParam("reqCnt") Long reqCnt) {

    try {
      Thread.sleep(1000*1); // 1초는 1000
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    List<Product> list = productSVC.findAll(reqPage, reqCnt);

    ApiResponse<List<Product>> res = null;
    if (list.size() > 0) {
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), list);
      res.setTotalCnt(productSVC.totalCnt());
      res.setReqPage(reqPage.intValue());
      res.setRecCnt(reqCnt.intValue());
    } else {
      res = ApiResponse.createApiResponseDetail(ResCode.OK.getCode(), ResCode.OK.name(), "등록된 상품이 존재하지 않습니다.", list);
    }

    return res;
  }
}
