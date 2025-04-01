package io.github.jhipster.online.domain;

import java.util.Objects;

public abstract class BaseEntity<T> {

    public abstract T getId();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same reference
        if (o == null || getClass() != o.getClass()) return false; // Null or different class
        BaseEntity<?> that = (BaseEntity<?>) o;
        if (this.getId() == null || that.getId() == null) return false;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
