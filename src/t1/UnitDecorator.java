package t1;

public class UnitDecorator implements Playable{
    private Playable baseUnit;

    public UnitDecorator(Playable baseUnit) {
        this.baseUnit = baseUnit;
    }

    @Override
    @JustForFun
    public void doVoice() {
        baseUnit.doVoice();
    }

    @Override
    public String getName() {
        return baseUnit.getName();
    }

    @Override
    public void setName(String name) {
        baseUnit.setName(name);
    }

    @Override
    public Float getHealth() {
        return baseUnit.getHealth();
    }

    @Override
    public void setHealth(Float health) {
        baseUnit.setHealth(health);
    }

    @Override
    public String toString() {
        return baseUnit.toString();
    }
}
