package org.framework.viewScan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.framework.annotation.FieldParamName;
import org.framework.annotation.FileParamName;
import org.framework.File.FileParam;
import org.framework.annotation.Param;
import org.framework.checker.Mapping;
import org.framework.checker.ParamChecker;
import org.framework.checker.RequestMappingChecker;
import org.framework.checker.Validator;
import org.framework.checker.ParamChecker.ParamWithType;
import org.framework.exceptions.InvocationMethodException;
import org.framework.exceptions.MappingNotFoundException;
import org.framework.exceptions.ParamException;
import org.framework.exceptions.RequestMappingException;
import org.framework.exceptions.ViewException;
import org.framework.view.RedirectView;
import org.framework.exceptions.ValidationException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.framework.view.ModelView;
import org.framework.session.CustomSession;
import jakarta.servlet.ServletContext;

import com.google.gson.Gson;

/**
 * The method invokingMethod uses reflection to dynamically invoke a method from
 * a controller class.
 * The methods syncHttpSessionToCustomSession and syncCustomSessionToHttpSession
 * synchronize data between the HttpSession (from javax.servlet) and the custom
 * session wrapper (CustomSession).
 * The addArgsObject method validates input data using a Validator object. If
 * errors are found, it sets error attributes (error_*) and old values (old_*)
 * in the request before forwarding to the previous page.
 */

public class ViewScan {
    private static CustomSession customSession;

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

    public static void viewScanner(HttpServletRequest request, HttpServletResponse response, String requestURL,
            Mapping map,
            Mapping mapRestAPI)
            throws Exception {

        if (map == null && mapRestAPI == null) {
            throw new MappingNotFoundException("Mapping Not Found");
        }

        if (mapRestAPI != null) {
            if (mapRestAPI.isPost() && request.getMethod().equalsIgnoreCase("GET")) {
                throw new RequestMappingException(
                        "The HTTP method used is not allowed. Please use POST instead of GET.");
            }
            if (!mapRestAPI.isPost() && request.getMethod().equalsIgnoreCase("POST")) {
                throw new RequestMappingException(
                        "The HTTP method used is not allowed. Please use GET instead of POST.");
            }
            processMapping(request, response, requestURL, mapRestAPI, true);
        }
        if (map != null) {
            if (map.isPost() && request.getMethod().equalsIgnoreCase("GET")) {
                throw new RequestMappingException(
                        "The HTTP method used is not allowed. Please use POST instead of GET");
            }
            if (!map.isPost() && request.getMethod().equalsIgnoreCase("POST")) {
                throw new RequestMappingException(
                        "The HTTP method used is not allowed. Please use GET instead of POST");
            }
            processMapping(request, response, requestURL, map, false);
        }

    }

    private static void processMapping(HttpServletRequest request, HttpServletResponse response, String requestURL,
            Mapping map,
            boolean isRestAPI) throws Exception {
        String className = map.getClassName();
        String methodName = map.getMethodName();
        System.out.println("methodName: " + methodName);
        Object result = null;

        try {
            result = invokingMethod(request, response, className, methodName);
        } catch (Exception e) {
            throw e;
        }

        if (result != null) {
            if (isRestAPI) {
                handleRestAPIResponse(response, requestURL, result);
            } else {
                handleWebResponse(request, response, requestURL, result);
            }
        } else {
            throw new ViewException("Method: " + methodName + " returned a null result");
        }
    }

    private static void handleRestAPIResponse(HttpServletResponse response, String requestURL, Object result)
            throws Exception {
        if (result instanceof String) {
            showMessageJson(response, requestURL, (String) result);
        } else if (result instanceof ModelView) {
            showModelViewJson(response, requestURL, (ModelView) result);
        } else {
            throw new ViewException("Unsupported return type for REST API");
        }
    }

    private static void handleWebResponse(HttpServletRequest request, HttpServletResponse response, String requestURL,
            Object result)
            throws Exception {
        if (result instanceof String) {
            showMessage(response, requestURL, (String) result);
        } else if (result instanceof ModelView) {
            ModelView modelView = (ModelView) result;
            HashMap<String, Object> data = modelView.getData();

            for (String key : data.keySet()) {
                Object value = data.get(key);
                request.setAttribute(key, value);
            }
            System.out.println("atooo2");

            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher(modelView.getUrl());
            dispatcher.forward(request, response);
        } else if (result instanceof RedirectView) {
            RedirectView redirectView = (RedirectView) result;

            System.out.println("atooo3");

            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher(redirectView.getUrl());
            System.out.println(redirectView.getUrl());
            dispatcher.forward(request, response);
        } else {
            throw new ViewException("Unsupported return type for Web response");
        }
    }

