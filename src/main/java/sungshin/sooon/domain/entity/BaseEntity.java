package sungshin.sooon.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter //PostRepository DataJpa Test를 위해서 추가
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column //(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /*
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    */
}