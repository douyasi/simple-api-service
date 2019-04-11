package com.douyasi.example.jersey_jetty_demo.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.douyasi.example.jersey_jetty_demo.api.RestApi;


/**
 * ref resources:
 *
 * - https://github.com/whzhaochao/spring-jetty-jersey-mybatis
 * - https://nikgrozev.com/2014/10/16/rest-with-embedded-jetty-and-jersey-in-a-single-jar-step-by-step/
 * - http://zetcode.com/articles/jerseyembeddedjetty/
 * - https://www.javacodegeeks.com/2015/03/creating-web-services-and-a-rest-server-with-jax-rs-and-jetty.html
 * - https://www.dovydasvenckus.com/rest/2017/08/20/jersey-on-embedded-jetty/
 * - https://crunchify.com/how-to-build-restful-service-with-java-using-jax-rs-and-jersey/
 * - https://www.jianshu.com/p/764fcdffc28a
 * - https://waylau.com/jersey-2-spring-4-rest/
 * - https://www.mkyong.com/webservices/jax-rs/json-example-with-jersey-jackson/
 * - https://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey
 * 
 * @author raoyc
 */
public class JettyServer {
    
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(18080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
            org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
            "jersey.config.server.provider.classnames",
            RestApi.class.getCanonicalName()
            );
        jerseyServlet.setInitParameter("javax.ws.rs.Application", JerseyConfig.class.getName());
        

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.stop();
            jettyServer.destroy();
        }
    }
}
