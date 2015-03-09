/* == Motrice Copyright Notice == 
 * 
 * Motrice Service Platform 
 * 
 * Copyright (C) 2011-2014 Motrice AB 
 * 
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Affero General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Affero General Public License for more details. 
 * 
 * You should have received a copy of the GNU Affero General Public License 
 * along with this program. If not, see <http://www.gnu.org/licenses/>. 
 * 
 * e-mail: info _at_ motrice.se 
 * mail: Motrice AB, Långsjövägen 8, SE-131 33 NACKA, SWEDEN 
 * phone: +46 8 641 64 14 
 
 */ 
 
package org.inheritsource.test.service.processengine;

import java.util.Locale;

import org.inheritsource.service.coordinatrice.CoordinatriceFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CoordinatriceFacadeTest {
	
	CoordinatriceFacade c;
	
	@Before
	public void before() {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");
        
        
		
		c = (CoordinatriceFacade)applicationContext.getBean("coordinatriceFacade");
		
	}
	
	@Test
        @Ignore
	public void testGetLabel() {
		
		System.out.println("testGetLabel:" + c.getLabel("ForenkladDelgivning", "sv", "Ta del av", 1));
	}
	
	@Test
        @Ignore
	public void testGetLabel2() {
		Locale locale = new Locale("sv", "SE");
		System.out.println("testGetLabel2:" + c.getLabel("ForenkladDelgivning:1:10", "Ta del av", locale));
	}

}
