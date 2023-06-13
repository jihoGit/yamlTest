package com.example.karavan.validation;

import jakarta.validation.*;
import lombok.*;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class Route {
    private String from;
    private String to;

    public void validateUser() {
        Route route = new Route(from, to);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Route>> violations = validator.validate(route);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations.iterator().next().getMessage());
        }
    }
}
