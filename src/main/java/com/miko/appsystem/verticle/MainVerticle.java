package com.miko.appsystem.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    router.route("/").handler(BodyHandler.create());
    /*router.route("/items").handler(rc -> {
      vertx.eventBus().<JsonArray>request(SERVICE_ADDRESS, new JsonObject(), reply -> {
        if (reply.succeeded()) {
          rc.response().end(reply.result().body().encode());
        } else {
          rc.fail(500);
        }
      });
    });*/
    vertx.createHttpServer().requestHandler(router).listen(8888).onComplete(http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
