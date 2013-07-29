/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model.restaurant;

import com.food.model.AEntity;
import com.food.model.Conf;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author daniyar.artykov
 */
@Entity
@Table(name = Conf.TABLE_PREFIX + "special_offer")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class SpecialOffer extends AEntity {

    private static final long serialVersionUID = 707823682885662073L;
    private Restaurant restaurant;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "special_offer_seq")
    @SequenceGenerator(name = "special_offer_seq", sequenceName = Conf.TABLE_PREFIX + "special_offer_seq", initialValue = 1, allocationSize = 1)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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
        if (!(object instanceof SpecialOffer)) {
            return false;
        }
        SpecialOffer other = (SpecialOffer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.food.model.restaurant.SpecialOffer[ id=" + id + " ]";
    }
}
