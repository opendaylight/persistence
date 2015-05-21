package org.opendaylight.persistence.util.common.model;


import java.io.Serializable;

import org.opendaylight.persistence.util.common.Identifiable;
import org.opendaylight.persistence.util.common.type.Id;

import com.google.common.base.Objects;


/**
 * Abstract identifiable.
 * 
 * @param <T> type of the identified object
 * @param <I> type of the id. This type should be immutable and it is critical it implements
 *            {@link Object#equals(Object)} and {@link Object#hashCode()} correctly.
 * @author Fabiel Zuniga
 */
public abstract class AbstractIdentifiable<T, I extends Serializable> implements Identifiable<T, I> {

    private final Id<? extends T, I> id;

    /**
     * Creates an identifiable object with no id.
     */
    protected AbstractIdentifiable() {
        this(null);
    }

    /**
     * Creates an identifiable object.
     * 
     * @param id object's id
     */
    protected AbstractIdentifiable(Id<? extends T, I> id) {
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

        AbstractIdentifiable<?, ?> other = (AbstractIdentifiable<?, ?>)obj;
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

