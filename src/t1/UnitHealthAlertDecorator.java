package t1;

public class UnitHealthAlertDecorator extends UnitDecorator {
    public UnitHealthAlertDecorator(Playable baseUnit) {
        super(baseUnit);
    }


    @Override
    @JustForFun
    public void doVoice() {
        super.doVoice();
        System.out.println(((getHealth() < 10) ? " DANGER!!! " : " ") + " \n");
    }
}
