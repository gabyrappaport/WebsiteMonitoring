package monitor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.TimerTask;

import classes.Check;
import classes.Website;
import dao.CheckDao;
import dao.DAOException;
import dao.DAOFactory;

/**
 * MonitorCheck performs the checks on a specific website and give it for storage to the data tier
 * @author gabriellerappaport
 *
 */
public class MonitorCheck extends TimerTask {
	/**
	 * checkDao - send the check to the database
	 */
	private CheckDao checkDao;
	
	/**
	 * Website - website we perform the checks on
	 */
	private Website website;

	/**
	 * MonitorCheck constructor
	 * @param website
	 */
	public MonitorCheck(Website website) {
		super();
		this.website = website;
		DAOFactory instance = DAOFactory.getInstance();
		this.checkDao = instance.getCheckDao();
	}

	public CheckDao getCheckDao() {
		return this.checkDao;
	}

	public Website getWebsite() {
		return this.website;
	}

	/**
	 * run method performs the checks
	 */
	@Override
	public void run() {
		URL url = null;
		try {
			/* create an url from a string */
			url = new URL(this.website.getUrl());
			/* start a timer to compute the response time of the connection to the url */
			long startTime = System.currentTimeMillis();
			URLConnection conn = url.openConnection();
			/* set timeout for the connection */
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(2000);
			long responseTime = System.currentTimeMillis() - startTime;
			/* retrieve the http status from the header*/
			List<String> key = conn.getHeaderFields().get(null);
			/* The header can be empty, if the website does not exist for example, it is then set to 0 */
			Integer httpStatusCode = 0;
			if (key != null) {
				httpStatusCode = Integer.parseInt(key.get(0).split(" ")[1]);
			}
			/* create a check Object and pass it to the datatier for storage*/
			Check check = new Check(httpStatusCode, responseTime, this.website.getIdWebsite());
			this.checkDao.create(check);
		} catch (MalformedURLException e1) {
			System.out.println(e1.getMessage());
		} catch (IOException e1) {
			System.out.println("hey");
			System.out.println(e1);
		} catch (DAOException e) {
			System.out.println(e);
		} catch (NullPointerException e) {
		}
	}

	public void setCheckDao(CheckDao checkDao) {
		this.checkDao = checkDao;
	}

	public void setWebsite(Website website) {
		this.website = website;
	};
}
