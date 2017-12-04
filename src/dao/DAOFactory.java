package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * The Factory is in charge of the instantiation of the different DAO of our application. 
 * In our database, we have two tables : checkWebsites and websites, so we need two instanciations.
 * If we need to add other tables to our database, the code is easy to maintain and evolve. 
 *
 *The Factory is responsible for : 
 *<ul>
 *<li> reading the configuration information from the properties file,
 *<li> load the JDBC driver of the used DBMS,
 *<li> provide a connection to the database.
 *</ul>
 *
 * @author gabriellerappaport
 *
 */

public class DAOFactory {

	private static final String PROPERTIES_FILE = "dao.properties";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_PASSWORD = "password";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_USERNAME = "username";

	private String password;
	private String url;
	private String username;

	DAOFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * Retrieve the database connection information, load the JDBC driver and return an instance of the factory
	 *
	 * @return instance of the Factory
	 * @throws DAOConfigurationException
	 */

	public static DAOFactory getInstance() throws DAOConfigurationException {
		Properties properties = new Properties();
		String url;
		String driver;
		String username;
		String password;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream PropertiesFile = classLoader.getResourceAsStream(DAOFactory.PROPERTIES_FILE);

		if (PropertiesFile == null) {
			throw new DAOConfigurationException(
					"The properties_file " + DAOFactory.PROPERTIES_FILE + " is nowhere to find.");
		}

		try {
			properties.load(PropertiesFile);
			url = properties.getProperty(DAOFactory.PROPERTY_URL);
			driver = properties.getProperty(DAOFactory.PROPERTY_DRIVER);
			username = properties.getProperty(DAOFactory.PROPERTY_USERNAME);
			password = properties.getProperty(DAOFactory.PROPERTY_PASSWORD);
		} catch (IOException e) {
			throw new DAOConfigurationException("Could not load properties_file " + DAOFactory.PROPERTIES_FILE, e);
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DAOConfigurationException("The driver was not found in the classpath.", e);
		}
		DAOFactory instance = new DAOFactory(url, username, password);
		return instance;
	}

	/**
	 * Method responsible for providing a connection to the database
	 * @return connection to the database
	 * @throws SQLException
	 */

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(this.url, this.username, this.password);
	}
	
	/**
	 *
	 * @return implementation of CheckDao
	 */
	public CheckDao getCheckDao() {
		return new CheckDaoImpl(this);
	}

	/**
	 *
	 * @return implementation of WebsiteDao
	 */
	public WebsiteDao getWebsiteDao() {
		return new WebsiteDaoImpl(this);
	}

}