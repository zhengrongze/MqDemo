package com.mq.util;

public enum ExchangeEnum {
    direct("direct"),
    fanout("fanout"),
    topic("topic")
    ;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    ExchangeEnum(String type) {
        this.type = type;
    }
}
