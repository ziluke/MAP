package Model.Types;

import Model.Values.RefValue;
import Model.Values.Value;

public class RefType implements Type {
    private Type inner;
    public RefType(Type i){
        inner = i;
    }

    public Type getInner(){return inner;}

    public boolean equals(Object other){
        if(other instanceof RefType){
            return inner.equals(((RefType) other).getInner());
        }
        else
            return false;
    }
    @Override
    public String toString() {
        return "Ref("+inner.toString()+")";
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }
}
