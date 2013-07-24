package com.food.model.auth;

import com.food.model.AEntity;
import com.food.model.Conf;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = Conf.TABLE_PREFIX + "auth_role")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Role extends AEntity {

    private static final long serialVersionUID = 1340561732210722997L;
    private ERole name;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

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

        final Role other = (Role) obj;
        return new EqualsBuilder().
                append(this.getName(), other.getName()).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(7, 67).
                append(this.getName()).
                toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("name", this.getName()).
                toString();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_role_seq")
    @SequenceGenerator(name = "auth_role_seq", sequenceName = Conf.TABLE_PREFIX + "auth_role_seq", initialValue = 1, allocationSize = 1)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
