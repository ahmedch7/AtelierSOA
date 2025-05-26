package config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class OpenApiConfig extends ResourceConfig {

        public OpenApiConfig() {
                packages("restApi"); // ton package REST
                registerEndpoints();
        }

        private void registerEndpoints() {
                register(io.swagger.v3.jaxrs2.integration.resources.OpenApiResource.class);
        }

        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .info(new Info()
                                .title("API Logement & RendezVous")
                                .version("1.0")
                                .description("Documentation API REST avec Swagger pour gérer des logements et rendez-vous"))
                        .addServersItem(new Server().url("http://localhost:8080/api"));
        }
}
