/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model.restaurant;

import com.food.model.AEntity;
import com.food.model.Conf;
import com.food.model.data.District;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = Conf.TABLE_PREFIX + "delivery_price")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class DeliveryPrice extends AEntity {

    private static final long serialVersionUID = -4825496194057761237L;
    private District district;
    private Double price;
    private Double discount;
    private Double discountPrice;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_price_seq")
    @SequenceGenerator(name = "delivery_price_seq", sequenceName = "delivery_price_seq", initialValue = 1, allocationSize = 1)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "discount")
    @Basic(optional = true)
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Column(name = "discount_price")
    @Basic(optional = true)
    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
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
        if (!(object instanceof DeliveryPrice)) {
            return false;
        }
        DeliveryPrice other = (DeliveryPrice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.food.model.restaurant.DeliveryPrice[ id=" + id + " ]";
    }
}
