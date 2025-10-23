package dev.osmaircoelho.productcatalog.service;

import dev.osmaircoelho.productcatalog.model.Category;
import dev.osmaircoelho.productcatalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    public CategoryRepository repository;

    public List<Category> findAll(){
        return repository.findAll();
    }
}
