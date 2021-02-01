package com.bxwl.admin.sys.utils;

import java.util.UUID;

public class UuidUtil {

    public UuidUtil() {
    }

    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString().replace("-", "");
        return id;
    }
}
