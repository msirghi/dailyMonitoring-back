package com.example.dailyMonitoring.models;

import com.example.dailyMonitoring.models.enums.StatusType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@ApiModel
@Builder
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class EmailData {

    @JsonProperty("email")
    @NotNull
    private String email;

}
