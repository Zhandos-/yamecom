package com.food.model.auth;

import com.food.model.AEntity;
import com.food.model.Conf;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = Conf.TABLE_PREFIX + "auth_user")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class User extends AEntity {

    private static final long serialVersionUID = -105633019920728916L;
    private String loginName;
    private String loginPassword;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String fullName;
    private String customsAuthority;
    private String position;
    private Date registeredDate;
    private String email;
    private boolean active = true;
    private Set<Role> roles = new HashSet<Role>();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Size(max = 255)
    public String getCustomsAuthority() {
        return customsAuthority;
    }

    public void setCustomsAuthority(String customsAuthority) {
        this.customsAuthority = customsAuthority;
    }

    @Size(max = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(max = 255)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Size(max = 255)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Size(max = 255)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Size(max = 255)
    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    @Size(max = 255)
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Column(length = 4000)
    @Size(max = 4000)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    @ManyToMany(cascade = {javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.REFRESH})
    @JoinTable(name = Conf.TABLE_PREFIX + "auth_user_role")
    public Set<Role> getRoles() {
        return roles;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
                append(this.getLoginName(), other.getLoginName()).
                append(this.getFirstName(), other.getFirstName()).
                append(this.getLastName(), other.getLastName()).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(23, 57).append(getId()).
                append(this.getLoginName()).
                append(this.getFirstName()).
                append(this.getLastName()).
                toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).
                append("loginName", this.getLoginName()).
                append("firstName", this.getFirstName()).
                append("lastName", this.getLastName()).
                toString();
    }

    @Transient
    public synchronized String getDisplayName() {
        return getDisplayFullName() + " [" + loginName + "]";
    }

    @Transient
    public synchronized String getDisplayFullName() {
        return ((lastName == null) ? "" : lastName) + " " + ((firstName == null) ? "" : firstName) + " " + ((patronymic == null) ? "" : patronymic);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_user_seq")
    @SequenceGenerator(name = "auth_user_seq", sequenceName = Conf.TABLE_PREFIX + "auth_user_seq", initialValue = 1, allocationSize = 1)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public synchronized boolean hasRole(ERole role) {
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
