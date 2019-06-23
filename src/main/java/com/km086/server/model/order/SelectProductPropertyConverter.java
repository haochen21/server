package com.km086.server.model.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.km086.server.model.store.ProductProperty;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SelectProductPropertyConverter implements AttributeConverter<List<SelectProductProperty>, String> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<SelectProductProperty> selectProductProperties) {
        String customerInfoJson = null;
        try {
            if (selectProductProperties != null && selectProductProperties.size() > 0) {
                customerInfoJson = objectMapper.writeValueAsString(selectProductProperties);
            }
        } catch (JsonProcessingException e) {
            log.error("JSON writing error", e);
        }

        return customerInfoJson;
    }

    @Override
    public List<SelectProductProperty> convertToEntityAttribute(String customerInfoJSON) {
        List<SelectProductProperty> selectProductProperties = new ArrayList<>();
        try {
            if (customerInfoJSON != null && !customerInfoJSON.equals("")) {
                selectProductProperties = objectMapper.readValue(customerInfoJSON, new TypeReference<List<SelectProductProperty>>() {
                });
            }
        } catch (IOException e) {
            log.error("JSON reading error", e);
        }
        return selectProductProperties;
    }
}
