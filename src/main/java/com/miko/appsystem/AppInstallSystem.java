package com.miko.appsystem;

import com.miko.appsystem.verticle.*;
import io.vertx.core.Vertx;

public class AppInstallSystem {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new SchedulerVerticle());
    vertx.deployVerticle(new InstallerVerticle());
    vertx.deployVerticle(new NotificationVerticle());
    vertx.deployVerticle(new ReSchedulerVerticle());
    vertx.deployVerticle(new ApiVerticle());
    vertx.deployVerticle(new DatabaseStateVerticle());
  }
}
