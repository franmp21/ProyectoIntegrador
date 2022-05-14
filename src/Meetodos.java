
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.ResultSet;

public class Meetodos {
	private static String cod_pedido = "";
	private static int contador=0;
	private static String bd="XE";
	private ResultSet rs;
	private Statement st;
	private static String login="PROYECTO";
	private static String password="PROYECTO";
	private static String url="jdbc:oracle:thin:@192.168.1.143:1521/"+bd;
	static Connection connection = null;
	public void conectar(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(url,login,password);
			if(connection!=null) {
				System.out.println("Conexxión realizada correctamente.");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void consultarVideojuegos() throws SQLException{
		String cod_videojuego;
		String titulo;
		String categoria;
		double precio;
		System.out.println("Aquí tiene los videojuegos disponibles:"+"\n");
		System.out.println("");
		st=connection.createStatement();
		rs=st.executeQuery("Select cod_videojuego, titulo, categoria, precio from videojuego");
		while(rs.next()) {
			cod_videojuego=rs.getString("cod_videojuego");
			titulo=rs.getString("titulo");
			categoria=rs.getString("categoria");
			precio=rs.getDouble("precio");
			System.out.println(cod_videojuego+"   "+titulo+"   "+categoria+"   "+precio+"€"+"\n");
		}
	}
	public void cerrar()throws SQLException {
		if(rs!=null)
			rs.close();
		if(st!=null)
			st.close();
		if(connection!=null)
			connection.close();
	}

	public void crearUsuario(String nombre, String email, String contraseña ) throws SQLException{
		PreparedStatement ps= connection.prepareStatement("insert into clientes values(?, ?, ?)");
				
		ps.setString(1, nombre);
		ps.setString(2, email);
		ps.setString(3, contraseña);
		ps.executeUpdate();
		
	}
	public boolean consultarUsuarios(String email, String contraseña) throws SQLException{
		boolean existe = false;
		String nombre;
		String email2;
		String contraseña2;
		st=connection.createStatement();
		rs=st.executeQuery("Select email, contraseña from clientes");
		while(rs.next()) {
			
			email2=rs.getString("email");
			contraseña2=rs.getString("contraseña");
			if(email2.equals(email) && contraseña2.equals(contraseña)) {
				existe=true;
			}
			else {
				System.out.println("Usuario o contraseña inválidos.");
			}
		}
		return existe;
	}
	public void generarCompra(String cod_videojuego, String email) throws SQLException {
		contador++;
		Double precio = 0.0;
		st=connection.createStatement();
		rs=st.executeQuery("Select cod_videojuego from videojuego");


		rs=st.executeQuery("Select precio from videojuego where cod_videojuego = "+"'"+cod_videojuego+"'");
		while(rs.next()) {
			precio = rs.getDouble("precio");
		}
		PreparedStatement ps= connection.prepareStatement("insert into pedido values(?, ?, ?, ?, ?, default, default)");
		cod_pedido+="P"+String.format("%03d", contador);
		ps.setString(1, cod_pedido);
		ps.setDouble(2, precio);
		ps.setString(3, cod_videojuego);
		ps.setString(4, "COMPRA");
		ps.setString(5, email);
		ps.executeUpdate();

		if(rs!=null)
			rs.close();
		if(st!=null)
			st.close();
		if(connection!=null)
			connection.close();

		System.out.println("Compra realizada con éxito");
	}
	public void generarAlquiler(String cod_videojuego, String email, int num_dias) throws SQLException{
		Date date = new Date();
		
		contador++;
	
		Double precio = 0.0;
		st=connection.createStatement();
		rs=st.executeQuery("Select cod_videojuego from videojuego");


		rs=st.executeQuery("Select precio from videojuego where cod_videojuego = "+"'"+cod_videojuego+"'");
		while(rs.next()) {
			precio = rs.getDouble("precio");
		}

		PreparedStatement ps= connection.prepareStatement("insert into pedido values(?, ?, ?, ?, ?, default, ?)");
		cod_pedido+="P"+String.format("%03d", contador);
		ps.setString(1, cod_pedido);
		ps.setDouble(2, precio);
		ps.setString(3, cod_videojuego);
		ps.setString(4, "ALQUILER");
		ps.setString(5, email);
		ps.setDate(6, date);
		ps.executeUpdate();

		if(rs!=null)
			rs.close();
		if(st!=null)
			st.close();
		if(connection!=null)
			connection.close();

		System.out.println("Compra realizada con éxito");
	}
	
	
	
	
}
