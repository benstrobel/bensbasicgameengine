package example.Weapons;

public abstract class Weapon {

    private int maxammo, currentammo;
    private double damage;
    private double spray;
    private double barrellength;
    private int reloadtime;
    private int shotcooldown;

    public Weapon(int maxammo, double damage, double spray, double barrellength, int reloadtime, int shotcooldown){
        this.maxammo = maxammo;
        this.damage = damage;
        this.spray = spray;
        this.barrellength = barrellength;
        this.reloadtime = reloadtime;
        this.shotcooldown = shotcooldown;
        currentammo = maxammo;
    }

    public abstract String getTransmissionData();

    public static Weapon createFromTransmission(String data){
        if(data.equals("p")){
            return new Pistol();
        }else if(data.equals("f")){
            return new Fists();
        }else{
            return null;
        }
    }

    public boolean shoot(){
        if(currentammo > 0){
            currentammo--;
            return true;
        }else{
            return false;
        }
    }

    public void reload(){
        currentammo = maxammo;
    }

    public double getBarrellength() {
        return barrellength;
    }

    public double getDamage() {
        return damage;
    }

    public double getSpray() {
        return spray;
    }

    public int getMaxammo() {
        return maxammo;
    }

    public int getCurrentammo() {
        return currentammo;
    }

    public int getReloadtime() {
        return reloadtime;
    }

    public int getShotcooldown() {
        return shotcooldown;
    }
}
