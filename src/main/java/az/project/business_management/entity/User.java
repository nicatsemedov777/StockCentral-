package az.project.business_management.entity;

import az.project.business_management.converter.RoleTypeConverter;
import az.project.business_management.entity.generator.IdGenerator;
import az.project.business_management.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", type = IdGenerator.class)
    private String id;

    @Column(name = "full_name")
    private String fullName;
    private String username;
    private String password;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @Enumerated(value = EnumType.STRING)
    @Type(value = RoleTypeConverter.class)
    @Column(name = "role")
    private RoleType roleType;
}
