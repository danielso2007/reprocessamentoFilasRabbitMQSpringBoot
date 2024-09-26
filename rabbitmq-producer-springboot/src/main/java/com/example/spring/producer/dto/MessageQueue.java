package com.example.spring.producer.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Representa a mensagem de texto enviada para a API")
public class MessageQueue {

    @NotBlank(message = "O texto não pode ser vazio.")
    @Size(min = 0, max = 255,
            message = "O texto precisa ter no mínimo 1 ou no máximo 255 caracteres.")
    @Schema(description = "A mensagem de texto que será enviada.")
    private String text;

    public MessageQueue() {
        // Construtor padrão.
    }

    public MessageQueue(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MessageQueue text(String text) {
        setText(text);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MessageQueue)) {
            return false;
        }
        MessageQueue messageQueue = (MessageQueue) o;
        return Objects.equals(text, messageQueue.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "{" + " text='" + getText() + "'" + "}";
    }

}
