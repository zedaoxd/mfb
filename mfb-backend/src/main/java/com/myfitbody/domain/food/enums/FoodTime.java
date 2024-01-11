package com.myfitbody.domain.food.enums;

import lombok.Getter;

@Getter
public enum FoodTime {
    BREAKFAST("BREAKFAST"),
    LUNCH("LUNCH"),
    DINNER ("DINNER"),
    SNACK("SNACK");

    private final String value;

    FoodTime(String value) {
        this.value = value;
    }
}
