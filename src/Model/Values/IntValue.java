package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value {
    private int val;

    public IntValue(int v){
        this.val= v;
    }

    public int getVal(){
        return this.val;
    }

    @Override
    public String toString(){
        return Integer.toString(this.val);
    }

    @Override
    public Type getType(){
        return new IntType();
    }

}
