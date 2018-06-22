package me.rajput.practice.it.services.auditing;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import org.springframework.data.auditing.DateTimeProvider;

/**
 * 
 * Description: Date time provider for Spring to provider time in UTC time zone only. 
 * 
 * @author Deependra Rajput
 * @date Jun 23, 2018
 *
 */
public class UniversalDateTimeProvider implements DateTimeProvider {
	
	/** Override UTC time zone to system specific to keep it standard and portable. */
	private static final ZoneId ZONEID = ZoneId.of("UTC");

	@Override
	public Optional<TemporalAccessor> getNow() {
		return Optional.of(ZonedDateTime.now(ZONEID).toLocalDateTime());
	}

}
