import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import controller.ClienteController;

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    //TODO mudar para pagina do app :)
    get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Hello World!");

            return new ModelAndView(attributes, "index.ftl");
        }, new FreeMarkerEngine());

//    get("/receber", (req, res) -> {
//    	String json = req;
//    	return new ClienteController().receberMsg(json);
//    });
//
//    post("/enviar", (req, res) -> {
//    	String json = req;
//    	new ClienteController().enviarMsg(json);
//		return "ok";
//    });
//    
//    post("/recomendacao", (req, res) -> {
//    	new ClienteController().addAmigos();
//    	String json = req;
//		return new ClienteController().recomendar(json);
//    });
//    
//    post("/newuser", (req, res) -> {
//    	String json = req;
//    	new ClienteController().criarCliente(json);
//		return "ok";
//    });
//    
//    post("/newfriend", (req, res) -> {
//    	String json = req;
//    	new ClienteController().novaAmizade(json);
//		return "ok";
//    });
//    
//    post("/localizacao", (req, res) -> {
//    	String json = req;
//    	new ClienteController().editarLocation(json);
//		return "ok";
//    });
    
    post("/teste", (req, res) -> {
    	JsonObject jsonObject;	
		JsonParser parser = new JsonParser();
		jsonObject = (JsonObject) parser.parse(req.body());
		String result = jsonObject.get("teste").toString();
    	return result;
    });
  }
}
