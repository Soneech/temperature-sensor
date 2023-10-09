package org.soneech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementResponseDTO {
    private Long id;
    private Double value;
    private Boolean raining;

    @JsonProperty("sensor_id")
    private Long sensorId;
}
