package dao;

/**
 * 
 * This class represents a generic DAO exception. It should wrap any exception on the database level, such as SQLExceptions.
 * 
 * @author gabriellerappaport
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of DAOException with a message.
	 * @param message The detail message of the DAOException.
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructor of DAOException with a message and root cause.
	 * @param message The detail message of the DAOException.
	 * @param cause The root cause of the DAOException.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor of DAOException with a root cause.
	 * @param cause The root cause of the DAOException.
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}