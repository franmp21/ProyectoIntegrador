import java.sql.SQLException;
import java.util.Scanner;
public class Principal {
	public static void main(String[] args) throws SQLException {
		Scanner sc = new Scanner(System.in);
		Meetodos p1 = new Meetodos();
		p1.conectar();
		
		try {
			System.out.println("�Cu�l es su email?");
			String email = sc.next();
			System.out.println("�Contrase�a?");
			String contrase�a = sc.next();
			if(p1.consultarUsuarios(email, contrase�a)==true) {
				p1.consultarVideojuegos();
				System.out.println("�Quiere COMPRAR o ALQUILER?");
				String pedido = sc.next();
				if(pedido.equals("COMPRAR")) {
					System.out.println("�Qu� c�digo de videojuego quiere comprar?");
					String cod_videojuego = sc.next();
					p1.generarCompra(cod_videojuego, email);
				}
				else if(pedido.equals("ALQUILER")){
					System.out.println("�Qu� c�digo de videojuego quiere alquilar?");
					String cod_videojuego = sc.next();
					System.out.println("�Cu�ntos d�as?");
					int num_dias = sc.nextInt();
					p1.generarAlquiler(cod_videojuego, email, num_dias);
				}
				
			}
			p1.cerrar();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
