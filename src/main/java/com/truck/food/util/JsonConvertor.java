package com.truck.food.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class JsonConvertor {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final ObjectMapper OBJECT_MAPPER_FULLNAME;

	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJECT_MAPPER_FULLNAME = new ObjectMapper();
		OBJECT_MAPPER_FULLNAME.setAnnotationIntrospector(new JacksonFullNameIntrospector());
	}

	/**
	 * @param object
	 * @return JSON String for the input object
	 * @throws JsonProcessingException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> String convertObjectToJson(T object){
		ObjectWriter writer = OBJECT_MAPPER.writer();
		try {
			return writer.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public static <T> T convertInputStreamReaderToObject(InputStreamReader file, TypeReference<T> valueTypeRef)
			throws JsonParseException, JsonMappingException, IOException {
		if (file != null) {
			return OBJECT_MAPPER.readValue(file, valueTypeRef);
		}
		return null;
	}

	public static <T> T toObject(InputStream inputStream, Class<T> typeClass)
			throws JsonParseException, JsonMappingException, IOException {
		return OBJECT_MAPPER.readValue(inputStream, typeClass);
	}

	/**
	 * @param object
	 * @return JSON String for the input object
	 * @throws JsonProcessingException 
	 */
	public static <T> String convertObjectToJsonFullNameProps(T object) throws JsonProcessingException {
		ObjectWriter writer;
		writer = OBJECT_MAPPER_FULLNAME.writer();
		return writer.writeValueAsString(object);
	}

	private static class JacksonFullNameIntrospector extends JacksonAnnotationIntrospector {

		private static final long serialVersionUID = 6991546067964041818L;

		public PropertyName findNameForSerialization(Annotated property) {
			String name = null;

			JsonGetter jg = _findAnnotation(property, JsonGetter.class);
			if (jg != null) {
				name = jg.value();
			} else {
				JsonProperty pann = _findAnnotation(property, JsonProperty.class);
				if (pann != null) {
					name = property.getName();
				} else if (_hasAnnotation(property, JsonSerialize.class) || _hasAnnotation(property, JsonView.class)
						|| _hasAnnotation(property, JsonRawValue.class)) {
					name = "";
				}
			}
			return PropertyName.construct(name);
		}
	};
}
