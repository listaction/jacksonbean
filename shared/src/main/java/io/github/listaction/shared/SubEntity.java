package io.github.listaction.shared;

import javax.validation.constraints.Pattern;

public class SubEntity {
    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$")
    private String url;
    @ValidCustomField
    private String customField;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCustomField() {
        return customField;
    }

    public void setCustomField(String customField) {
        this.customField = customField;
    }
}
