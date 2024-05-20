package com.rosorio.customer.service.mapper;

public class TextUtil {
    private static final int DEFAULT_VISIBLE_CHARACTERS_AFTER_MASKING = 4;

    public static String mask(String valueToMask){
        return mask(valueToMask, DEFAULT_VISIBLE_CHARACTERS_AFTER_MASKING);
    }

    public static String mask(String valueToMask, int charactersVisible){
        return valueToMask.replaceAll(".(?=.{" + charactersVisible+"})", "*");
    }
}
