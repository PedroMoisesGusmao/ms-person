package com.project.person.input.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddressResponse {

    @Schema(name = "zipCode", description = "The zip code of the address", example = "12345678")
    private String zipCode;

    @Schema(name = "thoroughfare", description = "The thoroughfare of the address", example = "Rua dos Bobos")
    private String thoroughfare;

    @Schema(name = "complement", description = "The address complement", example = "Apto 123")
    private String complement;

    @Schema(name = "neighborhood", description = "The neighborhood of the address", example = "Vila dos Devs")
    private String neighborhood;

    @Schema(name = "state", description = "The state of the address", example = "Bahia")
    private String state;
}
