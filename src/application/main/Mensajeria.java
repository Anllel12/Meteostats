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

	public final static String JSON_MENSAJERIA = "data/mensajeria.json";
	public static final int ERROR_ESCRITURA = -1;
	public static final int ESCRITURA_OK = 0;
	public static final int MENS_NO_EXISTE = -1;
	public static final int MENS_MODIFICADO = 0;
	public static final int STATUS_INICIAL = 0;
	public static final int STATUS_PENDIENTE = 1;
	public static final int USER_ADMIN = 2;
	public static final int CLIENTE_SUGERENCIA = 10;
	public static final int CLIENTE_ERROR = 11;
	public static final String TO_ADMIN = "TO_ADMIN_GENERICO"; 
	public static final String TO_TECNICO = "TO_TECNICO_GENERICO"; 
	
	public int writeNewMessage(String msg, int from, Vector<String> to) {
		return serializarArrayAJson(new MensajeObj(msg, from, to, STATUS_INICIAL, generateDate()));
	}
	
	public int deleteMessage(MensajeObj msg) {
		boolean found = false;
		Vector<MensajeObj> mensajes = deserializarJsonArray();
		Vector<MensajeObj> msgNew = new Vector<MensajeObj>();
		for (MensajeObj mensajeObj : mensajes) {
			if (mensajeObj.getDescripcion().equals(msg.getDescripcion()) && mensajeObj.getFecha().equals(msg.getFecha())) {
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
		
		/*
		Vector<MensajeObj> mensajes = deserializarJsonArray();
		if (mensajes.contains(msg)) {
			mensajes.remove(msg);
			return vectorToJson(mensajes);
		} else {
			return MENS_NO_EXISTE;
		}
		*/
	}
	
	
	public Vector<MensajeObj> getMessages(Usuario us) {
		Vector<MensajeObj> mensajes = deserializarJsonArray();
		Vector<MensajeObj> msgNew = new Vector<MensajeObj>();
		if (mensajes != null) {
			for (MensajeObj mensajeObj : mensajes) {
				if (us.getRol() == GestionGson.ROL_TECNICO) {
					if (mensajeObj.getTo().contains(us.getUsuario()) || mensajeObj.getTo().contains(TO_TECNICO)) {
						msgNew.add(mensajeObj);
					}
				} else if (us.getRol() == GestionGson.ROL_ADMIN) {
					if (mensajeObj.getTo().contains(TO_ADMIN)) {
						msgNew.add(mensajeObj);
					}
				}
				
			}
		}
		return msgNew;
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
		if (mensajes == null) {
			mensajes = new Vector<MensajeObj>();
		}
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