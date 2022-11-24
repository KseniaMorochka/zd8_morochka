package com.example.orgonizer1


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText

const val APP_PREFERENS = "APP_PREFERENS"
const val KEY_VALUE_PREF_EMAIL = "login"
const val KEY_VALUE_PREF_Pass ="PASS"
class MainActivity : AppCompatActivity() {
    private lateinit var  button: Button
    private lateinit var  email : AppCompatEditText
    private lateinit var  password : EditText
    private lateinit var preferens : SharedPreferences
    var WentOne :Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        email = findViewById(R.id.user_name)
        password = findViewById(R.id.user_password)
        preferens =getSharedPreferences(APP_PREFERENS, Context.MODE_PRIVATE)
        WentOne =preferens.getBoolean("WentOne",false)
        if(WentOne == true){
            email.setText(preferens.getString(KEY_VALUE_PREF_EMAIL,"null"))
        }
        button.setOnClickListener {

            if (WentOne == true && password.text.toString().isNotEmpty()) {
                var pas = preferens.getString(KEY_VALUE_PREF_Pass, "def")
                if (password.text.toString() == pas) {
                    var inten = Intent(this, SecondActivity::class.java);
                    startActivity(inten)
                } else {
                    var toast = Toast.makeText(this, "Пароль неверный", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.BOTTOM, 0, 30)
                    toast.show()
                }
            }
            else if (email.text.toString().isNotEmpty() && password.text.toString().isNotEmpty() && WentOne == false) {
                preferens.edit()
                    .putString(KEY_VALUE_PREF_EMAIL, email.text.toString())
                    .putString(KEY_VALUE_PREF_Pass, password.text.toString())
                    .putBoolean("WentOne", true)
                    .apply()

            } else {
                var toast = Toast.makeText(
                    this,
                    "Поля не заполнены",
                    Toast.LENGTH_SHORT
                )
                toast.setGravity(Gravity.BOTTOM, 0, 30)
                toast.show()
            }
        }
    }
}