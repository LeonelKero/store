package com.wbt.store.filters;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class CartFilter extends BaseFilter {
    private LocalDateTime createdAt;
    private String productName;
    private Integer gap;
}
