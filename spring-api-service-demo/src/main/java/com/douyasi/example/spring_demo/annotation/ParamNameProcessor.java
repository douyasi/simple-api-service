package com.douyasi.example.spring_demo.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author raoyc
 */
public class ParamNameProcessor extends ServletModelAttributeMethodProcessor {
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    private final Map<Class<?>, Map<String, String>> replaceMap = new ConcurrentHashMap<>(256);

    public ParamNameProcessor() {
        super(true);
    }
    
    /*
    public ParamNameProcessor(boolean annotationNotRequired) {
        super(annotationNotRequired);
    }
    */

    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest nativeWebRequest) {
        Object target = binder.getTarget();
        Class<?> targetClass = Objects.requireNonNull(target).getClass();
        if (!replaceMap.containsKey(targetClass)) {
            replaceMap.put(targetClass, analyzeClass(targetClass));
        }
        Map<String, String> mapping = replaceMap.get(targetClass);
        ParamNameDataBinder paramNameDataBinder = new ParamNameDataBinder(target, binder.getObjectName(), mapping);
        Objects.requireNonNull(requestMappingHandlerAdapter.getWebBindingInitializer())
            .initBinder(paramNameDataBinder);
        super.bindRequestParameters(paramNameDataBinder, nativeWebRequest);
    }

    private Map<String, String> analyzeClass(Class<?> targetClass) {
        Map<String, String> renameMap = new HashMap<>(32);
        for (Field field : targetClass.getDeclaredFields()) {
            ParamName paramNameAnnotation = field.getAnnotation(ParamName.class);
            if (paramNameAnnotation != null && !paramNameAnnotation.value().isEmpty()) {
                renameMap.put(paramNameAnnotation.value(), field.getName());
            }
        }
        if (targetClass.getSuperclass() != Object.class) {
            renameMap.putAll(analyzeClass(targetClass.getSuperclass()));
        }
        return renameMap;
    }
}