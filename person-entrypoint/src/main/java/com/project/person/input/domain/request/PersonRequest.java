package com.project.person.input.domain.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PersonRequest {

    @Schema(name = "name", description = "The name of the person", example = "John Doe")
    @NotBlank(message = "The 'name' field is required")
    private String name;

    @Schema(name = "cpf", description = "The CPF of the person (must be a valid cpf)", example = "09457463005")
    @CPF(message = "The 'cpf' field is invalid")
    @NotBlank(message = "The 'cpf' field is required")
    @Size(min = 11, max = 11, message = "The 'cpf' field must have 11 digits")
    private String cpf;

    @Schema(name = "email", description = "The email of the person (must be a valid email)", example = "example@example.com")
    @Email(message = "The 'email' field is invalid")
    @NotBlank(message = "The 'email' field is required")
    private String email;

    @Schema(name = "zip_code", description = "The zip code of the person", example = "04568020")
    @NotBlank(message = "The 'zipCode' field cannot be empty")
    @Size(min = 8, max = 9, message = "The 'zip_code' field must have 8 digits")
    private String zipCode;

    @Schema(name = "birth_date", description = "The birth date of the person", example = "2008-11-04")
    @NotNull(message = "The 'birth_date' field is required")
    private LocalDate birthDate;
}
