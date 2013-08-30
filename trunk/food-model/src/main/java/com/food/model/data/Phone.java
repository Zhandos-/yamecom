/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model.data;

import com.food.model.enums.EnumPhoneType;
import com.food.model.AEntity;
import com.food.model.Conf;
import com.food.model.user.User;
import com.food.model.restaurant.Restaurant;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author daniyar.artykov
 */
@Entity
@Table(name = Conf.TABLE_PREFIX + "phone")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Phone extends AEntity {

    private static final long serialVersionUID = 2029029446092242780L;
    private String number;
    private EnumPhoneType phoneType;
    private User user;
    private Restaurant restaurant;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_seq")
    @SequenceGenerator(name = "phone_seq", sequenceName = "phone_seq", initialValue = 1, allocationSize = 1)
    @Override
    public Long getId() {
        return id;
    }

    @Column(name = "number")
    @Size(max = 1000)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public EnumPhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(EnumPhoneType phoneType) {
        this.phoneType = phoneType;
    }

    @Basic(optional = false)
    @ManyToOne(cascade = CascadeType.ALL)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic(optional = true)
    @ManyToOne(cascade = CascadeType.ALL)
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Phone)) {
            return false;
        }
        Phone other = (Phone) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.food.model.data.Phone[ id=" + id + " ]";
    }
}
