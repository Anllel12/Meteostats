package application.model;

import java.sql.Timestamp;

public class TiempoObjConUnidades {

	private Timestamp hora;
    private String temperatura;
    private String presion;
    private String humedad;
    private String amanecer;
    private String atardecer;

    public Timestamp getHora() {
        return hora;
    }

    public void setHora(Timestamp timestamp) {
        this.hora = timestamp;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getPresion() {
        return presion;
    }

    public void setPresion(String presion) {
        this.presion = presion;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getAmanecer() {
        return amanecer;
    }

    public void setAmanecer(String amanecer) {
        this.amanecer = amanecer;
    }

    public String getAtardecer() {
        return atardecer;
    }

    public void setAtardecer(String atardecer) {
        this.atardecer = atardecer;
    }
	
}
