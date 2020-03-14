package example.Weapons;

public class Pistol extends Weapon {
    public Pistol() {
        super(8, 21, 0, 3, 120, 30);
    }

    public String getTransmissionData(){
        return "p";
    }
}
