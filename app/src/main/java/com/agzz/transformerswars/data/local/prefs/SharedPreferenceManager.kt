package com.agzz.transformerswars.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferenceStorage {
    var authToken: String
}

@Singleton
class SharedPreferencesManager @Inject constructor(@ApplicationContext context : Context): PreferenceStorage{

    companion object{
        const val PRF_NAME = "com.agzz.transformerswars"
        const val PRF_AUTH_TOKEN = "prf_auth_token"
    }

    private val prefs: Lazy<SharedPreferences> = lazy {
        context.applicationContext.getSharedPreferences(PRF_NAME, Context.MODE_PRIVATE)
    }

    override var authToken by StringPreference(prefs, PRF_AUTH_TOKEN, "")
}

class StringPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: String
) : ReadWriteProperty<Any, String> {

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return preferences.value.getString(name, defaultValue)!!
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        preferences.value.edit { putString(name, value) }
    }
}