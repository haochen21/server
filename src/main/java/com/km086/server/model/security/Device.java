package com.km086.server.model.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.km086.server.model.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "DEVICE", indexes = {@Index(name = "IDX_DEVICE_NO", columnList = "NO")})
public class Device implements Serializable {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    protected Long id;

    @NotNull
    @Column(name = "NO", unique = true, nullable = false)
    protected String no;

    @NotNull
    @Column(name = "PHONE", unique = true, nullable = false)
    protected String phone;

    @Version
    protected long version;

    private static final long serialVersionUID = -5242433935424543234L;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((no == null) ? 0 : no.hashCode());
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
        Device other = (Device) obj;
        if (no == null) {
            if (other.no != null)
                return false;
        } else if (!no.equals(other.no))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Device [no=" + no + ", phone=" + phone + "]";
    }

}
