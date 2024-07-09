package com.pleshkov.model.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {

    READY("READY"),
    NOT_READY("NOT READY");

    public final String statusName;

    OrderStatus(String statusName) {
        this.statusName = statusName;
    }

}
