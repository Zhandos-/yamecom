package com.food.model.user;

import com.food.model.enums.EnumRole;
import com.food.model.AEntity;
import com.food.model.Conf;
import com.food.model.additional.Comment;
import com.food.model.additional.Rating;
import com.food.model.data.Phone;
import com.food.model.food.Order;
import com.food.model.restaurant.Restaurant;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = Conf.TABLE_PREFIX + "user")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class User extends AEntity {

    private static final long serialVersionUID = -105633019920728916L;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Date creationDate;
    private boolean active = true;
    private List<Comment> comments;
    private List<Rating> ratings;
    private List<Restaurant> restaurants;
    private List<Order> orders;
    private Set<Role> roles = new HashSet<Role>();
    private List<Phone> phones;

    @Column(name = "name")
    @Size(max = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "surname")
    @Size(max = 255)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "email")
    @Size(max = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    @Size(max = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @ManyToMany(cascade = {javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.REFRESH})
    @JoinTable(name = Conf.TABLE_PREFIX + "user_role")
    public Set<Role> getRoles() {
        return roles;
    }

    @Basic(optional = true)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Basic(optional = true)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    @Basic(optional = true)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Transient
    public List<Role> getRolesAsList() {
        List<Role> l = new ArrayList<Role>();
        l.addAll(getRoles());
        return l;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
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

        final User other = (User) obj;
        return new EqualsBuilder().append(this.getId(), other.getId()).
                append(this.getEmail(), other.getEmail()).
                append(this.getName(), other.getName()).
                append(this.getSurname(), other.getSurname()).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(23, 57).append(getId()).
                append(this.getEmail()).
                append(this.getName()).
                append(this.getSurname()).
                toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).
                append("email", this.getEmail()).
                append("name", this.getName()).
                append("surname", this.getSurname()).
                toString();
    }

    @Transient
    public synchronized String getDisplayName() {
        return getDisplayFullName() + " [" + email + "]";
    }

    @Transient
    public synchronized String getDisplayFullName() {
        return ((surname == null) ? "" : surname) + " " + ((name == null) ? "" : name);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", initialValue = 1, allocationSize = 1)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public synchronized boolean hasRole(EnumRole role) {
        boolean hasRole = false;
        if (role != null && !getRoles().isEmpty()) {
            Iterator<Role> iterator = getRoles().iterator();
            while (iterator.hasNext()) {
                Role r = iterator.next();
                if (r != null && r.getName() != null && r.getName().equals(role)) {
                    hasRole = true;
                    break;
                }
            }
        }
        return hasRole;
    }
}
