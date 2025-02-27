package com.project.person.input.domain.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.project.person.domain.Address;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PersonRequest {

    @NotBlank(message = "The 'name' field is required")
    private String name;

    @NotNull(message = "The 'age' field is required")
    @Min(value = 16, message = "The 'age' field must be greater than 16")
    private String age;

    @CPF(message = "The 'cpf' field is invalid")
    @NotBlank(message = "The 'cpf' field is required")
    @Size(min = 11, max = 11, message = "The 'cpf' field must have 11 digits")
    private String cpf;

//    @NotEmpty(message = "The 'address' field is empty")
//    private List<Address> addresses;
    @NotEmpty(message = "The 'birth_date' field is required")
    private String birthDate;
}
