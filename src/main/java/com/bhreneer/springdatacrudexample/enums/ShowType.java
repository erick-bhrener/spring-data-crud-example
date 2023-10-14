package com.bhreneer.springdatacrudexample.enums;

import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public enum ShowType {
    MOVIE("Movie"),
    TV_SHOW("TV Show"),
    UNDEFINED("Undefined");

    private String description;

    ShowType(String description) {
        this.description = description;
    }

    public static String valueOfString(String showType) {
        if(!StringUtils.hasLength(showType))
            return UNDEFINED.name();

        for(ShowType st : ShowType.values()) {
            if(st.getDescription().equals(showType)) {
                return st.name();
            }
        }

        return null;
    }

}
