package bensbasicgameengine.Input;

public class StringContainer {

    private String string;

    public StringContainer(){
        string = "";
    }

    public StringContainer(String s){
        string = s;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void add(String s){
        string += s;
    }
}
