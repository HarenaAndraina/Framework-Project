package org.framework.classSources;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassFinder {
     public static List<Class<?>> findClassesController(String packageName) throws ClassNotFoundException {
        packageName+=".controller";
        String path = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ArrayList<Class<?>> classes = new ArrayList<>();

        try {
            File dir = new File(classLoader.getResource(path).getFile());
            for (File file : dir.listFiles()) {
                String className = packageName + '.' + file.getName().replace(".class", "");
                Class<?> cls = Class.forName(className);
                classes.add(cls);
            }
        } catch (NullPointerException e) {
            // Handle the case where the package doesn't exist
            System.err.println("Package '" + packageName + "' not found");
        }

        return classes;
    }
}
