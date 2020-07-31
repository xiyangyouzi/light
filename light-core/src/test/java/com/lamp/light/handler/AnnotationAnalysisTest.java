package com.lamp.light.handler;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import com.lamp.light.ReturnObject;
import com.lamp.light.TestInterface;

public class AnnotationAnalysisTest {

    private Class<?> clazz = TestInterface.class;
    
    private AnnotationAnalysis annotationAnalysis = new AnnotationAnalysis();
    
    private RequestInfo requestInfo = new RequestInfo(); 
    
    
    @Before
    public void testClass() throws Exception {
        requestInfo = annotationAnalysis.analysis(clazz);
    }
    
    @Test
    public void testFile() throws Exception {
        Method method = clazz.getMethod("testFile", new Class[]{ReturnObject.class,String.class});
        RequestInfo analysis = annotationAnalysis.analysis(method,   requestInfo);
    }
    
    @Test
    public void testHead() throws Exception {
       Method method = clazz.getMethod("testHead", new Class[]{ReturnObject.class,String.class});
       annotationAnalysis.analysis(method,   requestInfo);
    }
    
    @Test
    public void testQuery() throws Exception {
       Method method = clazz.getMethod("testQuery", new Class[]{ReturnObject.class,String.class});
       annotationAnalysis.analysis(method,   requestInfo);
    }
    
    @Test
    public void testPath() throws Exception {
       Method method = clazz.getMethod("testPath", new Class[]{ReturnObject.class,String.class});
       annotationAnalysis.analysis(method,   requestInfo);
    }
    
    @Test
    public void testObject() throws Exception {
       Method method = clazz.getMethod("testObject", new Class[]{ReturnObject.class,String.class});
       annotationAnalysis.analysis(method,   requestInfo);
    }
}
