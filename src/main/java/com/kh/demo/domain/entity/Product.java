package com.kh.demo.domain.entity;

/* wrapper class
   byte -> Byte, short -> Short, char -> Character, int -> Integer, long -> Long
   boolean -> Boolean, double -> Double, float -> Float
 */

import lombok.*;

import java.time.LocalDateTime;

@Data
public class Product {
  private Long productId;       // 상품 아이디 PRODUCT_ID	NUMBER(10,0)
  private String pname;         // 상품명 PNAME	VARCHAR2(30 BYTE)
  private Long quantity;        // 상품 수량 QUANTITY	NUMBER(10,0)
  private Long price;           // 상품 가격 PRICE	NUMBER(10,0)
  private LocalDateTime cdate;  // 생성 일시 CDATE	TIMESTAMP(6)
  private LocalDateTime udate;  // 수정 일시 UDATE	TIMESTAMP(6)
}
