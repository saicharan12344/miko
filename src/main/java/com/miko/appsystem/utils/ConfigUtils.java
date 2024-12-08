package com.miko.appsystem.utils;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class ConfigUtils {

  private static ConfigUtils instance;

  private final Properties properties;
  
  private ConfigUtils() {
    this.properties = readProperties();
  }

  public static ConfigUtils getInstance() {
    if (instance == null) {
      instance = new ConfigUtils();
    }

    return instance;
  }

  private Properties readProperties() {
    Properties properties = new Properties();

    try {
      try (InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties")) {
        try {
          properties.load(is);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return properties;
  }

}
