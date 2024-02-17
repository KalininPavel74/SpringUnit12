package home.kalinin.api_gate_way;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApiGateWay {

    public static void main(String[] args) {
        SpringApplication.run(MainApiGateWay.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("RestNotes",r->r.path("/**")
                        .uri("http://localhost:8081/"))
//                .route("MS_Unit8_2",r->r.path("/**")
//                        .uri("http://localhost:8082/"))
                .build();}
}
