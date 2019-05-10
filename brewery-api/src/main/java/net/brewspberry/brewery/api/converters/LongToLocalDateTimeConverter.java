package net.brewspberry.brewery.api.converters;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.databind.util.StdConverter;

public class LongToLocalDateTimeConverter extends StdConverter<Long, LocalDateTime>{

	@Override
	public LocalDateTime convert(Long value) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault());
	}

}
