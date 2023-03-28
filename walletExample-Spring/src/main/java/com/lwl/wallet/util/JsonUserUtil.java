package com.lwl.wallet.util;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;

@Repository
public class JsonUserUtil {
    public <T> T readFromJson(String file, TypeReference<T> typeReference){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());
        InputStream fileJson = JsonUserUtil.class.getResourceAsStream(file);

        try {
            return objectMapper.readValue(fileJson,typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
