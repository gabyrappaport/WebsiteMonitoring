package clui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;

import javax.swing.SwingUtilities;

import classes.Website;
import gui.MonitorFrame;
import monitor.MonitorAlert;
import monitor.MonitorCheck;
import monitor.MonitorWebsite;

/**
 * <b> Command Line User Interface</b>
 * <p>
 * The Command Line User Interface allows the user to communicate with the
 * application. Through the clui, the user can manage the websites he wants to monitor
 *  thanks to the following methods : 
 * <ul>
 * <li> addWebsite : add a new website to monitor.
 * <li> deleteWebsite : delete a website that is being monitored
 * <li> allWebsite : display all websites that are being monitored
 * <li> updateCheckInterval : update the check interval of website that is being monitored
 * <li> runMonitoring : start the monitoring of the websites added to the database.
 * </ul>
 * Note that those methods can be called whether the monitoring is running or not. 
 * Users can keep the app running and monitor the websites.
 * </p>
 *
 * @author gabriellerappaport
 *
 */
public class CommandLineUserInterface {
	/**
	 * Main method - create a new CommandLineUserInterface and load it in the
	 * console.
	 *
	 * @param args
	 */
	public static void main(String args[]) {
		CommandLineUserInterface clui = new CommandLineUserInterface();
		clui.load();
	}

	/*
	 * PARAMETERS
	 */

	/**
	 * new User Command.
	 *
	 * @see UserCommand
	 */
	private UserCommand command = new UserCommand();

	/**
	 * exitFlag - exit the app
	 */
	private boolean exitFlag = false;

	/**
	 * monitorWebsite - connection to the controller for the websites
	 */
	private MonitorWebsite monitorWebsite;

	/**
	 * Scanner - read the input of the user
	 */
	private Scanner reader = new Scanner(System.in);

	/**
	 * Timer - useful to launch the checks at the different checkIntervals.
	 */
	private Timer timer = new Timer();

	/**
	 * scheduledChecks - store the different MonitorChecks scheduled for the websites.
	 * key : unique website id. 
	 * value : MonitorCheck
	 * 
	 * When a new website is added, a new MonitorCheck (task) is scheduled every x second.
	 * This task is stored in the scheduledChecks map.
	 * 
	 * When a website is deleted from the database or its checkInterval is updated, 
	 * the MonitorCheck (task) is stopped. This Map help us retrieve the monitorCheck 
	 * that has to be canceled.
	 */
	private Map<Integer, MonitorCheck> scheduledChecks = new HashMap<>();

	/*
	 * CLUI METHODS
	 */

	/**
	 * Create the header for the console App
	 */
	private void makeHeader() {

		/**
		 * The monitor Website is created when the header is prompt so it is only
		 * created once in the CLUI.
		 */
		this.monitorWebsite = new MonitorWebsite();

		String header = "\n";
		// First row
		for (int i = 0; i < 30; i++) {
			header += "*";
		}
		header += "\n";
		// Title
		header += "*   Monitor Your Websites   *\n";
		// Last row
		for (int i = 0; i < 30; i++) {
			header += "*";
		}
		header += "\nType a Command";
		System.out.println(header);
		this.prompt();
	}

	/**
	 * Input Line for the user
	 */
	private void makeInputLine() {
		String inputLine = ">  ";
		System.out.print(inputLine);
	}

	/**
	 * Print the input line for the user and reader his input in the console.
	 */
	private void prompt() {
		this.makeInputLine();
		this.setCommand(this.getReader().nextLine());
	}

	/**
	 * reset the userCommand after the call of a command by the user.
	 */
	private void cleanUpCommand() {
		this.command = new UserCommand();
	}

