package com.dragon3.infrastructure.constants;

public enum EnableStatus {
    ENABLE("可用"),
    DISABLE ("不可用"),
    ;
    private final String label;
    EnableStatus(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
