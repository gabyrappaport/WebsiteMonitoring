package dao;

/**
 * DAOConfigurationException represents an exception in the DAO configuration which cannot be resolved at runtime,
 * such as a missing resource in the classpath, an error when loading properties (incorrect properties file format), driver not found...
 * 
 * @author gabriellerappaport
 *
 */
public class DAOConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 *  Constructor of DAOConfiguationException with message.
	 * @param message The detail message of the DAOConfigurationException.
	 */
	public DAOConfigurationException(String message) {
		super(message);
	}

	/**
	 * Constructor of DAOConfiguationException with message and root cause.
	 * @param message The detail message of the DAOConfigurationException.
	 * @param cause The root cause of the DAOConfigurationException.
	 */
	public DAOConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor of DAOConfiguationException with root cause.
	 * @param cause The root cause of the DAOConfigurationException.
	 */
	public DAOConfigurationException(Throwable cause) {
		super(cause);
	}
}