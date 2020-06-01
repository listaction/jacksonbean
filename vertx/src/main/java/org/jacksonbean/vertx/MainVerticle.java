package org.jacksonbean.vertx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jacksonbean.TestEntity;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.jacksonbean.ObjectMapperValidationFactory;

public class MainVerticle extends AbstractVerticle {

    private static final ObjectMapper mapper = ObjectMapperValidationFactory.createObjectMapper();

    public static void main(String[] args) {
        LoggerFactory.initialise();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(MainVerticle.class.getName());
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router = Router.router(vertx);
        router.post("/*").handler(BodyHandler.create());
        router.post("/demo").handler(this::demoHandler);
        router.errorHandler(500, this::handleErrors);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080, http -> {
                    if (http.succeeded()) {
                        startPromise.complete();
                        System.out.println("HTTP server started on port 8080");
                    } else {
                        startPromise.fail(http.cause());
                    }
                });
    }

    private void handleErrors(RoutingContext ctx) {
        ctx.failure().printStackTrace();
        if (ctx.failure() instanceof JsonMappingException){
            // decorate errors
            ctx.response().setStatusMessage("JSON_MAPPING_ERROR: " + ctx.failure().getCause());
        }
    }

    public void demoHandler(RoutingContext ctx) {
        HttpServerResponse response = ctx.response();

        String json = ctx.getBodyAsString("UTF-8");
        System.out.println("Input: "+json);
        TestEntity entity;
        try {
            entity = mapper.readValue(json, TestEntity.class);
        } catch (JsonProcessingException e) {
            ctx.fail(e);
            return;
        }

        String serializedObjectJson;
        try {
            serializedObjectJson = mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            ctx.fail(e);
            return;
        }

        response.putHeader("content-type", "application/json");
        response.end(serializedObjectJson);
    }
}
