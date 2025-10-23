package dev.osmaircoelho.productcatalog.dto;

import dev.osmaircoelho.productcatalog.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    public CategoryDTO(Category entity) {
       this.id = entity.getId();
        this.name = entity.getName();
    }

}
