package com.kh.demo.domain.product.svc;

import com.kh.demo.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductSVC {
  // 등록
  Long save(Product product);

  // 조회
  Optional<Product> findById(Long productId);

  // 삭제
  int deleteById(Long productId);

  // 목록
  List<Product> findAll();

  // 목록 (페이징)
  List<Product> findAll(Long reqPage, Long recCnt);

  // 다중 삭제
  int deleteByIds(List<Long> productIds);

  // 수정
  int updateById(Long productId, Product product);

  // 총 레코드 건수
  int totalCnt();
}
