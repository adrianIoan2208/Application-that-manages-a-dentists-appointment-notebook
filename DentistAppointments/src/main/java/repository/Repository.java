package repository;

import model.Identifiable;

import java.util.Collection;

public interface Repository<ID,T extends Identifiable<ID>> {
    public T add(T newT);
    public void delete(T delT);
    public void update(ID id, T newT);
    T findByID(ID id);

    Iterable<T> findAll();
    Collection<T> getAll();
}
