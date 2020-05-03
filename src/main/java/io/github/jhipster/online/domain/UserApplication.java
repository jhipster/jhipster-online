package io.github.jhipster.online.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserApplication.
 */
@Entity
@Table(name = "user_application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "shared")
    private Boolean shared;

    @Column(name = "shared_link")
    private String sharedLink;

    @Column(name = "user_id")
    private String userId;

    @Lob
    @Column(name = "configuration")
    private String configuration;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UserApplication name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isShared() {
        return shared;
    }

    public UserApplication shared(Boolean shared) {
        this.shared = shared;
        return this;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public String getSharedLink() {
        return sharedLink;
    }

    public UserApplication sharedLink(String sharedLink) {
        this.sharedLink = sharedLink;
        return this;
    }

    public void setSharedLink(String sharedLink) {
        this.sharedLink = sharedLink;
    }

    public String getUserId() {
        return userId;
    }

    public UserApplication userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConfiguration() {
        return configuration;
    }

    public UserApplication configuration(String configuration) {
        this.configuration = configuration;
        return this;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserApplication)) {
            return false;
        }
        return id != null && id.equals(((UserApplication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserApplication{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shared='" + isShared() + "'" +
            ", sharedLink='" + getSharedLink() + "'" +
            ", userId='" + getUserId() + "'" +
            ", configuration='" + getConfiguration() + "'" +
            "}";
    }
}
