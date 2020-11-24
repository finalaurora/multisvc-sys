package jettymw.server.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vn.rever.thrift.client.IClientExecutor;
import vn.rever.thrift.leadservice.Lead;
import vn.rever.thrift.leadservice.LeadService;

public class LeadServiceServlet extends HttpServlet {

  Gson gson;

  IClientExecutor exec;

  public LeadServiceServlet(Gson gson){
    this.gson = gson;
  }

  public LeadServiceServlet setExecutor(IClientExecutor executor){
    this.exec=executor;
    return this;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("application/json");
    String resource = req.getParameter("id");
    if(resource == null){
      doGetAll(resp);
    }
    else {
      doGetOne(resource ,resp);
    }
  }

  private void doGetOne(String id, HttpServletResponse resp) throws IOException {
    Lead lead = exec.getLead(Integer.parseInt(id));
    String jsonStr = gson.toJson(lead);
    resp.getWriter().println(jsonStr);
    resp.getWriter().close();
  }

  private void doGetAll(HttpServletResponse resp) throws IOException {
    List<Lead> list = exec.getAllLeads();
    String jsonStr = gson.toJson(list);
    resp.getWriter().println(jsonStr);
    resp.getWriter().close();
  }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.doPost(req, resp);
  }
}
