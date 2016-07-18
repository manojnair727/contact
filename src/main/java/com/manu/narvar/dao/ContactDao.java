package com.manu.narvar.dao;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import com.manu.narvar.model.Contact;

@Repository
public class ContactDao extends BaseDao<Contact> {
	
	public ContactDao(){
		super(Contact.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Contact> listContact(int pageNumber, int pageSize) {
		return this.getSession().createCriteria(Contact.class, "c").addOrder(Order.asc("name")).setFirstResult(pageNumber * pageSize).setMaxResults(
				pageSize).list();
	}
	
	public int countContact() {
		return ((Long) this
				.getSession()
				.createCriteria(this.getPersistentClass())
				.setProjection(Projections.count("id"))
				.uniqueResult()).intValue();
	}

}
