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

package org.springframework.boot.actuate.endpoint.invoke.convert;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IsoOffsetDateTimeConverter}.
 *
 * @author Phillip Webb
 */
class IsoOffsetDateTimeConverterTests {

	@Test
	void convertShouldConvertIsoDate() {
		IsoOffsetDateTimeConverter converter = new IsoOffsetDateTimeConverter();
		OffsetDateTime time = converter.convert("2011-12-03T10:15:30+01:00");
		assertThat(time).isNotNull();
	}

	@Test
	void registerConverterShouldRegister() {
		DefaultConversionService service = new DefaultConversionService();
		IsoOffsetDateTimeConverter.registerConverter(service);
		OffsetDateTime time = service.convert("2011-12-03T10:15:30+01:00", OffsetDateTime.class);
		assertThat(time).isNotNull();
	}

}
