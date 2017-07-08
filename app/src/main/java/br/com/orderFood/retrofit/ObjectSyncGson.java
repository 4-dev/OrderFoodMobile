package br.com.orderFood.retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import br.com.orderFood.dto.ObjectSync;
import br.com.orderFood.dto.PedidoSync;

/**
 * Created by Ruan Alves
 */
public class ObjectSyncGson implements JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonElement car = json.getAsJsonObject();

        if( json.getAsJsonObject().get("car") != null ){
            car = json.getAsJsonObject().get("car");
        }

        return ( new Gson().fromJson( car, ObjectSync.class ));
    }
}
