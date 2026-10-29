package interpreter.model.value;

import interpreter.model.type.IType;
import interpreter.model.type.StringType;

public record StringValue(String val) implements IValue {
    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof StringValue))
            return false;
        StringValue other = (StringValue) o;
        return this.val.equals(other.val);
    }

    @Override
    public int hashCode() {
        return this.val.hashCode();
    }
}
