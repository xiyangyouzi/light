
package com.lamp.light.handler;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;

import com.lamp.light.LightUpload;
import com.lamp.light.handler.CoordinateHandler.CookieCoordinateHandler;
import com.lamp.light.handler.CoordinateHandler.CoordinateHandlerWrapper;
import com.lamp.light.handler.CoordinateHandler.FieldCoordinateHandler;
import com.lamp.light.handler.CoordinateHandler.HeaderCoordinateHandler;
import com.lamp.light.handler.CoordinateHandler.QueryCoordinateHandler;
import com.lamp.light.handler.CoordinateHandler.UploadCoordinateHandler;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringEncoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.ErrorDataEncoderException;

public class CoordinateHandlerTest {

    private CoordinateHandlerWrapper coordinateHandlerWrapper;

    @Before
    public void test() {
        ThreadLocal<CoordinateHandlerWrapper> coordinatehandler = CoordinateHandler.COORDINATEHANDLER;
        coordinateHandlerWrapper = coordinatehandler.get();
    }

    @Test
    public void testCookieCoordinateHandler() throws Exception {
        CookieCoordinateHandler cookieCoordinateHandler = coordinateHandlerWrapper.cookieCoordinateHandler;
        cookieCoordinateHandler.setObject(new DefaultHttpHeaders());
        cookieCoordinateHandler.handler("name", "jtfr");
        HttpHeaders object = cookieCoordinateHandler.object;
        System.out.println("object" + object);
    }

    @Test
    public void testHeaderCoordinateHandler() throws Exception {
        HeaderCoordinateHandler headerCoordinateHandler = coordinateHandlerWrapper.headerCoordinateHandler;
        headerCoordinateHandler.setObject(new DefaultHttpHeaders());
        headerCoordinateHandler.handler("name", "jtfr");
        HttpHeaders object = headerCoordinateHandler.object;
        System.out.println("object" + object);
    }

    @Test
    public void testPathCoordinateHandler() throws Exception {

    }

    @Test
    public void testQueryCoordinateHandler() throws Exception {
        QueryCoordinateHandler queryCoordinateHandler = coordinateHandlerWrapper.queryCoordinateHandler;
        queryCoordinateHandler.setObject(new QueryStringEncoder(""));
        queryCoordinateHandler.handler("name", "/query");
        QueryStringEncoder object = queryCoordinateHandler.object;
        System.out.println("object" + object);
    }

    @Test
    public void testFieldCoordinateHandler() throws Exception {
        FieldCoordinateHandler fieldCoordinateHandler = coordinateHandlerWrapper.fieldCoordinateHandler;
        fieldCoordinateHandler.setObject(new HttpPostRequestEncoder(
            new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/post"), false));
        fieldCoordinateHandler.handler("name", "jtfr");
        HttpPostRequestEncoder object = fieldCoordinateHandler.object;
        System.out.println("object" + object);
    }

    @Test
    public void testUploadCoordinateHandler1() throws ErrorDataEncoderException {
        UploadCoordinateHandler uploadCoordinateHandler = coordinateHandlerWrapper.uploadCoordinateHandler;
        uploadCoordinateHandler.setObject(new HttpPostRequestEncoder(
            new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/upload"), false));
        HttpPostRequestEncoder object2 = uploadCoordinateHandler.object;
        File file = new File("D:\\soul\\ok.txt");
        uploadCoordinateHandler.handler("jtfr", file);
        HttpPostRequestEncoder object = uploadCoordinateHandler.object;
        System.out.println("object" + object);
    }

    @Test
    public void testUploadCoordinateHandler2() throws ErrorDataEncoderException {
        UploadCoordinateHandler uploadCoordinateHandler = coordinateHandlerWrapper.uploadCoordinateHandler;
        uploadCoordinateHandler.setObject(new HttpPostRequestEncoder(
            new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/upload"), false));
        LightUpload lightUpload = new LightUpload();
        lightUpload.setObject("D:\\soul\\ok.txt");
        uploadCoordinateHandler.handler("jtfr", lightUpload);
        HttpPostRequestEncoder object = uploadCoordinateHandler.object;
        System.out.println("object" + object);
    }

    @Test
    public void testUploadCoordinateHandler3() throws Exception {
        UploadCoordinateHandler uploadCoordinateHandler = coordinateHandlerWrapper.uploadCoordinateHandler;
        uploadCoordinateHandler.setObject(new HttpPostRequestEncoder(
            new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/upload"), false));
        LightUpload lightUpload = new LightUpload();
        lightUpload.setName("ok");
        lightUpload.setType("txt");
        FileInputStream fis = new FileInputStream("D:\\soul\\ok.txt");
        lightUpload.setObject(fis);
        uploadCoordinateHandler.handler("jtfr", lightUpload);
        HttpPostRequestEncoder object = uploadCoordinateHandler.object;
        System.out.println("object" + object);
    }

    @Test
    public void testUploadCoordinateHandler4() throws Exception {
        UploadCoordinateHandler uploadCoordinateHandler = coordinateHandlerWrapper.uploadCoordinateHandler;
        uploadCoordinateHandler.setObject(new HttpPostRequestEncoder(
            new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/upload"), false));
        LightUpload lightUpload = new LightUpload();
        lightUpload.setName("ok");
        lightUpload.setType("txt");
        lightUpload.setObject("你好".getBytes());
        uploadCoordinateHandler.handler("jtfr", lightUpload);
        HttpPostRequestEncoder object = uploadCoordinateHandler.object;
        System.out.println("object" + object);
    }
}
