package br.com.uel.sistemadadosabertos.DAO;

import java.util.List;

public interface DAO<T> {
    
    public int create(T object);
    public T get(Object key);
    public List<T> getAll();
    public int update(T object);
    public int delete(Object key);
    
}
