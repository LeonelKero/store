package com.wbt.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductSummaryDTO {
    Long id;
    String name;
    BigDecimal price;
}