	/**
	 * 
	 * @return whether the user input has the right number of arguments
	 */
	private Boolean analyseCommand() {
		Map<String, Integer> expectedArgument = new HashMap<>();
		expectedArgument.put("addWebsite", 2);
		expectedArgument.put("deleteWebsite", 1);
		expectedArgument.put("allWebsite", 0);
		expectedArgument.put("updateCheckInterval", 2);
		expectedArgument.put("runMonitoring", 0);
		expectedArgument.put("help", 0);
		String functionCalled = this.command.getFunction();
		if (this.command.getArguments() == null && expectedArgument.get(functionCalled) == 0) {
			return true;
		} else if (this.command.getArguments() == null && expectedArgument.get(functionCalled) != 0) {
			return false;
		} else if (this.command.getArguments().length != expectedArgument.get(functionCalled)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * <b> Analyse the input of the user and launch the right method.</b>
	 * <p>
	 * New instances are created : 
	 * <ul>
	 * <li>timeFrames - the statistics are computed over different timeframes. We
	 * chose: 2 minutes or 10 minutes but it can be modified.
	 * <li>period - Map between a period and update Interval :
	 * <ul>
	 * <li>period - 10 or 60 minutes - period chosen to display the statistics - it
	 * can be modified
	 * <li>update Interval - 10s or 60s - the statistics displayed are updated at
	 * this rate.
	 * </ul>
	 * Both period and updateInterval are set here so they can be easily modified without
	 * having to touch the rest of the code.
	 *
	 * <li>allWebsites - arrayList with all the websites that must be monitored
	 * <li>monitorFrame - instance of monitorFrame
	 * </ul>
	 * </p>
	 * <p>
	 * There is a Switch - Case that analyses the methods called by the user :
	 * <ul>
	 * <li>addWebsite - the website is added to the database by calling the
	 * monitorWebsite. A new MonitorAlert is also launched for the added website and
	 * schedule the check that must be done for this website.
	 * <li>deleteWebsite - delete the website from the database and stop the scheduled checks
	 * <li>allWebsite - prompt the websites that are being monitored.
	 * <li>updateCheckInterval - the checkInterval of a website is updated. The previous 
	 * MonitorCheck is canceled and a new one is launch with the updated value of checkInterval.
	 * <li>runMonitoring - for all the website in the database, the alerts are launched
	 * and the checks scheduled. A interface appears so the user can visualize the
	 * statistics for every website over different period and timeframes.
	 * <li>help - prompt a list of all the functions that can be called by the user.
	 * </ul>
	 *
	 */

	private void executeCommand() {

		ArrayList<Integer> timeframes = new ArrayList<>();
		timeframes.add(2 * 60);
		timeframes.add(10 * 60);

		Map<Integer, Integer> period = new HashMap<>();
		period.put(10 * 60, 10);
		period.put(60 * 60, 60);

		ArrayList<Website> allWebsites = this.monitorWebsite.allWebsite();

		String functionCalled = this.command.getFunction();

		MonitorFrame monitorFrame = MonitorFrame.getInstance();
		monitorFrame.init(allWebsites, timeframes, period);

		switch (functionCalled) {

		case "addWebsite":
			// The new website is added to the database.
			try {
				this.monitorWebsite.addWebsite(this.command.getArguments()[0],
						Integer.parseInt(this.command.getArguments()[1]));
			} catch (NumberFormatException e1) {
				// if the checkInterval argument can't be converted to an integer, the website is not added to the database.
				System.out.println("Wrong argument format ");
			}
			// ws : added website. It is retrieved from the database so we know what is its id.
			Website ws = this.monitorWebsite.read(this.command.getArguments()[0]);
			if (ws != null) {
				// Launch a Monitor Alert
				new MonitorAlert(ws, 2 * 60, true);
				// Create a new MonitorCheck, add it to the scheduledChecks map and schedule it.
				MonitorCheck t = new MonitorCheck(ws);
				this.scheduledChecks.put(ws.getIdWebsite(), t);
				this.timer.schedule(this.scheduledChecks.get(ws.getIdWebsite()), 0, ws.getCheckInterval() * 1000);
				// Refresh the GUI
				monitorFrame.setAllWebsites(this.monitorWebsite.allWebsite());
				monitorFrame.updateTab();
				monitorFrame.getContentPane().revalidate();
				monitorFrame.getContentPane().repaint();
			}
			this.cleanUpCommand();
			break;

		case "deleteWebsite":
			// ws : website we need to delete
			ws = this.monitorWebsite.read(this.command.getArguments()[0]);
			// Cancel the MonitorChecks only if it has been started by runMonitoring<>
			// so only if it is in scheduledChecks.
			try {
				this.scheduledChecks.get(ws.getIdWebsite()).cancel();
			} catch (Exception e) {}
			// Delete website from the database.
			this.monitorWebsite.deleteWebsite(this.command.getArguments()[0]);
			// Refresh the GUI
			monitorFrame.setAllWebsites(this.monitorWebsite.allWebsite());
			monitorFrame.updateTab();
			monitorFrame.getContentPane().revalidate();
			monitorFrame.getContentPane().repaint();
			this.cleanUpCommand();
			break;

		case "allWebsite":
			this.print(allWebsites);
			this.cleanUpCommand();
			break;

		case "updateCheckInterval":
			// ws : website we need to update
			ws = this.monitorWebsite.read(this.command.getArguments()[0]);
			if (ws != null) {
				// Cancel the MonitorChecks only if it has been started by runMonitoring<>
				// so only if it is in scheduledChecks.
				try {
					this.scheduledChecks.get(ws.getIdWebsite()).cancel();
				} catch (Exception e) {}
				// Update the website checkInterval in the database 
				try {
					this.monitorWebsite.updateCheckInterval(this.command.getArguments()[0],
							Integer.parseInt(this.command.getArguments()[1]));
				// if the checkInterval argument can't be converted to an integer, the website is not updated.
				} catch (NumberFormatException e) {
					System.out.println("Wrong argument format");
				}
				// Create a new MonitorCheck, add it to the scheduledChecks map and schedule it with the right checkInterval.
				MonitorCheck tk = new MonitorCheck(ws);
				this.scheduledChecks.put(ws.getIdWebsite(), tk);
				this.timer.schedule(this.scheduledChecks.get(ws.getIdWebsite()), 0, ws.getCheckInterval() * 1000);
			}
			this.cleanUpCommand();
			break;

		case "runMonitoring":
			// For each website, 
			// Create a new MonitorCheck, add it to the scheduledChecks map and schedule it with the right checkInterval.
			// Launch a new MonitorAlert
			for (Website website : allWebsites) {
				new MonitorAlert(website, 2 * 60, true);
				MonitorCheck task = new MonitorCheck(website);
				this.scheduledChecks.put(website.getIdWebsite(), task);
				this.timer.schedule(this.scheduledChecks.get(website.getIdWebsite()), 0, website.getCheckInterval() * 1000);
			}
			if (allWebsites.isEmpty()) {
				System.out.println("Please, add a website first");
			} else {
				// Launch the update of the graphs in the GUI
				SwingUtilities.invokeLater(monitorFrame);
				// Refresh the GUI
				monitorFrame.updateTab();
				monitorFrame.getContentPane().validate();
				monitorFrame.getContentPane().repaint();
			}
			this.cleanUpCommand();
			break;

		case "help":
			System.out.println("List of available methods :");
			System.out.println(
					"   *   addWebsite <url(String), checkInterval(Integer)>          // add a new Website you wqant to monitor");
			System.out.println(
					"   *   deleteWebsite <url(String)>                      // delete an existing website (will delete checks historical)");
			System.out.println("   *   allWebsite <>                           // print all monitored websites");
			System.out.println(
					"   *   updateCheckInterval <url(String), checkInterval(Integer)> // update checkInterval of an existing website (will keep checks historical)");
			System.out.println("   *   runMonitoring <>                         // run Monitoring of website");
			this.cleanUpCommand();
			break;

		default:
			System.out.println("Command not found");
		}
	}

	/**
	 * Displays all Website in the console.
	 *
	 * @param websites
	 *            All monitored websites
	 */
	private void print(ArrayList<Website> websites) {
		System.out.println("--------------------------------------------------------------------");
		System.out.printf("%10s %30s %20s", "N", "URL", "CHECK INTERVAL");
		System.out.println();
		System.out.println("--------------------------------------------------------------------");
		for (Website website : websites) {
			System.out.format("%10s %30s %20s", website.getIdWebsite(), website.getUrl(), website.getCheckInterval());
			System.out.println();
		}
		System.out.println("--------------------------------------------------------------------");
	}

	/**
	 * Load the clui and print the header
	 */
	public void load() {
		this.makeHeader();
		while (!this.shouldExit()) {
			this.prompt();
		}
	}

	/*
	 * GETTERS AND SETTERS
	 */

	/**
	 *
	 * @return UserCommand
	 */
	public UserCommand getCommand() {
		return this.command;
	}

	/**
	 *
	 * @return Scanner
	 */
	public Scanner getReader() {
		return this.reader;
	}

	/**
	 *
	 * @return whether the user has entered a command
	 */
	private boolean hasCommand() {
		return this.getCommand().getFunction() != null;
	}

	/**
	 *
	 * @return exitFlag
	 */
	public boolean isExitFlag() {
		return this.exitFlag;
	}

	/**
	 * Set userCommand
	 *
	 * @param command
	 *            User Input
	 */
	public void setCommand(String command) {
		this.command = new UserCommand(command);
		if (this.hasCommand()) {
			if (this.analyseCommand()) {
				this.executeCommand();
			} else {
				System.out.println("Wrong number of argument");
			}
		}
	}

	/**
	 * Set exitFlag
	 *
	 * @param exitFlag
	 */
	public void setExitFlag(boolean exitFlag) {
		this.exitFlag = exitFlag;
	}

	/**
	 * Set reader
	 *
	 * @param reader
	 */
	public void setReader(Scanner reader) {
		this.reader = reader;
	}

	/**
	 *
	 * @return exitFlag
	 */
	private boolean shouldExit() {
		return this.exitFlag;
	}
}
