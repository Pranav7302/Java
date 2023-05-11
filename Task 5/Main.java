package web;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
@WebServlet( urlPatterns ="/hello")
public class Main extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();
    protected void doGet(HttpServletRequest request, 
                                   HttpServletResponse response) throws ServletException, IOException {
        Hashtable<Integer,Employee> employeeTable = new Hashtable<>();
        int choice =0;
        Employee e1=new Employee("pranav",21,32000);
        Employee e2=new Employee("rosen",21,32000);
        Employee e3=new Employee("jeeva",23,37000);
        
        employeeTable.put(1,e1);
        employeeTable.put(2,e2);
        employeeTable.put(3,e3);
        String code = request.getParameter("employeecode").trim();
        Integer n=Integer.parseInt(code);
        for(int i=0;i<employeeTable.size();i++)
        {
          if(employeeTable.containsKey(n))
          {
        	  choice =1;
          }
        }
        if(choice==1)
        {
        String userJsonString = this.gson.toJson(employeeTable.get(n));
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(userJsonString);
        out.flush();
        }
        else
        {
        	PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");           
        	out.print("Employee Id not found");
        	out.flush();
        }
}
}