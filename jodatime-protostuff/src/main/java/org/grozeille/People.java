package org.grozeille;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;

public class People {
	private String name;
	
	private DateTime date;
	
	private double size;

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
