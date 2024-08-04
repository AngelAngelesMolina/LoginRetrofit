package com.example.loginretrofit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.loginretrofit.databinding.ActivityMainBinding
import com.example.loginretrofit.Constants
import com.example.loginretrofit.retrofit.LoginResponse
import com.example.loginretrofit.retrofit.LoginService
import com.example.loginretrofit.retrofit.UserInfo
import com.google.gson.Gson
import com.google.gson.internal.GsonBuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.swType.setOnCheckedChangeListener { button, checked ->
            button.text = if (checked) getString(R.string.main_type_login)
            else getString(R.string.main_type_register)

            mBinding.btnLogin.text = button.text
        }

        mBinding.btnLogin.setOnClickListener {
            if (mBinding.swType.isChecked) login() else register()


        }

        mBinding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }


    private fun login() {
        val email = mBinding.etEmail.text.toString().trim()
        val password = mBinding.etPassword.text.toString().trim()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(LoginService::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val result = service.loginUser(UserInfo(email, password))

                updateUI("${Constants.TOKEN_PROPERTY}: ${result.token}")
            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when (it.code()) {
                        400 -> {
                            updateUI(getString(R.string.main_error_server))
                        }

                        else -> {
                            updateUI(getString(R.string.main_error_response))
                        }
                    }
                }
            }
        }
        /*
            service.login(UserInfo(email, password)).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    when (response.code()) {
                        200 -> {
                            val result = response.body()
                            updateUI("${Constants.TOKEN_PROPERTY}: ${result?.token}")
                        }

                        400 -> {
                            updateUI(getString(R.string.main_error_server))
                        }
                        else -> {
                            updateUI(getString(R.string.main_error_response))
                        }
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("RETROFIT", "Problemas con el servidor.")
                }

            })
            */
    }

    private fun register() {
        val email = mBinding.etEmail.text.toString().trim()
        val password = mBinding.etPassword.text.toString().trim()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(LoginService::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val result = service.registerUser(UserInfo(email, password))
                updateUI(
                    "${Constants.ID_PROPERTY}: ${result.id}, " +
                            " ${Constants.TOKEN_PROPERTY}: ${result.token}"
                )
            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when (it.code()) {
                        400 -> {
                            updateUI(getString(R.string.main_error_server))
                        }

                        else -> {
                            updateUI(getString(R.string.main_error_response))
                        }
                    }
                }
            }
        }
    }

    private suspend fun updateUI(result: String) = withContext(Dispatchers.Main) {
        mBinding.tvResult.visibility = View.VISIBLE
        mBinding.tvResult.text = result
    }
}