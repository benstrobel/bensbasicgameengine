package example.Weapons;

public abstract class Weapon {

    private int maxammo, currentammo;
    private double damage;
    private double spray;
    private double barrellength;
    private int reloadtime, currentreloadtime = 0;
    private int shotcooldown, currentshotcooldown = 0;
    private boolean fullautocapable, fullautoenabled;

    public Weapon(int maxammo, double damage, double spray, double barrellength, int reloadtime, int shotcooldown, boolean fullautocapable, boolean fullautoenabled){
        this.maxammo = maxammo;
        this.damage = damage;
        this.spray = spray;
        this.barrellength = barrellength;
        this.reloadtime = reloadtime;
        this.shotcooldown = shotcooldown;
        this.fullautocapable = fullautocapable;
        this.fullautoenabled = fullautoenabled;
        currentammo = maxammo;
    }

    public abstract String getTransmissionData();

    public void tick(){
        if(currentreloadtime > 0){currentreloadtime--;}
        if(currentshotcooldown > 0){currentshotcooldown--;}
    }

    public static Weapon createFromTransmission(String data){
        if(data.equals("p")){
            return new Pistol();
        }else if(data.equals("f")){
            return new Fists();
        } else if (data.equals("r")) {
            return new Rifle();
        }
        else {
            return null;
        }
    }

    public boolean shoot(){
        if(currentammo > 0){
            if(currentreloadtime == 0 && currentshotcooldown == 0){
                currentammo--;
                currentshotcooldown = shotcooldown;
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public void toggleFullAuto(){
        if(fullautocapable){
            fullautoenabled = !fullautoenabled;
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

    public boolean isFullautocapable() {
        return fullautocapable;
    }

    public boolean isFullautoenabled() {
        return fullautoenabled;
    }
}
