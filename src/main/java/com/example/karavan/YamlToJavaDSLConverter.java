package com.example.karavan;

import com.example.karavan.validation.Route;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.yaml.snakeyaml.Yaml;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class YamlToJavaDSLConverter {

    public static void main(String[] args) throws Exception {

        // CamelContext 생성
        CamelContext context = new DefaultCamelContext();

        // YAML 문자열
        String yamlString = "route:\n" +
                "  from: direct:start\n" +
                "  to: mock:result";

        try{
            // YAML 파서 생성
            Yaml yaml = new Yaml();

            // YAML을 Java 객체로 파싱
            Map<String, Object> yamlMap = yaml.load(yamlString);

            // 자바 컬렉션을 원하는 자바 객체로 변환 (유효성 검사)
            if (yamlMap != null) {
                Route route = new Route();
                Map<String, Object> routeMap = (Map<String, Object>) yamlMap.get("route");
                route.setFrom((String)routeMap.get("from"));
                route.setTo((String)routeMap.get("to"));
                try{
                    route.validateUser();
                }catch (Exception e) {
                    log.error("validation error !!",e);
                }
                log.info("Parsed Route: " + route);
            } else {
                log.error("Invalid YAML format.");
            }

            // 라우트 정보 추출 및 등록
            Map<String, Object> routeMap = (Map<String, Object>) yamlMap.get("route");
            String fromEndpoint = (String) routeMap.get("from");
            String toEndpoint = (String) routeMap.get("to");

            // CamelContext에 라우트 등록
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from(fromEndpoint)
                            .to(toEndpoint);
                }
            });

            try{
                // CamelContext 시작 (기능 검증)
                context.start();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", "John Doe");
                jsonObject.put("age", 30);

                // 라우트가 동적으로 추가되었으므로 해당 엔드포인트로 메시지 전송
                context.createProducerTemplate().sendBody(fromEndpoint, jsonObject);
            }catch (Exception e) {
                log.error("route failed !! ", e);

            }finally {
                // CamelContext 정지
                context.stop();
            }

        }catch (Exception e) {
            log.error("yaml parsing error !!", e);
        }

    }

}