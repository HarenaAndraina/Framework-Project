package org.framework.classSources;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.framework.exceptions.PackageNotFoundException;

import org.framework.exceptions.ControllerNotFoundException;

public class ClassFinder {
    public static List<Class<?>> findClassesController(String packageName) throws PackageNotFoundException,ControllerNotFoundException {
        packageName += ".controller";
        String path = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        List<Class<?>> classes = new ArrayList<>();

        try {
            File directory = new File(classLoader.getResource(path).getFile());
            if (directory.exists()) {
                try {
                    findClasses(directory, packageName, classes);
                } catch (Exception e) {
                    throw new ControllerNotFoundException("Controller Not Found Exception");
                }
            } else {
                throw new PackageNotFoundException("Package Not Found Exception, check the context-param in web.xml");
            }
        } catch (NullPointerException e) {
            // Handle the case where the package doesn't exist
            throw new PackageNotFoundException("Package doesn't exist, check the context-param in web.xml");
        }

        return classes;
    }

    private static void findClasses(File directory, String packageName, List<Class<?>> classes)
            throws ClassNotFoundException {
        if (!directory.exists()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                findClasses(file, packageName + "." + file.getName(), classes);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
    }
}
