package org.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.framework.checker.ControllerChecker;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Andra
 */
public class FrontController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 // Récupérer l'objet ServletContext
    ServletContext context = getServletContext();
        
    // Récupérer la valeur du paramètre basePackage du contexte
    String packageName = context.getInitParameter("Package");
    ControllerChecker check = new ControllerChecker();
    response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
    try {
        check.getAllClassController(packageName);
        List<String> allClassesController = check.getClassController();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet frontController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet frontController at " + request.getContextPath() + "</h1>");
            out.println("<h2>List of Controller Classes:</h2>");
            out.println("<ul>");
            for (String className : allClassesController) {
                out.println("<li>" + className + "</li>");
            }
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}