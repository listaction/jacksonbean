package io.github.listaction.core;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;

import javax.validation.ValidatorFactory;


public final class BeanValidationModule extends com.fasterxml.jackson.databind.Module {

    private final ValidatorFactory validatorFactory;

    public BeanValidationModule(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    @Override
    public String getModuleName() {
        return "jackson-bean-validation";
    }


    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    @Override
    public void setupModule(SetupContext context) {
        context.addBeanDeserializerModifier(new BeanDeserializerModifier() {
            @Override
            public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
                return new ValidationBeanDeserializer(deserializer, beanDesc, validatorFactory);
            }
        });
    }
}
