package com.wbt.store.mappers;

import com.wbt.store.dtos.CategoryResponse;
import com.wbt.store.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toCategoryResp(final Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }

}
