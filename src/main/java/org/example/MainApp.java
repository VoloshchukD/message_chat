package org.example;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.example.entity.Constants;
import org.example.filter.ChatServlet;

import java.io.FileInputStream;
import java.util.Properties;

public class MainApp {

    public static void main(String[] args) throws Exception {

        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(Constants.SRC_MAIN_RESOURCES_DEMO_PROPERTIES)) {
            properties.load(input);
        }

        int port = Integer.parseInt(properties.getProperty(Constants.SERVER_PORT));

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);

        Context context = tomcat.addWebapp(Constants.CONTEXT_PATH,
                new java.io.File(Constants.SRC_MAIN_RESOURCES_WEBAPP).getAbsolutePath());

        Tomcat.addServlet(context, Constants.CHAT_SERVLET, new ChatServlet());
        context.addServletMappingDecoded(Constants.CHAT_ENDPOINT, Constants.CHAT_SERVLET);

        tomcat.start();
        Connector connector = tomcat.getConnector();
        connector.setPort(port);
        tomcat.getService().addConnector(connector);
        tomcat.getServer().await();
    }
}



