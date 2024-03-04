package com.kh.demo.domain.product.dao;

import com.kh.demo.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
  // 등록
  Long save(Product product);

  // 조회
  //  Optional : 객체를 최대 1개만 저장할 수 있는 컬렉션 (1개 or null)
  Optional<Product> findById(Long productId);
//  Product findById(Long productId); : 상품 객체가 존재함 (null 체크 필요)

  // 목록
  List<Product> findAll();

  // 단건 삭제
  int deleteById(Long productId);

  // 다중 삭제
  int deleteByIds(List<Long> productIds);

  // 수정
  int updateById(Long productId, Product product);

  // 총 레코드 건수
  int totalCnt();
}
