package uz.pdp.appspringpcmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Category category;

    @OneToMany
    private List<Attachment> photos;

    private String name;
    private float price;
    private String warranty;
    private boolean inStock;
    private boolean saleOnlyInComplete;

    @ManyToMany
    private List<Character> characters;

}
