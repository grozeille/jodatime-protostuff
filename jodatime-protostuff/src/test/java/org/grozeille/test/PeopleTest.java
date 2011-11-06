package org.grozeille.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.grozeille.DateTimeSchema;
import org.grozeille.People;
import org.joda.time.DateTime;
import org.junit.Test;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class PeopleTest {

	@Test
	public void test() throws IOException {
		People people = new People();
		people.setName("Test");
		people.setDate(new DateTime(2011, 11, 6, 16, 20, 0, 0));
		people.setSize(1.3);

		
		LinkedBuffer buffer = LinkedBuffer
				.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		
		RuntimeSchema.register(DateTime.class, new DateTimeSchema());
		
		RuntimeSchema.createFrom(People.class);

		FileOutputStream out2 = new FileOutputStream("target/people.out");

		ProtobufIOUtil.writeTo(out2, people,
				RuntimeSchema.getSchema(People.class), buffer);
		

		buffer.clear();
		
		FileInputStream in = new FileInputStream("target/people.net.out");

		ProtobufIOUtil.mergeFrom(in, people, RuntimeSchema.getSchema(People.class));
		
		System.out.println(people);

	}
}
