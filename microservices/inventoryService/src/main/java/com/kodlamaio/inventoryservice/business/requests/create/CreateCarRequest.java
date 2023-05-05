package com.kodlamaio.inventoryservice.business.requests.create;

import com.kodlamaio.inventoryservice.entities.enums.CarState;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRequest {
    @NotNull
    @NotBlank
    private UUID modelId;
    @Min(value = 1960)
    private int modelYear;
    @NotNull
    @NotBlank
    private String plate;
    @NotNull
    @NotBlank
    private CarState carState;
    @Min(value = 1)
    private double dailyPrice;
}
