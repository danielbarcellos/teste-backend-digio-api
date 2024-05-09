package br.com.digio.api.exception;

import static org.apache.commons.lang3.StringUtils.replace;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;

public class ApiExceptionUtils {
	
	public static final String PARAMETRO_DE_ENTRADA_INVALIDO_MESSAGEM = "Parâmetro inválido: [{}].";
	
	public static void assertTrue(boolean bool, HttpStatus httpStatus, String message) {
		if(!bool) {
			throw runApiException(httpStatus, message);
		}
	}

	public static void assertTrue(boolean bool, String message) {
		if(!bool) {
			throw runApiException(message);
		}
	}

	public static ApiException runApiException(String message) {
		return new ApiException(message);
	}
	
	public static ApiException runApiException(HttpStatus code, String message) {
		return new ApiException(code, message);
	}
	
	public static class OnthrownInvalidParameterApiException implements Supplier<ApiException> {
		private String message;

		public OnthrownInvalidParameterApiException(String message) {
			this.message = message;
		}
		@Override
		public ApiException get() {
			return ApiExceptionUtils.runApiException(HttpStatus.BAD_REQUEST, replace(PARAMETRO_DE_ENTRADA_INVALIDO_MESSAGEM, "{}", message));
		}
	}
}
