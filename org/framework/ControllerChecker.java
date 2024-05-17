package org.framework.checker;

import java.util.ArrayList;
import java.util.List;
import org.framework.annotation.Controller;
import org.framework.classSources.ClassFinder;

public class ControllerChecker {
    private List<String> classController=new ArrayList<String>();
    
    public void addClassController(String classController) {
        this.classController.add(classController);
    }
    
    public boolean hasControllerAnnotation(Class<?> clazz) {
            Controller controller=clazz.getAnnotation(Controller.class);
            if(controller!=null){
                return true;
            }        
        return false; // Si aucune classe avec l'annotation @Controller n'est trouvée, retourne false
    }
    
    public String getControllerClassName(Class<?> clazz) {
        if (hasControllerAnnotation(clazz)) {
            return clazz.getSimpleName();
        } else {
            return null;
        }
    }

    public void getAllClassController(String packaze) throws Exception {
        List<Class<?>> AllClazzez=ClassFinder.findClassesController(packaze);

        for (Class<?> class1 : AllClazzez) {
            String className=getControllerClassName(class1);
            if (className != null) {
                addClassController(className);
            }
        }
    }

    public List<String> getClassController() {
        return classController;
    }
    
}
