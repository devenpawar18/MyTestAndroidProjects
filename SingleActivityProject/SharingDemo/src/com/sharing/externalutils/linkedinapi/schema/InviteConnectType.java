
package com.sharing.externalutils.linkedinapi.schema;



/**
 * <p>Java class for null.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType>
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="friend"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
//@XmlEnum
public enum InviteConnectType {

    //@XmlEnumValue("friend")
    FRIEND("friend");
    private final String value;

    InviteConnectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InviteConnectType fromValue(String v) {
        for (InviteConnectType c: InviteConnectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
