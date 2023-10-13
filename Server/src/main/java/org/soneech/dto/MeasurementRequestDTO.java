package org.soneech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MeasurementRequestDTO {
    @NotNull
    @Max(value = 100, message = "температура не может быть выше 100 градусов")
    @Min(value = -100, message = "температура не может быть ниже -100 градусов")
    private Double value;

    @NotNull
    private Boolean raining;

    @NotNull
    @JsonProperty("sensor_id")
    private Long sensorId;
}
