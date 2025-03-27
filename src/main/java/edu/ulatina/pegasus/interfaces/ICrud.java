package edu.ulatina.pegasus.interfaces;

import java.util.List;

/**
 * @author PegasusTeam
 */
public interface ICrud<T> {

    public void insert(T objectTO) throws Exception;

    public void update(T objectTO) throws Exception;

    public void delete(T objectTO) throws Exception;

    public List<T> select() throws Exception;

    public T selectByPk(T objectTO) throws Exception;
    
}
