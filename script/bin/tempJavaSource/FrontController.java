package org.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.framework.annotation.RequestMapping;
import org.framework.checker.Mapping;
import org.framework.checker.RequestMappingChecker;
import org.framework.checker.RestAPIChecker;
import org.framework.exceptions.PackageNotFoundException;
import org.framework.exceptions.RequestMappingException;
import org.framework.checker.ControllerChecker;
import org.framework.viewScan.ViewScan;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
    maxFileSize = 1024 * 1024 * 10,      // 10 MB
    maxRequestSize = 1024 * 1024 * 15    // 15 MB
)

public class FrontController extends HttpServlet {
    private RequestMappingChecker checker;
    private RestAPIChecker checkerRestAPI;
    private String error = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("Initialisation de FrontController");

        try {
            ServletContext context = config.getServletContext();
            String packageName = context.getInitParameter("Package");
            System.out.println("Package à scanner: " + packageName);

            this.checker = new RequestMappingChecker();
            this.checkerRestAPI = new RestAPIChecker();

            System.out.println("Début du scan des mappings RequestMapping");
            checker.getAllMethodMapping(context);
            System.out.println("Scan des mappings RequestMapping terminé");

            System.out.println("Début du scan des mappings RestAPI");
            checkerRestAPI.getAllMethodMapping(context);
            System.out.println("Scan des mappings RestAPI terminé");

        } catch (Throwable e) {
            System.out.println("Erreur lors de l'initialisation: " + e.getMessage());
            this.error = e.toString();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String requestURL = request.getRequestURI();
            String contextPath = request.getContextPath();
            String relativeUrl = requestURL.substring(contextPath.length());

            if (this.error == null) {

                try {
                    Mapping mapping = checker.getMethodByURL(relativeUrl, request);

                    Mapping mapping2 = checkerRestAPI.getMethodByURL(relativeUrl);

                    ViewScan.viewScanner(request, response, requestURL, mapping, mapping2);
                } catch (Exception e) {
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.println(e.toString());
                    out.close();
                }

            } else {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("Error initializing: " + error);
                out.close();
            }

        } catch (Exception e) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println(e.toString());
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
