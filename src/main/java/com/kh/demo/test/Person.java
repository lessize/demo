package com.kh.demo.test;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Person {
  private String name;
  private int age;

  public void smile(){
    System.out.println("웃다");
  }
  public void eat(){
    System.out.println("먹다");
  }
}
