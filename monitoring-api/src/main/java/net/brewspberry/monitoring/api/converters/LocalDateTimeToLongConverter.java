package net.brewspberry.monitoring.api.converters;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.databind.util.StdConverter;

public class LocalDateTimeToLongConverter extends StdConverter<LocalDateTime, Long>{

	@Override
	public Long convert(LocalDateTime value) {
		return value.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
	}

}
