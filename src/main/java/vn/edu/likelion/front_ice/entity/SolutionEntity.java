package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * SolutionEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_solution")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SolutionEntity extends BaseEntity {

    @Column
    String challenger_id;

    @Column
    String challenge_id;

    @Column
    String url_product;

    @Column
    String url_repo;

    @Column
    String title;

    @Column
    String description;

    @Column
    String note;

//    @Column
//    Enum status;

}

