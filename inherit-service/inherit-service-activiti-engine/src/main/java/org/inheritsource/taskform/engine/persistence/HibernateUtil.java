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
 

package org.inheritsource.taskform.engine.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;

import org.hibernate.SessionFactory;
//import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.inheritsource.service.common.util.ConfigUtil;

public class HibernateUtil {

	public static final Logger log;

	static {
		log = LoggerFactory.getLogger(HibernateUtil.class.getName());
		init();
	}

	private static SessionFactory sessionFactory;

	private static void init() {
		// A SessionFactory is set up once for an application
		log.info("Init hibernate");
		try {
			Configuration cfg = new Configuration()
					.addAnnotatedClass(
							org.inheritsource.taskform.engine.persistence.entity.StartFormDefinition.class)
					.addAnnotatedClass(
							org.inheritsource.taskform.engine.persistence.entity.ActivityFormDefinition.class)
					.addAnnotatedClass(
							org.inheritsource.taskform.engine.persistence.entity.ProcessActivityFormInstance.class)
					.addAnnotatedClass(
							org.inheritsource.taskform.engine.persistence.entity.TagType.class)
					.addAnnotatedClass(
							org.inheritsource.taskform.engine.persistence.entity.ProcessActivityTag.class)
					.addAnnotatedClass(
							org.inheritsource.taskform.engine.persistence.entity.UserEntity.class)
					.setProperty("hibernate.dialect",
							"org.hibernate.dialect.PostgreSQLDialect")
					.setProperty("hibernate.connection.driver_class",
							"org.postgresql.Driver")
					.setProperty("hibernate.connection.url",
							" jdbc:postgresql://localhost/InheritPlatform");
			overrideProperties(cfg);
			sessionFactory = cfg.buildSessionFactory();
			log.info("Init hibernate finished");
		} catch (Exception e) {
			log.error("Could not initialize Hibernate SessionFactory: "
					+ e.getMessage());
			log.error("Could not initialize Hibernate SessionFactory: "
					+ e.toString());
		}
	}

	public HibernateUtil() {

	}

	/**
	 * Override properties in a Hibernate configuration if an environment
	 * variable is set. No exceptions are propagated, this step is just omitted
	 * in such case. SIDE EFFECT: Properties are set in the configuration,
	 * possibly overriding those already set.
	 */
	public static void overrideProperties(Configuration cfg) {
		try {
			Properties props = ConfigUtil.getConfigProperties();
			
			Properties hibernateProps = new Properties();
			for (Object key : props.keySet()) {
				if (key instanceof String && ((String)key).startsWith("dataSource.")) {
					Object val = props.get(key);
					String hibernateKey = "hibernate.connection." + ((String)key).substring(11);					
					hibernateProps.put(hibernateKey, val);
				}
			}
			cfg.addProperties(hibernateProps);
		} catch (Exception exc) {
			// Warn and ignore
			log.warn("Db default connect params (got "
					+ exc.getClass().getName() + ")");
		}

		String url = cfg.getProperty("hibernate.connection.url");
		log.info("Db connect url: " + ((url != null) ? url : "*NO URL*"));
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		HibernateUtil.sessionFactory = sessionFactory;
	}

}
