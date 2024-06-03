package org.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.framework.annotation.RequestMapping;
import org.framework.checker.ControllerChecker;
import org.framework.checker.Mapping;
import org.framework.checker.RequestMappingChecker;
import org.framework.viewScan.ViewScan;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {
    private String packageName;
    private RequestMappingChecker checker;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // Récupérer l'objet ServletContext
        ServletContext context = config.getServletContext();

        // Récupérer la valeur du paramètre basePackage du contexte
        this.packageName = context.getInitParameter("Package");
        this.checker = new RequestMappingChecker();

        try {
            checker.getAllMethodMapping(packageName);
        } catch (Exception e) {
            throw new ServletException("Error initializing controller checker", e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String requestURL = request.getRequestURI();
            String contextPath = request.getContextPath();
            String relativeUrl = requestURL.substring(contextPath.length());

            out.println("<h1>Servlet FrontController at " + requestURL + "</h1>");
            Mapping mapping = checker.getMethodByURL(relativeUrl);

           ViewScan.viewScanner(request, response, mapping);
        } catch (Exception e) {
            out.println(e.getMessage());
        } finally {
            out.close();
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
