package org.framework.viewScan;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.framework.annotation.FieldParamName;
import org.framework.annotation.Param;
import org.framework.checker.Mapping;
import org.framework.checker.ParamChecker;
import org.framework.checker.RequestMappingChecker;
import org.framework.checker.ParamChecker.ParamWithType;
import org.framework.exceptions.InvocationMethodException;
import org.framework.exceptions.MappingNotFoundException;
import org.framework.exceptions.ParamException;
import org.framework.exceptions.RequestMappingException;
import org.framework.exceptions.ViewException;
import org.framework.view.RedirectView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.framework.view.ModelView;
import org.framework.session.CustomSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * ModelAndView
 * //url mitovy controller roa mitovy url tsy misy package tsy misy type de
 * retour
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
                throw new RequestMappingException("tokony post de get");
            } 
             if (!mapRestAPI.isPost() && request.getMethod().equalsIgnoreCase("POST")) {
                throw new RequestMappingException("tokony get de lasa post");
            }
            processMapping(request, response, requestURL, mapRestAPI, true);
        }
        if (map != null) {
            if (map.isPost() && request.getMethod().equalsIgnoreCase("GET")) {
                throw new RequestMappingException("tokony post de get");
            } 
             if (!map.isPost() && request.getMethod().equalsIgnoreCase("POST")) {
                throw new RequestMappingException("tokony get de lasa post");
            }
            processMapping(request, response, requestURL, map, false);    
        }

    }

    private static void processMapping(HttpServletRequest request, HttpServletResponse response, String requestURL,
            Mapping map,
            boolean isRestAPI) throws Exception {
        String className = map.getClassName();
        String methodName = map.getMethodName();
        Object result = null;

        try {
            result = invokingMethod(request, className, methodName);
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

            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher(modelView.getUrl());
            dispatcher.forward(request, response);
        } else if (result instanceof RedirectView) {
            RedirectView redirectView = (RedirectView) result;

            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher(redirectView.getUrl());
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

    private static Object invokingMethod(HttpServletRequest request, String className, String methodName)
            throws InvocationMethodException, ParamException {
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

                for (int i = 0; i < parameterTypes.length; i++) {
                    if (parameters[i].getType().equals(CustomSession.class)) {
                        syncHttpSessionToCustomSession(request);
                        CustomSession customSession = getCustomSession();
                        args[i] = customSession;
                        idParamSession = i;
                    }
                }

                try {
                    checkerAnnotationParam.getAllMethodParam(parameters);
                } catch (ParamException e) {
                    throw e;
                }

                List<ParamWithType> listParam = checkerAnnotationParam.getParamList();

                for (int i = 0; i < args.length; i++) {
                    if (i != idParamSession) {
                        String paramName = listParam.get(i).getParam().value();
                        if (!listParam.get(i).getType().equals(String.class)) {
                            Class<?> paramClass = listParam.get(i).getType();
                            try {
                                addArgsObject(request, args, i, paramName, paramClass);
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

    private static void addArgsObject(HttpServletRequest request, Object[] args, int i, String paramName,
            Class<?> paramClass) throws InvocationMethodException {
        try {
            Object paramObject = paramClass.getDeclaredConstructor().newInstance();
            Field[] fields = paramClass.getDeclaredFields();
            for (Field field2 : fields) {
                field2.setAccessible(true);
                if (field2.isAnnotationPresent(FieldParamName.class)) {
                    FieldParamName fieldParamName = field2.getAnnotation(FieldParamName.class);

                    String fieldName = fieldParamName.value();
                    String fullParamName = paramName + "." + fieldName;
                    String paramValue = request.getParameter(fullParamName);

                    if (paramValue != null) {
                        Object convertedValue = convertToFieldType(field2, paramValue);
                        field2.set(paramObject, convertedValue);
                    }
                }
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

    private static Object convertToFieldType(Field field, String value) throws Exception {
        Class<?> fieldType = field.getType();

        if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
            return Long.parseLong(value);
        } else if (fieldType.equals(float.class) || fieldType.equals(Float.class)) {
            return Float.parseFloat(value);
        } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
            return Double.parseDouble(value);
        } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (fieldType.equals(String.class)) {
            return value;
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType);
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
}