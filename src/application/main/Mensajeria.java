package application.main;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Mensajeria {

	private final static String JSON_MENSAJERIA = "data/mensajeria.json";
	public static final int ERROR_ESCRITURA = -1;
	public static final int ESCRITURA_OK = 0;
	public static final int MENS_NO_EXISTE = -1;
	public static final int MENS_MODIFICADO = 0;
	public static final int STATUS_INICIAL = 0;
	public static final int STATUS_PENDIENTE = 1;
	
	public int writeNewMessage(String msg, int from, int to) {
		return serializarArrayAJson(new MensajeObj(msg, from, to, 0, generateDate()));
	}
	
	public int deleteMessage(MensajeObj msg) {
		Vector<MensajeObj> mensajes = deserializarJsonArray();
		if (mensajes.contains(msg)) {
			mensajes.remove(msg);
			return vectorToJson(mensajes);
		} else {
			return MENS_NO_EXISTE;
		}
		
	}
	
	public int modifyMessage(MensajeObj msg) {
		boolean found = false;
		Vector<MensajeObj> mensajes = deserializarJsonArray();
		Vector<MensajeObj> msgNew = new Vector<MensajeObj>();
		for (MensajeObj mensajeObj : mensajes) {
			if (mensajeObj.getDescripcion().equals(msg.getDescripcion()) && mensajeObj.getFecha().equals(msg.getFecha())) {
				msgNew.add(msg);
				found = true;
			} else {
				msgNew.add(mensajeObj);
			}
		}
		
		if (found) {
			return vectorToJson(msgNew);
		} else {
			return MENS_NO_EXISTE;
		}
	}
	
	private String generateDate() {
		SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy '-' HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}
	
	private int vectorToJson(Vector<MensajeObj> msgs) {
		Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
		try(FileWriter writer = new FileWriter(JSON_MENSAJERIA)){
			prettyGson.toJson(msgs, writer);
			return ESCRITURA_OK;
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR_ESCRITURA;
        }
	}
	
	private int serializarArrayAJson(MensajeObj msg) {
		Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
		Vector<MensajeObj> mensajes = deserializarJsonArray();
		mensajes.add(msg);
		try(FileWriter writer = new FileWriter(JSON_MENSAJERIA)){
			prettyGson.toJson(mensajes, writer);
			return ESCRITURA_OK;
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR_ESCRITURA;
        }
		
	}
	
	private Vector<MensajeObj> deserializarJsonArray() {
		Vector<MensajeObj> mensajes = new Vector<MensajeObj>();
		
		try (Reader reader = new FileReader(JSON_MENSAJERIA)) {
			Gson gson = new Gson();
			Type tipoListaUsuarios = new TypeToken<Vector<MensajeObj>>(){}.getType();
			mensajes = gson.fromJson(reader, tipoListaUsuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return mensajes;
	}
}
