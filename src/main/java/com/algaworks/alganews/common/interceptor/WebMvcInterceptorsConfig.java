package com.algaworks.alganews.common.interceptor;

import com.algaworks.alganews.chaos.ChaosInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcInterceptorsConfig implements WebMvcConfigurer {

  @Autowired(required = false)
  private ChaosInterceptor chaosInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    if (chaosInterceptor != null) {
      registry.addInterceptor(chaosInterceptor);
    }
  }
}