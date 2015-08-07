/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.dao;

import com.it250.projekat.entities.AbstractEntity;
import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Workbench
 * @param <T>
 */
public class GenericDaoImpl<T extends AbstractEntity> implements GenericDao<T> {

    @Inject
    protected Session session;

    @Override
    public List<T> findAll(Class c) {
        return session.createCriteria(c).list();
    }

    @Override
    public void add(T t) {
        session.persist(t);
    }

    @Override
    public void remove(int id, Class c) {
        Object o = session.createCriteria(c).add(Restrictions.eq("id", id)).uniqueResult();
        session.delete(o);
        System.out.println(o.toString());
    }

    @Override
    public void merge(T t) {
        session.merge(t);
    }

    @Override
    public void update(T t){
        session.update(t);
    }
    
}
