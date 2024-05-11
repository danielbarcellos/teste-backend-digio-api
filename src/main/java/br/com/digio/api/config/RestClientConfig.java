package br.com.digio.api.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class RestClientConfig {

  @Bean
  public RestTemplate restTemplate()
      throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

    TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
        .loadTrustMaterial(null, acceptingTrustStrategy).build();

    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

    HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory();

    requestFactory.setHttpClient(httpClient);

    RestTemplate restTemplate =
        new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));


    List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
    if (CollectionUtils.isEmpty(interceptors)) {
      interceptors = new ArrayList<>();
    }

    interceptors.add(new RequestResponseLoggingInterceptor());
    restTemplate.setInterceptors(interceptors);

    MappingJackson2HttpMessageConverter jsonHttpMessageConverter =
        new MappingJackson2HttpMessageConverter();
    jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,
        false);
    restTemplate.getMessageConverters().add(jsonHttpMessageConverter);

    return restTemplate;
  }
}
