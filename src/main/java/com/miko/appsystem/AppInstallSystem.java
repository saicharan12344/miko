package com.miko.appsystem;

import io.vertx.core.Vertx;

public class AppInstallSystem {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new SchedulerVerticle());
    vertx.deployVerticle(new InstallerVerticle());
    vertx.deployVerticle(new NotificationVerticle());
  }
}
