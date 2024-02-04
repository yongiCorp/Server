package com.brandol.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @Operation(hidden = true)
    @GetMapping("/health")
    public String healthAPI(){
        return "I'm Healthy";
    }

    @Operation(hidden = true)
    @GetMapping("/")
    public String welcomeAPI(){return "브랜돌 서버";}
}
