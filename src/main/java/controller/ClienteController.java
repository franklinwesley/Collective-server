package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dao.ClienteDAO;
import model.Category;
import model.InterestesCalculator;
import model.Messeger;
import model.UserInfo;


/**
 * 
 * Classe responsavel por ser o controlador entre o resource e a camada DAO
 */
public class ClienteController {
	
	private InterestesCalculator recommender = new InterestesCalculator();
	private List<Messeger> messeger = new ArrayList<Messeger>();
	
	public void addAmigos() {
		//ygor
		ClienteDAO.getInstance().newUser("836885636398591", "Ygor Santos", "ygor_gs@live.com", "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn2/v/t1.0-1/c254.36.452.452/s50x50/945140_455330777887414_891839605_n.jpg?oh=321c9a6861239b67abd6f7b28f93f9ba&oe=563DDB8D&__gda__=1447440487_9bc4512ae9b4d57d8835fac0a465e845");
		//yuri
		ClienteDAO.getInstance().newUser("900945989976185", "Yuri Lugo", "yuri.lugo@hotmail.com", "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xat1/v/t1.0-1/p50x50/11078239_876286065775511_4795499323612538017_n.jpg?oh=ad1cef7bbb839492191a9a0eb607ec63&oe=56813584&__gda__=1446564544_163754e09dd6262e35a0e68ce23c6be6");
		//diego
		ClienteDAO.getInstance().newUser("451357765036291", "Diego Augusto Moura", "diego.cc.ufcg@gmail.com", "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xta1/v/t1.0-1/c0.0.50.50/p50x50/11110472_405296816309053_1061210670051534768_n.jpg?oh=bd399fef78bdde5ecc6f56083d2e963d&oe=56806C65&__gda__=1451444950_f4cc0c7cbe8c55a8c69cb3f0e4400369");
		//felipe
		ClienteDAO.getInstance().newUser("798015963630353", "Felipe Andrade", "", "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpf1/v/t1.0-1/p50x50/10985481_790228817742401_2146748792769360423_n.jpg?oh=590cfd0d3f3fd0716658c6cdbcb7455c&oe=563DCA25&__gda__=1451329678_98a837a7210249f6a634f892fa5aca0a");
	}
	
