package com.beise.carros.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String get(){
        return "API dos carros";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("login") String login,
            @RequestParam("senha") String senha){
        return "Login "+login+" senha "+senha;
    }

    @GetMapping("/carros/{id}")
    public String getCarroById(@PathVariable("id") Long id){
        return "Carro "+id;
    }
}
