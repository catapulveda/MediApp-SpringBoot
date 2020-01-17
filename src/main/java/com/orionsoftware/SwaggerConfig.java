package com.orionsoftware;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

//@EnableSwagger2 -> Activa las caracteristicas de la documentacion
/*
@Configuration -> Permite que una clase sea leida por spring... Habran anotaciones que spring las va a gestionar
    Tendra Beans definidos, es decir una instancia de alguna clase que spring no tiene en su core container, para que luego con la anotacion
    @Autowired se pueda hacer una inyecion de dependencias
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final Contact contacto = new Contact("Orion Software Services", "http://orionsoftware.com.co/", "catapulveda@orionsoftware.com.co");
    public static final ApiInfo DEFAULT_API_INFO =
            new ApiInfo("Orion Software Services", "Software Informatico", "1.0",
                    "ENTRENAMIENTO", contacto, "Apache 2.0",
                    "http://orionsoftware.com.co/", new ArrayList<VendorExtension>());
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO);
    }

}
