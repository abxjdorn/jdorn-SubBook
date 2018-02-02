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
 *
 * @param <T> type that the converter converts to/from. The intended
 *            use is for implementations to implement FieldConverter
 *            of some type in order to convert to/from that type, rather
 *            than implementing FieldConverter as a generic.
 */
public interface FieldConverter<T> {
    /**
     * Return value representing a successful string
     * to object conversion.
     */
    int VALID = 0;
    
    /**
     * Return value representing a generic conversion failure.
     */
    int INVALID_FORMAT = 1;
    
    /**
     * Gets the string representation of the current state
     * of the FieldConverter.
     *
     * @return string representation of the current state. May be null if state is invalid.
     */
    String getString();
    
    /**
     * Gets the object representation of the current state
     * of the FieldConverter.
     *
     * @return object representation of the current state. May be null if state is invalid.
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
     *
     * @param string string to attempt to convert
     * @return value representing whether the conversion succeeded
     */
    int setString(String string);
    
    /**
     * Updates the FieldConverter state by setting its object.
     * The object will also be converted into a string representing
     * it (such that passing the string to setString will recreate
     * the object).
     *
     * @param object object to convert
     */
    void setObject(T object);
}
