package t1;

public class UnitHospitalAdapter implements Healable {
    private Playable baseUnit;

    private UnitHospitalAdapter() {
    }

    public UnitHospitalAdapter(Playable baseUnit) {
        this.baseUnit = baseUnit;
    }


    @Override
    public void setHealth(Float health) {
        baseUnit.setHealth(health);
    }

    @Override
    public Float getHealth() {
        return baseUnit.getHealth();
    }
}
