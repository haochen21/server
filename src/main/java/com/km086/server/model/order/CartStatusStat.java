package com.km086.server.model.order;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartStatusStat implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    protected CartStatus status;

    protected BigDecimal price;

    protected Long total;

    private static final long serialVersionUID = 6787875623668652505L;

    @Override
    public String toString() {
        return "CartStatusStat [status=" + status + ", price=" + price + ", total=" + total + "]";
    }
}
