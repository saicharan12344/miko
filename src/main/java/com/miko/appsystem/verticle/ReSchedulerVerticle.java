package com.miko.appsystem.verticle;

import com.miko.appsystem.enums.StateEnum;
import com.miko.appsystem.utils.DbUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class ReSchedulerVerticle extends AbstractVerticle {

  @Override
  public void start() {
    final JDBCClient dbClient = DbUtils.buildDbClient(vertx);
    vertx.setPeriodic(50000, id -> scheduleErrorApps(dbClient));
  }

  private void scheduleErrorApps(JDBCClient dbClient) {
    System.out.println("Started ReScheduling error apps !!!!!");
    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        connection.query("SELECT * FROM apps WHERE state = " + StateEnum.ERROR.name(), res -> {
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
