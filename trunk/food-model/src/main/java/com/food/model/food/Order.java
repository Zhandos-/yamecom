/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model.food;

import com.food.model.restaurant.*;
import com.food.model.AEntity;
import com.food.model.Conf;
import com.food.model.enums.EnumPayType;
import com.food.model.user.User;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = Conf.TABLE_PREFIX + "order")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Order extends AEntity {

    private static final long serialVersionUID = -4369200794068787228L;
    private User user;
    //I think we can delete this parameter
    private Integer quantity;
    private EnumPayType payType;
    private Date orderDate;
    private String status;
    private Date statusDate;
    private Double orderPrice;
    private List<Food> foods;
    private Set<Restaurant> restaurants;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = Conf.TABLE_PREFIX + "order_seq", initialValue = 1, allocationSize = 1)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    public EnumPayType getPayType() {
        return payType;
    }

    public void setPayType(EnumPayType payType) {
        this.payType = payType;
    }

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "status_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    @Column(nullable = false, name = "order_price")
    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = Conf.TABLE_PREFIX + "food_order")
    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = Conf.TABLE_PREFIX + "restaurant_order")
    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
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
        Order other = (Order) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.food.model.food.Order[ id=" + id + " ]";
    }
}
