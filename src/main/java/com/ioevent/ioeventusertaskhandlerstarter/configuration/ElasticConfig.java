/*
 * Copyright © 2024 CodeOnce Software (https://www.codeonce.fr/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ioevent.ioeventusertaskhandlerstarter.configuration;

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
