package az.project.business_management.entity;

import az.project.business_management.entity.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "turnover_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TurnoverHistory {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", type = IdGenerator.class)
    private String id;

    private LocalDate localDate;

    @Column(name = "total_turnover")
    private Double totalTurnover;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
}
