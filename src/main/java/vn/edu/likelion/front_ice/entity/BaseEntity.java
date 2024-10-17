package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import vn.edu.likelion.front_ice.common.utils.HelperUtil;
import vn.edu.likelion.front_ice.security.SecurityUtil;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * BaseEntity -
 *
 * @param
 * @return
 * @throws
 */
@MappedSuperclass
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseEntity implements Serializable {

    @Id
    @UuidGenerator
    String id;

    @Column(nullable = false, updatable = false)
    LocalDateTime createAt;

    @Column(nullable = true, insertable = false)
    LocalDateTime updateAt;

    @Column
    String createdBy;

    @Column
    String updatedBy;

    @Column(nullable = false)
    int isDeleted;

    @PrePersist
    protected void onCreate() {

        if (id == null) {
            id = HelperUtil.getUUID();
        }
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();

        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent()
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();

        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent()
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
    }
}
