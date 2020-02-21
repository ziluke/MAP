package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value {
    private boolean val;

    public BoolValue(boolean val){
        this.val = val;
    }

    public boolean getVal(){return this.val;}

    @Override
    public String toString(){
        return Boolean.toString(this.val);
    }

    @Override
    public Type getType(){
        return new BoolType();
    }
}
