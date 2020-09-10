package ru.zzemlyanaya.edocument.login

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.zzemlyanaya.edocument.App
import ru.zzemlyanaya.edocument.R
import ru.zzemlyanaya.edocument.afterTextChanged
import ru.zzemlyanaya.edocument.data.local.PrefsConst
import ru.zzemlyanaya.edocument.databinding.ActivityLoginBinding
import ru.zzemlyanaya.edocument.main.MainActivity


class LoginActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(LoginViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding
                = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        if(App.prefs.getPref(PrefsConst.PREF_KEEP_LOGIN) == true)
            goOnMain()

        viewModel.loginFormState.observe(this, Observer {
            val loginState = it ?: return@Observer

            binding.inputLogin.error = getString(loginState.usernameError)
            binding.inputPassword.error = getString(loginState.passwordError)
        })

        binding.butLogin.setOnClickListener {
            goOnMain()
        }

        binding.textLogin.afterTextChanged {
            viewModel.loginDataChanged(
                binding.textLogin.text.toString(),
                binding.textPassword.text.toString()
            )
        }

        binding.textPassword.apply {
            afterTextChanged {
                viewModel.loginDataChanged(
                    binding.textLogin.text.toString(),
                    binding.textPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->  {
                        if (binding.checkKeepLogin.isChecked)
                            App.prefs.setPref(PrefsConst.PREF_KEEP_LOGIN, true)
                        goOnMain()
                    }
//                        if(viewModel.loginFormState.value!!.isDataValid)
//                            login(
//                                binding.textLogin.text.toString().hashCode(),
//                                binding.textPassword.text.toString(),
//                                binding.checkKeepLogin.isChecked
//                            )
                }
                false
            }
        }

    }

    private fun goOnMain(){
        val intent = Intent(this, MainActivity::class.java)
        //val bundle: Bundle = Bundle().apply { putSerializable(USER, user) }
        //intent.putExtra(USER, bundle)
        startActivity(intent)
        finish()
    }

    private fun getString(id: Int?): String? {
        return if (id == null) null else getString(id)
    }
}