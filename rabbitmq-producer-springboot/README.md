# Swagger

http://localhost:8181/api-docs
http://localhost:8181/swagger-ui/index.html


# Estrutura Padrão de Retorno de Erro

Status HTTP: O código de status HTTP é essencial e deve refletir corretamente o tipo de erro. Exemplos:

- 400 Bad Request: Para erros de validação de entrada.
- 401 Unauthorized: Para falhas de autenticação.
- 403 Forbidden: Para tentativas de acesso não autorizadas.
- 404 Not Found: Quando um recurso não é encontrado.
- 500 Internal Server Error: Para erros do servidor.

Corpo da Resposta de Erro: A resposta deve ter um corpo bem estruturado em JSON, contendo:

- timestamp: O momento em que o erro ocorreu.
- status: O código de status HTTP.
- error: A descrição do erro (p. ex., "Bad Request", "Not Found").
- message: Uma mensagem detalhada explicando a causa do erro.
- path: O caminho da requisição que gerou o erro.

```json
{
  "timestamp": "2024-09-23T12:45:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Campo 'email' não pode ser vazio",
  "path": "/api/usuarios"
}
```