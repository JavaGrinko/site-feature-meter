package javagrinko.sitefeaturemeter.dom.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Counter {

    Long id;

    @JsonProperty("owner_login")
    String ownerLogin;

    @JsonProperty("code_status")
    String codeStatus;

    @JsonProperty("site")
    String siteName;

    String permission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
