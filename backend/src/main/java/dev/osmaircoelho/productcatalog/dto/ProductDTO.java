package dev.osmaircoelho.productcatalog.dto;

import dev.osmaircoelho.productcatalog.model.Category;
import dev.osmaircoelho.productcatalog.model.Product;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @Setter
    private String name;

    @Setter
    private String description;

    @Setter
    private Double price;

    @Setter
    private String imageUrl;

    @Setter
    private LocalDate date;

    //Para categories, nao queremos o setter, entao usamos:
    @Setter(AccessLevel.NONE)
    private List<CategoryDTO> categories = new ArrayList<>();

    // Construtor que recebe uma entidade Product e inicializa o DTO com os dados da entidade
    // Isso facilita a conversão de entidade para DTO
    public ProductDTO(Product entity) {
       this.id = entity.getId();
       this.name = entity.getName();
       this.description = entity.getDescription();
       this.price = entity.getPrice();
       this.imageUrl = entity.getImgUrl();
       this.date = LocalDate.from(entity.getDate());
    }

    //sobrecarga do construtor para inicializar o DTO com os dados da entidade e suas categorias
    // Isso facilita a conversão de entidade para DTO
    // O Set<Category> é um conjunto de categorias que pertencem ao produto
    // Para cada categoria no conjunto, criamos um CategoryDTO e o adicionamos à lista de categorias do ProductDTO
    public ProductDTO(Product entity, Set<Category> categories) {
        //chama o construtor acima para inicializar os dados do produto (produtoDTO)
        this(entity);
        //adiciona as categorias ao DTO do produto com base no conjunto de categorias passado como parâmetro
        categories.forEach(cat -> this.categories.add(new CategoryDTO(cat)));
    }
}
