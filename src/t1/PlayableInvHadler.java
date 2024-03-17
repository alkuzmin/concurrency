package t1;

import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import java.lang.invoke.VarHandle;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class PlayableInvHadler implements InvocationHandler
{
    private Object obj;
    PlayableInvHadler(Object obj){this.obj = obj;}

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //System.out.println("It works");
        Method m = obj.getClass().getMethod(method.getName(), method.getParameterTypes());

        //Annotation[] anns = m.getDeclaredAnnotations();
        Annotation[] anns = m.getAnnotationsByType(JustForFun.class);
        for (Annotation a: anns)
        {
             System.out.println("Ha ha ha ha ha ha ha ha ha");
        }
//        if (Arrays.stream(anns).filter(x->((Annotation)x).annotationType().equals(JustForFun.class)).count()>0)
//        System.out.println("Ha ha ha ha ha ha ha ha ha");


        return method.invoke(obj, args);

    }
}
