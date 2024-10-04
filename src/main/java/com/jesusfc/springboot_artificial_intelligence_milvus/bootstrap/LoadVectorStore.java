package com.jesusfc.springboot_artificial_intelligence_milvus.bootstrap;

import com.jesusfc.springboot_artificial_intelligence_milvus.config.MilvusConfig;
import com.jesusfc.springboot_artificial_intelligence_milvus.config.VectorStoreProperties;
import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.ListDatabasesResponse;
import io.milvus.param.R;
import io.milvus.param.collection.HasCollectionParam;
import io.milvus.param.highlevel.collection.CreateSimpleCollectionParam;
import io.milvus.param.highlevel.collection.ListCollectionsParam;
import io.milvus.param.highlevel.collection.response.ListCollectionsResponse;
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

        MilvusServiceClient milvusConnection = milvusConfig.createMilvusConnection();

        R<ListDatabasesResponse> r = milvusConnection.listDatabases();
        System.out.println(r.getData());

        R<ListCollectionsResponse> lc = milvusConnection.listCollections(ListCollectionsParam.newBuilder().build());
        System.out.println(lc.getData());

        HasCollectionParam hasCollectionReq = HasCollectionParam.newBuilder()
                .withDatabaseName("default")
                .withCollectionName("vector_store")
                .build();

        R<Boolean> b = milvusConnection.hasCollection(hasCollectionReq);
        System.out.println(b.getData());

        if (!b.getData()) {
            // 2. Create a collection in quick setup mode
            CreateSimpleCollectionParam quickSetupReq = CreateSimpleCollectionParam.newBuilder()
                    .withCollectionName("vector_store")
                    .withDimension(5)
                    .build();

            milvusConnection.createCollection(quickSetupReq);
        }

        R<ListCollectionsResponse> listCollectionsResp = milvusConnection.listCollections(ListCollectionsParam.newBuilder().build());
        System.out.println(listCollectionsResp.getData());

       // if (vectorStore.similaritySearch("Sportsman").isEmpty()){
            System.out.println("Loading documents into vector store");

            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                System.out.println("Loading document: " + document.getFilename());


                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> documents = documentReader.get();

                TextSplitter textSplitter = new TokenTextSplitter();

                List<Document> splitDocuments = textSplitter.apply(documents);

                vectorStore.add(splitDocuments);


           });

        //}

        System.out.println("Vector store loaded");

    }


}
