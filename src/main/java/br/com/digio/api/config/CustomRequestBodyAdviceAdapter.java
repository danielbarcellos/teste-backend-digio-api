package br.com.digio.api.config;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import org.springframework.web.util.UrlPathHelper;

@ControllerAdvice
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

  @Autowired
  HttpServletRequest httpServletRequest;

  @Autowired
  LoggingService loggingService;

  @Override
  public boolean supports(MethodParameter methodParameter, Type type,
      Class<? extends HttpMessageConverter<?>> aClass) {
    return true;
  }

  @Override
  public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
      Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
    String url = UrlPathHelper.getResolvedLookupPath(httpServletRequest);

    if (!url.contains("/actuator/")) {
      loggingService.logRequest(httpServletRequest, body);
    }

    return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
  }

}
