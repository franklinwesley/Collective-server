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

    post("/receber", (req, res) -> {
    	return new ClienteController().receberMsg(req.body());
    });

    post("/enviar", (req, res) -> {
    	new ClienteController().enviarMsg(req.body());
		return "ok";
    });
    
    post("/recomendacao", (req, res) -> {
    	new ClienteController().addAmigos();
		return new ClienteController().recomendar(req.body());
    });
    
    post("/newuser", (req, res) -> {
    	new ClienteController().criarCliente(req.body());
		return "ok";
    });
    
    post("/newfriend", (req, res) -> {
    	new ClienteController().novaAmizade(req.body());
		return "ok";
    });
    
    post("/localizacao", (req, res) -> {
    	new ClienteController().editarLocation(req.body());
		return "ok";
    });
  }
}
