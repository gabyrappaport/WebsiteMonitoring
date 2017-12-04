package dao;

import java.util.ArrayList;

import classes.Website;

/**
 * <b> WebsiteDao Interface </b>
 * <p>
 * List of the methods that need to be implemented.
 * The logic tier can have access to the code responsible for storage only through the interface.
 * </p>
 *
 * @author gabriellerappaport
 *
 */

public interface WebsiteDao {
	void create(Website website) throws DAOException;

	void delete(String url) throws DAOException;

	ArrayList<Website> read() throws DAOException;

	Website read(Integer idWebsite) throws DAOException;

	Website read(String url) throws DAOException;
}
