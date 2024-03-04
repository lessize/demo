package com.kh.demo.web.form;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@AllArgsConstructor
@Getter
class Person {
  String name;
  int age;
}

@AllArgsConstructor
@Getter
class Test<T> {
  T data;
}

