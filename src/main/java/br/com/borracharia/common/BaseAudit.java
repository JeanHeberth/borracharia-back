package br.com.borracharia.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@Setter
public abstract class BaseAudit {
    @CreatedDate
    @Field("created_at") protected Instant createdAt;
    @CreatedBy
    @Field("created_by")  protected String createdBy; // username (login)
}
