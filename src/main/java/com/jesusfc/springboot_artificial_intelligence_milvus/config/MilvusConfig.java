package com.jesusfc.springboot_artificial_intelligence_milvus.config;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import io.milvus.v2.client.ConnectConfig;
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
    public MilvusServiceClient createMilvusConnection() {

        String CLUSTER_ENDPOINT = "http://localhost:19530";

        ConnectConfig connectConfig = ConnectConfig.builder()
                .uri(CLUSTER_ENDPOINT)
                //.username("root666666")
                //.password("milvus66666")
                .dbName("default")
                .build();

        // return  new MilvusClientV2(connectConfig);

        // Connect to Milvus server. Replace the "localhost" and port with your Milvus server address.
        MilvusServiceClient milvusClient = new MilvusServiceClient(ConnectParam.newBuilder()
                .withHost("localhost")
                .withPort(19530)
                .withDatabaseName("default")
                .build());
/*
        R<ListDatabasesResponse> r = milvusClient.listDatabases();
        System.out.println(r.getData());

        R<ListCollectionsResponse> lc = milvusClient.listCollections(ListCollectionsParam.newBuilder().build());
        System.out.println(lc.getData());
   */
        return milvusClient;
    }


}
