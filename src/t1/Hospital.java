package t1;

public class Hospital {
    public void heal (Healable p)
    {
        p.setHealth((p.getHealth()*1.05f));
    }
}
