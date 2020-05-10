package com.example.dailymonitoring.models.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthsData {

  @JsonProperty("january")
  private Long january = 0L;

  @JsonProperty("february")
  private Long february = 0L;

  @JsonProperty("march")
  private Long march = 0L;

  @JsonProperty("april")
  private Long april = 0L;

  @JsonProperty("may")
  private Long may = 0L;

  @JsonProperty("june")
  private Long june = 0L;

  @JsonProperty("july")
  private Long july = 0L;

  @JsonProperty("august")
  private Long august = 0L;

  @JsonProperty("september")
  private Long september = 0L;

  @JsonProperty("october")
  private Long october = 0L;

  @JsonProperty("november")
  private Long november = 0L;

  @JsonProperty("december")
  private Long december = 0L;

}
