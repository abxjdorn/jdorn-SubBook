package ca.ualberta.cmput301w18.jdorn_subbook;


/**
 * Converts between a subtype of value and the text in a field.
 * An implementation of FieldConverter makes available two objects:
 * an object of the target type and its textual representation.
 * Either an object or its representation can be passed into the
 * converter, generating the other in doing so. An object of the
 * correct type should always be convertible to a string, but not
 * all strings need be convertible to object - thus the object of
 * the converter may be null. In this state, attempting to retrieve
 * the string representation will also yield null.
 */
public interface FieldConverter<T> {
    /**
     * Return value representing a successful string
     * to object conversion.
     */
    int VALID = 0;
    
    /**
     * Gets the string representation of the current state
     * of the FieldConverter. May be null if the converter
     * state is invalid.
     */
    String getString();
    
    /**
     * Gets the object representation of the current state
     * of the FieldConverter. May be null if the converter
     * state is invalid.
     */
    T getObject();
    
    /**
     * Updates the FieldConverter state by setting its string.
     * The string will be converted into the target object type
     * of the FieldConverter, if possible. If successful, the
     * return value will be zero (FieldConverter.VALID). If not,
     * the object of the converter will become null and the return
     * value will be non-zero. The specific meaning of a possible
     * non-zero value is defined by the implementing subclass.
     */
    int setString(String string);
    
    /**
     * Updates the FieldConverter state by setting its object.
     * The object will also be converted into a string representing
     * it (such that passing the string to setString will recreate
     * the object).
     */
    void setObject(T object);
}
