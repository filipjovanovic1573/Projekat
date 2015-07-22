/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.dao;

import com.it250.projekat.entities.User;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Workbench
 */
public class UserDaoImpl extends GenericDaoImpl implements UserDao {

    @Override
    public User checkUser(String email, String password) {
        return (User) session.createCriteria(User.class).add(Restrictions.eq("email", email)).add(Restrictions.eq("password", password)).uniqueResult();
    }

    @Override
    public boolean checkEmail(String email, int id) {
        long count = (Long) session.createCriteria(User.class).add(
                Restrictions.eq("email", email)).add(
                        Restrictions.ne("id", id)).setProjection(Projections.rowCount()).uniqueResult();
        System.out.println("checkEmail " + count);
        return count == 0;
    }

    @Override
    public boolean checkUsername(String username, int id) {
        long count = (Long) session.createCriteria(User.class).add(
                Restrictions.eq("username", username)).add(
                        Restrictions.ne("id", id)).setProjection(Projections.rowCount()).uniqueResult();

        System.out.println("checkUsername " + count);
        return count == 0;
    }

    @Override
    public boolean checkEmail(String email) {
        long count = (Long) session.createCriteria(User.class).add(
                Restrictions.eq("email", email)).setProjection(Projections.rowCount()).uniqueResult();
        return count == 0;
    }

    @Override
    public boolean checkUsername(String username) {
        long count = (Long) session.createCriteria(User.class).add(
                Restrictions.eq("username", username)).setProjection(Projections.rowCount()).uniqueResult();
        return count == 0;
    }

    @Override
    public User getUserById(int id) {
        return (User) session.createCriteria(User.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

}
