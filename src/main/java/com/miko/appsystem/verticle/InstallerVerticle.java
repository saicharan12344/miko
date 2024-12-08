package com.miko.appsystem.verticle;

import com.miko.appsystem.enums.StateEnum;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;

public class InstallerVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(InstallerVerticle.class);

  @Override
  public void start() {
    vertx.eventBus().consumer("install.app", message -> {
      JsonObject app = (JsonObject) message.body();
      installApp(app);
    });
  }

  private void installApp(JsonObject app) {
    String appId = app.getString("id");
    int retries = app.getInteger("retries", 0);

    updateAppState(appId, StateEnum.PICKEDUP.name());
    vertx.setTimer(2000, id -> {
      boolean success = simulateInstallation(app);

      if (success) {
        updateAppState(appId, StateEnum.COMPLETED.name());
      } else if (retries < 3) {
        logger.info("retry count increased to :" + retries + 1 + " of app : " + appId);
        app.put("retries", retries + 1);
        vertx.eventBus().send("install.app", app);
      } else {
        logger.error("app installation failed for app : " + appId);
        app.put("errormessage", "app installation failed");
        updateAppState(appId, StateEnum.ERROR.name());
        vertx.eventBus().send("notify.failure", app);
      }
    });
  }

  private boolean simulateInstallation(JsonObject app) {
    return Math.random() > 0.3;
  }

  private void updateAppState(String appId, String state) {
    vertx.eventBus().send("update.state", new JsonObject().put("id", appId).put("state", state));
  }
}
