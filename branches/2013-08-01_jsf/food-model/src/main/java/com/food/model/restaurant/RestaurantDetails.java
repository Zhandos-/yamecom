/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model.restaurant;

import com.food.model.AEntity;
import com.food.model.Conf;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = Conf.TABLE_PREFIX + "restaurant_details")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class RestaurantDetails extends AEntity {

    private static final long serialVersionUID = 167153939075005557L;
    private Restaurant restaurant;
    private RestaurantType restaurantType;
    private Double minOrderPrice;
    private Date startTime;
    private Date endTime;
    private DeliveryPrice deliveryPrice;
    private Boolean deliveryFree;
    private Boolean payByCard;

    /**
     *
     * @return RestaurantDetails
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_details_seq")
    @SequenceGenerator(name = "restaurant_details_seq", sequenceName = Conf.TABLE_PREFIX + "restaurant_details_seq", initialValue = 1, allocationSize = 1)
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

    @OneToOne(cascade = CascadeType.ALL)
    public RestaurantType getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(RestaurantType restaurantType) {
        this.restaurantType = restaurantType;
    }

    @Column(name = "min_order_price")
    public Double getMinOrderPrice() {
        return minOrderPrice;
    }

    public void setMinOrderPrice(Double minOrderPrice) {
        this.minOrderPrice = minOrderPrice;
    }

    @Column(name = "start_time")
    @Temporal(TemporalType.TIME)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time")
    @Temporal(TemporalType.TIME)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public DeliveryPrice getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(DeliveryPrice deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    @Column(name = "delivery_free")
    public Boolean getDeliveryFree() {
        return deliveryFree;
    }

    public void setDeliveryFree(Boolean deliveryFree) {
        this.deliveryFree = deliveryFree;
    }

    @Column(name = "pay_by_card")
    public Boolean getPayByCard() {
        return payByCard;
    }

    public void setPayByCard(Boolean payByCard) {
        this.payByCard = payByCard;
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
        RestaurantDetails other = (RestaurantDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.food.model.restaurant.RestaurantDetails[ id=" + id + " ]";
    }
}
