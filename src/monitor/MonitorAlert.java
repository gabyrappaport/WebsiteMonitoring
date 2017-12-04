package monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Timer;

import classes.Website;
import dao.CheckDao;
import dao.DAOException;
import dao.DAOFactory;
import dao.WebsiteDao;

/**
 * MonitorAlert is performing the alerts on a website.
 * It is computing very second whether the app should raise an alert. 
 * 
 * @author gabriellerappaport
 *
 */
public class MonitorAlert {
	/**
	 * CheckDao - link to the data tier to retrieve the availability
	 */
	private CheckDao checkDao;
	
	/**
	 * WebsiteDao - link to the data tier to check whether the website is still in the database
	 */
	private WebsiteDao websiteDao;
	
	/**
	 * flagUp - true if the availability > 0,8
	 */
	private Boolean flagUp;
	
	/**
	 * period - We need to compute the availability over the last x minutes
	 */
	private Integer period;
	
	/**
	 * Timer - call alert method every second
	 */
	private Timer timer;

	/**
	 * website - the website we generate the alerts on
	 */
	private Website website;

	/**
	 * Constructor of Monitor Alert
	 * When called, the timer is set to 1 second
	 * 
	 * @param website website we generate the alerts on
	 * @param period compute the availability over the last x minutes
	 * @param flagUp true if the availability > 0,8
	 */
	
	public MonitorAlert(Website website, Integer period, Boolean flagUp) {
		super();
		DAOFactory instance = DAOFactory.getInstance();
		this.checkDao = instance.getCheckDao();
		this.websiteDao = instance.getWebsiteDao();
		this.website = website;
		this.period = period;
		this.flagUp = flagUp;

		this.timer = new Timer(1000, new TimerListener());
		this.timer.start();
	}

	/**
	 * First, the alert method checks if the website is still in the database, if not, the timer is stopped.
	 * Then , the alert method retrieve the availability from the data tier.
	 * If the flapUp was true, it checks if during the last period, the availability <0,8 :
	 * <ul>
	 * <li> If the availability <0,8, it prints the alert and set flagUp to false
	 * <li> otherwise, it does nothing
	 * </ul>
	 *  If the flapUp was false (thus an alert has been raised) it checks if during the last period, the availability > 0,8 :
	 * <ul>
	 * <li> If the availability > 0,8, it prints the end of the alert and set flagUp to true
	 * <li> otherwise, it does nothing
	 * </ul>
	 * 
	 * @param flagUp
	 * @return true if the availability > 0,8, false otherwise 
	 */
	
	public Boolean alert(Boolean flagUp) {
		Double availability = null;
		if (this.websiteDao.read(this.website.getIdWebsite()) == null) {
			this.timer.stop();
		}

		try {
			availability = this.checkDao.readAvailability(this.website.getIdWebsite(), this.period);
		} catch (DAOException e) {
			System.out.println(e);
		}
		Date date = new Date();
		if (flagUp) {
			if (availability < 0.8) {
				System.out.println("Website " + this.website.getUrl() + " is DOWN. availability= " + availability
						+ ", time= " + date.toString());
				return false;
			} else {
				return true;
			}
		} else {
			if (availability > 0.8) {
				System.out.println("Website " + this.website.getUrl() + " is BACK UP. availability= " + availability
						+ ", time= " + date.toString());
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * The TimerListener is called every second by the timer and launch the alert method.
	 * 
	 * @author gabriellerappaport
	 *
	 */
	private class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			MonitorAlert.this.flagUp = MonitorAlert.this.alert(MonitorAlert.this.flagUp);
		}
	}
	
	public void killTimer() {
		this.timer.stop();
	}
}
