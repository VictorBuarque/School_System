package controleEscolar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Controle_EscolarConnection{
	
    public static Connection ConnectDb() {
        try {
            // Carregar o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Estabelecer a conexão com o banco de dados
            String url = "jdbc:mysql://localhost:3306/sistema_escolar";
            String username = "root";
            String password = "";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            JOptionPane.showMessageDialog(null, "Base de dados Conectada");
			return conn;
            
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}