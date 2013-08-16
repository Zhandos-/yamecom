/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model.data;

import com.food.model.AEntity;
import com.food.model.Conf;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
@Table(name = Conf.TABLE_PREFIX + "city")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class District extends AEntity {

    private static final long serialVersionUID = 3983317731223754041L;
    private String name;
    private City city;
    private String zipCode;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_seq")
    @SequenceGenerator(name = "city_seq", sequenceName = Conf.TABLE_PREFIX + "city_seq", initialValue = 1, allocationSize = 1)
    @Override
    public Long getId() {
        return id;
    }

    @Column(name = "district_name")
    @Size(max = 1000)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "zip_code")
    @Size(max = 255)
    public String getZipCode() {
        return zipCode;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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
        if (!(object instanceof District)) {
            return false;
        }
        District other = (District) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.food.model.data.City[ id=" + id + " ]";
    }
}
