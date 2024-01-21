package com.bhreneer.springdatacrudexample.utils;

public class StringUtils {

    public static boolean isNotEmpty(String string) {
        return org.springframework.util.StringUtils.hasLength(string) && org.springframework.util.StringUtils.hasLength(string.trim());
    }
}
