package ru.zzemlyanaya.edocument.data

import ru.zzemlyanaya.edocument.App

class Repository {
    private val api = App.api
    private val prefs = App.prefs

    // server api part
    fun login() = api.login()

    fun getDataByUserID() = api.getDataByUserID("sdkfhdsk", App.readerID)


    // prefs part
    fun setPref(key: String, value: Any) {
        prefs.setPref(key, value)
    }

    fun getPref(key: String) = prefs.getPref(key)


    // local db part
}