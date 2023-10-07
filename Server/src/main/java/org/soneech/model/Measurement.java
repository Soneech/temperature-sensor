package org.soneech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
@NoArgsConstructor
@Getter
@Setter
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Max(value = 100)
    @Min(value = -100)
    private Double value;

    @NotNull
    private Boolean raining;

    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    @JsonIgnore
    private Sensor sensor;

    @Override
    public String toString() {
        return String.format("Measurement(id=%d, value=%f, raining=%b, createdAt=%s",
                id, value, raining, createdAt == null ? null : createdAt.toString());
    }
}
