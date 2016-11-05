package fr.sysgli.disbotapi.user;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Stores all metadata for a DiscordUser.
 * @author Jeremy LAMBERT
 * @see DiscordUser
 *
 */
public final class UserData implements Serializable {

	private static final long serialVersionUID = -8824839545895220523L;
	
	private HashMap<String , String> data;
	
	UserData() {
		data = new HashMap<String , String>();
	}
	
	/**
	 * Stores the value with the identifier key as a String object.
	 * @param key
	 * @param value
	 */
	public void setString(String key , String value) {
		data.put(key, value);
	}
	
	/**
	 * Returns the String associated with the key or null if the key doesn't exists.
	 * @param key
	 * @return String value
	 */
	public String getString(String key) {
		return data.get(key);
	}
	
	/**
	 * Stores the value with the identifier key as a boolean.
	 * @param key
	 * @param value
	 */
	public void setBoolean(String key , boolean value) {
		data.put(key, String.valueOf(value));
	}
	
	/**
	 * Returns the boolean associated with the key.
	 * @param key
	 * @return boolean value
	 */
	public boolean getBoolean(String key) {
		String value = data.get(key);
		if(value == null)
			return false;
		return Boolean.parseBoolean(value);
	}
	
	/**
	 * Stores the value with the identifier key as a byte.
	 * @param key
	 * @param value
	 */
	public void setByte(String key , byte value) {
		data.put(key, String.valueOf(value));
	}
	
	/**
	 * Returns the int associated with the key or -1 if the key doesn't exists.<br>
	 * <b>Be sure that the associated value IS a byte or an exception will be thrown!</b>
	 * @param key
	 * @return byte value
	 */
	public byte getByte(String key) {
		String value = data.get(key);
		if(value == null)
			return -1;
		return Byte.parseByte(value);
	}
	
	/**
	 * Stores the value with the identifier key as a short.
	 * @param key
	 * @param value
	 */
	public void setShort(String key , short value) {
		data.put(key, String.valueOf(value));
	}
	
	/**
	 * Returns the int associated with the key or -1 if the key doesn't exists.<br>
	 * <b>Be sure that the associated value IS a short or an exception will be thrown!</b>
	 * @param key
	 * @return short value
	 */
	public short getShort(String key) {
		String value = data.get(key);
		if(value == null)
			return -1;
		return Short.parseShort(value);
	}
	
	/**
	 * Stores the value with the identifier key as an int.
	 * @param key
	 * @param value
	 */
	public void setInt(String key , int value) {
		data.put(key, String.valueOf(value));
	}
	
	/**
	 * Returns the int associated with the key or -1 if the key doesn't exists.<br>
	 * <b>Be sure that the associated value IS an int or an exception will be thrown!</b>
	 * @param key
	 * @return int value
	 */
	public int getInt(String key) {
		String value = data.get(key);
		if(value == null)
			return -1;
		return Integer.parseInt(value);
	}
	
	/**
	 * Stores the value with the identifier key as a long.
	 * @param key
	 * @param value
	 */
	public void setLong(String key , long value) {
		data.put(key, String.valueOf(value));
	}
	
	/**
	 * Returns the long associated with the key or -1 if the key doesn't exists.<br>
	 * <b>Be sure that the associated value IS a long or an exception will be thrown!</b>
	 * @param key
	 * @return long value
	 */
	public long getLong(String key) {
		String value = data.get(key);
		if(value == null)
			return -1;
		return Long.parseLong(value);
	}
	
	/**
	 * Stores the value with the identifier key as a float.
	 * @param key
	 * @param value
	 */
	public void setFloat(String key , float value) {
		data.put(key, String.valueOf(value));
	}
	
	/**
	 * Returns the float associated with the key or NaN if the key doesn't exists.<br>
	 * <b>Be sure that the associated value IS a float or an exception will be thrown!</b>
	 * @param key
	 * @return float value
	 */
	public float getFloat(String key) {
		String value = data.get(key);
		if(value == null)
			return Float.NaN;
		return Float.parseFloat(value);
	}
	
	/**
	 * Stores the value with the identifier key as a double.
	 * @param key
	 * @param value
	 */
	public void setDouble(String key , double value) {
		data.put(key, String.valueOf(value));
	}
	
	/**
	 * Returns the double associated with the key or NaN if the key doesn't exists.<br>
	 * <b>Be sure that the associated value IS a double or an exception will be thrown!</b>
	 * @param key
	 * @return double value
	 */
	public double getDouble(String key) {
		String value = data.get(key);
		if(value == null)
			return Double.NaN;
		return Double.parseDouble(value);
	}
	
	HashMap<String , String> getData() {
		return data;
	}
	
	/**
	 * Returns if a value is associated to the specified key.
	 * @param key
	 * @return boolean hasKey
	 */
	public boolean hasKey(String key) {
		return data.containsKey(key);
	}
	
	/**
	 * Removes the value associated with the specified key.
	 * @param key
	 */
	public void remove(String key) {
		data.remove(key);
	}
	
}
