package io.github.listaction.core;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

public class ObjectMapperValidationFactory {

    public static ObjectMapper createObjectMapper(){
        ValidatorFactory validatorFactory = createValidatorFactory();
        ObjectMapper mapper = new ObjectMapper();
        BeanValidationModule module = new BeanValidationModule(validatorFactory);
        mapper.registerModule(module);
        return mapper;
    }

    private static ValidatorFactory createValidatorFactory() {
        return Validation.byDefaultProvider()
                .configure()
                .buildValidatorFactory();
    }
}
