package com.example.dailymonitoring.models.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PieStatisticsData {

  @JsonProperty("year")
  private Long year;

  @JsonProperty("total")
  private Long total;

  @JsonProperty("perMonth")
  private MonthsData perMonth;

  @JsonProperty("progression")
  private Float progression;

}
