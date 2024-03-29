package com.km086.server.model.apk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.km086.server.model.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "APKVERSION")
public class ApkVersion implements Serializable {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    protected Long id;

    @Column(name = "VERSIONCODE")
    protected Integer versionCode;

    @Column(name = "VERSIONNAME")
    protected String versionName;

    @Column(name = "DOWNLOADURL")
    @Size(min = 0, max = 255)
    protected String downloadUrl;

    private static final long serialVersionUID = -6830704513295992902L;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((versionCode == null) ? 0 : versionCode.hashCode());
        result = prime * result + ((versionName == null) ? 0 : versionName.hashCode());
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
        ApkVersion other = (ApkVersion) obj;
        if (versionCode == null) {
            if (other.versionCode != null)
                return false;
        } else if (!versionCode.equals(other.versionCode))
            return false;
        if (versionName == null) {
            if (other.versionName != null)
                return false;
        } else if (!versionName.equals(other.versionName))
            return false;
        return true;
    }

}
