package com.kh.demo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/a")
public class TestController2 {

  @GetMapping("/{v}/c")
  public String t1(@PathVariable("v") String value) {
    log.info("value");
    return "test/";
  }

  @GetMapping("/{v1}/{v2}")
  public String t2(@PathVariable("v") String value1,
                   @PathVariable("v") String value2) {
    log.info("value1={}, value2={}", value1, value2);
    return "test/";
  }

}
