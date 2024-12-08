package com.miko.appsystem;

import com.miko.appsystem.verticle.InstallerVerticle;
import com.miko.appsystem.verticle.NotificationVerticle;
import com.miko.appsystem.verticle.ReSchedulerVerticle;
import com.miko.appsystem.verticle.SchedulerVerticle;
import io.vertx.core.Vertx;

public class AppInstallSystem {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new SchedulerVerticle());
    vertx.deployVerticle(new InstallerVerticle());
    vertx.deployVerticle(new NotificationVerticle());
    vertx.deployVerticle(new ReSchedulerVerticle());
  }
}
