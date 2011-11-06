package org.grozeille;

import java.io.IOException;
import java.lang.reflect.Field;

import org.joda.time.DateTime;
import org.joda.time.base.BaseDateTime;

import com.dyuproject.protostuff.Input;
import com.dyuproject.protostuff.Output;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * read/write JodaTime to be compatible with Protobuf-net
 * 
 * @see http://code.google.com/p/protobuf-net/
 * @author Mathias Kluba
 * 
 */
public final class DateTimeSchema implements Schema<DateTime> {

	private static int SCALE_DAYS = 0;
	private static int SCALE_HOURS = 1;
	private static int SCALE_MINUTES = 2;
	private static int SCALE_SECONDS = 3;
	private static int SCALE_MILLISECONDS = 4;
	private static int SCALE_TICKS = 5; // http://msdn.microsoft.com/en-us/library/system.timespan.ticks.aspx

	private static long TICKS_PER_MILLISECONDS = 10000;

	public static final DateTimeSchema DATETIME_SCHEMA = new DateTimeSchema();

	static {
		RuntimeSchema.register(DateTime.class, DATETIME_SCHEMA);
	}

	public String getFieldName(int number) {
		return null;
	}

	public int getFieldNumber(String name) {
		return 0;
	}

	public boolean isInitialized(DateTime message) {
		return true;
	}

	public DateTime newMessage() {
		return new DateTime(0);
	}

	public String messageName() {
		return null;
	}

	public String messageFullName() {
		return null;
	}

	public Class<? super DateTime> typeClass() {
		return DateTime.class;
	}

	public void mergeFrom(Input input, DateTime message) throws IOException {
		int number = input.readFieldNumber(this);
		long value = 0;
		int scale = 0;
		while (number > 0) {
			if (number == 1) {
				value = input.readSInt64();
			} else if (number == 2) {
				scale = input.readInt32();
			}
			number = input.readFieldNumber(this);
		}
		long millis = 0;
		if (scale == DateTimeSchema.SCALE_TICKS) {
			millis = value / DateTimeSchema.TICKS_PER_MILLISECONDS;
		} else if (scale == DateTimeSchema.SCALE_MILLISECONDS) {
			millis = value;
		} else if (scale == DateTimeSchema.SCALE_SECONDS) {
			millis = value * 1000;
		} else if (scale == DateTimeSchema.SCALE_MINUTES) {
			millis = value * 1000 * 60;
		} else if (scale == DateTimeSchema.SCALE_HOURS) {
			millis = value * 1000 * 60 * 60;
		} else if (scale == DateTimeSchema.SCALE_DAYS) {
			millis = value * 1000 * 60 * 60 * 24;
		}

		try {
			Field field = BaseDateTime.class.getDeclaredField("iMillis");
			field.setAccessible(true);
			field.set(message, message.getZone().convertUTCToLocal(millis));
		} catch (Exception ex) {
			throw new IOException(ex);
		}
	}

	public void writeTo(Output output, DateTime message) throws IOException {
		output.writeSInt64(1,
				message.getZone().convertLocalToUTC(message.getMillis(), true)
						* DateTimeSchema.TICKS_PER_MILLISECONDS, false);
		output.writeInt32(2, DateTimeSchema.SCALE_TICKS, false);
	}

}
