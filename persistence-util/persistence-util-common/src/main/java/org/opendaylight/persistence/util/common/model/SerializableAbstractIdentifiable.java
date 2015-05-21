package org.opendaylight.persistence.util.common.model;

import java.io.Serializable;

import org.opendaylight.persistence.util.common.Identifiable;
import org.opendaylight.persistence.util.common.type.Id;

import com.google.common.base.Objects;

public abstract class SerializableAbstractIdentifiable<T, I extends Serializable> implements Identifiable<T, I>, Serializable {
    private static final long serialVersionUID = 1L;

    private final Id<? extends T, I> id;

    /**
     * Creates an identifiable object with no id.
     */
    protected SerializableAbstractIdentifiable() {
        this(null);
    }

    /**
     * Creates an identifiable object.
     * 
     * @param id object's id
     */
    protected SerializableAbstractIdentifiable(Id<? extends T, I> id) {
        this.id = id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends T> Id<E, I> getId() {
        return (Id<E, I>)this.id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        SerializableAbstractIdentifiable<?, ?> other = (SerializableAbstractIdentifiable<?, ?>)obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        }
        else if (!this.id.equals(other.id)) {
            return false;
        }

        return true;
    }

    @Override
	public String toString() {
		return Objects.toStringHelper(this.getClass()).add("id", this.id)
				.toString();
	}
}

