package t1;

import java.util.Date;

public class UnitDateDecorator extends UnitDecorator {
    public UnitDateDecorator(Playable baseUnit) {
        super(baseUnit);
    }

    @Override
    @JustForFun
    public void doVoice() {
        super.doVoice();
        System.out.println(new Date().toString()); //additional

    }
}
