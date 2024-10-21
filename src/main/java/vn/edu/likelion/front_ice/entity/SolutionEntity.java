package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import vn.edu.likelion.front_ice.common.enums.StatusSolution;


/**
 * SolutionEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_solution")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SolutionEntity extends BaseEntity {

    @Column
    String urlProduct;

    @Column
    String urlRepository;

    @Column
    String title;

    @Column
    String description;

    @Column
    String note;

    @Column(name = "is_hidden")
    @ColumnDefault("false")
    boolean hidden;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_solution")
   StatusSolution statusSolution;

}

