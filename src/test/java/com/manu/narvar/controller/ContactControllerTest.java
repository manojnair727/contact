package com.manu.narvar.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.manu.narvar.model.Contact;
import com.manu.narvar.model.CountAndList;
import com.manu.narvar.service.ContactService;

public class ContactControllerTest {
	
	@Mock
	private ContactService contactService;
	
	private ContactController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.controller = new ContactController();
		ReflectionTestUtils.setField(this.controller, "contactService", this.contactService);
	}

	@Test
	public void list() throws JsonProcessingException, IOException {
		
		int pageNumber=0;
		int pageSize=10;
		Contact c = new Contact("Anju", "emailId", "junit");
		List<Contact> contacts = new ArrayList<Contact>();
		contacts.add(c);
		c = new Contact("Asin", "asin", "junit");
		contacts.add(c);
		
		Mockito.when(contactService.listContact(pageNumber, pageSize)).thenReturn(new CountAndList<Contact>(2, contacts));
		
		Map<String, Object> response = controller.listContact(pageNumber, pageSize);
		
		assertEquals(2, response.get("count"));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> contactListJson= (List<Map<String, Object>>) response.get("contacts");
		assertEquals("Anju", contactListJson.get(0).get("name"));
		assertEquals("Asin", contactListJson.get(1).get("name"));
	}
	
	@Test
	public void create() throws JsonProcessingException, IOException {
		
		Contact c = new Contact("Harsha K", "harsha", "junit");
		c.setId(2L);
		
		Mockito.when(contactService.createContact("harsha", "Harsha K", "Art Director", "junit")).thenReturn(c);
		
		Map<String, Object> response = controller.createContact("{\"emailId\":\"harsha\",\"name\":\"Harsha K\",\"profession\":\"Art Director\",\"user\":\"junit\"}");
		
		assertEquals(2L, response.get("contactId"));
	}
	
	@Test
	public void update() throws JsonProcessingException, IOException {
		
		Contact c = new Contact("Anju", "emailId", "junit");
		c.setId(2L);
		
		Mockito.when(contactService.updateContact(2L, "Art Director", "junit")).thenReturn(c);
		
		String response = controller.updateContact("{\"contactId\":2,\"profession\":\"Art Director\",\"user\":\"junit\"}");
		
		assertEquals(response, "{\"contactId\":2}");
	}
}
