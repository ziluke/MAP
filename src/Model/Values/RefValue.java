package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements Value {
    private int address;
    private Type locationType;

    public RefValue(int addr, Type locationType){
        address = addr;
        this.locationType = locationType;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getAddress(){return this.address;}

    @Override
    public String toString() {
        return "RefValue("+ address + "," + locationType.toString()+")";
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }
}
