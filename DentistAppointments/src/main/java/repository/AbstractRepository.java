package repository;

import model.Identifiable;

import java.util.*;

public abstract class AbstractRepository <ID,T extends Identifiable<ID>> implements Repository<ID,T>
{
    private Map<ID,T> map;
    public AbstractRepository()
    {
        this.map=new TreeMap<>();
    }
    public T add(T newT)
    {
        if(map.containsKey(newT.getID()))
        {
            throw new RuntimeException("The item is already in the repository.");
        }
        else
        {
            map.put(newT.getID(), newT);
        }
        return newT;
    }
    public void delete(T delT)
    {
        if(map.containsKey(delT.getID()))
        {
            map.remove(delT.getID());
        }
        else
        {
            throw new RuntimeException("The item is not in the repository.");
        }
    }
    public void update(ID id, T newT)
    {
        if(map.containsKey(id))
        {
            map.put(id, newT);
        }
        else
        {
            throw new RuntimeException("The item is not in the repository.");
        }
    }
    public T findByID(ID id)
    {
        if(map.containsKey(id))
            return map.get(id);
        else
            return null;

    }
    public Iterable<T> findAll()
    {
        return map.values();
    }

    public Collection<T> getAll()
    {
        return map.values();
    }
}
