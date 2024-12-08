package com.miko.appsystem.verticle;

import com.miko.appsystem.utils.DbUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class DatabaseStateVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(DatabaseStateVerticle.class);

  @Override
  public void start() {
    final JDBCClient dbClient = DbUtils.buildDbClient(vertx);
    vertx.eventBus().consumer("update.state", message -> {
      JsonObject update = (JsonObject) message.body();
      String appId = update.getString("id");
      String state = update.getString("state");
      dbClient.getConnection(ar -> {
        if (ar.succeeded()) {
          SQLConnection connection = ar.result();
          logger.info("updating the status of app : " + appId + " to : " + state);
          connection.query("update app set state = " + state + " where id = " + appId, res -> {
            if (res.succeeded()) {
              logger.info("App ID: " + appId + " State: " + state);
            }
            connection.close();
          });
        }
      });
    });
  }
}
