package com.example.spring.producer.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.spring.producer.api.exception.handler.dto.ApiError;
import com.example.spring.producer.dto.MessageQueue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Message Queue", description = "Envio de texto para a fila.")
public interface AmqpApi {
    
    @Operation(summary = "Enviar um texto para a fila.", description = "É enviado um texto para a fila. Esse método não tem retorno.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "O json a ser enviado.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageQueue.class))}),
        @ApiResponse(responseCode = "400", description = "Erro de validações.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
        @ApiResponse(responseCode = "500", description = "Erro interno na API.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/send", produces = { "application/json;charset=utf-8" })
    void sendToConsumer(@Valid @RequestBody final MessageQueue message);

}
