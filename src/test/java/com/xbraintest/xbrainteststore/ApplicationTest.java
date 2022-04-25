package com.xbraintest.xbrainteststore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


@WebMvcTest(XbrainTestStoreApplication.class)
public class ApplicationTest {

	@Test
	public void testApplication() {
		XbrainTestStoreApplication.main(new String[] { "parameter1", "parameter2" });
	}
}
