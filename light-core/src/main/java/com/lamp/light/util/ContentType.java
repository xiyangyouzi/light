

package com.lamp.light.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ContentType {

    private static final Map<String, String>  CONTENT_TYPE_MAP = new HashMap<>();
    
    static {
        
    }
    
    public static String getContentType(String suffix) {
        String contentType = CONTENT_TYPE_MAP.get(suffix);
        return Objects.isNull(contentType) ? "application/octet-stream" : contentType;
    }
}
