package com.kh.demo.web.form.product;

import lombok.Data;

@Data
public class AddForm {
  // form 엘리먼트의 이름과 동일
  private String pname;
  private Long quantity;
  private Long price;
}
