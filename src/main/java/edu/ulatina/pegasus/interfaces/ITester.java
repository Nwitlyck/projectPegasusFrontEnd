package edu.ulatina.pegasus.interfaces;

import java.util.List;

/**
 * @author PegasusTeam
 */
public interface ITester<T> {

    public List<T> testSelect();

    public T testSelectByPk(T objectTO);

    public void testInsert(T objectTO);

    public void testUpdate(T objectTO);

    public void testDelete(T objectTO);

}
