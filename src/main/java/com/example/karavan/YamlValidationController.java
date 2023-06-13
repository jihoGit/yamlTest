package com.example.karavan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import java.io.*;


@RestController
@RequiredArgsConstructor
@Slf4j
public class YamlValidationController {

    private final YamlRunService yamlRunService;

    @PostMapping("/validate-yaml")

    public void yamlRun(@RequestBody String yamlContent) throws IOException, InterruptedException {
        yamlRunService.validate(yamlContent);
        yamlRunService.yamlRun();
        yamlRunService.runJbangVersionCheck();
    }
}