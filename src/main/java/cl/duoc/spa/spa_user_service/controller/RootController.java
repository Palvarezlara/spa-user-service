package cl.duoc.spa.spa_user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RootController {

    @GetMapping("/")
    public String root() {
        return "spa-user-service running";
    }
}
//Este archivo permite comprobar el servicio escribiendo en el navegador:
//http://localhost:8081/