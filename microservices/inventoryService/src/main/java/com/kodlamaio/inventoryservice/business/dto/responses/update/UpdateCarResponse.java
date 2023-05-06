package com.kodlamaio.inventoryservice.business.dto.responses.update;

import com.kodlamaio.inventoryservice.entities.enums.CarState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarResponse {
    private UUID id;
    private UUID modelId;
    private int modelYear;
    private String plate;
    private CarState carState;
    private double dailyPrice;
}
