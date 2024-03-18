import t1.Playable;
import t1.SimpleThread;
import t1.Unit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");
//        SimpleThread st1 = new SimpleThread("report 1");
        //Thread.sleep(2000L);
//        SimpleThread st2 = new SimpleThread("report 2");

//        Runnable st3 = ()->{
//            for (int i = 0; i<=100; i++) {
//                System.out.println("!!!!");
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        };
//        (new Thread(st3)).start();

        Unit t1 = new Unit("dfdf",14f);
        Playable t1_cashed = (Playable)t1.getProxy();

        System.out.println("before cashe");
        System.out.println(t1.getName());
        t1.setName("qwerty");
        System.out.println(t1.getName());

        System.out.println("after cashe");
        System.out.println(t1_cashed.getName());
        System.out.println(t1_cashed.getName());
        System.out.println(t1_cashed.getName());
        t1_cashed.setName("asdfgh");
        System.out.println(t1_cashed.getName());


//        for (int i = 1; i <= 5; i++) {
//            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
//            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
//            System.out.println("i = " + i);
//        }
    }
}