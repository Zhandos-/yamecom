package com.food.model;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@MappedSuperclass
public abstract class AEntity implements Serializable, IEntity {

    private static final long serialVersionUID = 7952773835310430717L;
    protected Long id;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!obj.getClass().isAssignableFrom(getClass())) {
            return false;
        }

        final AEntity other = (AEntity) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).
                append(this.getId(), other.getId()).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 37).append(getId()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }
}
