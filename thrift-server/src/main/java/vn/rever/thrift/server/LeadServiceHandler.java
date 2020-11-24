package vn.rever.thrift.server;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.rever.data.entity.LeadEntity;
import vn.rever.thrift.leadservice.Lead;
import vn.rever.thrift.leadservice.LeadService.Iface;
import vn.rever.thrift.leadservice.OperationalError;
import vn.rever.thrift.leadservice.OperationalException;

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
    if(resultColl.size() == 0){
      throw new OperationalException("No record found.", OperationalError.NO_RECORD_FOUND);
    }
    return resultColl;
  }

  public Lead getById(long id) throws TException {
    logger.debug("getById - Get lead with id={}", id);
    try{
    return mongoCollection.find(eq("id",id)).first().toLead();}
    catch (MongoException ex){
      throw new OperationalException("Requested ID not exists",OperationalError.REQUESTED_ID_NOT_EXISTS);
    }
  }

  public void addNew(Lead newLead) throws TException {
    try{
      mongoCollection.insertOne(LeadEntity.fromLead(newLead));
      logger.debug("New Lead added id = {}, lead = {}",newLead.id,newLead);
    } catch (MongoWriteException ex){
      throw new TException("Lead with same id has existed in current list collection", ex);
    } catch (Exception ex)
    {
      throw  new TException("Internal error");
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