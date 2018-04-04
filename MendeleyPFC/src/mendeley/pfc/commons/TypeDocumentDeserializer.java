package mendeley.pfc.commons;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class TypeDocumentDeserializer implements JsonDeserializer<TypeDocument> {

	@Override
	public TypeDocument deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		String key = element.getAsString();
		return TypeDocument.fromKey(key);
	}

}
