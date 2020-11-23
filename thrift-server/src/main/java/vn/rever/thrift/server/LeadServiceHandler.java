package vn.rever.thrift.server;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoCollection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.rever.data.entity.LeadEntity;
import vn.rever.thrift.leadservice.Lead;
import vn.rever.thrift.leadservice.LeadService.Iface;

public class LeadServiceHandler implements Iface {

  MongoCollection<LeadEntity> mongoCollection;

  Logger logger = LoggerFactory.getLogger(LeadServiceHandler.class);

  public LeadServiceHandler setMongoCollection(MongoCollection<LeadEntity> coll)
  {
    this.mongoCollection = coll;
    return this;
  }

  public LeadServiceHandler() {
  }

  public Map<Long, Lead> getAll() throws TException {
    HashMap<Long, Lead> resultColl = new HashMap<>();
    mongoCollection.find().forEach(
        (Consumer<LeadEntity>)  entity -> resultColl.put(entity.id,entity.toLead()));
    return resultColl;
  }

  public Lead getById(long id) throws TException {
    logger.debug("getById - Get lead with id={}", id);
    return mongoCollection.find(eq("id",String.valueOf(id))).first().toLead();
  }

  public void addNew(Lead newLead) throws TException {
    if (getById(newLead.id) == null) {
      mongoCollection.insertOne(LeadEntity.fromLead(newLead));
      logger.debug("New Lead added id = {}, lead = {}",newLead.id,newLead);
    } else {
      throw new TException("Lead with same id has existed in current list collection", new IllegalStateException());
    }
  }

  public void removeById(long id) throws TException {
    mongoCollection.deleteOne(eq("id",String.valueOf(id)));
    logger.debug("Delete Lead with id = {}",id);
  }

  public void updateLead(Lead lead) throws TException {
    mongoCollection.replaceOne(eq("id",String.valueOf(lead.id)),LeadEntity.fromLead(lead));
    logger.debug("Update lead with id = {} by new lead = {}",lead.id, lead);
  }
}