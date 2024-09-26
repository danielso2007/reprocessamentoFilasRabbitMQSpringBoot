package com.example.spring.consumer.api.exception.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.example.spring.consumer.api.exception.handler.dto.ApiError;
import com.example.spring.consumer.api.exception.handler.dto.ErroDeFormularioDto;
import com.example.spring.consumer.api.exception.handler.dto.ResponseDto;
import com.example.spring.consumer.api.exception.lang.ResourceNotFoundException;
import com.example.spring.consumer.api.exception.lang.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GlobalControllerExceptionHandler {

    private final MessageSource messageSource;


    public GlobalControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDto<MethodArgumentNotValidException> handle(MethodArgumentNotValidException ex,
            WebRequest request) {
        List<ApiError> dto = new ArrayList<>();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ApiError erro =
                    new ApiError(HttpStatus.NOT_FOUND, mensagem, request.getDescription(false));
            dto.add(erro);
        });

        ResponseDto<MethodArgumentNotValidException> response = new ResponseDto<>();
        response.setData(null);
        response.setValidation(true);
        response.getErrors().add(dto);
        return response;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<ErroDeFormularioDto> constraintViolationExceptionHander(
            ConstraintViolationException ex) {
        return buildValidationErrors(ex.getConstraintViolations());
    }

    private List<ErroDeFormularioDto> buildValidationErrors(
            Set<ConstraintViolation<?>> violations) {
        return violations.stream().map(violation -> new ErroDeFormularioDto(
                Objects.requireNonNull(
                        StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                                .reduce((first, second) -> second).orElse(null))
                        .toString(),
                violation.getMessage())).collect(Collectors.toList());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex,
            WebRequest request) {
        ApiError apiError =
                new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro inesperado. Entre em contato com o suporte", request.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(criarResponse(ex.getMessage(), true));
    }

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleConnversion(RuntimeException ex) {
        return ResponseEntity.badRequest().body(criarResponse(ex.getMessage()));
    }

    protected ResponseDto<?> criarResponse(String message) {
        return criarResponse(null, message, null);
    }

    protected ResponseDto<?> criarResponse(Exception e, String message, Boolean validation) {
        ResponseDto<Exception> response = new ResponseDto<>();
        response.setData(e != null ? e : null);
        response.setValidation(validation);
        response.getErrors().add(message);
        return response;
    }

    protected ResponseDto<?> criarResponse(String message, Boolean validation) {
        return criarResponse(null, message, validation);
    }

}
