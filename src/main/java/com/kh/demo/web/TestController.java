package com.kh.demo.web;

import com.kh.demo.test.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/test")        // http://localhost:9080/test
public class TestController {

  @GetMapping("/t2")            // http://localhost:9080/test/t2
  public String t2() {
    return "test/test2";
  }

  @GetMapping("/product")       // GET http://localhost:9080/test/product
  public String get(@RequestParam("pname") String pname,
                    @RequestParam("price") Long price) {
    log.info("get() 호출됨");
    log.info("pname={}, price={}", pname, price);
    return "redirect:/test/test2";
  }

  @GetMapping("/product/{pname}/{price}")       // GET http://localhost:9080/test/product/상품명/가격
  public String get2(@PathVariable("pname") String pname,
                     @PathVariable("price") Long price) {
    log.info("get2() 호출됨");
    log.info("pname={}, price={}", pname, price);
    return "redirect:/test/test2";
  }

  @PostMapping("/product")      // POST http://localhost:9080/test
  public String post(@RequestParam("pname") String pname,
                     @RequestParam("price") Long price) {
    log.info("post() 호출됨");
    log.info("pname={}, price={}", pname, price);
    return "redirect:/test/test2";
  }

  @GetMapping("/t1")            // get http://localhost:9080/test/t1
  public String t1(Model model) {

    model.addAttribute("key1", "KH인재교육원1");
    model.addAttribute("key2", "KH인재교육원2");
    model.addAttribute("key3", "KH인재교육원3");

    List<Person> persons = new ArrayList<>();
    Person p1 = new Person("홍길동", 30);
    Person p2 = new Person("홍길서", 20);
    Person p3 = new Person("홍길남", 40);

    persons.add(p1);
    persons.add(p2);
    persons.add(p3);

    model.addAttribute("persons", persons);

    return "test/test1";
  }

  @GetMapping("/url/t1")          // GET http://locahost:9080/test/url/t1
  public String url_t1() {

    return "/test/url";
  }
}
