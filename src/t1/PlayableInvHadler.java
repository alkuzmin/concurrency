package t1;

import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import java.lang.invoke.VarHandle;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

public class PlayableInvHadler implements InvocationHandler {
    private Object obj;

    PlayableInvHadler(Object obj) {
        this.obj = obj;
        cashes = new HashMap<>();
        modCounts = new HashMap<>();
    }

    private Object cashedVal;
    private Integer modCount=1;
    private HashMap<String, Object> cashes;
    private HashMap<String, Integer> modCounts;
    private class Result
    {
        Object value;
        Integer modCount;
    }

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
            //modCount++;
            modCounts.put(m.getName(), modCounts.containsKey(m.getName())?modCounts.get(m.getName())+1:1);
            System.out.println("Not cashed val from mutator ");
            return method.invoke(obj, args);

        }

        if (hasCasheAnn) {
            if (!modCounts.containsKey(m.getName()) || modCounts.get(m.getName()) > 0) {
                System.out.println("Not cashed val: ");
                cashedVal = method.invoke(obj, args);
                cashes.put(m.getName(),cashedVal);
                //modCount=0;
                modCounts.put(m.getName(), 0);
                return cashedVal;
            } else {
                //modCount=0;
                System.out.println("Cashed val: ");
                //return cashedVal;
                return cashes.get(m.getName());
            }

        }


        return method.invoke(obj, args);

    }
}
