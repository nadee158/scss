package com.privasia.scss.core.util.enumusertype;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.EnhancedUserType;

import com.privasia.scss.core.util.constant.Enumable;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
/**
 * Hibernate User type for enums.
 * @author janaka wanigatunga
 * @see Enumable
 * @param <t> any enum type that implements Enumable
 *
 */
public class GeneralEnumMapUserType<T extends Enumable> implements EnhancedUserType, Serializable {
 
 
    private static final long serialVersionUID = -5993020929647717601L;
    private Class enumClass;
    private Enumable FIRST_ENUM_ITEM;
 
    /**
     * Constructor.
     * @param enumClass enum class
     *
     */
    protected GeneralEnumMapUserType(Class<T> enumClass) {
        this.enumClass = enumClass;
        if(enumClass.getEnumConstants()[0] instanceof Enumable) {
            FIRST_ENUM_ITEM = enumClass.getEnumConstants()[0];
        } else {
            throw new IllegalStateException("The class " + enumClass + " MUST implement Enumable interface!");
        }
    }
 
    @Override
    public int[] sqlTypes() {
        return new int[]{StandardBasicTypes.STRING.sqlType()};
    }
 
    @Override
    public Class returnedClass() {
        return Enum.class;
    }
 
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if(x != null) {
            return x.equals(y);
        }
        return false;
    }
 
    @Override
    public int hashCode(Object x) throws HibernateException {
        return x != null ? x.hashCode() : 1978;
    }
 
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        String name = rs.getString(names[0]);
        return rs.wasNull() || name == null ? null : FIRST_ENUM_ITEM.getEnumFromValue(name);
    }
 
    @Override
    public void nullSafeSet(PreparedStatement st,
                            Object value, int index, SessionImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, StandardBasicTypes.STRING.sqlType());
        } else {
            st.setString(index, ((Enumable)value).getValue());
        }
    }
 
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }
 
    @Override
    public boolean isMutable() {
        // TODO Auto-generated method stub
        return false;
    }
 
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        // TODO Auto-generated method stub
        return (Serializable) value;
    }
 
    @Override
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        // TODO Auto-generated method stub
        return cached;
    }
 
    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        // TODO Auto-generated method stub
        return original;
    }
 
    @Override
    public String objectToSQLString(Object value) {
        return '\'' + ((Enumable)value).getValue() + '\'';
    }
 
    @Override
    public String toXMLString(Object value) {
        return ((Enumable) value).getValue();
    }
 
    @Override
    public Object fromXMLString(String xmlValue) {
        return xmlValue == null || xmlValue.isEmpty() ? null : FIRST_ENUM_ITEM.getEnumFromValue(xmlValue);
    }
}
