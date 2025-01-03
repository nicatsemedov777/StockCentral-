package az.project.business_management.entity;

import az.project.business_management.entity.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", type = IdGenerator.class)
    private String id;

    private String name;
    private String code;
    private String colour;
    private Double quantity;
    private Double cost;

    @Column(name = "other_infos")
    private String otherInfos;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @Column(name = "create_date")
    private Date createDate;

    @PrePersist
    private void setDate() {
        this.createDate = new Date();
    }
}

