package app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private static final String API_BASE_PATH = "/api";

  /**
   * RestControllerクラスに対して、自動で/apiプレフィックスを追加する。
   */
  @Override
  public void configurePathMatch(@NonNull PathMatchConfigurer configurer) {
    configurer.addPathPrefix(
        API_BASE_PATH,
        controllerClass -> controllerClass.isAnnotationPresent(RestController.class));
  }
}
