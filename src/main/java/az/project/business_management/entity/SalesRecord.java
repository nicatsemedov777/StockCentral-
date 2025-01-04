package az.project.business_management.entity;

import az.project.business_management.entity.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "sales_records")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class SalesRecord {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", type = IdGenerator.class)
    private String id;

    @Column(name = "selling_price")
    private Double sellingPrice;

    @Column(name = "profit_quantity")
    private Double totalProfit;

    @Column(name = "quantity_of_product_sold")
    private Double quantityOfProductSold;

    @Column(name = "total_sale")
    private Double totalSale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "turnover_history_id")
    private TurnoverHistory turnoverHistory;

    @Column(name = "sale_date")
    private Date saleDate;

    @PrePersist
    private void setDateAndTotalSale() {
        this.saleDate = new Date();
        this.totalSale = quantityOfProductSold * sellingPrice;
    }


}
