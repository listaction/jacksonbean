package org.jacksonbean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ValidationTest {

    private static final ObjectMapper mapper = ObjectMapperValidationFactory.createObjectMapper();

    @Test
    public void positiveTest() throws JsonProcessingException {
        String json = mapper.writeValueAsString(createValidTestEntity());
        System.out.println(json);
        TestEntity testEntity = mapper.readValue(json, TestEntity.class);
        assert testEntity != null;
    }

    @Test
    public void negativeTest() throws JsonProcessingException {
        String json = mapper.writeValueAsString(createNotValidTestEntity());
        System.out.println(json);
        Assertions.assertThrows(JsonProcessingException.class, () -> {
            TestEntity testEntity = mapper.readValue(json, TestEntity.class);
        });
    }

    @Test
    public void notValidPayload_withoutValidationTest_shouldPass() throws JsonProcessingException {
        ObjectMapper mapperWithoutValidation = new ObjectMapper();
        String json = mapperWithoutValidation.writeValueAsString(createNotValidTestEntity());
        System.out.println(json);
        TestEntity testEntity = mapperWithoutValidation.readValue(json, TestEntity.class);
    }

    private TestEntity createValidTestEntity(){
        SubEntity subEntity = new SubEntity();
        subEntity.setUrl("http://google.com");
        subEntity.setCustomField("correct_value");
        
        List<String> list = new ArrayList<String>();
        list.add("test");
        
        TestEntity entity = new TestEntity();
        entity.setValue1("test");
        entity.setListValue(list);
        entity.setSubEntity(subEntity);

        return entity;        
    }

    private TestEntity createNotValidTestEntity(){
        SubEntity subEntity = new SubEntity();
        subEntity.setUrl("not_allowed_value");

        List<String> list = new ArrayList<String>();

        TestEntity entity = new TestEntity();
        entity.setListValue(list);
        entity.setSubEntity(subEntity);

        return entity;
    }

}
