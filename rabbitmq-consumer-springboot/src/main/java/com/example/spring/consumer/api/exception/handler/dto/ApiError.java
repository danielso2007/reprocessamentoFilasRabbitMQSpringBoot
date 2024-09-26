package com.example.spring.consumer.api.exception.handler.dto;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Representa a mensagem de retorno de erro da API.")
public class ApiError {

    @Schema(description = "O código de erro HttpStatus.", requiredMode = RequiredMode.REQUIRED, example = "400")
    private Integer statusCode;
    @Schema(description = "O tipo de erro HttpStatus.", requiredMode = RequiredMode.REQUIRED, example = "400 BAD_REQUEST")
    private HttpStatus status;
    @Schema(description = "A mensagem de retorno de erro.", requiredMode = RequiredMode.REQUIRED,
            example = "Campo 'email' não pode ser vazio")
    private String message;
    @Schema(description = "O caminho do recurso.", requiredMode = RequiredMode.REQUIRED,
            example = "/api/usuarios")
    private String path;
    @Schema(description = "Data e hora do erro.", requiredMode = RequiredMode.REQUIRED,
            example = "2024-09-25T22:32:47.829Z", exampleClasses = LocalDateTime.class)
    private LocalDateTime timestamp;

    public ApiError() {
        // Construto padrão.
    }

    public ApiError(Integer statusCode, HttpStatus status, String message, String path,
            LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
    }

    public ApiError(HttpStatus status, String message, String path) {
        this.statusCode = status.value();
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String message, String path, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ApiError status(HttpStatus status) {
        setStatus(status);
        return this;
    }

    public ApiError message(String message) {
        setMessage(message);
        return this;
    }

    public ApiError path(String path) {
        setPath(path);
        return this;
    }

    public ApiError timestamp(LocalDateTime timestamp) {
        setTimestamp(timestamp);
        return this;
    }


    public Integer getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }


    public ApiError statusCode(Integer statusCode) {
        setStatusCode(statusCode);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ApiError)) {
            return false;
        }
        ApiError apiError = (ApiError) o;
        return Objects.equals(statusCode, apiError.statusCode)
                && Objects.equals(status, apiError.status)
                && Objects.equals(message, apiError.message) && Objects.equals(path, apiError.path)
                && Objects.equals(timestamp, apiError.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, status, message, path, timestamp);
    }

    @Override
    public String toString() {
        return "{" + " statusCode='" + getStatusCode() + "'" + ", status='" + getStatus() + "'"
                + ", message='" + getMessage() + "'" + ", path='" + getPath() + "'"
                + ", timestamp='" + getTimestamp() + "'" + "}";
    }

}
