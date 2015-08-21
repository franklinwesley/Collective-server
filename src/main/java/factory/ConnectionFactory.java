package factory;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;


/**
 * 
 * Classe responsavel por conter os metodos criar e fechar o banco de dados.
 */
public class ConnectionFactory {

	/**
	 * 
	 * Metodo responsavel por criar uma conexao com o banco 
	 *
	 * @return
	 */
	public Connection criarConexao() throws URISyntaxException, SQLException{
		URI dbUri = new URI(System.getenv("DATABASE_URL"));
		String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

	    return DriverManager.getConnection(dbUrl, username, password);
	}
	
	
	public void fecharConexao(Connection conexao, Statement pstmt, ResultSet rs){
		
		try {
			
			if(conexao != null){
				conexao.close();
			}
			if(pstmt != null){
				pstmt.close();
			}
			if(rs != null){
				rs.close();
			}
					
		} catch (Exception e) {
			System.out.println("Erro ao fechar conexao com o banco");
		}
	}
	
public void fecharConexao(Connection conexao, Statement pstmt){
		
		try {
			
			if(conexao != null){
				conexao.close();
			}
			if(pstmt != null){
				pstmt.close();
			}
					
		} catch (Exception e) {
			System.out.println("Erro ao fechar conexao com o banco");
		}
	}
}

