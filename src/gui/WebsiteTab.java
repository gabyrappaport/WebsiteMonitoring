package gui;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import classes.Website;
import monitor.MonitorWebsite;

/**
 * The Website Tab is a JTabbedPane wich contains websitePanels.
 * For each website, a WebsitePanel is created.
 * It is called by the monitorFrame.
 * 
 * @see MonitorFrame
 * @see WebsitePanel
 * @author gabriellerappaport
 *
 */
public class WebsiteTab extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	private ArrayList<Website> allWebsites;
	private MonitorWebsite monitorWebsite;

	public WebsiteTab(Map<Integer, Integer> period, ArrayList<Integer> timeframes) {
		this.monitorWebsite = new MonitorWebsite();
		this.allWebsites = this.monitorWebsite.allWebsite();
		this.setAlignmentX(SwingConstants.TOP);

		ArrayList<WebsitePanel> websitePanels = new ArrayList<>();
		for (Website website : this.allWebsites) {
			WebsitePanel websitePanel = new WebsitePanel(timeframes, website, period);
			websitePanels.add(websitePanel);
			this.add(website.getUrl(), websitePanel);
		}
	}
}
