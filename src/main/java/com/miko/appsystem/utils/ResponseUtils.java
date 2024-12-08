package com.miko.appsystem.utils;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ResponseUtils {

  private static final String CONTENT_TYPE_HEADER = "Content-Type";
  private static final String APPLICATION_JSON = "application/json";

  private ResponseUtils() {

  }

  public static void buildOkResponse(RoutingContext rc,
                                     Object response) {
    rc.response()
      .setStatusCode(200)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(Json.encodePrettily(response));
  }

  public static void buildErrorResponse(RoutingContext rc, String message) {
    rc.response()
      .setStatusCode(500)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(new JsonObject().put("error", message).encodePrettily());
  }

}
