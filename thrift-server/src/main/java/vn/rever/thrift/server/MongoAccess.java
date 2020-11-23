package vn.rever.thrift.server;

import static com.mongodb.client.model.Filters.eq;
import static java.util.Arrays.asList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.Block;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.rever.data.entity.LeadEntity;
import vn.rever.thrift.leadservice.Status;


public class MongoAccess {

  Logger logger = LoggerFactory.getLogger(MongoAccess.class);

  private MongoCollection<LeadEntity> collection;

  public MongoCollection<LeadEntity> getMongoCollection(){
    return this.collection;
  }

  public void connectMongoDB() {
    MongoClient mongoClient = MongoClients.create();
    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
        fromProviders(
            PojoCodecProvider.builder().automatic(true).build()));
    MongoDatabase database = mongoClient.getDatabase("leadmanage")
        .withCodecRegistry(pojoCodecRegistry);
    collection = database.getCollection("lead", LeadEntity.class);

//    // Drop all data in it
//    collection.drop();
//
//    LeadEntity shaw = new LeadEntity(1, "Rick", "Shaw", "rshaw@nortonfield.uk", "123-3242-123",
//        Status.LOOKING_FOR_RENTAL, "google.com",
//        Instant.now().getEpochSecond(), Instant.now().getEpochSecond());
//    logger.debug("Original Person Model: {}", shaw.toString());
//    collection.insertOne(shaw);
//    logger.debug("Mutated Person Model: {}", shaw.toString());
//
//
//    List<LeadEntity> leadgroup = asList(
//        new LeadEntity(2, "Json", "Statham", "jstatham@fast10.com", "134-213-4356", Status.ARCHIVED,
//            "mannerrealestate.com", Instant.now().getEpochSecond(), Instant.now().getEpochSecond()),
//        new LeadEntity(3, "Steve", "Roger", "captain_of_america@avenger.com", "2345-213-235",
//            Status.ARCHIVED, "rever.com", Instant.now().getEpochSecond(),
//            Instant.now().getEpochSecond()),
//        new LeadEntity(4, "Tony", "Stark", "ironman@starkindustries.com", "134-234-4356",
//            Status.ARCHIVED, "kragerrealestate.com", Instant.now().getEpochSecond(),
//            Instant.now().getEpochSecond())
//    );
//    collection.insertMany(leadgroup);
//
//    collection.find(eq("userStatus",Status.ARCHIVED.toString())).forEach(
//        (Consumer<LeadEntity>) leadEntity -> logger.debug(leadEntity.toString()));

  }


}
