package com.bhreneer.springdatacrudexample.utils;

import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

public class CSVUtils {

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        if (ObjectUtils.isEmpty(file) || !TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }
}
