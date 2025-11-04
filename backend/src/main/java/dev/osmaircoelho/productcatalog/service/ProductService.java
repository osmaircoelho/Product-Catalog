package dev.osmaircoelho.productcatalog.service;

import dev.osmaircoelho.productcatalog.dto.ProductDTO;
import dev.osmaircoelho.productcatalog.model.Product;
import dev.osmaircoelho.productcatalog.repository.ProductRepository;
import dev.osmaircoelho.productcatalog.service.exceptions.DataBaseException;
import dev.osmaircoelho.productcatalog.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    public ProductRepository repository;

    @Transactional(readOnly = true) //evita que faz locking no banco de dados
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        Page<Product> list = repository.findAll(pageRequest);

        //x -> new ProductDTO(x)
        //transforma cada elemento da lista em um ProductDTO
        //e coleta os resultados em uma nova lista
        //Collectors.toList() coleta os elementos do stream em uma lista
        //e retorna a lista de ProductDTO
        //map() é um método do stream que transforma cada elemento do stream em outro elemento
        //collect() é um método do stream que coleta os elementos do stream em um objeto
        //neste caso, coleta os elementos do stream em uma lista
        //ProductDTO::new é uma referência de método que chama o construtor ProductDTO(Product entity)
        return list.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        //converter o dto em uma entidade
        Product entity = new Product();
        //setar o nome da entidade
        //entity.setName(dto.getName());
        //salvar a entidade no banco de dados
        entity = repository.save(entity);
        //converter a entidade em um dto
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            //entity.setName(dto.getName());
            entity = repository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    // nao colocar o transactional
    // pois DataIntegrityViolationException
    // precisa ser capturada fora do contexto transacional
    // para funcionar corretamente
    // pois o delete é imediato
    // diferente do save que é feito no final da transação
    // assim o deleteById é executado imediatamente
    // e a exceção pode ser capturada
    // se o delete fosse feito dentro de uma transação
    // a exceção só seria lançada no final da transação
    // e não poderia ser capturada aqui
    //por isso não colocamos o @Transactional aqui
    // pois queremos capturar a exceção fora do contexto transacional
    // para lançar a exceção personalizada
    // assim o deleteById é executado imediatamente
    // e a exceção pode ser capturada
    public void delete(Long id) {
        //verifica se o id existe no banco de dados
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Id not found " + id);
        }

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }

    }
}
