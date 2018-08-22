package com.facetuis.server.utils;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class Base64Util {

    public static String encode(ByteArrayOutputStream outputStream) {
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    public static String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
