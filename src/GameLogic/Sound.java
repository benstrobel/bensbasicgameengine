package GameLogic;

public enum Sound {
    AUA("aua"), SWORD("swordslash"), ITEM("item"), MUSIC("music"), DIED("youdied");

    private final String soundName;

    private Sound(String soundName){this.soundName = soundName;};

    public String getSoundName(){return this.soundName;};
}


