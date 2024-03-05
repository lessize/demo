package com.kh.demo.domain.product.dao;

import com.kh.demo.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest // springboot 테스트 환경 실행
class ProductDAOImplTest {

  @Autowired  // springboot 컨테이너의 객체를 주입받는다.
  ProductDAO productDAO;

  @Test
  @DisplayName("상품 등록")
  void save() {
    Product product = new Product();
    product.setPname("노트북");
    product.setQuantity(3L);
    product.setPrice(2_000_000L);

    Long productId = productDAO.save(product);
    log.info("productId={}{}", productId, "2");
  }

  @Test
  @DisplayName("상품 조회")
  void findById() {
    Long productId = 100L;
    Optional<Product> findProduct = productDAO.findById(productId);
    Product product = findProduct.orElse(null);
    log.info("product={}", product);
  }

  @Test
  @DisplayName("상품 삭제")
  void deleteById() {
    Long pid = 62L;
    int deletedRowCnt = productDAO.deleteById(pid);
//    log.info("삭제건수={}", deletedRowCnt);
    Assertions.assertThat(deletedRowCnt).isEqualTo(1);
  }

  @Test
  @DisplayName("다중 삭제")
  void deleteByIds() {
//    productDAO.deleteByIds(List.of(1L, 2L, 3L));
    List<Long> ids = new ArrayList<>();
    ids.add(1L);
    ids.add(2L);
    ids.add(3L);
    int deletedRowCnt = productDAO.deleteByIds(ids);

    Assertions.assertThat(deletedRowCnt).isEqualTo(ids.size());
  }

  @Test
  @DisplayName("상품 목록")
  void findAll() {

    List<Product> list = productDAO.findAll();
    for (Product product : list) {
      log.info("product={}", product);
    }
    log.info("size={}", list.size());
  }

  @Test
  @DisplayName("상품 수정")
  void updateById() {
    Long productId = 101L;
    Product product = new Product();
    product.setPname("볼펜");
    product.setPrice(1L);
    product.setQuantity(1L);
    int updatedRowCnt = productDAO.updateById(productId, product);
//    log.info("updateRowCnt={}", updatedRowCnt);
    if (updatedRowCnt == 0) {
      Assertions.fail("변경 내역이 없습니다.");
    }
    Optional<Product> optionalProduct = productDAO.findById(productId);
    if (optionalProduct.isPresent()) {
      Product findedProduct = optionalProduct.get();
      Assertions.assertThat(findedProduct.getPname()).isEqualTo("볼펜");
      Assertions.assertThat(findedProduct.getQuantity()).isEqualTo(1L);
      Assertions.assertThat(findedProduct.getPrice()).isEqualTo(1L);
    } else {
      Assertions.fail("변경할 상품이 없습니다.");
    }
  }

  @Test
  @DisplayName("총 레코드 건수")
  void totalCnt() {
    int totalCnt = productDAO.totalCnt();
    List<Product> list = productDAO.findAll();

    Assertions.assertThat(totalCnt).isEqualTo(list.size());
  }

  @Test
  @DisplayName("상품 다중 등록")
  void saveMultipleProducts() {

    long start = 1;
    long end = 115;
    for (long i = start; i <= end; i++) {
      Product product = new Product();
      product.setPname("노트북"+i);
      product.setQuantity(i+10);
      product.setPrice(i*2);
      Long productId = productDAO.save(product);
    }

//    log.info("productId={}{}", productId, "2");
  }

  @Test
  @DisplayName("상품목록(페이징)")
  void FindAllByPaging() {
    List<Product> list = productDAO.findAll(1L, 10L);
    for (Product product : list) {
      log.info("product={}", product);
    }
    log.info("size={}", list.size());
  }
}