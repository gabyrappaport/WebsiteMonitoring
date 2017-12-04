package classes;

import java.util.Date;

/**
 * <b> Website class represents one website the user wants to monitorize</b>
 * <p>
 * One website is characterized by :
 * <ul>
 * <li>A check Interval - it is given by the user in seconds - it tells us how
 * often we do a check on a website
 * <li>A date - when the website was added in the monitoring system
 * <li>A unique id
 * <li>A String url - it must be an existing url
 * </ul>
 * </p>
 *
 * @author gabriellerappaport
 *
 */
public class Website {
	/**
	 * check interval : indicates the interval of seconds to wait between two
	 * checks. It can be modified my the user anytime.
	 */

	private Integer checkInterval;

	/**
	 * date : indicates when the website was added to the basis. The date is updated
	 * when the user updates the check interval.
	 */
	private Date date;

	/**
	 * idWebsite : unique id to identify the website in the databse
	 */
	private Integer idWebsite;

	/**
	 * url : used to do checks on the website
	 */
	private String url;

	/**
	 * First Constructor for a website
	 * <p>
	 * This first method is useful when we retrieve a website stored in the
	 * database. In fact, we can set every parameter of the Object. Most
	 * particularly, the websiteId and the date are set when the website is stored
	 * in the database.
	 * </p>
	 *
	 * @param idWebsite
	 *            Unique Id of the website
	 * @param url
	 *            Functionnal url used to do the checks
	 * @param checkInterval
	 *            Second interval between two checks
	 * @param date
	 *            Date of the last checkInterval update
	 */
	public Website(Integer idWebsite, String url, Integer checkInterval, Date date) {
		super();
		this.idWebsite = idWebsite;
		this.url = url;
		this.checkInterval = checkInterval;
		this.date = date;
	}

	/**
	 * Second Constructor for a website
	 * <p>
	 * This second method is useful when we need to create a Website Object from the
	 * CLUI In fact, we only know the url and idCheck in the CLUI, the idWebsite and
	 * date will be generated when the website is stored in the database. Thus, we
	 * can't set the informations yet.
	 * </p>
	 *
	 * @param url
	 *            Functionnal url used to do the checks
	 * @param checkInterval
	 *            Second interval between two checks
	 */
	public Website(String url, Integer checkInterval) {
		super();
		this.url = url;
		this.checkInterval = checkInterval;
	}

	/**
	 *
	 * @return checkInterval
	 */
	public Integer getCheckInterval() {
		return this.checkInterval;
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
	 * @return idWebsite
	 */
	public Integer getIdWebsite() {
		return this.idWebsite;
	}

	/**
	 *
	 * @return url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 *
	 * @param checkInterval
	 *            Second interval between two checks
	 */
	public void setCheckInterval(Integer checkInterval) {
		this.checkInterval = checkInterval;
	}

	/**
	 *
	 * @param date
	 *            Date of the last checkInterval update
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 *
	 * @param idWebsite
	 *            Unique Id of the website
	 */
	public void setIdWebsite(Integer idWebsite) {
		this.idWebsite = idWebsite;
	}

	/**
	 *
	 * @param url
	 *            Functionnal url used to do the checks
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Website [url=" + this.url + ", checkInterval=" + this.checkInterval + ", idWebsite=" + this.idWebsite
				+ ", date=" + this.date + "]";
	}

}
