package ru.otus.homework05.myTestFramework;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import ru.otus.homework05.myTestFramework.annotations.After;
import ru.otus.homework05.myTestFramework.annotations.Before;
import ru.otus.homework05.myTestFramework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class FrameWork {

    public static void start(Class className) throws Exception{
        if(classHaveMethodWithTestAnnotation(className)) {
            runTest(className);
        }
        else{
            throw new Exception("This class have not @Test annotation");
        }
    }

    public static void start(String packageName) throws Exception{
        Set<String> classNameList = getSetOfClassNamesFromPackage(packageName);
        Set<Class> classList = new HashSet<>();

        for(String cnl : classNameList){
            classList.add(Class.forName(cnl));
        }

        int countClassesWhoseHaveMethodWithTestAnnotation = 0;
        for(Class cl : classList){
            if(classHaveMethodWithTestAnnotation(cl)){
                start(cl);
                countClassesWhoseHaveMethodWithTestAnnotation++;
            }
        }
        if(countClassesWhoseHaveMethodWithTestAnnotation == 0){
            throw new Exception("No one methods at this package has an annotation @Test");
        }

    }

    private static Set<String> getSetOfClassNamesFromPackage(String packageName) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        ClassPath currentClassPath = ClassPath.from(loader);
        ImmutableSet<ClassPath.ClassInfo> classInfoSet = currentClassPath.getTopLevelClasses(packageName);
        Set<String> setOfClassNames = new HashSet<>();
        for(ClassPath.ClassInfo classInfo : classInfoSet){
            setOfClassNames.add(classInfo.getName());
        }
        return setOfClassNames;
    }

    private static boolean classHaveMethodWithTestAnnotation(Class className){
        Method[] methods = className.getDeclaredMethods();
        for(Method m : methods){
            if(haveTestAnnotation(m)){
                return true;
            }
        }
        return false;
    }

    private static void runTest(Class className) throws Exception{
        Object testClassObject = className.newInstance();

        if(haveMoreThanOneAfterBeforeAnnotations(className)){
            throw new Exception("Class has more than one After or Before annotations");
        }
        if (haveIllegalDeclaredAnnotation(className)) {
            throw new Exception("One method has two or more incompatible annotations");
        }

        Method[] methods = className.getDeclaredMethods();
        Method methodWithAfter = null, methodWithBefore = null;
        List<Method> methodsWithTest = new ArrayList<>();
        for(Method m : methods){
            if (haveBeforeAnnotation(m)) {
                methodWithBefore = m;
            }
            if(haveAfterAnnotation(m)){
                methodWithAfter = m;
            }
            if(haveTestAnnotation(m)){
                methodsWithTest.add(m);
            }
        }

        for(Method m : methodsWithTest){
            if(methodWithBefore != null){
                methodWithBefore.invoke(testClassObject);
            }

            m.invoke(testClassObject);

            if(methodWithAfter != null){
                methodWithAfter.invoke(testClassObject);
            }
        }
    }


    private static boolean haveTestAnnotation(Method method){
        Annotation[] annotations = method.getDeclaredAnnotations();
        for(Annotation a : annotations){
            if(a.annotationType().equals(Test.class)){
                return true;
            }
        }
        return false;
    }

    private static boolean haveAfterAnnotation(Method method){
        Annotation[] annotations = method.getDeclaredAnnotations();
        for(Annotation a : annotations){
            if(a.annotationType().equals(After.class)){
                return true;
            }
        }
        return false;
    }

    private static boolean haveBeforeAnnotation(Method method){
        Annotation[] annotations = method.getDeclaredAnnotations();
        for(Annotation a : annotations){
            if(a.annotationType().equals(Before.class)){
                return true;
            }
        }
        return false;
    }

    private static boolean haveIllegalDeclaredAnnotation(Class clazz){
        Method[] methods = clazz.getDeclaredMethods();
        for(Method m : methods){
            boolean haveAfter = haveAfterAnnotation(m);
            boolean haveBefore = haveBeforeAnnotation(m);
            boolean haveTest = haveTestAnnotation(m);
            if((haveAfter && haveBefore) || (haveAfter && haveTest) || (haveBefore && haveTest)){
                return true;
            }
        }
        return false;
    }

    private static boolean haveMoreThanOneAfterBeforeAnnotations(Class clazz){
        Method[] methods = clazz.getDeclaredMethods();

        int beforeAnnotations = 0;
        int afterAnnotations = 0;

        for(Method m : methods){
            if (haveBeforeAnnotation(m)) {
                beforeAnnotations++;
            }
            if(haveAfterAnnotation(m)){
                afterAnnotations++;
            }
        }

        if(beforeAnnotations > 1 || afterAnnotations > 1){
            return true;
        }

        return false;
    }
}
