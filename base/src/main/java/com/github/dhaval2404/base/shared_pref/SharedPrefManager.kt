package com.github.dhaval2404.base.shared_pref

import android.content.SharedPreferences
import java.util.Collections

/**
 * SharedPreferences DisplayUtil class
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 27 July 2018
 */
class SharedPrefManager(private val mSharedPreferences: SharedPreferences) {

    /**
     * Checks whether the preferences contains a preference.
     *
     * @param key The name of the preference to check.
     * @return Returns true if the preference exists in the preferences,
     *         otherwise false.
     */
    public fun contains(key: String): Boolean {
        return mSharedPreferences.contains(key)
    }

    public fun getString(key: String): String? {
        return get(key, null)
    }

    /**
     * Retrieve a String value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a String.
     *
     **/
    public fun get(key: String, defValue: String?): String? {
        return mSharedPreferences.getString(key, defValue)
    }

    public fun getStringSet(key: String): MutableSet<String>? {
        return get(key, Collections.emptySet())
    }

    /**
     * Retrieve a set of String values from the preferences.
     *
     * <p>Note that you <em>must not</em> modify the set instance returned
     * by this call.  The consistency of the stored data is not guaranteed
     * if you do, nor is your ability to modify the instance at all.
     *
     * @param key The name of the preference to retrieve.
     * @param defValues Values to return if this preference does not exist.
     *
     * @return Returns the preference values if they exist, or defValues.
     * Throws ClassCastException if there is a preference with this name
     * that is not a Set.
     *
     */
    public fun get(key: String, defValues: MutableSet<String>): MutableSet<String> {
        val set = mSharedPreferences.getStringSet(key, defValues)
        if (set != null) return set
        return Collections.emptySet()
    }

    public fun getInt(key: String): Int {
        return get(key, 0)
    }

    /**
     * Retrieve an int value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * an int.
     *
     */
    public fun get(key: String, defValue: Int): Int {
        return mSharedPreferences.getInt(key, defValue)
    }

    public fun getLong(key: String): Long {
        return get(key, 0L)
    }

    /**
     * Retrieve a long value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a long.
     *
     */
    public fun get(key: String, defValue: Long): Long {
        return mSharedPreferences.getLong(key, defValue)
    }

    public fun getFloat(key: String): Float {
        return get(key, 0.0f)
    }

    /**
     * Retrieve a float value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a float.
     *
     */
    public fun get(key: String, defValue: Float): Float {
        return mSharedPreferences.getFloat(key, defValue)
    }

    public fun getBoolean(key: String): Boolean {
        return get(key, false)
    }

    /**
     * Retrieve a boolean value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a boolean.
     *
     */
    public fun get(key: String, defValue: Boolean): Boolean {
        return mSharedPreferences.getBoolean(key, defValue)
    }

    /**
     * Set a String value in the preferences editor, to be written back once
     * commit is called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.  Passing {@code null}
     *    for this argument is equivalent to calling {@link #remove(String)} with
     *    this key.
     */
    public fun put(key: String, value: String?) {
        mSharedPreferences.edit().putString(key, value).apply()
    }

    /**
     * Set a set of String values in the preferences editor, to be written
     * commit is called.
     *
     * @param key The name of the preference to modify.
     * @param values The set of new values for the preference.  Passing {@code null}
     *    for this argument is equivalent to calling {@link #remove(String)} with
     *    this key.
     */
    public fun put(key: String, values: MutableSet<String>?) {
        mSharedPreferences.edit().putStringSet(key, values).apply()
    }

    /**
     * Set an int value in the preferences editor, to be written back once
     * commit is called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     */
    public fun put(key: String, value: Int) {
        mSharedPreferences.edit().putInt(key, value).apply()
    }

    /**
     * Set a long value in the preferences editor, to be written back once
     * commit is called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     */
    public fun put(key: String, value: Long) {
        mSharedPreferences.edit().putLong(key, value).apply()
    }

    /**
     * Set a float value in the preferences editor, to be written back once
     * commit is called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     */
    public fun put(key: String, value: Float) {
        mSharedPreferences.edit().putFloat(key, value).apply()
    }

    /**
     * Set a boolean value in the preferences editor, to be written back
     * commit is called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     */
    public fun put(key: String, value: Boolean) {
        mSharedPreferences.edit().putBoolean(key, value).apply()
    }

    /**
     * Mark in the editor to remove <em>all</em> values from the
     * preferences.  Once commit is called, the only remaining preferences
     * will be any that you have defined in this editor.
     *
     * <p>Note that when committing back to the preferences, the clear
     * is done first, regardless of whether you called clear before
     * or after put methods on this editor.
     *
     * @return Returns true if the SharedPreferences clear successfully
     */
    public fun clear() {
        mSharedPreferences.edit().clear().apply()
    }

    /**
     * Mark in the editor that a preference value should be removed, which
     * will be done in the actual preferences once commit is called.
     *
     * <p>Note that when committing back to the preferences, all removals
     * are done first, regardless of whether you called remove before
     * or after put methods on this editor.
     *
     * @param key The name of the preference to remove.
     *
     * @return Returns true if the SharedPreferences remove key successfully
     */
    public fun remove(key: String) {
        mSharedPreferences.edit().remove(key).apply()
    }
}
