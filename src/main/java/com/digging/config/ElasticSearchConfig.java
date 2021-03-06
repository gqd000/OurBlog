package com.digging.config;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    //    <beans id='restHighLevelClient' class=RestHighLevelClient

//    @Value("${es.host}")
    private String host = "129.150.63.35";
//    @Value("${es.port}")
    private int port = 9200;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host,port,"http")
                )
        );
        return client;
    }

}