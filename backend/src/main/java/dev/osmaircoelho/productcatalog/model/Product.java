package dev.osmaircoelho.productcatalog.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
// Removemos @Setter da classe e vamos colocar em cada campo que queremos, ou usar uma abordagem mista.
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //para equals e hashCode, incluindo apenas o campo id.
@NoArgsConstructor //construtor sem argumentos
@AllArgsConstructor //construtor com todos os argumentos
@Table(name = "tb_product")
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Setter
    private String name;

    @Setter
    @Column(columnDefinition = "TEXT") //texto longos
    private String description;

    @Setter
    private Double price;

    @Setter
    private String imgUrl;

    @Setter
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime date;

    @Setter
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime created_At;

    @Setter
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime updated_At;

    //Para categories, nao queremos o setter, entao usamos:
    @Setter(AccessLevel.NONE)

    @ManyToMany
    @JoinTable(
            name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"), //referencia o product
            inverseJoinColumns = @JoinColumn(name = "category_id") // referencia a category
    )
    //conjunto SET<> nao aceita repeticao
    Set<Category> categories = new HashSet<>();

}
