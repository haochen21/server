package com.km086.server.model.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductPropertyConverter implements AttributeConverter<List<ProductProperty>, String> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ProductProperty> productProperties) {
        String customerInfoJson = null;
        try {
            if (productProperties != null && productProperties.size() > 0) {
                customerInfoJson = objectMapper.writeValueAsString(productProperties);
            }
        } catch (JsonProcessingException e) {
            log.error("JSON writing error", e);
        }

        return customerInfoJson;
    }

    @Override
    public List<ProductProperty> convertToEntityAttribute(String customerInfoJSON) {
        List<ProductProperty> productProperties = new ArrayList<>();
        try {
            if (customerInfoJSON != null && !customerInfoJSON.equals("")) {
                productProperties = objectMapper.readValue(customerInfoJSON, new TypeReference<List<ProductProperty>>() {
                });
            }
        } catch (IOException e) {
            log.error("JSON reading error", e);
        }
        return productProperties;
    }
}
