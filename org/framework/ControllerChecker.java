package org.framework.checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.framework.annotation.Controller;
import org.framework.classSources.ClassFinder;

public class ControllerChecker {
    private List<Class<?>> classController=new ArrayList<Class<?>>();
    
    //getter
    public List<Class<?>> getClassController() {
        return classController;
    }

    //setter
    void addClassController(Class<?> classController) {
        this.classController.add(classController);
    }
    
    boolean hasControllerAnnotation(Class<?> clazz) {
            Controller controller=clazz.getAnnotation(Controller.class);
            if(controller!=null){
                return true;
            }        
        return false; // Si aucune classe avec l'annotation @Controller n'est trouvée, retourne false
    }
    
    String getControllerClassName(Class<?> clazz) {
        if (hasControllerAnnotation(clazz)) {
            return clazz.getSimpleName();
        } else {
            return null;
        }
    }

    Class<?> getControllerClass(Class<?> clazz) {
        if (hasControllerAnnotation(clazz)) {
            return clazz;
        }
        else{
            return null;
        }
    }

    public void getAllClassController(String packaze) throws Exception {
        List<Class<?>> AllClazzez=ClassFinder.findClassesController(packaze);

        for (Class<?> class1 : AllClazzez) {
            //String className=getControllerClassName(class1);
            if (getControllerClass(class1) != null) {
                addClassController(class1);
            }
        }
    }
   
}
