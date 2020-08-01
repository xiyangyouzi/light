package com.lamp.light.response;

import com.lamp.light.serialize.Serialize;

import io.netty.handler.codec.http.HttpResponse;

// 适配 HttpResponse
public class Response<T> {

    private Serialize serialize;
    private HttpResponse httpResponse;
    
    public Response(HttpResponse httpResponse, Serialize serialize) {
        this.httpResponse = httpResponse;
        this.serialize = serialize;
    }
    
    public int getStatus() {
        return httpResponse.status().code();
    }
    
    public String getProtocolVersion(){
        return httpResponse.protocolVersion().text();
    }
    
    public String getHeader(String headerKey) {
       return httpResponse.headers().get(headerKey);
    }
    
    public String getDecoderResult() {
        return httpResponse.decoderResult().toString();
    }
}
