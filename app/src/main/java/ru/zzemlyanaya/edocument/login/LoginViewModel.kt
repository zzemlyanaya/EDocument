package ru.zzemlyanaya.edocument.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.zzemlyanaya.edocument.R
import ru.zzemlyanaya.edocument.data.Repository
import ru.zzemlyanaya.edocument.data.local.PrefsConst.PREF_KEEP_LOGIN
import ru.zzemlyanaya.edocument.data.local.PrefsConst.PREF_PERSONAL_UID
import ru.zzemlyanaya.edocument.data.local.PrefsConst.PREF_UID


class LoginViewModel : ViewModel() {
    private val repository = Repository()

    private val _loginForm = MutableLiveData(LoginFormState())
    val loginFormState: LiveData<LoginFormState> = _loginForm


//    fun login(isKeepLogin: Boolean) = liveData(Dispatchers.IO) {
//        saveLogin(isKeepLogin, id, password.hashCode())
//        emit(Resource.loading(data = null))
//        try {
//            val result: Result<User> = repository.login(id, password.hashCode(), isKeepLogin)
//            if (result.error == null)
//                emit(Resource.success(data = result.data))
//            else
//                emit(Resource.error(data = null, message = result.error))
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Ошибка сервера! Попробуйте снова."))
//        }
//    }

    private fun saveLogin(isKeep: Boolean, id: Int, uid: Long, personalUid: Long){
        repository.setPref(PREF_KEEP_LOGIN, isKeep)
        if (isKeep) {
            repository.setPref(PREF_UID, uid)
            repository.setPref(PREF_PERSONAL_UID, personalUid)
        }
    }

    fun loginDataChanged(email: String, password: String) {
        _loginForm.value =
            LoginFormState(usernameError = validateEmail(email),
                passwordError = validatePassword(password),
                isDataValid = isAllDataValid(email, password))
    }

    private fun isAllDataValid(email: String, password: String)
            = email.isNotBlank() and (password.length >= 8)

    private fun validateEmail(email: String) =
        if (email.isNotBlank()) null else R.string.invalid_email


    private fun validatePassword(password: String) =
        if (password.length >= 8) null else R.string.invalid_password
}