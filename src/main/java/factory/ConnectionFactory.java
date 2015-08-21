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
	public Connection criarConexao(){
		URI dbUri;
		try {
			dbUri = new URI(System.getenv("postgres://oypfylxlwwczkz:k1a3GulmScVSMLX1f19p23yke_@ec2-107-21-125-143.compute-1.amazonaws.com:5432/dfa6hd1b2fpt89"));
			String username = dbUri.getUserInfo().split(":")[0];
	        String password = dbUri.getUserInfo().split(":")[1];
	        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

	        return DriverManager.getConnection(dbUrl, username, password);
		} catch (URISyntaxException | SQLException e) {
			System.out.println("Erro ao criar conexao com o banco");
			e.printStackTrace();
		}
		return null;
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

