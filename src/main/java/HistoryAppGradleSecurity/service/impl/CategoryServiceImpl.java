package HistoryAppGradleSecurity.service.impl;

import HistoryAppGradleSecurity.model.entity.Category;
import HistoryAppGradleSecurity.model.enums.CategoryNameEnum;
import HistoryAppGradleSecurity.repository.CategoryRepository;
import HistoryAppGradleSecurity.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findCategoryByName(CategoryNameEnum categoryNameEnum) {
        return categoryRepository.findByName(categoryNameEnum)
                .orElse(null);
    }
}
