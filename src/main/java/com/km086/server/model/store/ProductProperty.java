package com.km086.server.model.store;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductProperty {

    private int sequence;

    private String name;

    private List<String> values;

    private String defaultValue;
}
