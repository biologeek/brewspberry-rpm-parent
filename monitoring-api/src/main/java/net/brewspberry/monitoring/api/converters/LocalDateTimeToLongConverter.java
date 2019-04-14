package net.brewspberry.monitoring.api.converters;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.databind.util.StdConverter;

public class LocalDateTimeToLongConverter extends StdConverter<LocalDateTime, Long> {

	@Override
	public Long convert(LocalDateTime value) {
		return value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

}
