package com.miko.appsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class App implements Serializable {
  private Integer id;
  private String name;
  private String state;
  private String errormessage;
  private Integer retires;
}
