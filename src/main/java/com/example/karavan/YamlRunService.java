package com.example.karavan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
@Slf4j
public class YamlRunService {
    public void validate(String yamlContent) {
        try{
            Yaml yaml = new Yaml();
            Object yamlObj = yaml.load(yamlContent);
            log.info("yamlObj = " + yamlObj);

        }catch (Exception e) {
            log.error("유효성 검증 오류", e);
        }
    }

    public void yamlRun() throws IOException, InterruptedException {
        log.info(":: START :: Use Runtime");

        String command = "jbang -Dcamel.jbang.version=3.20.3 camel@apache/camel run *";

        // 명령 실행
        Process process = Runtime.getRuntime().exec(command);

        // 실행 결과 읽기
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
           log.info(line);
        }

        // 프로세스 종료 대기
        int exitCode = process.waitFor();
        log.info("Exit code: " + exitCode);
    }

    public void runJbangVersionCheck() {
        String command = "jbang --version";

        ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();

            // Process의 입력 및 출력 처리
            try (InputStream inputStream = process.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // 프로세스의 종료를 대기하고 종료 코드 가져오기
            int exitCode;
            try {
                exitCode = process.waitFor();
            } catch (InterruptedException e) {
                exitCode = -1;
            }

            System.out.println("Exit code: " + exitCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
