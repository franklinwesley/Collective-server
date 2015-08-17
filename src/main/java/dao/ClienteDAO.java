package dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import factory.ConnectionFactory;
import model.UserInfo;

/**
 * 
 * Classe responsavel por conter os metodos do CRUD
 */
public class ClienteDAO extends ConnectionFactory {

	private static ClienteDAO instance;
	private final String TOKEN = "?access_token=CAAW9J2rrbi0BANtRD7l1ve3b695QdS3WZAV7mru2KUbjHzWhCrWsZBqEuoEVArZBcxOcZC3ZB7phZAtDvkokhG95g2fIXrfsmqHLoCqOr3rVxhERpeQpYw5oxGqe0rIm4K4vKIBnun7eNIVcZCxp8LZA6yiOs0uGlX6Dt2gWYsphqjlEIEBeExQF";
	
	/**
	 * 
	 * Metodo responsavel por criar uma instancia da classe ClienteDAO (Singleton)
	 *
	 * @return
	 */
	public static ClienteDAO getInstance(){
		if(instance == null)
			instance = new ClienteDAO();
		return instance;
	}
	
	/**
	 * 
	 * Metodo responsavel por listar todos os clientes do banco
	 *
	 * @return ArrayList<UserInfo> clientes
	 */
	public List<UserInfo> listarTodos(){
		Connection conexao = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<UserInfo> clientes = null;
		
		conexao = criarConexao();
		clientes = new ArrayList<UserInfo>();
		try {
			pstmt = conexao.prepareStatement("select * from cliente");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UserInfo cliente = new UserInfo();
				
				cliente.setId(String.valueOf(rs.getInt("id")));
				cliente.setName(rs.getString("nome"));
				cliente.setEmail(rs.getString("email"));
				cliente.setPicture(rs.getString("picture"));
				cliente.setLatitude(rs.getString("latitude"));
				cliente.setLongitude(rs.getString("longitude"));
				dadosCategorias(cliente, "movies");
				dadosCategorias(cliente, "books");
				dadosCategorias(cliente, "games");
				dadosCategorias(cliente, "music");
				dadosCategorias(cliente, "television");
				clientes.add(cliente);
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao listar todos os clientes: " + e);
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return clientes;
	}
	
	public UserInfo getUser(double id) {
		List<UserInfo> usuarios = ClienteDAO.getInstance().listarTodos();
		UserInfo result = null;
		for (int i = 0; i < usuarios.size(); i++) {
			if (Double.parseDouble(usuarios.get(i).getId()) == id) {
				result = usuarios.get(i);
			}
		}
		return result;
	}
	
	public List<UserInfo> getFriends(double id) {
		Connection conexao = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<UserInfo> amigos = null;
		
		conexao = criarConexao();
		amigos = new ArrayList<UserInfo>();
		try {
			pstmt = conexao.prepareStatement("select amigo from amizade where cliente=" + id);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UserInfo cliente = new UserInfo();
				
				cliente.setId(String.valueOf(rs.getInt("id")));
				cliente.setName(rs.getString("nome"));
				cliente.setEmail(rs.getString("email"));
				cliente.setPicture(rs.getString("picture"));
				cliente.setLatitude(rs.getString("latitude"));
				cliente.setLongitude(rs.getString("longitude"));
				dadosCategorias(cliente, "movies");
				dadosCategorias(cliente, "books");
				dadosCategorias(cliente, "games");
				dadosCategorias(cliente, "music");
				dadosCategorias(cliente, "television");
				amigos.add(cliente);
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao listar os amigos: " + e);
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return amigos;
	}
	
	public void dadosCategorias(UserInfo user, String categoria) {
		String url1 = "https://graph.facebook.com/" + user.getId() + "/" + categoria + TOKEN;
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(url1);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
			in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:"+ url1, e);
		} 
		
		try {
			JSONObject json = new JSONObject(sb.toString());
			ArrayList<String> list = new ArrayList<String>();
	        JSONArray array = json.getJSONArray("data");
	        int qtd = array.length();
	            for (int i = 0; i < qtd; i++) {
	                list.add(array.getJSONObject(i).get("name").toString());
	            }
	            switch(categoria){
	                case "movies":
	                    user.addUserMovies(list);
	                    break;
	                case "books":
	                    user.addUserBooks(list);
	                    break;
	                case "games":
	                    user.addUserGames(list);
	                    break;
	                case "music":
	                    user.addUserMusic(list);
	                    break;
	                case "television":
	                    user.addUserTv(list);
	                    break;
	            }
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public void newUser(String id, String name, String email, String picture) {
		Connection conexao = null;
		PreparedStatement pstmt = null;
		conexao = criarConexao();
		try {
			pstmt = conexao.prepareStatement("insert into cliente values(" + id + "," + name + "," + email + "," + picture +  "," + 0 + "," + 0 +")");
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void newFriend(String idCliente, String idAmigo) {
		Connection conexao = null;
		PreparedStatement pstmt = null;
		conexao = criarConexao();
		try {
			pstmt = conexao.prepareStatement("insert into amizade values(" + idCliente + "," + idAmigo + ")");
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void editarLocation (String idCliente, String latitude, String longitude) {
		Connection conexao = null;
		PreparedStatement pstmt = null;
		conexao = criarConexao();
		try { 
			pstmt = conexao.prepareStatement("update cliente set latitude=" + latitude + ",longitude=" + longitude + "where id=" + idCliente);
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
