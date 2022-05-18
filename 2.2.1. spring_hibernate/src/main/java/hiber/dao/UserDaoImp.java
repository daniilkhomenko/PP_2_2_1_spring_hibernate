package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().persist(user);
   }

   @Override
   public List<User> listUsers() {
      return sessionFactory.getCurrentSession().createQuery("select u from User u", User.class).getResultList();
   }

   @Override
   public List<User> getUserByCar(String model, int series) {
      TypedQuery<User> query = sessionFactory.getCurrentSession()
              .createQuery("SELECT u FROM Car c INNER JOIN c.user u " +
                      "WHERE c.model = :model " +
                      "AND c.series = :series", User.class)
              .setParameter("model", model)
              .setParameter("series", series);
      return query.getResultList();
   }
}
