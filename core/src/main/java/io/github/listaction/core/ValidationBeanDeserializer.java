package io.github.listaction.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Set;

public class ValidationBeanDeserializer extends DelegatingDeserializer {

    private final BeanDescription beanDescription;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ValidationBeanDeserializer(JsonDeserializer<?> delegate, BeanDescription beanDescription, ValidatorFactory validatorFactory) {
        super(delegate);
        this.beanDescription = beanDescription;
        this.validatorFactory =validatorFactory;
        this.validator = validatorFactory.getValidator();
    }

    @Override
    protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> newDelegatee) {
        return new ValidationBeanDeserializer(newDelegatee, beanDescription, validatorFactory);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Object object = super.deserialize(p, ctxt);

        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (violations.size() > 0){
            throw new ConstraintViolationException(violations);
        }

        return object;
    }

}