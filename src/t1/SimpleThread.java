package t1;

public class SimpleThread implements Runnable{

    private final String str;

    public SimpleThread(String str)
    {
        this.str = str;
        (new Thread(this)).start();
    }
    @Override
    public void run() {
        try {
            while(true) {
                Thread.sleep(1000L);
                System.out.println(this.str);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
