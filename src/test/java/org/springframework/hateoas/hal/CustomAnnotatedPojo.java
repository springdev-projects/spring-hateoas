package org.springframework.hateoas.hal;

import lombok.Setter;

import java.io.IOException;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Relation(value = "pojo", collectionRelation = "pojos")
@JsonDeserialize(contentUsing = CustomAnnotatedPojo.CustomDeserializer.class)
public class CustomAnnotatedPojo extends SimplePojo {

	private @Setter String secretValue = "default";

	public CustomAnnotatedPojo() {
	}

	public CustomAnnotatedPojo(String text, int number, String customSecretValue) {

		setText(text);
		setNumber(number);
		setSecretValue(customSecretValue);
	}

	static class CustomDeserializer extends JsonDeserializer<CustomAnnotatedPojo> {

		@Override
		public CustomAnnotatedPojo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			
			JsonDeserializer<Object> deser = ctxt.findRootValueDeserializer(ctxt.constructType(SimpleAnnotatedPojo.class));

			SimpleAnnotatedPojo foo = (SimpleAnnotatedPojo) deser.deserialize(p, ctxt);

			return new CustomAnnotatedPojo(foo.getText(), foo.getNumber(), "custom deserializer was here!");
		}
	}

}
