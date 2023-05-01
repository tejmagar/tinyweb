package org.tinyweb.views;

import org.tinyweb.conf.Configurations;

import java.util.Map;

public abstract class BaseView {
    private Configurations configurations;

    public void setConfigurations(Configurations configurations) {
        this.configurations = configurations;
    }

    public Map<String, Object> getConfigurations() {
        return configurations.get();
    }
}
