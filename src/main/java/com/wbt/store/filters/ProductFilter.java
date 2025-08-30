package com.wbt.store.filters;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Data
public class ProductFilter extends BaseFilter {
    private String name;
    private BigDecimal price;
    private Byte categoryId;
    private String categoryName;
}
