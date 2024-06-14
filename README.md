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
* The `FrontController` class (located in `org.framework.FrontController`) captures the user's requested URL and displayes it in the browser. It also shows the details of the associated controller for that URL.

* The `@Controller` annotation (located in `org.framework.annotation.Controller`) is used to annotate all classes that the developer wants to be scanned as a controller.

* The `@RequestMapping` (located in `org.framework.annotation.RequestMapping`)  annotation is used to map methods to specific URLs.

* The method using annotations can retrieve a `String` and simply display it.

* The `ModelView` class (located in `org.framework.view.ModelView`) can also retrieve methods that use annotations. When instantiating the class, you need to add the name of the .jsp file. The `addObject` method in the class is used to place items that need to be displayed in the .jsp file.

* The `RedirectView` class (located in `org.framework.view.RedirectView`) can also retrieve methods that just need to redirect to a .jsp file.

* The argument of the method using the annotation can be only no parameter and String only


@HarenaAndraina
