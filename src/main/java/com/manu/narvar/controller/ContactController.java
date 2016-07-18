package com.manu.narvar.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.manu.narvar.model.Contact;
import com.manu.narvar.model.CountAndList;
import com.manu.narvar.service.ContactService;

@Controller
@RequestMapping("/contact")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	

	@RequestMapping(value="/list", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> listContact(int pageNumber, int pageSize) {
		CountAndList<Contact> paginatedList = contactService.listContact(pageNumber, pageSize);
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("count", paginatedList.getCount());
		List<Map<String, Object>> contactList = new ArrayList<Map<String, Object>>();
		for (Contact c : paginatedList.getItems()) {
			Map<String, Object> contactJson = new HashMap<String, Object>();
			contactJson.put("contactId", c.getId());
			contactJson.put("emailId", c.getEmailId());
			contactJson.put("name", c.getName());
			contactJson.put("profession", c.getProfession());
			contactList.add(contactJson);
		}
		json.put("contacts", contactList);
		return json;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> createContact(@RequestBody(required = true) final String jsonRequest) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> json = convertJsonToObject(jsonRequest, Map.class);
			Contact contact = contactService.createContact(json.get("emailId").toString(), json.get("name").toString(), (String)json.get("profession"), json.get("user").toString());
			response.put("contactId", contact.getId());
		} catch (Exception e) {
			response.put("error", "Error parsing the request json. "+e);
		}
		return response;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String updateContact(@RequestBody(required = true) final String jsonRequest) {
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> json = convertJsonToObject(jsonRequest, Map.class);
			Contact contact = contactService.updateContact(Long.valueOf(json.get("contactId").toString()), (String)json.get("profession"), json.get("user").toString());
			return "{\"contactId\":"+contact.getId()+"}";
		} catch (Exception e) {			
			return "{\"error\", \"Error parsing the request json.\"}";
		}	
		
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public String deleteContact(final Long contactId, HttpServletResponse response) {
		contactService.deleteContact(contactId);
		return"{\"deleted\",true}";
	}
	
	private <T> T convertJsonToObject(final String jsonRequest, final Class<T> clazz) throws Exception {

		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(jsonRequest, clazz);
		}
		catch (final JsonGenerationException e) {
			throw new Exception("Exception converting json.", e);
		}
		catch (final JsonMappingException e) {
			throw new Exception("Exception converting json. Json Mapping error.", e);
		}
		catch (final IOException e) {
			throw new Exception("Exception converting json. Issue with json object.", e);
		}
	}
}
