package io.github.listaction.shared;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Valid
public class TestEntity {

    @NotNull
    private String value1;
    @Size(min = 1)
    private List<String> listValue;
    @Valid
    private SubEntity subEntity;

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public List<String> getListValue() {
        return listValue;
    }

    public void setListValue(List<String> listValue) {
        this.listValue = listValue;
    }

    public SubEntity getSubEntity() {
        return subEntity;
    }

    public void setSubEntity(SubEntity subEntity) {
        this.subEntity = subEntity;
    }
}
