package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLRestriction;
import vn.edu.likelion.front_ice.common.constants.SQLRestrictions;


/**
 * ReportEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_feedback")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLRestriction(SQLRestrictions.SQL_DELETE_CONDITION)
public class ReportEntity extends BaseEntity {

    @Column
    String solutionId;

    @Column
    String accountId;

    @Column
    String feedbackId;

    @Column
    String reason;
    
}