	public String recomendar(String json){
		double idCliente = Double.parseDouble(dadoJson(json, "idCliente"));
		List<String> categorys = dadoListJson(json, "categorys");
		int distancia = Integer.parseInt(dadoJson(json, "distancia"));
		List<Category> categorias = getCategorias(categorys);
		UserInfo result = null;
		List<Double> intereses = new ArrayList<Double>();
		UserInfo cliente = ClienteDAO.getInstance().getUser(idCliente);
		List<UserInfo> amigos = ClienteDAO.getInstance().getFriends(idCliente);
		while (result != null) {
			Random r = new Random();
			int random = r.nextInt(ClienteDAO.getInstance().listarTodos().size());
			UserInfo user = ClienteDAO.getInstance().listarTodos().get(random);
			if (!amigos.contains(user)) {
				if (categorias.contains(Category.LOCATION)) {
					if (proximidade(cliente,user) <= distancia) {
						double nivelDeCoisaEmComum = recommender.getLevel(cliente, user, categorias);
						//TODO saber o porcentagem q eh pra comparar (extra)
						if (nivelDeCoisaEmComum >= 50) {
							result = user;
							List<Category> category = new ArrayList<Category>();
							List<Category> c = Category.getCategorias();
							for (int i = 0; i < c.size(); i++) {
								category.add(c.get(i));
								intereses.add(recommender.getLevel(cliente, user, category));
								category.remove(c.get(i));
							}
						}
					}
				} else {
					double nivelDeCoisaEmComum = recommender.getLevel(cliente, user, categorias);
					//TODO saber o porcentagem q eh pra comparar (extra)
					if (nivelDeCoisaEmComum >= 50) {
						result = user;
						List<Category> category = new ArrayList<Category>();
						List<Category> c = Category.getCategorias();
						for (int i = 0; i < c.size(); i++) {
							category.add(c.get(i));
							intereses.add(recommender.getLevel(cliente, user, category));
							category.remove(c.get(i));
						}
					}
				}
			}
		}
		
		JSONObject j = new JSONObject();
		JSONArray jsonA = new JSONArray(intereses);
		try {
			j.put("user", result);
			j.put("intereses", jsonA);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return j.toString();
	}
	
	public void criarCliente(String json){
		String id = dadoJson(json, "id");
		String name = dadoJson(json, "nome");
		String email = dadoJson(json, "email");
		String picture = dadoJson(json, "picture");
		ClienteDAO.getInstance().newUser(id, name, email, picture);
	}
	
	public void novaAmizade(String json){
		String idCliente = dadoJson(json, "idCliente");
		String idAmigo = dadoJson(json, "idAmigo");
		ClienteDAO.getInstance().newFriend(idCliente, idAmigo);
	}
	
	private List<Category> getCategorias(List<String> categorias) {
		List<Category> result = new ArrayList<Category>();
		for (int i = 0; i < categorias.size(); i++) {
			switch (categorias.get(i)) {
				case "movies":
					result.add(Category.MOVIES);
					break;
				case "books":
					result.add(Category.BOOKS);
					break;
				case "games":
					result.add(Category.GAMES);
					break;
				case "music":
					result.add(Category.MUSIC);
					break;
				case "television":
					result.add(Category.TV);
					break;
			}
		}
		return result;
	}
	
	private String dadoJson (String json, String data) {
		String result = "";
		JsonObject jsonObject;	
		JsonParser parser = new JsonParser();
		jsonObject = (JsonObject) parser.parse(json);
		result = jsonObject.get(data).toString();
		return result;
	}
	
	private List<String> dadoListJson(String json, String data) {
		List<String> result = new ArrayList<String>();
		JsonObject jsonObject;	
		JsonParser parser = new JsonParser();
		jsonObject = (JsonObject) parser.parse(json);
		JsonArray jsonArray = (JsonArray) jsonObject.get(data);
		for (int i = 0; i < jsonArray.size(); i++) {
			result.add(jsonArray.get(i).toString());
		}
		return result;
	}
	
	private int proximidade(UserInfo cliente, UserInfo candidato) {
		String url1 = "http://maps.googleapis.com/maps/api/directions/json?origin=" + cliente.getLatitude() + ",%20" + cliente.getLongitude() + "&destination=" + candidato.getLatitude() + "," + candidato.getLongitude();
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
		String json = sb.toString();
		String location = json.substring(json.indexOf("\"distance\" :"), json.indexOf("\"duration\"")-8);
		String distacia = location.substring((location.indexOf("\"value\"")+10), location.indexOf("}")-12);
		return Integer.parseInt(distacia);
	}
	
	public void editarLocation(String json) {
		String latitude = dadoJson(json, "latitude");
		String longitude = dadoJson(json, "longitude");
		String idCliente = dadoJson(json, "id");
		ClienteDAO.getInstance().editarLocation(idCliente,latitude,longitude);
	}
	
	public void enviarMsg(String json) {
		String idOrigem = dadoJson(json, "idOrigem");
		String idDestino = dadoJson(json, "idDestino");
		String msg = dadoJson(json, "msg");
		Messeger chat = new Messeger(idOrigem, idDestino, msg);
		messeger.add(chat);
	}
	
	public String receberMsg(String json) {
		String result = "";
		String remetente = "";
		String id = dadoJson(json, "id");
		for (Messeger m: messeger) {
			if (m.getDestino().equals(id)) {
				result = m.getMsg();
				remetente = m.getOrigem();
				messeger.remove(m);
				break;
			}
		}
		JSONObject j = new JSONObject();
		try {
			j.put("user", remetente);
			j.put("msg", result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return j.toString();
	}
}
