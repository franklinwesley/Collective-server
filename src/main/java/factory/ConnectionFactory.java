package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * 
 * Classe responsavel por conter os metodos criar e fechar o banco de dados.
 */
public class ConnectionFactory {

	// Caminho do banco de dados.
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "postgres://oypfylxlwwczkz:k1a3GulmScVSMLX1f19p23yke_@ec2-107-21-125-143.compute-1.amazonaws.com:5432/dfa6hd1b2fpt89";
	private static final String USUARIO = "oypfylxlwwczkz";
	private static final String SENHA = "k1a3GulmScVSMLX1f19p23yke_";
	

	/**
	 * 
	 * Metodo responsavel por criar uma conexao com o banco 
	 *
	 * @return
	 */
	public Connection criarConexao(){
		
		Connection conexao = null;
		
		try {
			
			Class.forName(DRIVER);
			conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
			
		} catch (Exception e) {
			System.out.println("Erro ao criar conexao com o banco: " + URL);
			e.printStackTrace();
		}
		return conexao;
	}
	
	
	public void fecharConexao(Connection conexao, PreparedStatement pstmt, ResultSet rs){
		
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
			System.out.println("Erro ao fechar conexao com o banco: " + URL);
		}
	}
}

