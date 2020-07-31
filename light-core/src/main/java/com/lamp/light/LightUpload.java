
package com.lamp.light;

import java.nio.charset.Charset;

public class LightUpload {

    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件后缀
     */
    private String type;
    /**
     * 文件编码
     */
    private Charset charset;
    /**
     * 文件格式 String、InPutStream、byte[]
     */
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
