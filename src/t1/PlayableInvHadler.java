package t1;

import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import java.lang.invoke.VarHandle;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class PlayableInvHadler implements InvocationHandler {
    private Object obj;

    PlayableInvHadler(Object obj) {
        this.obj = obj;
    }

    private Object cashedVal;
    private Integer modCount=1;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //System.out.println("It works");
        Method m = obj.getClass().getMethod(method.getName(), method.getParameterTypes());
        boolean hasMutatorAnn = false;
        boolean hasCasheAnn = false;

        for (Annotation a: m.getAnnotations())
        {
            if (a.annotationType().equals(Mutator.class)){
                hasMutatorAnn = true;
            };
            if (a.annotationType().equals(Cashe.class)){
                hasCasheAnn = true;
            };
        }

        if (hasMutatorAnn) {

            modCount++;
            //cashedVal = method.invoke(obj, args);
            System.out.println("Not cashed val from mutator ");
            return method.invoke(obj, args);

        }

        if (hasCasheAnn) {
            if (modCount > 0) {
                System.out.println("Not cashed val: ");
                cashedVal = method.invoke(obj, args);
                modCount=0;
                return cashedVal;
            } else {
                //modCount=0;
                System.out.println("Cashed val: ");
                return cashedVal;
            }

        }


        return method.invoke(obj, args);

    }
}
