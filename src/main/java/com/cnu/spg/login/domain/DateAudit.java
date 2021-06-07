package com.cnu.spg.login.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {
                "createdDate",
                "updatedDate"
        },
        allowGetters = true
)
public abstract class DateAudit implements Serializable {
    @CreatedDate
    @Column(name = "create_date")
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private Instant updatedDate;
}
