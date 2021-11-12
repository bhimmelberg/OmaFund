/**
 */
package util;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import datamodel.UserModel;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @since JavaSE-1.8
 */
public class UtilDB {
   static SessionFactory sessionFactory = null;

   public static SessionFactory getSessionFactory() {
      if (sessionFactory != null) {
         return sessionFactory;
      }
      Configuration configuration = new Configuration().configure();
      StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
      sessionFactory = configuration.buildSessionFactory(builder.build());
      return sessionFactory;
   }

   public static List<UserModel> listAppointments() {
      List<UserModel> resultList = new ArrayList<UserModel>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;  // each process needs transaction and commit the changes in DB.

      try {
         tx = session.beginTransaction();
         List<?> appointments = session.createQuery("FROM AppointmentKucera").list();
         for (Iterator<?> iterator = appointments.iterator(); iterator.hasNext();) {
            UserModel appointment = (UserModel) iterator.next();
            resultList.add(appointment);
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }

   public static List<UserModel> listAppointments(String keyword) {
      List<UserModel> resultList = new ArrayList<UserModel>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;

      try {
         tx = session.beginTransaction();
         System.out.println((UserModel)session.get(UserModel.class, 1)); // use "get" to fetch data
        // Query q = session.createQuery("FROM Employee");
         List<?> appointments = session.createQuery("FROM AppointmentKucera").list();
         for (Iterator<?> iterator = appointments.iterator(); iterator.hasNext();) {
            UserModel appointment = (UserModel) iterator.next();
            if (appointment.getDate().startsWith(keyword)) {
               resultList.add(appointment);
            }
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }

   public static void createAppointments(String DATE, String TIME, String LOCATION, String DESCRIPTION) {
      Session session = getSessionFactory().openSession();
      Transaction tx = null;
      try {
         tx = session.beginTransaction();
         session.save(new UserModel(DATE, TIME, LOCATION, DESCRIPTION));
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
   }
}
