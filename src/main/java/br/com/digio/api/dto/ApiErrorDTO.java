package br.com.digio.api.dto;

import java.util.Base64;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiErrorDTO {

  private HttpStatus status;
  private String errorCode;
  private String message;
  private String debugMessage;
  private List<ApiSubErrorDTO> subErrors;

  private ApiErrorDTO() {}

  public ApiErrorDTO(HttpStatus status) {
    this();
    this.status = status;
  }

  ApiErrorDTO(HttpStatus status, Throwable ex) {
    this();
    this.status = status;
    this.message = "Unexpected error";
    this.debugMessage = ex.getLocalizedMessage();
  }

  public ApiErrorDTO(HttpStatus status, String message, Throwable ex) {
    this();
    this.status = status;
    this.message = message;
    this.debugMessage = ex.getLocalizedMessage();
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public HttpStatus getStatus() {
    return this.status;
  }

  public String getDebugMessage() {
    return debugMessage != null ? new String(Base64.getEncoder().encode(debugMessage.getBytes()))
        : null;
  }

  public void setDebugMessage(String debugMessage) {
    this.debugMessage = debugMessage;
  }

  public List<ApiSubErrorDTO> getSubErrors() {
    return subErrors;
  }

  public void setSubErrors(List<ApiSubErrorDTO> subErrors) {
    this.subErrors = subErrors;
  }

  public String getMessage() {
    return message;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }
}
