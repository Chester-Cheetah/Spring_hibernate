package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      Session session = sessionFactory.getCurrentSession();
      String HQL = "FROM User WHERE firstName = :userFirstName and lastName = :userLastName and email = :userEmail";
      Query <User> query = session.createQuery(HQL);
      query.setParameter("userFirstName", user.getFirstName());
      query.setParameter("userLastName", user.getLastName());
      query.setParameter("userEmail", user.getEmail());
      User u = null;
      try {
         u = query.getSingleResult();
         System.out.println("Пользователь с таким именем и email существует в базе данных");
      } catch (NoResultException e) {
         session.save(user);
      }
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Transactional
   @Override
   public User getUser(Car car) {
      Session session = sessionFactory.getCurrentSession();
      String HQL = "FROM User WHERE car = :c";
      Query<User> query = session.createQuery(HQL);
      query.setParameter("c", car);
      User result;
      try {
         result = query.getSingleResult();
      } catch (NoResultException e) {
         System.out.println("Владелец машины не найден");
         return null;
      }
      return result;
   }

   @Transactional
   @Override
   public User getUser(String model, int series) {
      return getUser(new Car(model, series));
   }
}
