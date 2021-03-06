package com.example.notesapp.globalData

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient

const val PREF_NAME : String = "LoginSharedPreference"

object Preferences {

    lateinit var sharedPreferences : SharedPreferences
    lateinit var editorForSharedPreferences : SharedPreferences.Editor
    lateinit var con: Context
    private var initialized = false

    fun initialize(con: Context) {
        if (!initialized) {
            initialized = true
            this.con = con
            sharedPreferences = con.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            editorForSharedPreferences = sharedPreferences.edit()
        }
    }

    var email: String?
        get() = sharedPreferences.getString("email", "")!!
        set(value) {
            editorForSharedPreferences.putString("email", value)
            editorForSharedPreferences.apply()
        }

    var loggedIn: Boolean
        get() = sharedPreferences.getBoolean("loggedIn", false)
        set(value) {
            editorForSharedPreferences.putBoolean("loggedIn", value)
            editorForSharedPreferences.apply()
        }

    var userName: String
        get() = sharedPreferences.getString("userName", "")!!
        set(value) {
            editorForSharedPreferences.putString("userName", value)
            editorForSharedPreferences.apply()
        }

}

class Global {

    companion object {

        //Here because it will be used from another fragment to sign out
        var mGoogleSignInClient: GoogleSignInClient? = null

        fun toast(
            stringToBeShowed : String,
            context : Context?
        ) {
            Toast.makeText(context, stringToBeShowed, Toast.LENGTH_SHORT).show()
        }

    }

}