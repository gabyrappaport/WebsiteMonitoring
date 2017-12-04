package classes;

import java.util.Date;

import dao.CheckDao;
import monitor.MonitorCheck;

/**
 * <b> Check class represents one check made on a particular website at a
 * precise date</b>
 * <p>
 * One check is characterized by :
 * <ul>
 * <li>A unique id
 * <li>The HTTP Status Code returned by the header when checking the website
 * <li>The response time in milliseconds
 * <li>The id of the Website on which the check is made
 * <li>The date when the check is made
 * </ul>
 * </p>
 *
 * @author gabriellerappaport
 *
 */
public class Check {
	/**
	 * Date of the check
	 */
	private Date date;

	/**
	 * HTTP Status Code of the check.
	 */
	private Integer httpStatusCode;

	/**
	 * Unique Id of the check.
	 */
	private Integer idCheck;

	/**
	 * Response time in milliseconds
	 */
	private Integer idWebsite;

	/**
	 *
	 */
	private long responseTime;

	/**
	 * First Constructor for a check.
	 * <p>
	 * This first method is useful when we retrieve a check stored in the database.
	 * In fact, we can set the idCheck and the date because they are stored in the
	 * database.
	 * </p>
	 *
	 * @param idCheck
	 *            Unique Id of the check
	 * @param httpStatusCode
	 *            HTTP Status Code of the check
	 * @param responseTime
	 *            Response Time of the check
	 * @param idWebsite
	 *            Id of the website the check is made on
	 * @param date
	 *            Date of the check
	 *
	 * @see CheckDao
	 */
	public Check(Integer idCheck, Integer httpStatusCode, long responseTime, Integer idWebsite, Date date) {
		super();
		this.idCheck = idCheck;
		this.httpStatusCode = httpStatusCode;
		this.responseTime = responseTime;
		this.idWebsite = idWebsite;
		this.date = date;
	}

	/**
	 * Second Constructor for a check.
	 * <p>
	 * This second method is useful when we create a check in the MonitorCheck
	 * class. In fact, the idCheck and date will be generated when the check is
	 * stored in the database. Thus, we don't have the informations yet.
	 * </p>
	 *
	 * @param httpStatusCode
	 *            HTTP Status Code of the check
	 * @param responseTime
	 *            Response Time of the check
	 * @param idWebsite
	 *            Id of the website the check is made on
	 *
	 * @see MonitorCheck
	 */
	public Check(Integer httpStatusCode, long responseTime, Integer idWebsite) {
		super();
		this.httpStatusCode = httpStatusCode;
		this.responseTime = responseTime;
		this.idWebsite = idWebsite;
	}

	/**
	 *
	 * @return date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 *
	 * @return HTTP Status Code
	 */
	public Integer getHttpStatusCode() {
		return this.httpStatusCode;
	}

	/**
	 *
	 * @return idCheck
	 */
	public Integer getIdCheck() {
		return this.idCheck;
	}

	/**
	 *
	 * @return idWebsite
	 */
	public Integer getIdWebsite() {
		return this.idWebsite;
	}

	/**
	 *
	 * @return response Time
	 */
	public long getResponseTime() {
		return this.responseTime;
	}

	/**
	 *
	 * @param idWebsite
	 *            Id of the website the check is made on
	 */
	public void seIdtWebsite(Integer idWebsite) {
		this.idWebsite = idWebsite;
	}

	/**
	 *
	 * @param date
	 *            Date of the check
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 *
	 * @param httpStatusCode
	 *            HTTP Status Code of the check
	 */
	public void setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	/**
	 *
	 * @param idCheck
	 *            Unique Id of the check.
	 */
	public void setIdCheck(Integer idCheck) {
		this.idCheck = idCheck;
	}

	/**
	 *
	 * @param responseTime
	 *            Response Time of the check
	 */
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	@Override
	public String toString() {
		return "Check [idCheck=" + this.idCheck + ", httpStatusCode=" + this.httpStatusCode + ", responseTime="
				+ this.responseTime + ", idWebsite=" + this.idWebsite + ", date=" + this.date + "]";
	}

}
