package br.com.digio.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationErrorResponseDTO {

  @JsonProperty("schemaLocation")
  private String schemaLocation;
  @JsonProperty("pointerToViolation")
  private String pointerToViolation;
  @JsonProperty("causingExceptions")
  ArrayList<Object> causingExceptions;
  @JsonProperty("keyword")
  private String keyword;
  @JsonProperty("message")
  private String message;
  @JsonProperty("subErrors")
  private List<ValidationErrorResponseDTO> subErros;

  public String getSchemaLocation() {
    return schemaLocation;
  }

  public String getPointerToViolation() {
    return pointerToViolation;
  }

  public String getKeyword() {
    return keyword;
  }

  public String getMessage() {
    return message;
  }

  public void setSchemaLocation(String schemaLocation) {
    this.schemaLocation = schemaLocation;
  }

  public void setPointerToViolation(String pointerToViolation) {
    this.pointerToViolation = pointerToViolation;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<ValidationErrorResponseDTO> getSubErros() {
    return subErros;
  }

  public void setSubErros(List<ValidationErrorResponseDTO> subErros) {
    this.subErros = subErros;
  }
}
