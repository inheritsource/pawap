package org.inheritsource.test.service.identity;

import java.util.ArrayList;

import javax.naming.NamingException;

import org.inheritsource.service.common.domain.UserDirectoryEntry;
import org.inheritsource.service.identity.UserDirectoryService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class UserDirectoryServiceTest {
	UserDirectoryService userDirectoryService ; 
	@Before
	public void before() {
		 userDirectoryService = new UserDirectoryService();
	}

	@After
	public void after() {

	}

	@Ignore
	@Test
	public void testfindAllInGroup() {
		System.out.println("=========================");

		ArrayList<UserDirectoryEntry> candidates = null;
		String role = "Registrator";
		try {
			candidates = userDirectoryService.lookupUserEntriesByGroup(role);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (UserDirectoryEntry candidate : candidates) {
			System.out.println(candidate.getCn() ); 
			// System.out.println(candidate);
		}

	}
	
	/* 
	@Ignore
	@Test
	public void testfindAllInGroupgroupBaseDn() {
		// used by TaskMessageListener to 
		// send mail to all candidates 
		
		System.out.println("=========================");
		

		ArrayList<UserDirectoryEntry> candidates = null;
		String role = "Handlaggare";
		String groupBaseDn = "ou=eserviceRoller,OU=074 Milj\u00f6f\u00f6rvaltningen,OU=IDMGroups,ou=Organisation,ou=Malmo" ; 
		try {
			candidates = userDirectoryService.lookupUserEntriesByGroup(role, groupBaseDn);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (UserDirectoryEntry candidate : candidates) {
			System.out.println(candidate.getCn() ); 
			// System.out.println(candidate);
		}

	}
	*/ 
	
	@Ignore
	@Test
	public void testfindUsers() {
		System.out.println("=========================");
		String[] userIds = { "john", "james", "jack" };
		ArrayList<UserDirectoryEntry> result = userDirectoryService.lookupUserEntries(userIds);
		for (UserDirectoryEntry candidate : result) {
			System.out.println(candidate.getCn() ); 
			// System.out.println(candidate);
		}
	}
	@Ignore
	@Test
	public void testsearchForUserEntries() {
		// searchForUserEntries
		// from TaskFormService by dirSearchUserEntries
		// 
		// used by SiteAjaxApplication. 
		// 
		
		
		System.out.println("=========================");
		String[] filterArgs ={"jac"};
		ArrayList<UserDirectoryEntry> result = userDirectoryService.searchForUserEntries(filterArgs )  ;
		for (UserDirectoryEntry candidate : result) {
			System.out.println(candidate.getCn() ); 
			// System.out.println(candidate);
		}
		
		System.out.println("=========================");
	}
}
