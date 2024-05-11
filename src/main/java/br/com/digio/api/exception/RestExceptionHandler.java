package br.com.digio.api.exception;

import static br.com.digio.api.utils.Utils.REGISTRO_NAO_LOCALIZADO_MESSAGE;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.everit.json.schema.ValidationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.digio.api.dto.ApiErrorDTO;
import br.com.digio.api.dto.ValidationErrorResponseDTO;



@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {

  Logger logger = LogManager.getLogger(RestExceptionHandler.class);

  private ResponseEntity<ApiErrorDTO> buildResponseEntityApiError(ApiErrorDTO apiError) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new ResponseEntity<>(apiError, headers, apiError.getStatus());
  }

  @ExceptionHandler({NoSuchElementException.class})
  protected ResponseEntity<ApiErrorDTO> handleEntityNotFound(Exception ex) {
	logger.warn(REGISTRO_NAO_LOCALIZADO_MESSAGE);

	ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.NOT_FOUND);
	apiError.setErrorCode(HttpStatus.NOT_FOUND.value());
    apiError.setMessage(REGISTRO_NAO_LOCALIZADO_MESSAGE);
    return buildResponseEntityApiError(apiError);
  }

  @ExceptionHandler({IOException.class, JsonProcessingException.class, Exception.class})
  protected ResponseEntity<ApiErrorDTO> handleIOException(Exception ex) {
    logger.catching(ex);
    ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR);
    apiError.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    apiError.setMessage(ex.getMessage());
    return buildResponseEntityApiError(apiError);
  }

  @ExceptionHandler(HttpClientErrorException.class)
  protected ResponseEntity<ApiErrorDTO> handleException(HttpClientErrorException ex) {
    logger.catching(ex);

    ApiErrorDTO apiError = new ApiErrorDTO(ex.getStatusCode());
    apiError.setErrorCode(ex.getStatusCode().value());
    apiError.setMessage(ex.getMessage());
    apiError.setDebugMessage(ex.getResponseBodyAsString());
    return buildResponseEntityApiError(apiError);
  }
  
  @ExceptionHandler(ApiException.class)
  protected ResponseEntity<ApiErrorDTO> handleException(ApiException ex) {
	  logger.warn(ex.getMessage());
	  
	  ApiErrorDTO apiError = new ApiErrorDTO(ex.getCode());
	  apiError.setErrorCode(ex.getCode().value());
	  apiError.setMessage(ex.getMessage());
	  apiError.setDebugMessage(ex.getResponseBody());
	  return buildResponseEntityApiError(apiError);
  }

  @ExceptionHandler(ValidationException.class)
  protected ResponseEntity<ValidationErrorResponseDTO> handleException(ValidationException ex) {
    org.springframework.util.MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add("content-type", "application/json");
    logger.catching(ex);
    
    ValidationErrorResponseDTO validationErrorResponse = subErrosExtract(ex.toJSON());
    
    return new ResponseEntity<>(validationErrorResponse, headers, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(ParseException.class)
  protected ResponseEntity<ApiErrorDTO> handldParseException(ParseException ex) {
    org.springframework.util.MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add("content-type", "application/json");
    logger.catching(ex);

    ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.BAD_REQUEST);
    apiError.setErrorCode(HttpStatus.BAD_REQUEST.value());
    apiError.setMessage(ex.getMessage());
    return buildResponseEntityApiError(apiError);
  }

  private ValidationErrorResponseDTO subErrosExtract(JSONObject causingException) {
    ValidationErrorResponseDTO validationSubErrorResponse = new ValidationErrorResponseDTO();

    validationSubErrorResponse
        .setPointerToViolation(causingException.getString("pointerToViolation"));
    validationSubErrorResponse.setMessage(causingException.getString("message"));

    if (causingException.has("keyword")) {
      validationSubErrorResponse.setKeyword(causingException.getString("keyword"));
    } else if (causingException.has("causingExceptions")) {
      JSONArray jsonArray = causingException.getJSONArray("causingExceptions");
      validationSubErrorResponse.setSubErros(new ArrayList<>());
      for (Object object : jsonArray) {
        validationSubErrorResponse.getSubErros().add(subErrosExtract((JSONObject) object));
      }
    }

    return validationSubErrorResponse;
  }

  @ResponseBody
  @ExceptionHandler({HttpServerErrorException.class})
  protected ResponseEntity<ApiErrorDTO> handleException(HttpServerErrorException ex) {
    logger.catching(ex);

    ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.SERVICE_UNAVAILABLE);
    apiError.setErrorCode(HttpStatus.SERVICE_UNAVAILABLE.value());
    apiError.setMessage(ex.getMessage());
    apiError.setDebugMessage(ex.getResponseBodyAsString());
    return buildResponseEntityApiError(apiError);
  }

  @ResponseBody
  @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
  protected ResponseEntity<ApiErrorDTO> handleException(HttpMediaTypeNotSupportedException ex) {
    logger.catching(ex);

    ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    apiError.setErrorCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    apiError.setMessage(ex.getMessage());
    return buildResponseEntityApiError(apiError);
  }
}
