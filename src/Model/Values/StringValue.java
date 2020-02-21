package Model.Values;

import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value{
    private String val;

    public StringValue(String v){
        this.val= v;
    }

    public String getVal(){
        return this.val;
    }

    @Override
    public String toString(){
        return this.val;
    }

    @Override
    public Type getType(){
        return new StringType();
    }

}
