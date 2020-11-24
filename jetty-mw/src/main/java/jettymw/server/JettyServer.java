package jettymw.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jettymw.server.servlet.LeadServiceServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import vn.rever.thrift.client.ClientExecutor;
import vn.rever.thrift.client.IClientExecutor;

public class JettyServer extends Server{
  private static final Gson gson = new GsonBuilder().
      setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

  public IClientExecutor clientExecutor;

  LeadServiceServlet leadServlet = new LeadServiceServlet(gson);

  public JettyServer setExecutor(IClientExecutor executor){
    this.clientExecutor = executor;
    this.leadServlet.setExecutor(executor);
    return this;
  }

  public JettyServer(int port) {
    super(port);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler
        .NO_SESSIONS);
    context.setContextPath("/test");
    context.addServlet(new ServletHolder(leadServlet), "/Lead/*");
    this.setHandler(context);
    this.setStopAtShutdown(true);
  }

}
