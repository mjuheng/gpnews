package com.gpnews.file.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuangChongHeng
 * @date 2020/2/23
 */
@Configuration
public class ServerConfig {
//    @Value("${server.port}")
//    private Integer port;
//
//    @Value("${server.port}")
//    private Integer httpsPort;
//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                // 如果要强制使用https，请松开以下注释
//                // SecurityConstraint constraint = new SecurityConstraint();
//                // constraint.setUserConstraint("CONFIDENTIAL");
//                // SecurityCollection collection = new SecurityCollection();
//                // collection.addPattern("/*");
//                // constraint.addCollection(collection);
//                // context.addConstraint(constraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(createStandardConnector()); // 添加http
//        return tomcat;
//    }
//
//    // 配置http
//    private Connector createStandardConnector() {
//        // 默认协议为org.apache.coyote.http11.Http11NioProtocol
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setSecure(false);
//        connector.setScheme("http");
//        connector.setPort(9008);
//        connector.setRedirectPort(9008); // 当http重定向到https时的https端口号
//        return connector;
//    }

}
