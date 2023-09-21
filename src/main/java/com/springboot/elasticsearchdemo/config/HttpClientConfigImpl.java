package com.springboot.elasticsearchdemo.config;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.io.File;

@Configuration
public class HttpClientConfigImpl implements RestClientBuilder.HttpClientConfigCallback {

    @Override
    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
        try {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

            // Enter your credential of elasticsearch (username, password)
            UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials("elastic", "1wSg3F+h+-+FcwDn6_b5");
            credentialsProvider.setCredentials(AuthScope.ANY, usernamePasswordCredentials);
            httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

            // Path to your created certificate
            String trustStoreLocation = "/Users/incepit/Developer/elasticsearch-8.10.1/config/certs/truststore.p12";
            File trustStoreLocationFile = new File(trustStoreLocation);

            SSLContextBuilder sslContextBuilder = SSLContexts.custom().loadTrustMaterial(trustStoreLocationFile, "password".toCharArray());
            SSLContext sslContext = sslContextBuilder.build();

            httpAsyncClientBuilder.setSSLContext(sslContext);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return httpAsyncClientBuilder;
    }
}
