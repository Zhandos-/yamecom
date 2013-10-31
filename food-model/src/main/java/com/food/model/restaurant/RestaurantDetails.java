/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model.restaurant;

import com.food.model.AEntity;
import com.food.model.Conf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    private Double minOrderPrice;
    private Date startTime;
    private Date endTime;
    private DeliveryPrice deliveryPrice;
    private Boolean deliveryFree;
    private Boolean payByCard;
    private Integer deliveryTime;
    private String facilityType;
    private String logo;
    private List<RestaurantType> types = new ArrayList<RestaurantType>();

    /**
     *
     * @return RestaurantDetails
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_details_seq")
    @SequenceGenerator(name = "restaurant_details_seq", sequenceName = "restaurant_details_seq", initialValue = 1, allocationSize = 1)
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

    @Column(name = "delivery_time")
    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Column(name = "facility_type")
    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    @ManyToMany(cascade = {javax.persistence.CascadeType.MERGE,
        javax.persistence.CascadeType.PERSIST,
        javax.persistence.CascadeType.REFRESH})
    @JoinTable(name = Conf.TABLE_PREFIX + "restaurant_types")
    public List<RestaurantType> getTypes() {
        return types;
    }

    public void setTypes(List<RestaurantType> types) {
        this.types = types;
    }

    public void setTypes(RestaurantType[] types) {
        this.types.addAll(Arrays.asList(types));
    }
}
