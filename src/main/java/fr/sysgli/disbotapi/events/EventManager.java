package fr.sysgli.disbotapi.events;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import net.dv8tion.jda.JDA;
import net.dv8tion.jda.hooks.EventListener;
import net.dv8tion.jda.hooks.ListenerAdapter;
import net.dv8tion.jda.hooks.SubscribeEvent;

/**
 * This class manages all events. It allows to register or unregister events dynamically.
 * @author Jérémy LAMBERT
 *
 */
public final class EventManager {
	
	private JDA jda;
	
	public EventManager(JDA jda) {
		this.jda = jda;
	}
	
	/**
	 * <p>Registers an event listener.</p>
	 * <p>An event listener must implement EventListener, extend ListenerAdpter or contain at least one method annotated with "@SubscribeEvent".
	 * If this condition is not met, an InvalidEventListenerException will be thrown.</p>
	 * @param event
	 * @see EventListener
	 * @see ListenerAdapter
	 * @see SubscribeEvent
	 * @see InvalidEventListenerException
	 */
	public void registerEvent(Object event) {
		if(event instanceof EventListener || event instanceof ListenerAdapter || checkAnnotations(event))
			jda.addEventListener(event);
		else
			try {
				throw new InvalidEventListenerException();
			} catch (InvalidEventListenerException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Unregisters an event.
	 * @param event
	 */
	public void unregisterEvent(Object event) {
		jda.removeEventListener(event);
	}
	
	private boolean checkAnnotations(Object object) {
		for(Method m : object.getClass().getMethods())
			for(Annotation a : m.getAnnotations())
				if(a instanceof SubscribeEvent)
					return true;
		return false;
	}

}
