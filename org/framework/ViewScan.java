package org.framework.viewScan;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.framework.checker.Mapping;
import org.framework.checker.ParamChecker;
import org.framework.checker.RequestMappingChecker;
import org.framework.view.ModelView;
import org.framework.view.RedirectView;
import org.framework.annotation.Param;
import org.framework.exceptions.InvocationMethodException;
import org.framework.exceptions.MappingNotFoundException;
import org.framework.exceptions.ViewException;

/**
 * ModelAndView
 * //url mitovy controller roa mitovy url tsy misy package tsy misy type de
 * retour
 */

public class ViewScan {

    public static String getJspByURL(String url) {
        // Split the normalized URL by '/'
        String[] parts = url.split("/");
        String jspFile = null;
        // Check if we have at least one part
        if (parts.length > 0) {
            jspFile = parts[1];
        }
        return jspFile;
    }

    public static void viewScanner(HttpServletRequest request, HttpServletResponse response, Mapping map)
            throws Exception {
        if (map == null) {
            throw new MappingNotFoundException("Mapping Not Found");
        }

        String className = map.getClassName();
        String methodName = map.getMethodName();
        Object result = null;

        try {
            result = invokingMethod(request, className, methodName);
        } catch (Exception e) {
            throw e;
        }

        if (result != null) {
            if (result instanceof String) {
                showMessage(response, (String) result);
            }

            else if (result instanceof ModelView) {
                ModelView modelView = (ModelView) result;
                HashMap<String, Object> data = modelView.getData();

                for (String key : data.keySet()) {
                    Object value = data.get(key);

                    request.setAttribute(key, value);
                }

                RequestDispatcher dispatcher = null;
                dispatcher = request.getRequestDispatcher(modelView.getUrl());
                dispatcher.forward(request, response);
            } else if (result instanceof RedirectView) {
                RedirectView redirectView = (RedirectView) result;

                RequestDispatcher dispatcher = null;
                dispatcher = request.getRequestDispatcher(redirectView.getUrl());
                dispatcher.forward(request, response);
            } else {
                throw new ViewException("instance of the method: " + methodName + " invalide");
            }
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

    private static Object invokingMethod(HttpServletRequest request, String className, String methodName)
            throws InvocationMethodException {
        Object result = null;
        try {
            Class<?> clazz = Class.forName(className);
            // Obtenir les méthodes de la classe
            Method[] methods = clazz.getMethods();

            // Rechercher la méthode avec le nom spécifié
            Method method = null;
            for (Method m : methods) {
                if (m.getName().equals(methodName)) {
                    method = m;
                    break;
                }
            }

            // Si la méthode n'a pas été trouvée, lever une exception
            if (method == null) {
                throw new InvocationMethodException("Method " + methodName + " not found in class " + className);
            }

            int parameterCount = method.getParameterCount();

            Object instance = null;
            if (!java.lang.reflect.Modifier.isStatic(method.getModifiers())) {
                instance = clazz.getDeclaredConstructor().newInstance();
            }

            if (parameterCount == 0) {
                result = method.invoke(instance);
            } else {
                Parameter[] parameters = method.getParameters();
                Object[] args = new Object[parameterCount];
                
                ParamChecker checkerAnnotationParam = new ParamChecker();
                checkerAnnotationParam.getAllMethodParam(parameters);
                List<Param> listParam = checkerAnnotationParam.getParamList();

                for (int i = 0; i < args.length; i++) {
                    if (listParam.size() == 0) {
                        if (parameters[i].getType() == String.class) {
                            Parameter parameter = parameters[i];
                            String paramName = parameter.getName();
                            
                            addArgs(request,args,i,paramName);
                        } 
                    }else {
                        String paramName = listParam.get(i).value();
                        addArgs(request, args, i, paramName);
                    }
                    result = method.invoke(instance, args);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvocationMethodException("There is some error invoking the method of this url");
        }
        return result;
    }

    private static void addArgs(HttpServletRequest request, Object[] args, int i, String paramName) {
        String requestParam = request.getParameter(paramName);
        System.out.println("param name: "+paramName);
        System.out.println("value: "+ requestParam);
        if (requestParam != null) {
            args[i] = requestParam;
        } else {
            args[i] = null;
        }
    }

}