package com.miko.appsystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;

public class NotificationVerticle extends AbstractVerticle {
  private MailClient mailClient;

  @Override
  public void start() {
    MailConfig config = new MailConfig()
      .setHostname("miko.appsystem.com")
      .setPort(587)
      .setUsername("sanagavarapu.sravanth2013@gmail.com")
      .setPassword("root123");

    mailClient = MailClient.createShared(vertx, config);

    vertx.eventBus().consumer("notify.failure", message -> {
      JsonObject app = (JsonObject) message.body();
      sendFailureNotification(app);
    });
  }

  private void sendFailureNotification(JsonObject app) {
    String appName = app.getString("name");

    mailClient.sendMail(new io.vertx.ext.mail.MailMessage()
      .setFrom("miko-noreply-appsystem@gmail.com")
      .setTo("sanagavarapu.sravanth2013@gmail.com")
      .setSubject("App Installation Failed")
      .setText("The installation of app " + appName + " failed after 3 attempts."), res -> {
      if (res.succeeded()) {
        System.out.println("Notification sent successfully.");
      } else {
        res.cause().printStackTrace();
      }
    });
  }
}
