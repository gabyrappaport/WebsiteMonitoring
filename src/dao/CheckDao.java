package dao;

import java.util.Date;
import java.util.Map;

import classes.Check;

/**
 * <b> CheckDao Interface </b>
 * <p>
 * List of the methods that need to be implemented.
 * The logic tier can have access to the code responsible for storage only through the interface.
 * </p>
 *
 * @author gabriellerappaport
 *
 */
public interface CheckDao {
	void create(Check check) throws DAOException;

	void delete(Integer idWebsite) throws DAOException;

	Double readAvailability(Integer idWebsite, Integer period) throws DAOException;

	Map<Date, Double> readAvailability(Integer idWebsite, Integer period, Integer timeframe) throws DAOException;

	Map<Date, Integer> readAvgResponseTime(Integer idWebsite, Integer period, Integer timeframe) throws DAOException;

	Map<Date, Map<Integer, Integer>> readCodesCount(Integer idWebsite, Integer period, Integer timeframe)
			throws DAOException;

	Map<Date, Integer> readMaxResponseTime(Integer idWebsite, Integer period, Integer timeframe) throws DAOException;
}
