package vn.rever.thrift.server;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.rever.data.entity.LeadEntity;
import vn.rever.thrift.leadservice.Lead;
import vn.rever.thrift.leadservice.LeadService.AsyncIface;
import vn.rever.thrift.leadservice.OperationalError;
import vn.rever.thrift.leadservice.OperationalException;

public class AsyncLeadServiceHandler implements AsyncIface {

  Logger logger = LoggerFactory.getLogger(AsyncLeadServiceHandler.class);

  MongoCollection<LeadEntity> mongoCollection;

  public AsyncLeadServiceHandler setMongoCollection(MongoCollection<LeadEntity> coll)
  {
    this.mongoCollection = coll;
    return this;
  }

  @Override
  public void getAll(AsyncMethodCallback<Map<Long, Lead>> resultHandler) throws TException {
    HashMap<Long, Lead> resultColl = new HashMap<>();
    mongoCollection.find().forEach(
        (Consumer<LeadEntity>)  entity -> resultColl.put(entity.id,entity.toLead()));
    if(resultColl.size() == 0){
      resultHandler.onError(new OperationalException("No record found.", OperationalError.NO_RECORD_FOUND));
    }
    else {
      resultHandler.onComplete(resultColl);
    }
  }

  @Override
  public void getById(long id, AsyncMethodCallback<Lead> resultHandler) throws TException {
    logger.debug("getById - Get lead with id={}", id);
    try{
      Lead ld = mongoCollection.find(eq("id",id)).first().toLead();
      resultHandler.onComplete(ld);
    }
    catch (MongoException ex){
      resultHandler.onError(new OperationalException("Requested ID not exists",OperationalError.REQUESTED_ID_NOT_EXISTS));
    }
  }

  @Override
  public void addNew(Lead newLead, AsyncMethodCallback<Void> resultHandler) throws TException {
    try{
      mongoCollection.insertOne(LeadEntity.fromLead(newLead));
      logger.debug("New Lead added id = {}, lead = {}",newLead.id,newLead);
      resultHandler.onComplete(null);
    } catch (MongoWriteException ex){
     resultHandler.onError( new TException("Lead with same id has existed in current list collection", ex));
    } catch (Exception ex)
    {
      throw  new TException("Internal error");
    }
  }

  @Override
  public void removeById(long id, AsyncMethodCallback<Void> resultHandler) throws TException {

  }

  @Override
  public void updateLead(Lead lead, AsyncMethodCallback<Void> resultHandler) throws TException {

  }
}
