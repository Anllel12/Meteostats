package application.main;

public class Usuario {
	private String usuario;
	private String nombre;
	private String apellido;
	private String contrasena;
	private String email;
	private int rol;
	
	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", nombre=" + nombre + ", apellido=" + apellido + ", contrasena="
				+ contrasena + ", email=" + email + ", rol=" + rol + "]";
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
	
	

}
