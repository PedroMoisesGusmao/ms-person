package com.project.person.input.controller.contract;

import com.project.person.input.domain.request.PersonRequest;
import com.project.person.input.domain.response.PersonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Tag(name = "Person Controller")
public interface PersonContract {
    @Operation(summary = "Fetch Person", description = "Get Person")
    @Parameter(name = "id", description = "Person ID", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "An bad request occurred"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")})
    ResponseEntity<PersonResponse> fetch(final String id);

    @Operation(summary = "Save Person", description = "Save a new person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "An bad request occurred"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")})
    ResponseEntity<PersonResponse> save(final PersonRequest request);

    @Operation(summary = "Update Person", description = "Update a existing person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update was realized successfully"),
            @ApiResponse(responseCode = "400", description = "An bad request occurred (maybe a non-existent field was passed)"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @Parameter(name = "id", description = "The person id")
    @RequestBody(description = "The exacts field that are supposed to be updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(
                    example = "{\n\t\"email\": \"example@example.com\",\n\t\"zip_code\": \"04568020\"\n}")))
    ResponseEntity<PersonResponse> update(final String id, final Map<String, Object> request);
}
