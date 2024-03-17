package t1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class Unit  implements  Playable{

    public  Object getProxy()
    {
        Class cls = this.getClass();
        return  Proxy.newProxyInstance(cls.getClassLoader(),
                new Class[]{Playable.class, Healable.class},
                new PlayableInvHadler(this));
    }
    private String name;
    private Float health;

    @JustForFun
    private Integer id;

    private Unit() {
    }

    public Unit(String name, Float health) {
        this.setName(name);
        this.setHealth(health);
    }
    @Sensitive
    @JustForFun  @JustForFun  @JustForFun  @JustForFun  @JustForFun
    public void doVoice() {
        System.out.println("Unit " + getName() + " " + getHealth());
    }

    @Override
    @Protocolable(severity = 6)
    public String toString() {
        return new String("Unit " + getName() + " " + getHealth());
    }

    public String getName() {
        return name;
    }


    @Protocolable(severity = 9)
    public void setName(String name) {
        this.name = name;
    }

    public Float getHealth() {
        return health;
    }

    @Protocolable(severity = 4)
    public void setHealth(Float health) {

        if (health < 0 || health > 100) throw new IllegalArgumentException();
        this.health = health;
    }
}
