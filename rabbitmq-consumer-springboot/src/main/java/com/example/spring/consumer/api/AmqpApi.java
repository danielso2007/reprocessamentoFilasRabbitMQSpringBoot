package com.example.spring.consumer.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.spring.consumer.api.exception.handler.dto.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Repost", description = "Report da mensagem da fila.")
public interface AmqpApi {

    @Operation(summary = "Report da mensagem da fila.", description = "É enviado um texto para a fila. Esse método não tem retorno.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorno ok."),
        @ApiResponse(responseCode = "400", description = "Erro de validações.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
        @ApiResponse(responseCode = "500", description = "Erro interno na API.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/repost")
    void sendToQueue();

}
