package io.vertx.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class HelloWorldEmbedded extends AbstractVerticle {

//	public static void main(String[] args) {
//
//		JsonArray heroes = new JsonArray();
//		heroes.add(new JsonObject().put("id", "1").put("name", "Capitão Caverna"));
//		// Create an HTTP server which simply returns "Hello World!" to each
//		// request.
//		Vertx.vertx().createHttpServer().requestHandler(req -> prepararResponse(req.response(), heroes)).listen(8080);
//		Runner.runExample(HelloWorldEmbedded.class);
//	
//	}
	public static void main(String[] args) {
	    Vertx vertx = Vertx.vertx();
	    vertx.deployVerticle(new HelloWorldEmbedded());
	}

	@Override
	public void start() throws Exception {

		Router router = Router.router(vertx);

		router.route().handler(CorsHandler.create("*").allowedMethod(HttpMethod.GET).allowedMethod(HttpMethod.POST)
				.allowedMethod(HttpMethod.OPTIONS).allowedHeader("X-PINGARUNER").allowedHeader("Content-Type"));

		router.get("/").handler(this::prepararResponse);
		// Serve the static resources
		router.route().handler(StaticHandler.create());

		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
	}

	private void prepararResponse(RoutingContext ctx) {
		//res.putHeader("content-type", "application/json; charset=utf-8").putHeader("Access-Control-Allow-Origin", "*")
			//	.putHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
		JsonArray heroes = new JsonArray();
		heroes.add(new JsonObject().put("id", "1").put("name", "Capitão Caverna"));
		heroes.add(new JsonObject().put("id", "2").put("name", "Hulk"));
		ctx.response().setChunked(true);

		ctx.response().end(Json.encode(heroes));

	}

}