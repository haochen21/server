package com.km086.server.model.store;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.km086.server.model.Constants;
import com.km086.server.model.security.Merchant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CATEGORY", uniqueConstraints = {
        @UniqueConstraint(name = "UNQ_CATEGORY_MERCHANT_NAME", columnNames = {"MERCHANT_ID", "NAME"})}, indexes = {
        @Index(name = "IDX_CATEGORY_MERCHANT", columnList = "MERCHANT_ID")})
public class Category implements Serializable {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    protected Long id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "DESCRIPTION")
    @Size(min = 0, max = 255)
    protected String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    protected Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.CreationTimestamp
    protected Date updatedOn;

    @Column(name = "SEQUENCE")
    protected Integer sequence;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANT_ID", nullable = false)
    protected Merchant merchant;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    protected Collection<Product> products = new ArrayList<Product>();

    @Version
    protected long version;

    private static final long serialVersionUID = -7363057544755885418L;

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
        Category other = (Category) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Category [name=" + name + ", merchant=" + merchant + "]";
    }
}
