package vn.rever.common;

public class Definition {
  private Definition(){

  }

  public static class ThriftConstant {

    private ThriftConstant(){

    }

    /**
     * Thrift server port for listening client connection
     */
    public static final int THRIFT_SERVER_PORT = 8088;

    /**
     * Thrift server predefined keep alive time for request
     */
    public static final int SERVER_KEEP_ALIVE_TIME = 120;

    /**
     * Predefined number of maximum allowed thread used for execution
     */
    public static final int MAX_THREAD_ALLOW = 5;
  }

  public static class MongoConstant{

    /**
     * MongoDB server instance connection port
     */
    public static  final int MONGO_PORT = 27017;

    /**
     * MongoDB database name
     */
    public static final String MONGO_DBNAME = "leadmanage";
  }
}
