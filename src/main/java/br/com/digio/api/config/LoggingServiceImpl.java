package br.com.digio.api.config;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class LoggingServiceImpl implements LoggingService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private Logger logger;

	@Override
	public void logRequest(HttpServletRequest httpServletRequest, Object body) {
		Map<String, String> parameters = buildParametersMap(httpServletRequest);

		log.info("===========================ingress request begin================================================");
		log.info("URI         : {}", httpServletRequest.getRequestURI());
		log.info("Method      : {}", httpServletRequest.getMethod());
		log.info("Headers     : {}", buildHeadersMap(httpServletRequest));
		if (!parameters.isEmpty()) {
			log.info("parameters  : {}", body);
		}
		if (body != null) {
			log.info("Request body: \n{}", body);
		}

		log.info("==========================ingress request end================================================");

	}

	@Override
	public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object body) {

		log.info("============================ingress response begin==========================================");
		log.info("Status code  : {}", httpServletResponse.getStatus());
		log.info("Status text  : {}", HttpStatus.valueOf(httpServletResponse.getStatus()));
		log.info("Headers      : {}", buildHeadersMap(httpServletResponse));
		log.info("Response body: \n{}", body);
		log.info("=======================ingress response end=================================================");
	}

	private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
		Map<String, String> resultMap = new HashMap<>();
		Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String key = parameterNames.nextElement();
			String value = httpServletRequest.getParameter(key);
			resultMap.put(key, value);
		}

		return resultMap;
	}

	private Map<String, String> buildHeadersMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();

		Enumeration<?> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}

		return map;
	}

	private Map<String, String> buildHeadersMap(HttpServletResponse response) {
		Map<String, String> map = new HashMap<>();

		Collection<String> headerNames = response.getHeaderNames();
		for (String header : headerNames) {
			map.put(header, response.getHeader(header));
		}

		return map;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

}
