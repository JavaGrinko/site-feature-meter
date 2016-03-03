package javagrinko.sitefeaturemeter.main;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class SiteFeatureMeterProperties {
    private Integer version;
    private Integer subVersion;
    private Integer buildVersion;

    public Integer getVersion() {
        return version;
    }

    public Integer getSubVersion() {
        return subVersion;
    }

    public Integer getBuildVersion() {
        return buildVersion;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setSubVersion(Integer subVersion) {
        this.subVersion = subVersion;
    }

    public void setBuildVersion(Integer buildVersion) {
        this.buildVersion = buildVersion;
    }
}
