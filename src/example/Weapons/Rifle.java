package example.Weapons;

public class Rifle extends Weapon {
    public Rifle() {
        super(30, 34, 0, 6, 90, 7, true, true);
    }

    @Override
    public String getTransmissionData() {
        return "r";
    }
}
