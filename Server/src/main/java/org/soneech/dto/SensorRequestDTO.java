package org.soneech.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SensorRequestDTO {
    @NotNull
    @Size(min = 3, max = 30, message = "имя должно содержать от 3 до 30 символов")
    private String name;
}
