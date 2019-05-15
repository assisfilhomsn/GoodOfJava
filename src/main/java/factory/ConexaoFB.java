package factory;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConexaoFB {
	static String serverName = "localhost";
	static String porta = "3050";
	static String myDataBase = "C:\\Projetos\\Java\\workspace\\GoodOfJava_ProjetoFinal_27042019\\GodOfJava\\Banco\\GODOFJAVA.FDB"; // Diretório onde se encontra o Banco de Dados
	static String url = "jdbc:firebirdsql:" + serverName + "/" + porta + ":" + myDataBase;
	static String user = "SYSDBA";
	static String senha = "masterkey";
	
	public static void getConnection() {
		try {
			DriverManager.getConnection(url, user, senha);
			JOptionPane.showMessageDialog(null,"Conectado com sucesso");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean conectado() {
		try {
			DriverManager.getConnection(url, user, senha);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}	

}
