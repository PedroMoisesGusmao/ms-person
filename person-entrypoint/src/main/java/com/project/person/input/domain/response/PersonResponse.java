package com.project.person.input.domain.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PersonResponse {
    @Schema(name = "id", description = "The unique ID of the person", example = "67cf1cc9e5f27cca892a6d41")
    private int id;

    @Schema(name = "name", description = "The name of the person", example = "John Doe")
    private String name;

    @Schema(name = "cpf", description = "The CPF of the person (must be a valid cpf)", example = "09457463005")
    private String cpf;

    @Schema(name = "address", description = "The address of the person")
    private AddressResponse address;

    @Schema(name = "cpf", description = "The birth date of the person (must be a valid date)", example = "1991-01-01")
    private LocalDate birthDate;
}
