package com.miko.appsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailedData implements Serializable {

  private static final long serialVersionUID = -8964658883487451260L;
  @JsonProperty(value = "appName")
  private String name;
  @JsonProperty(value = "retryCount")
  private Integer retires;
  @JsonProperty(value = "errorMessage")
  private String errormessage;

  public FailedData(JsonObject entries) {
    this.name = entries.getString("name");
    this.retires = entries.getInteger("retires");
    this.errormessage = entries.getString("errormessage");
  }
}
