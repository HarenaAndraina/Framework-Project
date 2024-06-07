package org.framework.viewScan;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.framework.checker.Mapping;
import org.framework.checker.RequestMappingChecker;
import org.framework.view.ModelView;
import org.framework.exceptions.MappingNotFoundException;
import org.framework.exceptions.ViewException;

/**
 * ModelAndView
 * //url mitovy controller roa mitovy url tsy misy package tsy misy type de retour 
 */

public class ViewScan {

    public static String getJspByURL(String url) {
        // Split the normalized URL by '/'
        String[] parts = url.split("/");
        String jspFile=null;
        // Check if we have at least one part
        if (parts.length > 0) {
            jspFile = parts[1];
        }
        return jspFile; 
    }

    public static void viewScanner(HttpServletRequest request, HttpServletResponse response, Mapping map) throws Exception {

        if (map == null) {
            throw new MappingNotFoundException("Mapping Not Found");
        }

        String className = map.getClassName();
        String methodName = map.getMethodName();

        Class<?> clazz = Class.forName(className);
        Method method = clazz.getMethod(methodName);
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Object result = method.invoke(instance);

        if (result instanceof String) {
            showMessage(response, (String) result);
        }

        else if (result instanceof ModelView ) {
            ModelView modelView = (ModelView) result;
            HashMap<String, Object> data = modelView.getData();

            for (String key : data.keySet()) {
                Object value = data.get(key);

                request.setAttribute(key, value);
            }

            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher(modelView.getUrl());
            dispatcher.forward(request, response);
        }
        else{
            throw new ViewException("instance of the method: "+methodName+" invalide");
        }
    }

    private static void showMessage(HttpServletResponse response, String message) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FrontController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>");
            out.println(message);
            out.println("</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}