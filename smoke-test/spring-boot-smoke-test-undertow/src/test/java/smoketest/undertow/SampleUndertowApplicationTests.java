/*
 * Copyright 2012-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package smoketest.undertow;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.test.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic integration tests for demo application.
 *
 * @author Ivan Sopov
 * @author Andy Wilkinson
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SampleUndertowApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testHome() {
		assertOkResponse("/", "Hello World");
	}

	@Test
	void testAsync() {
		assertOkResponse("/async", "async: Hello World");
	}

	@Test
	void testCompression() throws Exception {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Accept-Encoding", "gzip");
		HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);
		ResponseEntity<byte[]> entity = this.restTemplate.exchange("/", HttpMethod.GET, requestEntity, byte[].class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);

		try (GZIPInputStream inflater = new GZIPInputStream(new ByteArrayInputStream(entity.getBody()))) {
			assertThat(StreamUtils.copyToString(inflater, StandardCharsets.UTF_8)).isEqualTo("Hello World");
		}
	}

	private void assertOkResponse(String path, String body) {
		ResponseEntity<String> entity = this.restTemplate.getForEntity(path, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isEqualTo(body);
	}

}
