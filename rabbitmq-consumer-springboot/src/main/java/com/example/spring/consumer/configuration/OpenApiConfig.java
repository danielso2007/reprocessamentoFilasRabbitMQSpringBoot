package com.example.spring.consumer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfig {

    private static final String HTTP_LOCALHOST_PADRAO = "http://localhost:%s";

    @Bean
    public OpenAPI customOpenApi(@Value("${info.app.version}") String appVersion,
            @Value("${server.port}") String serverPort) {
        Contact contact = new Contact();
        contact.setName("Daniel Oliveira");
        contact.setUrl(String.format(HTTP_LOCALHOST_PADRAO, serverPort));
        contact.setEmail("danielso2007@gmail.com");

        License license = new License();
        license.setName("Proprietário");
        license.setUrl(String.format(HTTP_LOCALHOST_PADRAO, serverPort));

        Server dev = new Server();
        dev.setUrl(String.format(HTTP_LOCALHOST_PADRAO, serverPort));
        dev.setDescription("Servidor de desenvolvimento");

        Server hmg = new Server();
        hmg.setUrl(String.format(HTTP_LOCALHOST_PADRAO, serverPort));
        hmg.setDescription("Servidor de produção");

        Server prod = new Server();
        prod.setUrl(String.format(HTTP_LOCALHOST_PADRAO, serverPort));
        prod.setDescription("Servidor de homologação");

        List<Server> servers = new ArrayList<>();
        servers.add(dev);
        servers.add(hmg);
        servers.add(prod);

        Components components = new Components().addSecuritySchemes("bearerAuth",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI().components(components)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(new Info().title(
                        "Projeto de estudo sobre fila em spring boot")
                        .description("Apenas para estudo de fila")
                        .version(appVersion)
                        .termsOfService("https://smartbear.com/terms-of-use/")
                        .contact(contact).license(license))
                .servers(servers);
    }
}
