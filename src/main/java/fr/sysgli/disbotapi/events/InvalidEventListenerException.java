package fr.sysgli.disbotapi.events;

/**
 * Exception thrown when there is an attempt to register an invalid class as an event in an EventManager.
 * @author Jeremy LAMBERT
 * @see EventManager
 *
 */
public final class InvalidEventListenerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6583366373700262702L;
	
	public InvalidEventListenerException() {
		System.out.println("Attempt to register an invalid event! An event must implement EventListener,"
				+ " extend ListenerAdapter or contain at least on method annotated with @SubscribeEvent!");
	}

}
