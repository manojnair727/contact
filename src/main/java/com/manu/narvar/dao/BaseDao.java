package com.manu.narvar.dao;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * Default implementation of the generic Hibernate data access object. DAOs for
 * domain objects can should this class.
 * 
 * @param <T>
 *            the class of the domain object this DAO supports
 */
@Repository
public class BaseDao<T>  {

	private Class<T> persistentClass;

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Create generic DAO supporting no domain object.
	 */
	public BaseDao() {

		super();
	}

	/**
	 * Create a DAO support instances of type persistentClass.
	 * 
	 * @param persistentClass
	 *            the class of the domain object this DAO will support
	 */
	public BaseDao(final Class<T> persistentClass) {

		super();
		this.persistentClass = persistentClass;
	}

	/**
	 * Sets the session factory.
	 * 
	 * @param sessionFactory
	 *            the new session factory
	 */
	public void setSessionFactory(final SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;
	}

	
	public SessionFactory getSessionFactory() {

		return this.sessionFactory;
	}

	/**
	 * Gets the session.
	 * 
	 * @return the session
	 */
	protected Session getSession() {
		
		return sessionFactory.getCurrentSession();  
		           
	}

	/**
	 * Sets the persistent class.
	 * 
	 * @param persistentClass
	 *            the new persistent class
	 */
	public void setPersistentClass(final Class<T> persistentClass) {

		this.persistentClass = persistentClass;
	}

	
	public Class<T> getPersistentClass() {

		return this.persistentClass;
	}

	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {

		return this.getSession().createCriteria(this.getPersistentClass()).list();
	}


	public T getById(final Long id) {

		return this.fetchById(id);
	}

	
	public T fetchById(final Long id) {

		return (T) this.getSession().get(this.getPersistentClass(), id);
	}

	
	@SuppressWarnings("unchecked")
	public T loadById(final Long id) {

		return (T) this.getSession().load(this.getPersistentClass(), id);
	}

	
	@SuppressWarnings("unchecked")
	public T loadById(final Long id, final LockOptions lockOptions) {

		return (T) this.getSession().load(this.getPersistentClass(), id, lockOptions);

	}

	
	public void save(final T o) {

		this.getSession().saveOrUpdate(o);
	}

	
	public void remove(final T o) {

		this.getSession().delete(o);
	}

	
	public void evict(final T o) {

		this.getSession().evict(o);
	}

	
	public void flush() {

		this.getSession().flush();
	}

	
	public void clear() {

		
		this.getSession().clear();
	}

}
