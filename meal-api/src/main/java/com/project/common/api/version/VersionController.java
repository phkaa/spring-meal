package com.project.common.api.version;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/versions")
public class VersionController {
    @GetMapping
    public ResponseEntity<String> version() {
        return ResponseEntity.ok().body("v1");
    }
}
