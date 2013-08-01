/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model.food;

import com.food.model.restaurant.*;
import com.food.model.AEntity;
import com.food.model.Conf;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author daniyar.artykov
 */
@Entity
@Table(name = Conf.TABLE_PREFIX + "food_type")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class FoodType extends AEntity {

    private static final long serialVersionUID = -3958103253554275726L;
    private String type;
    private Date creationDate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_type_seq")
    @SequenceGenerator(name = "food_type_seq", sequenceName = Conf.TABLE_PREFIX + "food_type_seq", initialValue = 1, allocationSize = 1)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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
        if (!(object instanceof RestaurantDetails)) {
            return false;
        }
        FoodType other = (FoodType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.food.model.food.FoodType[ id=" + id + " ]";
    }
}
