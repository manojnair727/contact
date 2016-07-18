package com.manu.narvar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.manu.narvar.dao.ContactDao;
import com.manu.narvar.model.Contact;
import com.manu.narvar.model.CountAndList;

@Service
public class ContactService {
	
	@Autowired
	private ContactDao contactDao;

	@Transactional(readOnly=true)
	public CountAndList<Contact> listContact(int pageNumber, int pageSize) {
		
		return new CountAndList<Contact>(contactDao.countContact(), contactDao.listContact(pageNumber, pageSize));
	}
	
	@Transactional
	public Contact createContact(String emailId, String name, String profession, String createdBy) {
		
		Contact contact = new Contact(name, emailId, createdBy);
		contact.setProfession(profession);
		contactDao.save(contact);
		return contact;
	}
	
	@Transactional
	public Contact updateContact(Long contactId, String profession, String updatedBy) {
		
		Contact contact = contactDao.loadById(contactId);
		
		contact.setProfession(profession);
		contact.updateAudit(updatedBy);
		contactDao.save(contact);
		return contact;
	}
	
	@Transactional
	public Contact deleteContact(Long contactId) {
		
		Contact contact = contactDao.loadById(contactId);
		contactDao.remove(contact);
		return contact;
	}
}
