package com.km086.server.model.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.km086.server.model.Constants;
import com.km086.server.model.store.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "OPENRANGE", indexes = {@Index(name = "IDX_OPENRANGE_MERCHANT", columnList = "MERCHANT_ID")})
public class OpenRange implements Serializable {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    protected Long id;

    @NotNull
    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    protected Date beginTime;

    @NotNull
    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    protected Date endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    protected OpenRangeType type;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANT_ID", nullable = false)
    @JsonBackReference
    protected Merchant merchant;

    @ManyToMany(mappedBy = "openRanges")
    protected Set<Product> products = new HashSet<>();

    private static final long serialVersionUID = -4343731302412808649L;

    @Transient
    private int sequence;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OpenRange other = (OpenRange) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "OpenRange [beginTime=" + beginTime + ", endTime=" + endTime + "]";
    }

}
