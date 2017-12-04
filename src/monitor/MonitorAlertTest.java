package monitor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import classes.Website;
import dao.CheckDao;
import dao.DAOFactory;
import dao.WebsiteDao;

class MonitorAlertTest {

	@Test
	void test() throws InterruptedException {
		DAOFactory instance = DAOFactory.getInstance();
		CheckDao checkDao = instance.getCheckDao();
		WebsiteDao websiteDao = instance.getWebsiteDao();
		MonitorWebsite mwebsite = new MonitorWebsite();
		Timer timer = new Timer();
		
		/*
		 * Create new websites that are added temporarily to the database
		 * 
		 */
		
		mwebsite.addWebsite("https://www.linkedin.com", 1);
		mwebsite.addWebsite("https://path.blue/404/", 1);
		
		Website wsUp = mwebsite.read("https://www.linkedin.com");
		Website wsDown = mwebsite.read("https://path.blue/404/");
		
		/*
		 * Lauch checks on the temporary websites 
		 */
		MonitorCheck tUp = new MonitorCheck(wsUp);
		timer.schedule(tUp, 0, wsUp.getCheckInterval() * 1000);
		
		MonitorCheck tDown = new MonitorCheck(wsDown);
		timer.schedule(tDown, 0, wsUp.getCheckInterval() * 1000);
		
		/*
		 * List of tests
		 */
	
		/*
		 * We wait so some checks are added to the database and create MonitorAlert
		 */
		
		MonitorAlert monUp = new MonitorAlert(wsUp, 2*60, true);
		MonitorAlert monDown = new MonitorAlert(wsDown, 2*60, true);
		
		TimeUnit.SECONDS.sleep(10);
		
		/* 
		 * WsUp is up, we check if alert returns true 
		 */
		
		assertTrue("website up return true", monUp.alert(true));
		
		/* We simulate that WsUp is down by setting flagUp to false,
		 * we check if alert returns true 
		 */
		
		assertTrue("website up return true, when set false", monUp.alert(false));
		
		/* We simulate that WsDown is up by setting flagUp to true,
		 * we check if alert returns false
		 */
		
		assertFalse("website down return false, when set true", monDown.alert(true));
		
		/* 
		 * WsDown is down, we check if alert returns false
		 */
		
		assertFalse("website down return false", monDown.alert(false));
		

		/*
		 * Delete the temporary websites from the database 
		 * (and thus delete the checks from the database)
		 * 
		 */
		
		mwebsite.deleteWebsite("https://www.linkedin.com");
		mwebsite.deleteWebsite("https://path.blue/404/");
	}
}
