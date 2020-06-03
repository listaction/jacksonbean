package io.github.listaction.spring;

import io.github.listaction.shared.TestEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class DemoRestController {

    @RequestMapping(value = "/demo", method = RequestMethod.POST)
    public TestEntity testEntity(@Valid @RequestBody TestEntity dto){
        // deserialize + serialize
        return dto;
    }

}
