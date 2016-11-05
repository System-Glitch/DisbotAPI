package fr.sysgli.disbotapi.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Log utility that allows to quickly and easily print log messages with time and status info.</p>
 * @author Jeremy LAMBERT
 *
 */
public abstract class Logger_ {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
	
	public enum LogStatus {

		INFO("Info"),
		WARNING("Warning"),
		ERROR("Error"),
		DEBUG("Debug");

		private String message;

		LogStatus(String message) {
			this.message = message;
		}
		
		String getMessage() {
			return message;
		}
	}
	
	/**
	 * Displays a message in the console with the chosen status.
	 * @param status
	 * @param message
	 */
	public static final void log(LogStatus status , String message) {
		System.out.println("[" + DATE_FORMAT.format(new Date()) + "] [" + status.getMessage() + "] " + message);
	}
	
	/**
	 * Displays an info message in the console.
	 * @param message
	 */
	public static final void info(String message) {
		log(LogStatus.INFO , message);
	}
	
	/**
	 * Displays a warning message in the console
	 * @param message
	 */
	public static final void warning(String message) {
		log(LogStatus.WARNING , message);
	}
	
	/**
	 * Displays an error message in the console.
	 * @param message
	 */
	public static final void error(String message) {
		log(LogStatus.ERROR , message);
	}
	
	/**
	 * Displays a debug message in the console.
	 * @param message
	 */
	public static final void debug(String message) {
		log(LogStatus.DEBUG , message);
	}

}
