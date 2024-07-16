package com.quantacomit.practical_test.Util;

import com.quantacomit.practical_test.exception.CustomException;

public class ValidationUtil {
    public static void isNullOrEmptyException(String value, String key) throws CustomException {
        if (value == null) {
            throw new CustomException(key + " is Empty");
        } else if (value.isEmpty()) {
            throw new CustomException(key + " is Empty");
        }
    }

    public static boolean isNullOrEmpty(String value){
        if (value == null) {
            return true;
        } else return value.isEmpty();
    }
}
