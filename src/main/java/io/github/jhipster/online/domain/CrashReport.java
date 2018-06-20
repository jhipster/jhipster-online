package io.github.jhipster.online.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A CrashReport.
 */
@Entity
@Table(name = "crash_report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrashReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private Instant date;

    @Column(name = "source")
    private String source;

    @Column(name = "env")
    private String env;

    @Column(name = "stack")
    private String stack;

    @Column(name = "yorc")
    private String yorc;

    @Column(name = "jdl")
    private String jdl;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public CrashReport date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public CrashReport source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEnv() {
        return env;
    }

    public CrashReport env(String env) {
        this.env = env;
        return this;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getStack() {
        return stack;
    }

    public CrashReport stack(String stack) {
        this.stack = stack;
        return this;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public String getYorc() {
        return yorc;
    }

    public CrashReport yorc(String yorc) {
        this.yorc = yorc;
        return this;
    }

    public void setYorc(String yorc) {
        this.yorc = yorc;
    }

    public String getJdl() {
        return jdl;
    }

    public CrashReport jdl(String jdl) {
        this.jdl = jdl;
        return this;
    }

    public void setJdl(String jdl) {
        this.jdl = jdl;
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
        CrashReport crashReport = (CrashReport) o;
        if (crashReport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), crashReport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CrashReport{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", source='" + getSource() + "'" +
            ", env='" + getEnv() + "'" +
            ", stack='" + getStack() + "'" +
            ", yorc='" + getYorc() + "'" +
            ", jdl='" + getJdl() + "'" +
            "}";
    }
}
