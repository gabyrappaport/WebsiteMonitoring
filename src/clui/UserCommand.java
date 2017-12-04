package clui;

public class UserCommand {

	/**
	 * Class to interpret the command entered by the user.
	 */

	/**
	 * arguments - function arguments
	 */
	private String[] arguments;

	/**
	 * functions to call
	 */
	private String function;

	/**
	 * First Constructor
	 * <p>
	 * The two instances variables function and arguments are set null
	 * </p>
	 */

	UserCommand() {
		this.function = null;
		this.arguments = null;
	}

	/**
	 * Second Constructor
	 * <p>
	 * This constructor takes a string in argument : it is the input from the user.
	 * Check that the command looks like the following (spaces are important) :
	 * function <> function <arg1> function <arg1, ..., argn> Set the instance
	 * variables with the user input
	 * </p>
	 *
	 * @param command
	 */
	UserCommand(String command) {

		if (command.matches("[A-Za-z]+ <.*>")) {
			this.function = command.split(" <")[0];
			String[] raw_arguments = command.split(">")[0].split(" <");
			if (raw_arguments.length > 1) {
				this.arguments = raw_arguments[1].split(", ");
			}
		} else {
			this.function = null;
			System.out.println("Invalid input! Expected syntax: function <arg1, ..., arg2>");
		}
	}

	/*
	 * Getters and Setters
	 */

	/**
	 *
	 * @return arguments
	 */
	public String[] getArguments() {
		return this.arguments;
	}

	/**
	 *
	 * @return function
	 */
	public String getFunction() {
		return this.function;
	}

	/**
	 *
	 * @param arguments
	 *            Arguments of the function
	 */
	public void setArguments(String[] arguments) {
		this.arguments = arguments;
	}

	/**
	 *
	 * @param command
	 *            User Input
	 */
	public void setFunction(String command) {
		this.function = command;
	}

}
