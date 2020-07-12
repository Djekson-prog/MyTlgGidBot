package by.evgkor.TouristBot.dao;

import by.evgkor.TouristBot.exception.NoSuchCityException;
import by.evgkor.TouristBot.model.City;
import by.evgkor.TouristBot.util.HibernateFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CityDao implements CrudDao{
    @Override
    public City findByName(String name) throws NoSuchCityException {
        Transaction transaction = null;
        try (Session session = HibernateFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<City> query = builder.createQuery(City.class);
            Root<City> root = query.from(City.class);
            query.select(root).where(builder.equal(root.get("name"), name));
            Query<City> q = session.createQuery(query);
            City city = q.getSingleResult();
            transaction.commit();
            return city;
        }
        catch (NoResultException e){
            throw new NoSuchCityException();
        }
    }

    @Override
    public void save(City city) {
        Session session = HibernateFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(city);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(City city) {
        Session session = HibernateFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(city);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(City city) {
        Session session = HibernateFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(city);
        tx1.commit();
        session.close();
    }
}
