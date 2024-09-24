package com.jesusfc.springboot_artificial_intelligence_milvus.bootstrap;

import com.jesusfc.springboot_artificial_intelligence_milvus.config.MilvusConfig;
import com.jesusfc.springboot_artificial_intelligence_milvus.config.VectorStoreProperties;
import io.milvus.grpc.ListDatabasesResponse;
import io.milvus.param.R;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.service.collection.request.DescribeCollectionReq;
import io.milvus.v2.service.collection.request.GetLoadStateReq;
import io.milvus.v2.service.collection.request.HasCollectionReq;
import io.milvus.v2.service.collection.request.LoadCollectionReq;
import io.milvus.v2.service.collection.response.DescribeCollectionResp;
import io.milvus.v2.service.collection.response.ListCollectionsResp;
import io.milvus.v2.service.database.response.ListDatabasesResp;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author Jesús Fdez. Caraballo
 * jfcaraballo@gmail.com
 * Created on sept - 2024
 */
@Component
public class LoadVectorStore implements CommandLineRunner {

    private final VectorStore vectorStore;
    private final VectorStoreProperties vectorStoreProperties;
    private final MilvusConfig milvusConfig;

    public LoadVectorStore(VectorStore vectorStore, VectorStoreProperties vectorStoreProperties, MilvusConfig milvusConfig) {
        this.vectorStore = vectorStore;
        this.vectorStoreProperties = vectorStoreProperties;
        this.milvusConfig = milvusConfig;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {

        MilvusClientV2 milvusConnection = milvusConfig.createMilvusConnection();

        ListDatabasesResp listDatabasesResponse = milvusConnection.listDatabases();
        System.out.println(listDatabasesResponse.getDatabaseNames());

        HasCollectionReq hasCollectionReq = HasCollectionReq.builder().
                collectionName("vector_store").build();

        Boolean b = milvusConnection.hasCollection(hasCollectionReq);
        System.out.println(b);

        ListCollectionsResp listCollectionsResp = milvusConnection.listCollections();
        System.out.println(listCollectionsResp);



        /*
        if (vectorStore.similaritySearch("Sportsman").isEmpty()){
            System.out.println("Loading documents into vector store");

            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                System.out.println("Loading document: " + document.getFilename());

                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> documents = documentReader.get();

                TextSplitter textSplitter = new TokenTextSplitter();

                List<Document> splitDocuments = textSplitter.apply(documents);

                vectorStore.add(splitDocuments);
            });
        }

        System.out.println("Vector store loaded");
        
        */
    }
    
         
}
