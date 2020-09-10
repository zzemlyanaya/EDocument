package ru.zzemlyanaya.edocument.data.local

import com.kryptoprefs.context.KryptoContext
import com.kryptoprefs.preferences.KryptoPrefs

object PrefsConst {
    const val PREFS_NAME = "NFCPrefs"
    const val PREF_KEEP_LOGIN = "keep login"
    const val PREF_UID = "uid"
    const val PREF_PERSONAL_UID = "personal uid"
}

class Prefs(prefs: KryptoPrefs): KryptoContext(prefs) {
    private val isKeepLogin = boolean(PrefsConst.PREF_KEEP_LOGIN, false)
    private val uid = string(PrefsConst.PREF_UID, "7gwpqUjaS6ZZffA9blglvYrcJBq1")
    private val personalUid = string(PrefsConst.PREF_PERSONAL_UID, "OMQwK5wupYvywgqYuYgDJjjXEBQyK6h1")

    fun setPref(key: String, value: Any){
        when(key){
            PrefsConst.PREF_KEEP_LOGIN -> isKeepLogin.put(value as Boolean)
            PrefsConst.PREF_UID -> uid.put(value as String)
            PrefsConst.PREF_PERSONAL_UID -> personalUid.put(value as String)
            else -> throw Exception("Unknown key!")
        }
    }

    fun getPref(key: String): Any = when(key) {
        PrefsConst.PREF_KEEP_LOGIN -> isKeepLogin.get()
        PrefsConst.PREF_UID -> uid.get()
        PrefsConst.PREF_PERSONAL_UID -> personalUid.get()
        else -> throw Exception("Unknown key!")
    }
}