package com.tavish.voice.reco.core.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseModel implements Serializable {
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifyTime = new Date();
    private String lastModifyByUser;
    private String createdByUser;
}
