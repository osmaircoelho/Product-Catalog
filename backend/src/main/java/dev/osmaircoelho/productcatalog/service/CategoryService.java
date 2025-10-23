package dev.osmaircoelho.productcatalog.service;

import dev.osmaircoelho.productcatalog.dto.CategoryDTO;
import dev.osmaircoelho.productcatalog.model.Category;
import dev.osmaircoelho.productcatalog.repository.CategoryRepository;
import dev.osmaircoelho.productcatalog.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    public CategoryRepository repository;

    @Transactional(readOnly = true) //evita que faz locking no banco de dados
    public List<CategoryDTO> findAll() {
        List<Category> list = repository.findAll();

        //x -> new CategoryDTO(x)
        //transforma cada elemento da lista em um CategoryDTO
        //e coleta os resultados em uma nova lista
        //Collectors.toList() coleta os elementos do stream em uma lista
        //e retorna a lista de CategoryDTO
        //map() é um método do stream que transforma cada elemento do stream em outro elemento
        //collect() é um método do stream que coleta os elementos do stream em um objeto
        //neste caso, coleta os elementos do stream em uma lista
        //CategoryDTO::new é uma referência de método que chama o construtor CategoryDTO(Category entity)
        return list.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow(
                () -> new EntityNotFoundException("Entity not found")
        );
        return new CategoryDTO(entity);
    }

}