    private static void showMessageJson(HttpServletResponse response, String requestURL, String message)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Construction du JSON
            String json = "{\"message\": \"" + message + "\"}";
            out.print(json);
            out.flush();
        }
    }

    private static void showModelViewJson(HttpServletResponse response, String requestURL, ModelView result)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HashMap<String, Object> data = result.getData();
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(data);
            out.print(json);
            out.flush();
        }
    }

    private static void showMessage(HttpServletResponse response, String requestURL, String message) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FrontController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FrontController at " + requestURL + "</h1>");
            out.println("<p>");
            out.println(message);
            out.println("</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private static Object invokingMethod(HttpServletRequest request, HttpServletResponse response, String className,
            String methodName)
            throws InvocationMethodException, ParamException {
        System.out.println("method invoked : " + methodName);
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
                Class<?>[] parameterTypes = new Class[parameterCount];
                Object[] args = new Object[parameterCount];
                ParamChecker checkerAnnotationParam = new ParamChecker();
                int idParamSession = -1;
                int idParamFile = -1;

                for (int i = 0; i < parameterTypes.length; i++) {
                    if (parameters[i].getType().equals(CustomSession.class)) {
                        syncHttpSessionToCustomSession(request);
                        CustomSession customSession = getCustomSession();
                        args[i] = customSession;
                        idParamSession = i;
                    }
                    if (parameters[i].isAnnotationPresent(FileParamName.class)) {
                        FileParamName paramFile = parameters[i].getAnnotation(FileParamName.class);
                        FileParam fileParam = new FileParam(paramFile.value());

                        fileParam.add(request);
                        args[i] = fileParam;
                        idParamFile = i;
                    }
                }

                try {
                    checkerAnnotationParam.getAllMethodParam(parameters);
                } catch (ParamException e) {
                    throw e;
                }

                List<ParamWithType> listParam = checkerAnnotationParam.getParamList();

                for (int i = 0; i < args.length; i++) {

                    if (i != idParamSession && i != idParamFile) {
                        String paramName = listParam.get(i).getParam().value();
                        if (!listParam.get(i).getType().equals(String.class)) {
                            Class<?> paramClass = listParam.get(i).getType();
                            try {
                                addArgsObject(request, response, args, i, paramName, paramClass);
                            } catch (InvocationMethodException e) {
                                throw e;
                            }
                        } else {
                            addArgsString(request, args, i, paramName);
                        }
                    }
                }

                result = method.invoke(instance, args);
                syncCustomSessionToHttpSession(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvocationMethodException(
                    "There is some error invoking the method of this url: " + e.getMessage());
        }
        return result;
    }

    private static void addArgsObject(HttpServletRequest request, HttpServletResponse response, Object[] args, int i,
            String paramName, Class<?> paramClass)
            throws InvocationMethodException, ValidationException {
        try {
            Object paramObject = paramClass.getDeclaredConstructor().newInstance();
            Field[] fields = paramClass.getDeclaredFields();
            Validator valide = new Validator();
            valide.validate(fields, request, paramObject, paramName);
            Map<String, String> validationErrors = valide.getErrors();

            if (!validationErrors.isEmpty()) {
                Map<String, Object> oldValues = valide.getOldValue();

                for (Map.Entry<String, String> errorEntry : validationErrors.entrySet()) {
                    String errorKey = errorEntry.getKey(); // Key for the error
                    String errorMessage = errorEntry.getValue(); // Value of the error message
                    System.out.println(errorMessage);
                    // Add the error to the request attributes
                    request.setAttribute("error_" + errorKey, errorMessage);
                }
                for (Map.Entry<String, Object> oldValue : oldValues.entrySet()) {
                    System.out.println(oldValue.getValue());
                    request.setAttribute("old_" + oldValue.getKey(), oldValue.getValue());
                }

                HttpServletRequest modifiedRequest = new HttpServletRequestWrapper(request) {
                    @Override
                    public String getMethod() {
                        return "GET"; // Override the method to GET
                    }
                };
            
                RequestDispatcher dispatcher = modifiedRequest.getRequestDispatcher(getPREVURL(request));
                dispatcher.forward(modifiedRequest, response);
            }
            args[i] = paramObject;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvocationMethodException("Cannot access the field parameter");
        }
    }

    private static void addArgsString(HttpServletRequest request, Object[] args, int i, String paramName) {
        String requestParam = request.getParameter(paramName);

        System.out.println("param name: " + paramName);
        System.out.println("value: " + requestParam);
        // mila caste'na aminy loniny

        if (requestParam != null) {
            args[i] = requestParam;
        } else {
            args[i] = null;
        }
    }

    // Méthode pour synchroniser HttpSession vers CustomSession
    public static void syncHttpSessionToCustomSession(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        Enumeration<String> attributeNames = httpSession.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = httpSession.getAttribute(attributeName);
            getCustomSession().getSessionList().put(attributeName, attributeValue);
        }
    }

    // Méthode pour synchroniser CustomSession vers HttpSession
    public static void syncCustomSessionToHttpSession(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        Map<String, Object> sessionList = getCustomSession().getSessionList();

        for (Map.Entry<String, Object> entry : sessionList.entrySet()) {
            httpSession.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    public static synchronized CustomSession getCustomSession() {
        if (customSession == null) {
            customSession = new CustomSession();
        }
        return customSession;
    }

    public static String getPREVURL(HttpServletRequest request) {
        String referer = request.getHeader("Referer");

        // Vérifier si le referer n'est pas null
        if (referer != null && !referer.isEmpty()) {
            // Extraire le dernier segment après le dernier '/'
            int lastSlashIndex = referer.lastIndexOf("/");
            if (lastSlashIndex != -1 && lastSlashIndex < referer.length() - 1) {
                return referer.substring(lastSlashIndex + 1); // Retourne le dernier segment
            }
        }

        // Retourner une valeur par défaut ou null si le referer est introuvable
        return null;
    }

}