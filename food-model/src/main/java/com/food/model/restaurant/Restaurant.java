/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model.restaurant;

import com.food.model.AEntity;
import com.food.model.Conf;
import com.food.model.data.Address;
import com.food.model.data.Phone;
import com.food.model.food.FoodType;
import com.food.model.user.User;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author daniyar.artykov
 */
@Entity
@Table(name = Conf.TABLE_PREFIX + "restaurant")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Restaurant extends AEntity {

    private static final long serialVersionUID = 369037569587867117L;
    private String name;
    private String about;
    private Date creationDate;
    private User user;
    private List<Phone> phones;
    private Address address;
    private RestaurantDetails details;
    private List<FoodType> foodType;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    @SequenceGenerator(name = "restaurant_seq", sequenceName = "restaurant_seq", initialValue = 1, allocationSize = 1)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    @Size(max = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "about")
    @Size(max = 4000)
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public RestaurantDetails getDetails() {
        return details;
    }

    public void setDetails(RestaurantDetails details) {
        this.details = details;
    }

    @OneToMany(cascade = CascadeType.REFRESH, orphanRemoval = false)
    public List<FoodType> getFoodType() {
        return foodType;
    }

    public void setFoodType(List<FoodType> foodType) {
        this.foodType = foodType;
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
        if (!(object instanceof Restaurant)) {
            return false;
        }
        Restaurant other = (Restaurant) object;
        if ((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.food.model.restaurant.Restaurant[ id=" + id + " ]";
    }
}
