package com.simoncherry.faketantan.sp;

import com.alibaba.fastjson.JSONObject;
import com.baoyz.treasure.Converter;

import java.lang.reflect.Type;

/**
 * Created by Simon on 2016/10/22.
 */

public class JsonConverterFactory implements Converter.Factory {
    @Override
    public <F> Converter<F, String> fromType(Type fromType) {
        return new Converter<F, String>() {
            @Override
            public String convert(F value) {
                return JSONObject.toJSONString(value);
            }
        };
    }

    @Override
    public <T> Converter<String, T> toType(final Type toType) {
        return new Converter<String, T>() {
            @Override
            public T convert(String value) {
                return JSONObject.parseObject(value, toType);
            }
        };
    }
}
