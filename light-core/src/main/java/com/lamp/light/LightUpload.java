

package com.lamp.light;

import java.nio.charset.Charset;

public class LightUpload {

    private String name;
    
    private String type;
    
    private Charset charset;
    
    private Object object;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        //
        this.object = object;
    }
    
    
}
