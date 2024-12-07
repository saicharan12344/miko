package com.miko.appsystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class InstallerVerticle extends AbstractVerticle {
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

    updateAppState(appId, "PICKEDUP");
    vertx.setTimer(2000, id -> {
      boolean success = simulateInstallation(app);

      if (success) {
        updateAppState(appId, "COMPLETED");
      } else if (retries < 3) {
        app.put("retries", retries + 1);
        vertx.eventBus().send("install.app", app);
      } else {
        updateAppState(appId, "ERROR");
        vertx.eventBus().send("notify.failure", app);
      }
    });
  }

  private boolean simulateInstallation(JsonObject app) {
    // Simulate success or failure
    return Math.random() > 0.3;
  }

  private void updateAppState(String appId, String state) {
    vertx.eventBus().send("update.state", new JsonObject().put("id", appId).put("state", state));
  }
}
