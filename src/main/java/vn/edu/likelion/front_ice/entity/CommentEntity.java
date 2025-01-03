package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLRestriction;
import vn.edu.likelion.front_ice.common.constants.SQLRestrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * CommentEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_comments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLRestriction(SQLRestrictions.SQL_DELETE_CONDITION)
public class CommentEntity extends BaseEntity{

    @Column(columnDefinition = "TEXT")
    String content;

    @Column
    Long parentId;

    @ManyToOne
    @JoinColumn(name = "challenger_id", nullable = false)
    ChallengerEntity challenger;

    @Column(name = "left_position")
     Integer left;

    @Column(name = "right_position")
     Integer right;

    @ManyToOne
    @JoinColumn(name = "solution_id",nullable = false)
    SolutionEntity solution;

    @Column
    Boolean isEdit;

    @Column
    int replies;

    @Column
    String commentId;



    @OneToMany(mappedBy = "parentId", cascade = CascadeType.ALL)
    private List<CommentEntity> children = new ArrayList<>();

}
