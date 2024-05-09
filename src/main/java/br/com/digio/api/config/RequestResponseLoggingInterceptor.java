package br.com.digio.api.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

@Override
	public ClientHttpResponse intercept(org.springframework.http.HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {
		logRequest(request, body);
	    ClientHttpResponse response = execution.execute(request, body);
	    logResponse(response);
	    return response;
	}
  private void logRequest(HttpRequest request, byte[] body) throws IOException {
    if (log.isInfoEnabled()) {

      // pretty print
      String requestBody = null;
      if (MediaType.APPLICATION_JSON.equals(request.getHeaders().getContentType())) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        Object json;
        try {
          json = mapper.readValue(body, Map.class);
        } catch (MismatchedInputException e) {
          json = mapper.readValue(body, List.class);
        }
        requestBody = mapper.writeValueAsString(json);
      } else {
        requestBody = new String(body, StandardCharsets.UTF_8);
      }

      log.info(
          "===========================request begin================================================");
      log.info("URI         : {}", request.getURI());
      log.info("Method      : {}", request.getMethod());
      log.info("Headers     : {}", request.getHeaders());
      log.info("Request body: \n{}", requestBody);
      log.info(
          "==========================request end================================================");
    }
  }

  private void logResponse(ClientHttpResponse response) throws IOException {

    // pretty print
    String responseBody = null;
    if (MediaType.APPLICATION_JSON.equals(response.getHeaders().getContentType())) {
      ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
      Object json = null;
      try {
        json = mapper.readValue(
            StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()), Map.class);
      } catch (MismatchedInputException e) {
        try {
          json = mapper.readValue(
              StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()), List.class);
        } catch (Exception e2) {
          log.error("Erro na conversão do response");
        }
      }
      if (json == null) {
        responseBody = null;
      } else {
        responseBody = mapper.writeValueAsString(json);
      }
    } else {
      responseBody = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
    }

    if (log.isInfoEnabled()) {
      log.info(
          "============================response begin==========================================");
      log.info("Status code  : {}", response.getStatusCode());
      log.info("Status text  : {}", response.getStatusText());
      log.info("Headers      : {}", response.getHeaders());
      log.info("Response body: \n{}", responseBody);
      log.info(
          "=======================response end=================================================");
    }
  }
}
