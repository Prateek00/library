package com.aura.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("success")
public class CreateBookResourceModel {
    @JsonProperty("location")
    @Schema(description = "URL of the created book resource", example = "/api/books/1")
    private String location;

    @JsonProperty("id")
    @Schema(description = "ID of the created book", example = "1")
    private Long id;
}
