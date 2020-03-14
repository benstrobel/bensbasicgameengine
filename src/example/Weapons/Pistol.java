package example.Weapons;

public class Pistol extends Weapon {
    public Pistol() {
        super(8, 21, 0, 3, 90, 15, false, false);
    }

    public String getTransmissionData(){
        return "p";
    }
}
