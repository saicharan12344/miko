package com.miko.appsystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class SchedulerVerticle extends AbstractVerticle {
  private JDBCClient dbClient;

  @Override
  public void start() {
    dbClient = JDBCClient.createShared(vertx, new JsonObject()
      .put("url", "jdbc:mysql://localhost:3306/miko")
      .put("driver_class", "com.mysql.cj.jdbc.Driver")
      .put("user", "root")
      .put("password", "password"));

    vertx.setPeriodic(5000, id -> scheduleApps());
  }

  private void scheduleApps() {
    System.out.println("Started Scheduling!!!!!");
    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        connection.query("SELECT * FROM apps WHERE state = 'SCHEDULED'", res -> {
          if (res.succeeded()) {
            res.result().getRows().forEach(app -> {
              vertx.eventBus().send("install.app", app);
            });
          }
          connection.close();
        });
      }
    });
  }
}
