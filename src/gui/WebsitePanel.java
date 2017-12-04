package gui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import classes.Website;

/**
 * WebsitePanel is a JPanel associated to a specific panel, it is called by the Monitor Frame. 
 * A WebsitePanel also has a JTabbedPane bar menu to display the statistics over two different periods : 
 * the past hour and the past 10 minutes.
 * For those two period, a periodPanel is created.
 * 
 * @see WebsiteTab
 * @see PeriodPanel
 * @author gabriellerappaport
 *
 */

public class WebsitePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public WebsitePanel(ArrayList<Integer> timeframes, Website website, Map<Integer, Integer> period) {
		this.setLayout(new GridLayout(0, 1));

		JTabbedPane tab = new JTabbedPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		ArrayList<JPanel> timeframePanels = new ArrayList<>();
		Set<Integer> periodSet = period.keySet();
		for (Integer p : periodSet) {
			// Create a new Panel for each website
			PeriodPanel periodPanel = new PeriodPanel(timeframes, website, p, period.get(p));
			timeframePanels.add(periodPanel);
			tab.add("Monitoring Period : " + String.valueOf(p / 60) + " minutes", periodPanel);
		}
		this.add(tab);
	}
}