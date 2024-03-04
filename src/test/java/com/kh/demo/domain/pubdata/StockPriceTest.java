package com.kh.demo.domain.pubdata;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class StockPriceTest {

  @Autowired
  StockPrice stockPrice;

  @Test
  void getStockPrice() {
    String itmsNm = "SK하이닉스";
    String beginBasDt = "20240201";
    String endBasDt = "20240221";
//    String data = stockPrice.reqStockPrice(itmsNm, beginBasDt, endBasDt);
//    log.info("data={}", data);
  }
}
