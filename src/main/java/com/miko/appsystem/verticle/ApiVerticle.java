package com.miko.appsystem.verticle;

import com.miko.appsystem.enums.StateEnum;
import com.miko.appsystem.model.FailedData;
import com.miko.appsystem.utils.DbUtils;
import com.miko.appsystem.utils.ResponseUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.List;

public class ApiVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(ApiVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    final JDBCClient dbClient = DbUtils.buildDbClient(vertx);
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.route("/").handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      response
        .putHeader("content-type", "text/html")
        .end("<h1>Hello from Miko App Installation application</h1>");
    });
    router.route("/failed/data").handler(rc -> {
      dbClient.getConnection(ar -> {
        if (ar.succeeded()) {
          SQLConnection connection = ar.result();
          connection.query("select name, retries, errormessage from app where state = " + StateEnum.ERROR.name() + " or retires != 0 ", res -> {
            if (res.succeeded()) {
              List<FailedData> resultSet = res.result().getRows().stream().map(FailedData::new).toList();
              ResponseUtils.buildOkResponse(rc, resultSet);
            } else {
              ResponseUtils.buildErrorResponse(rc, "error while fetching failed-data");
            }
            connection.close();
          });
        }
      });
    });
    vertx.createHttpServer().requestHandler(router).listen(8080).onComplete(http -> {
      if (http.succeeded()) {
        startPromise.complete();
        logger.info("HTTP server started on port 8080");
      } else {
        startPromise.fail(http.cause());
        logger.error("Api Server failed to Startup");
      }
    });
  }
}
