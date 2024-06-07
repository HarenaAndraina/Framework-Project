# Framework-Project

**Directory stuctures and how to use**
* src/
    add all your java file

* lib/
    add the project.jar

* views/
    all your jsp file

* web.xml
    the web.xml file should follow this below:

    <?xml version="1.0" encoding="UTF-8"?>
    <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
                                http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
            version="4.0">
    <servlet>
    <servlet-name>FrontController</servlet-name>
    <servlet-class>org.framework.FrontController</servlet-class>
    </servlet>

    <!-- Mapping des URLs pour le FrontController -->
    <servlet-mapping>
        <servlet-name>FrontController</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <context-param>
        <param-name>Package</param-name>
        <param-value># add your package directory</param-value>
    </context-param>
    </web-app>

**Current functionality**
* The `FrontController` class (located in `org\framework/FrontController.java`) captures the user's requested URL and displayes it in the browser. It also shows the details of the associated controller for that URL.
* The `@Controller` annotation is used to annotate all classes that the developer wants to be scanned as a controller.
* The `@GetMapping` annotation is used to map methods to specific URLs.
* 

@HarenaAndraina
