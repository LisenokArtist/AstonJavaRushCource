package module.Core.Builder;

public class IntBuilder {
    private int value;
    
    public IntBuilder(){
        value = 0;
    }

    public IntBuilder(int value){
        this.value = value;
    }

    public void plus(int value){
        this.value += value;
    }

    public void minus(int value){
        this.value -= value;
    }

    public void power(int value){
        this.value *= value;
    }

    public void divide(int value){
        this.value /= value;
    }

    public int toInt(){
        return this.value;
    }
}
