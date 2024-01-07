package com.ioevent.ioeventhumantaskhandlerstarter.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import io.micrometer.common.util.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class ElasticConfig {
    @Value("${servers.elasticsearch.url}")
    private String elasticsearchUrl;
    @Value("${servers.elasticsearch.username:}")
    private String elasticsearchUsername;
    @Value("${servers.elasticsearch.password:}")
    private String elasticsearchPassword;

    public RestClientBuilder httpClientBuilder() throws MalformedURLException {
        URL hostUrl = new URL(elasticsearchUrl);
        return RestClient.builder(new HttpHost(hostUrl.getHost(), hostUrl.getPort(), hostUrl.getProtocol()));
    }

    public ElasticsearchTransport transport() throws MalformedURLException {
        if (StringUtils.isBlank(elasticsearchUsername)|| StringUtils.isBlank(elasticsearchPassword)) {
            return new RestClientTransport(httpClientBuilder().build(), new JacksonJsonpMapper());
        }
        /** remote elasticsearch cluster connection **/
        CredentialsProvider cp = new BasicCredentialsProvider();
        cp.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticsearchUsername, elasticsearchPassword));

        return new RestClientTransport(httpClientBuilder()
                .setHttpClientConfigCallback(
                        httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(cp)
                                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()))
                .build(),new JacksonJsonpMapper());
    }

    @Bean
    public ElasticsearchClient client() throws MalformedURLException {
        return new ElasticsearchClient(transport());
    }
}
