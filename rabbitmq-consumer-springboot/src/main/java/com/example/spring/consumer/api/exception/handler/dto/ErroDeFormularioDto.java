package com.example.spring.consumer.api.exception.handler.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Responsável por apresentar erros de retorno de um requisição.")
public class ErroDeFormularioDto {

    @Schema(description = "Nome do campos.", example = "titulo")
    private String campo;

    @Schema(description = "A descrição do erro.", example = "O título é obrigatório.")
    private String erro;

    public ErroDeFormularioDto(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }

}
