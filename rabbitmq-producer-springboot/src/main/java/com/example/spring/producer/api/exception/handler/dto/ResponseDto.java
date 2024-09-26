package com.example.spring.producer.api.exception.handler.dto;

import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa os dados de retorno REST.")
public class ResponseDto<T> {

    @Schema(description = "StackTrace do erro", example = "{stackTrace : []}")
    private T data;
    @Schema(description = "Lista de errors", example = "Descrição do erro")
    private List<Object> errors = new ArrayList<>();
    @Schema(description = "Indicar se é um retorno de validação", example = "true/false")
    private Boolean validation = false;
    @Schema(description = "Indicar se é uma mensagem de ativação de login",
        example = "Um e-mail de ativação foi enviado.")
    private Boolean ativacao;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public ResponseDto<T> data(T data) {
        setData(data);
        return this;
    }

    public ResponseDto<T> errors(List<Object> errors) {
        setErrors(errors);
        return this;
    }

    public void addError(String error) {
        getErrors().add(error);
    }

    public Boolean isValidation() {
        return validation;
    }

    public void setValidation(Boolean validation) {
        this.validation = validation;
    }

    public Boolean getAtivacao() {
        return ativacao;
    }

    public void setAtivacao(Boolean ativacao) {
        this.ativacao = ativacao;
    }
}
