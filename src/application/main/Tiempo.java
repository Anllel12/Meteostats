package application.main;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.io.Reader;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Tiempo {
	
	public final static String JSON_TIEMPO = "data/tiempo.json";

	public Vector<TiempoObj> getWeather() {
		Vector<TiempoObj> tiempo = deserializarJsonArray();
//		for (TiempoObj tiempoObj : tiempo) {
//			if (tiempoObj.getTo() == to) {
//				msgNew.add(tiempoObj);
//			}
//		}
		return tiempo;
	}
	
	private Vector<TiempoObj> deserializarJsonArray() {
		Vector<TiempoObj> tiempos = new Vector<TiempoObj>();
		
		try (Reader reader = new FileReader(JSON_TIEMPO)) {
			Gson gson = new Gson();
			Type tipoListaTiempos = new TypeToken<Vector<TiempoObj>>(){}.getType();
			tiempos = gson.fromJson(reader, tipoListaTiempos);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return tiempos;
	}
}
