package core;

import core.config.ServerConfig;
import core.service.ServerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.IOException;

public class ThreadFixServer {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();

        annotationConfigApplicationContext.register(ServerConfig.class);
        annotationConfigApplicationContext.refresh();

        ServerService severService = (ServerService) annotationConfigApplicationContext.getBean("serverService");

        severService.startServer();
    }
}
