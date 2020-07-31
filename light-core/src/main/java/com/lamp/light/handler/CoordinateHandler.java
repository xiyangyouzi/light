/*
* frxs Inc.  湖南兴盛优选电子商务有限公司.
* Copyright (c) 2017-2019. All Rights Reserved.
*/
package com.lamp.light.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import com.lamp.light.LightUpload;
import com.lamp.light.util.ContentType;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.QueryStringEncoder;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.ErrorDataEncoderException;
import io.netty.handler.codec.http.multipart.MemoryFileUpload;

public interface CoordinateHandler<T,V> {

    static final ThreadLocal<CoordinateHandlerWrapper> COORDINATEHANDLER = new ThreadLocal<CoordinateHandlerWrapper>() {

        public CoordinateHandlerWrapper initialValue() {
            return new CoordinateHandlerWrapper();
        }
    };

    public static CoordinateHandlerWrapper getCoordinateHandlerWrapper() {
        return COORDINATEHANDLER.get();
    }

    void handler(String key, V value);

    void clean();
    
    static abstract class AbstractCoordinateHandler<T,V> implements CoordinateHandler<T,V> {

        T object;

        void setObject(T object) {
            this.object = object;
        }
        
        public void clean() {
            this.object = null;
        }
    }

    static class CookieCoordinateHandler extends AbstractCoordinateHandler<HttpHeaders,String> {
        @Override
        public void handler(String name, String value) {
            object.add(name, value);
        }
    }

    static class HeaderCoordinateHandler extends AbstractCoordinateHandler<HttpHeaders,String> {
        @Override
        public void handler(String name, String value) {
            object.add(name, value);
        }
    }

    static class PathCoordinateHandler extends AbstractCoordinateHandler<String,String> {
        @Override
        public void handler(String name, String value) {

        }
    }

    static class QueryCoordinateHandler extends AbstractCoordinateHandler<QueryStringEncoder,String> {
        @Override
        public void handler(String name, String value) {
            object.addParam(name, value);
        }
    }

    static class FieldCoordinateHandler extends AbstractCoordinateHandler<HttpPostRequestEncoder,String> {
        @Override
        public void handler(String name, String value) {
            try {
                object.addBodyAttribute(name, value);
            } catch (ErrorDataEncoderException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    static class UploadCoordinateHandler extends AbstractCoordinateHandler<HttpPostRequestEncoder,Object> {
        @Override
        @SuppressWarnings("unchecked")
        public void handler(String name, Object value) {
            
            try {
                if ( value instanceof List) {
                    List<Object> list = (List<Object>)value;
                    for (Object object : list) {
                        setFileUpload(name, object);
                    }
                }else {
                    setFileUpload(name, value);
                }
              
            } catch (ErrorDataEncoderException e) {
               throw  new RuntimeException(e);
            } catch (IOException e) {
                throw  new RuntimeException(e);
            }
        }
        
        private void setFileUpload(String name, Object object) throws ErrorDataEncoderException, IOException {
            if (object instanceof File) {
                File file = (File)object;
                String fileName = file.getName();
                int index = fileName.lastIndexOf('.');
               this.object.addBodyFileUpload(fileName.substring(0, index), file, ContentType.getContentType(fileName.substring(index+1)), false);
            }else if(object instanceof LightUpload) {
                LightUpload upload = (LightUpload)object;
                Object object2 = upload.getObject();
                if(object2 instanceof String) {
                    File file = new File(object2.toString());
                    String fileName = file.getName();
                    int index = fileName.lastIndexOf('.');
                   this.object.addBodyFileUpload(fileName.substring(0, index), file, ContentType.getContentType(fileName.substring(index+1)), true);
                   FileUpload fileUpload = new DiskFileUpload(name, fileName, ContentType.getContentType(fileName.substring(index+1)), Objects.isNull(upload.getCharset()) ? "binary" : null, upload.getCharset(), file.length());
                   this.object.addBodyHttpData(fileUpload);
                } else if (object2 instanceof InputStream) {
                    MemoryFileUpload memoryFileUpload = new MemoryFileUpload(upload.getName(), upload.getName()+"."+upload.getType(),  ContentType.getContentType(upload.getType()), Objects.isNull(upload.getCharset()) ? "binary" : null,upload.getCharset(), ((InputStream)object2).available());
                    memoryFileUpload.setContent((InputStream)object2);
                    this.object.addBodyHttpData(memoryFileUpload);
                } else if(object2 instanceof byte[]) {
                    byte[] bytes = (byte[])object2;
                    ByteBuf buffer = Unpooled.buffer(bytes.length);
                    buffer.writeBytes(bytes);
                    MemoryFileUpload memoryFileUpload = new MemoryFileUpload(upload.getName(), upload.getName()+"."+upload.getType(),  ContentType.getContentType(upload.getType()), Objects.isNull(upload.getCharset()) ? "binary" : null,upload.getCharset(), bytes.length);
                    memoryFileUpload.setContent(buffer);
                    this.object.addBodyHttpData(memoryFileUpload);
                } 
            }else {
                throw new RuntimeException("运行异常");
            }
            
        }
        
        
    }

    public static class CoordinateHandlerWrapper {

        public QueryCoordinateHandler queryCoordinateHandler = new QueryCoordinateHandler();

        public FieldCoordinateHandler fieldCoordinateHandler = new FieldCoordinateHandler();

        public PathCoordinateHandler pathCoordinateHandler = new PathCoordinateHandler();

        public HeaderCoordinateHandler headerCoordinateHandler = new HeaderCoordinateHandler();

        public CookieCoordinateHandler cookieCoordinateHandler = new CookieCoordinateHandler();
        
        public UploadCoordinateHandler uploadCoordinateHandler = new UploadCoordinateHandler();
    }
}
