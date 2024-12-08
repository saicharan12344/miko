package com.miko.appsystem.verticle;

import com.miko.appsystem.utils.MailUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mail.MailClient;

public class NotificationVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(NotificationVerticle.class);

  @Override
  public void start() {
    final MailClient mailClient = MailUtils.buildMailClient(vertx);
    vertx.eventBus().consumer("notify.failure", message -> {
      JsonObject app = (JsonObject) message.body();
      sendFailureNotification(app, mailClient);
    });
  }

  private void sendFailureNotification(JsonObject app, MailClient mailClient) {
    String appName = app.getString("name");
    mailClient.sendMail(new io.vertx.ext.mail.MailMessage()
      .setFrom("miko-noreply-appsystem@gmail.com")
      .setTo("sanagavarapu.sravanth2013@gmail.com")
      .setSubject("App Installation Failed")
      .setText("The installation of app " + appName + " failed after 3 attempts."), res -> {
      if (res.succeeded()) {
        logger.info("Notification sent successfully.");
      } else {
        logger.error("error occurred while sending email notification");
        res.cause().printStackTrace();
      }
    });
  }
}
