package com.jesusfc.springboot_artificial_intelligence_milvus.config;

import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.client.MilvusClientV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author Jesús Fdez. Caraballo
 * jfcaraballo@gmail.com
 * Created on sept - 2024
 */
@Configuration
public class MilvusConfig {


    @Bean
    public MilvusClientV2 createMilvusConnection() {

        String CLUSTER_ENDPOINT = "http://localhost:19530";

        ConnectConfig connectConfig = ConnectConfig.builder()
                .uri(CLUSTER_ENDPOINT)
                .username("root666666")
                .password("milvus66666")
                .dbName("default")
                .build();

        return  new MilvusClientV2(connectConfig);
    }



}
