package com.miko.appsystem.utils;

import io.vertx.core.Vertx;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;

import java.util.Properties;

public class MailUtils {
  private static final String HOST_CONFIG = "mail.host";
  private static final String PORT_CONFIG = "mail.port";
  private static final String USERNAME_CONFIG = "mail.username";
  private static final String PASSWORD_CONFIG = "mail.password";

  private MailUtils() {

  }

  public static MailClient buildMailClient(Vertx vertx) {
    final Properties properties = ConfigUtils.getInstance().getProperties();
    MailConfig config = new MailConfig()
      .setHostname(properties.getProperty(HOST_CONFIG))
      .setPort(Integer.parseInt(properties.getProperty(PORT_CONFIG)))
      .setUsername(properties.getProperty(USERNAME_CONFIG))
      .setPassword(properties.getProperty(PASSWORD_CONFIG));
    return MailClient.createShared(vertx, config);
  }
}
