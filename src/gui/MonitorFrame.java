package gui;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import classes.Website;

/**
 *
 * MonitorFrame is the frame of the graphical user interface.
 * It contains the bar menu with all the websites that are being monitored. 
 * This bar menu can be updated when a website is added or deleted from the database.
 * 
 * It is implemented with a singleton pattern.
 * 
 * @see WebsiteTab
 * @author gabriellerappaport
 *
 */
public class MonitorFrame extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private static MonitorFrame monitorFrame;
	private ArrayList<Website> allWebsites;
	private Map<Integer, Integer> period;
	private JTabbedPane tab;
	private ArrayList<Integer> timeframes;

	/**
	 * Constructor 
	 */
	public MonitorFrame() {
		this.setTitle("Monitoring");
		this.setSize(1500, 900);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/** 
	 * 
	 * @return the unique instance of MonitorFrame
	 */
	public static MonitorFrame getInstance() {
		if (MonitorFrame.monitorFrame == null) {
			MonitorFrame.monitorFrame = new MonitorFrame();
		}
		return MonitorFrame.monitorFrame;
	}

	/**
	 * Initialization of the frame
	 * @param websites
	 * @param timeframes
	 * @param period
	 */
	public void init(ArrayList<Website> websites, ArrayList<Integer> timeframes, Map<Integer, Integer> period) {

		MonitorFrame.monitorFrame.setWebsites(websites);
		MonitorFrame.monitorFrame.setTimeframes(timeframes);
		MonitorFrame.monitorFrame.setPeriod(period);

		MonitorFrame.monitorFrame.tab = new JTabbedPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		ArrayList<WebsitePanel> websitePanels = new ArrayList<>();
		for (Website website : websites) {
			// Create a new Website Panel for each website and add it to the tab
			WebsitePanel websitePanel = new WebsitePanel(timeframes, website, period);
			websitePanels.add(websitePanel);
			this.tab.add(website.getUrl(), websitePanel);
		}
		this.getContentPane().add(this.tab);

		this.setVisible(true);
	}

	/**
	 * Update Tab is called when a website is added or deleted from the database.
	 * It updates the JTabbedPane which contains the websites.
	 */
	public void updateTab() {
		this.getContentPane().removeAll();
		MonitorFrame.monitorFrame.tab = new JTabbedPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		ArrayList<WebsitePanel> websitePanels = new ArrayList<>();
		for (Website website : this.allWebsites) {
			// Create a new Website Panel for each website and add it to the tab
			WebsitePanel websitePanel = new WebsitePanel(this.timeframes, website, this.period);
			websitePanels.add(websitePanel);
			this.tab.add(website.getUrl(), websitePanel);
		}
		this.getContentPane().add(this.tab);
	}
	

	@Override
	public void run() {
	}
	
	public ArrayList<Website> getAllWebsites() {
		return this.allWebsites;
	}

	public Map<Integer, Integer> getPeriod() {
		return this.period;
	}

	public ArrayList<Integer> getTimeframes() {
		return this.timeframes;
	}
	
	public static void setMonitorFrame(MonitorFrame monitorFrame) {
		MonitorFrame.monitorFrame = monitorFrame;
	}

	public void setAllWebsites(ArrayList<Website> allWebsites) {
		this.allWebsites = allWebsites;
	}

	public void setPeriod(Map<Integer, Integer> period) {
		this.period = period;
	}

	public void setTimeframes(ArrayList<Integer> timeframes) {
		this.timeframes = timeframes;
	}

	public void setWebsites(ArrayList<Website> websites) {
		this.allWebsites = websites;
	}

}
