package com.miko.appsystem.verticle;

import com.miko.appsystem.enums.StateEnum;
import com.miko.appsystem.utils.DbUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class SchedulerVerticle extends AbstractVerticle {

  @Override
  public void start() {
    final JDBCClient dbClient = DbUtils.buildDbClient(vertx);
    vertx.setPeriodic(15000, id -> scheduleApps(dbClient));
  }

  private void scheduleApps(JDBCClient dbClient) {
    System.out.println("Started Scheduling!!!!!");
    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        connection.query("SELECT * FROM apps WHERE state = " + StateEnum.SCHEDULED.name(), res -> {
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
