package application.model;

import java.util.Vector;

public class Usuario {
	private String usuario;
	private String nombre;
	private String apellido;
	private String contrasena;
	private String email;
	private int rol;
	private Vector<Integer> adminsACargo;
	private Vector<Integer> tecnicosACargo;
	
	

	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", nombre=" + nombre + ", apellido=" + apellido + ", contrasena="
				+ contrasena + ", email=" + email + ", rol=" + rol + ", adminsACargo=" + adminsACargo + "]";
	}
	
	public Usuario(String usuario, String nombre, String apellido, String contrasena, String email, int rol,
			Vector<Integer> adminsACargo, Vector<Integer> tecnicosACargo) {
		super();
		this.usuario = usuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contrasena = contrasena;
		this.email = email;
		this.rol = rol;
		this.adminsACargo = adminsACargo;
		this.tecnicosACargo = tecnicosACargo;
	}


	public Usuario(String usuario, String nombre, String apellido, String contrasena, String email, int rol) {
		super();
		this.usuario = usuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contrasena = contrasena;
		this.email = email;
		this.rol = rol;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getRol() {
		return rol;
	}
	public void setRol(int rol) {
		this.rol = rol;
	}

	public Vector<Integer> getAdminsACargo() {
		return adminsACargo;
	}

	public void setAdminsACargo(Vector<Integer> adminsACargo) {
		this.adminsACargo = adminsACargo;
	}
	
	public Vector<Integer> getTecnicosACargo() {
		return tecnicosACargo;
	}
	
	public void setTecnicosACargo(Vector<Integer> tecnicosACargo) {
		this.tecnicosACargo = tecnicosACargo;
	}

}
