package monitor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import classes.Website;
import dao.CheckDao;
import dao.DAOException;
import dao.DAOFactory;
import dao.WebsiteDao;

/**
 * MonitorWebsite is a link to the datatier and perform all the action on a website
 * @author gabriellerappaport
 *
 */
public class MonitorWebsite {

	private CheckDao checkDao;
	private WebsiteDao websiteDao;

	public MonitorWebsite() {
		DAOFactory instance = DAOFactory.getInstance();
		this.websiteDao = instance.getWebsiteDao();
		this.checkDao = instance.getCheckDao();
	}

	public Website read(String url) {
		Website website = null;
		try {
			website = this.websiteDao.read(url);
		} catch (DAOException e) {
			return website;
		}
		return website;
	}

	public boolean addWebsite(String url, Integer checkInterval) {
		try {
			new URL(url);
			Website website = new Website(url, checkInterval);
			this.websiteDao.create(website);
		} catch (MalformedURLException e1) {
			System.out.println("Please add a valable url");
		} catch (DAOException e) {
			return false;
		}
		return true;
	}

	public ArrayList<Website> allWebsite() {
		ArrayList<Website> allWebsites = new ArrayList<>();
		try {
			allWebsites = this.websiteDao.read();
		} catch (DAOException e) {
		}
		return allWebsites;
	}

	public boolean deleteWebsite(String url) {
		try {
			Website website = this.websiteDao.read(url);
			this.checkDao.delete(website.getIdWebsite());
			this.websiteDao.delete(url);
		} catch (DAOException e) {
			return false;
		} catch (NullPointerException e) {
			System.out.println(url + " does not exist");
		}
		return true;
	}

	public boolean updateCheckInterval(String url, int newCheckInterval) {
		Website website = this.websiteDao.read(url);
		website.setCheckInterval(newCheckInterval);
		try {
			this.websiteDao.delete(url);
			this.websiteDao.create(website);
		} catch (DAOException e) {
			return false;
		}
		return true;
	}
}
