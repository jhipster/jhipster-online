package io.github.jhipster.online.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Language.
 */
@Entity
@Table(name = "language")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "lang_key", nullable = false)
    private String langKey;

    @ManyToMany(mappedBy = "languages")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<YoRC> yorcs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLangKey() {
        return langKey;
    }

    public Language langKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<YoRC> getYorcs() {
        return yorcs;
    }

    public Language yorcs(Set<YoRC> yoRCS) {
        this.yorcs = yoRCS;
        return this;
    }

    public Language addYorcs(YoRC yoRC) {
        this.yorcs.add(yoRC);
        yoRC.getLanguages().add(this);
        return this;
    }

    public Language removeYorcs(YoRC yoRC) {
        this.yorcs.remove(yoRC);
        yoRC.getLanguages().remove(this);
        return this;
    }

    public void setYorcs(Set<YoRC> yoRCS) {
        this.yorcs = yoRCS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Language language = (Language) o;
        if (language.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), language.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Language{" +
            "id=" + getId() +
            ", langKey='" + getLangKey() + "'" +
            "}";
    }
}
