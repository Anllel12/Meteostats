package application.main;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.io.Reader;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Tiempo {
	
	private final static String JSON_TIEMPO = "data/tiempo.json";

	public Vector<TiempoObj> getWeather() {
		Vector<TiempoObj> tiempo = deserializarJsonArray();
		Vector<TiempoObj> msgNew = new Vector<TiempoObj>();
//		for (TiempoObj tiempoObj : tiempo) {
//			if (tiempoObj.getTo() == to) {
//				msgNew.add(tiempoObj);
//			}
//		}
		return msgNew;
	}
	
	private Vector<TiempoObj> deserializarJsonArray() {
		Vector<TiempoObj> mensajes = new Vector<TiempoObj>();
		
		try (Reader reader = new FileReader(JSON_TIEMPO)) {
			Gson gson = new Gson();
			Type tipoListaUsuarios = new TypeToken<Vector<TiempoObj>>(){}.getType();
			mensajes = gson.fromJson(reader, tipoListaUsuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return mensajes;
	}
}
