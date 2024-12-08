package com.miko.appsystem.utils;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;

import java.util.Properties;

public class DbUtils {

  private static final String HOST_CONFIG = "datasource.host";
  private static final String PORT_CONFIG = "datasource.port";
  private static final String DATABASE_CONFIG = "datasource.database";
  private static final String USERNAME_CONFIG = "datasource.username";
  private static final String PASSWORD_CONFIG = "datasource.password";
  private static final String DRIVER_CONFIG = "datasource.driver";

  private DbUtils() {

  }

  public static JDBCClient buildDbClient(Vertx vertx) {
    final Properties properties = ConfigUtils.getInstance().getProperties();
    final String url = "jdbc:mysql://" + properties.getProperty(HOST_CONFIG) + ":" + properties.getProperty(PORT_CONFIG) + "/" + properties.getProperty(DATABASE_CONFIG);
    return JDBCClient.createShared(vertx, new JsonObject()
      .put("url", url)
      .put("driver_class", properties.getProperty(DRIVER_CONFIG))
      .put("user", properties.getProperty(USERNAME_CONFIG))
      .put("password", properties.getProperty(PASSWORD_CONFIG)));
  }

}
