/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model.restaurant;

import com.food.model.enums.EnumRestaurantType;
import com.food.model.AEntity;
import com.food.model.Conf;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author daniyar.artykov
 */
@Entity
@Table(name = Conf.TABLE_PREFIX + "restaurant_type")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class RestaurantType extends AEntity {

    private static final long serialVersionUID = 167153939075005557L;
    private EnumRestaurantType type;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_type_seq")
    @SequenceGenerator(name = "restaurant_type_seq", sequenceName = Conf.TABLE_PREFIX + "restaurant_type_seq", initialValue = 1, allocationSize = 1)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    public EnumRestaurantType getType() {
        return type;
    }

    public void setType(EnumRestaurantType type) {
        this.type = type;
    }

    @Basic(optional = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RestaurantType)) {
            return false;
        }
        RestaurantType other = (RestaurantType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.food.model.restaurant.RestaurantType[ id=" + id + " ]";
    }
}
