package t1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayableInvHadler implements InvocationHandler {
    private Object obj;
    //для каждого состояния - свой набор кешей результатов методов

    private final Map<State, Map<Method, Result>> states = new HashMap<>();
    private State curState;
    //потокобезопасная мапа кешей <метод> - <результат с ttl>
    private Map<Method, Result> curStateResults;

    PlayableInvHadler(Object obj) {
        this.obj = obj;
        //потокобезопасная мапа кешей
        curStateResults = new ConcurrentHashMap<>();
        curState = new State();
        states.put(curState, curStateResults);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //System.out.println("It works");
        Method m = obj.getClass().getMethod(method.getName(), method.getParameterTypes());
        Object objectResult;

        //если с аннотацией @кеш и надо закешировать или попробовать взять закешированное
        if (m.isAnnotationPresent(Cache.class)) {
            Result res = curStateResults.get(m); //пытаемся прочитать кеш
            long time = m.getAnnotation(Cache.class).time(); //читаем время жизни
            if (res != null)
            //если уже было значение
            {
                res.ttl = System.currentTimeMillis() + time; //освежаем время
                if (time == 0) res.ttl = 0L; //если хотят бессрочно
                curStateResults.put(m, res); //обновляем в кеше
                return res.value;
            }
            objectResult = method.invoke(obj, args); //вызывам сам метод
            //сохраняем результат с указанием времени жизни
            res = new Result(System.currentTimeMillis() + time, objectResult);
            if (time == 0) res.ttl = 0L; //если хотим бессрочно
            curStateResults.put(m, res); //добавляем в кеш
            return objectResult; //возвращаем результат
        }

        //если мутатор и надо сохранять состояние
        if (m.isAnnotationPresent(Mutator.class)) {
            //добавляем новое состояние
            curState = new State(curState, m, args);
            if (states.containsKey(curState)) {
                curStateResults = states.get(curState);
            } else {
                curStateResults = new ConcurrentHashMap<>();
                states.put(curState, curStateResults);
            }
        }

        new CacheCleaner().start();
        return method.invoke(obj, args);

//        System.out.println("captured " + m.getName());
//        return method.invoke(obj, args);

    }


    private class CacheCleaner extends Thread {
        @Override
        public void run() {
            //перебираем состояния
            for (Map<Method, Result> map : states.values()) {
                //перебираем методы в состоянии
                for (Method met : map.keySet()) {
                    Result result = map.get(met);
                    if (result.ttl == 0) continue; //пока не истек срок
                    if (result.ttl < System.currentTimeMillis()) {
                        map.remove(met); //срок уже истек
                    }
                }
            }
        }
    }
}
