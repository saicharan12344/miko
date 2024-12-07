package com.miko.appsystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class DatabaseStateVerticle extends AbstractVerticle {
  @Override
  public void start() {
    vertx.eventBus().consumer("update.state", message -> {
      JsonObject update = (JsonObject) message.body();
      String appId = update.getString("id");
      String state = update.getString("state");

      // Log or update state in the database (mock implementation here)
      System.out.println("App ID: " + appId + " State: " + state);
    });
  }
}
