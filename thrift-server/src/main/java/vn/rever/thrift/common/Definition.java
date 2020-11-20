package vn.rever.thrift.common;

public class Definition {
  private Definition(){

  }

  public static class Constant {

    private Constant(){

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
}
