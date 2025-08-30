package com.wbt.store.filters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
public class BaseFilter {
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "id";
    private Sort.Direction direction;
}
