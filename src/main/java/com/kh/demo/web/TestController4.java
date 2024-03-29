package com.kh.demo.web;

import com.kh.demo.test.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/api/test")
public class TestController4 {

  @ResponseBody
  @GetMapping("/t1")
  public String t1() {
    return "hi";
  }

  @ResponseBody
  @GetMapping("/t2")
  public String t2() {
    return "{ \"name\":\"홍길동\", \"age\":\"20\" }";
  }

  @ResponseBody
  @GetMapping("/t3")
  public Person t3() {

    Person p1 = new Person("홍길순", 30);

    return p1;
  }

  @ResponseBody
  @GetMapping("/t4/map")
  public Map<String, Person> map() {
    Map<String, Person> map = new HashMap<>();

    map.put("1", new Person("홍길동", 10));
    map.put("2", new Person("홍길서", 20));
    map.put("3", new Person("홍길남", 30));

    return map;
  }

  @ResponseBody
  @GetMapping("/t4/set")
  public Set<Person> set() {
    Set<Person> set = new HashSet<>();

    set.add(new Person("홍길동", 10));
    set.add(new Person("홍길서", 20));
    set.add(new Person("홍길남", 30));

    return set;
  }

  @ResponseBody
  @GetMapping("/t4/list1")
  public List<Person> list1() {
    List<Person> list = new ArrayList<>();

    list.add(new Person("홍길동", 10));
    list.add(new Person("홍길서", 20));
    list.add(new Person("홍길남", 30));

    return list;
  }

//  @ResponseBody
//  @GetMapping("/t4/list2")
//  public com.kh.demo.web.form.Test<List<Person>> list2() {
//
//    List<Person> list = new ArrayList<>();
//    list.add(new Person("홍길동", 10));
//    list.add(new Person("홍길서", 20));
//    list.add(new Person("홍길남", 30));
//
//    com.kh.demo.web.form.Test<List<Person>> test = new com.kh.demo.web.form.Test<>(list);
//
//    return test;
//  }
}
