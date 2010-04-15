package org.pssframework.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingFilter {
	/** The logger name */
	private final String name;

	/** The logger */
	private final Logger logger;

	/** The log level for the exceptionCaught event. Default to WARN. */
	private LogLevel exceptionCaughtLevel = LogLevel.WARN;

	/** The log level for the messageSent event. Default to INFO. */
	private LogLevel messageSentLevel = LogLevel.INFO;

	/** The log level for the messageReceived event. Default to INFO. */
	private LogLevel messageReceivedLevel = LogLevel.INFO;

	/** The log level for the sessionCreated event. Default to INFO. */
	private LogLevel sessionCreatedLevel = LogLevel.INFO;

	/** The log level for the sessionOpened event. Default to INFO. */
	private LogLevel sessionOpenedLevel = LogLevel.INFO;

	/** The log level for the sessionIdle event. Default to INFO. */
	private LogLevel sessionIdleLevel = LogLevel.INFO;

	/** The log level for the sessionClosed event. Default to INFO. */
	private LogLevel sessionClosedLevel = LogLevel.INFO;

	/**
	 * Default Constructor.
	 */
	public LoggingFilter() {
		this(LoggingFilter.class.getName());
	}

	/**
	 * Create a new NoopFilter using a class name
	 * 
	 * @param clazz
	 *            the cass which name will be used to create the logger
	 */
	public LoggingFilter(Class<?> clazz) {
		this(clazz.getName());
	}

	/**
	 * Create a new NoopFilter using a name
	 * 
	 * @param name
	 *            the name used to create the logger. If null, will default to
	 *            "NoopFilter"
	 */
	public LoggingFilter(String name) {
		if (name == null) {
			this.name = LoggingFilter.class.getName();
		} else {
			this.name = name;
		}

		logger = LoggerFactory.getLogger(this.name);
	}

	/**
	 * @return The logger's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Log if the logger and the current event log level are compatible. We log
	 * a message and an exception.
	 * 
	 * @param eventLevel
	 *            the event log level as requested by the user
	 * @param message
	 *            the message to log
	 * @param cause
	 *            the exception cause to log
	 */
	private void log(LogLevel eventLevel, String message, Throwable cause) {
		switch (eventLevel) {
		case TRACE:
			logger.trace(message, cause);
			return;
		case DEBUG:
			logger.debug(message, cause);
			return;
		case INFO:
			logger.info(message, cause);
			return;
		case WARN:
			logger.warn(message, cause);
			return;
		case ERROR:
			logger.error(message, cause);
			return;
		default:
			return;
		}
	}

	/**
	 * Log if the logger and the current event log level are compatible. We log
	 * a formated message and its parameters.
	 * 
	 * @param eventLevel
	 *            the event log level as requested by the user
	 * @param message
	 *            the formated message to log
	 * @param param
	 *            the parameter injected into the message
	 */
	private void log(LogLevel eventLevel, String message, Object param) {
		switch (eventLevel) {
		case TRACE:
			logger.trace(message, param);
			return;
		case DEBUG:
			logger.debug(message, param);
			return;
		case INFO:
			logger.info(message, param);
			return;
		case WARN:
			logger.warn(message, param);
			return;
		case ERROR:
			logger.error(message, param);
			return;
		default:
			return;
		}
	}

	/**
	 * Log if the logger and the current event log level are compatible. We log
	 * a simple message.
	 * 
	 * @param eventLevel
	 *            the event log level as requested by the user
	 * @param message
	 *            the message to log
	 */
	private void log(LogLevel eventLevel, String message) {
		switch (eventLevel) {
		case TRACE:
			logger.trace(message);
			return;
		case DEBUG:
			logger.debug(message);
			return;
		case INFO:
			logger.info(message);
			return;
		case WARN:
			logger.warn(message);
			return;
		case ERROR:
			logger.error(message);
			return;
		default:
			return;
		}
	}

}
