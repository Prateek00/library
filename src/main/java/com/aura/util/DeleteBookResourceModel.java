package com.aura.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DeleteBookResourceModel {
    @JsonProperty("message")
    @Schema(description = "Message indicating the result of the delete operation", example = "Book with ID 1 has been deleted successfully.")
    private String message;
    @JsonProperty("id")
    @Schema(description = "ID of the deleted book", example = "1")
    private Long id;
}
