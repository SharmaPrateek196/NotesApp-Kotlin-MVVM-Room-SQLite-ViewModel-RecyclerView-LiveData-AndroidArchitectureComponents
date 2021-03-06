package com.example.notesapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.notesapp.R
import com.example.notesapp.globalData.Global
import com.example.notesapp.globalData.Preferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val SIGN_IN_REQ_CODE : Int = 1
    private val LOG_TAG : String = "GoogleSignInTag"
    private lateinit var trasaction : FragmentTransaction
    private lateinit var notesFragment: NotesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize Shared Preferences
        Preferences.initialize(applicationContext)

        if( isLoggedIn() ) {
            Global.toast(getString(R.string.welcome_back).
                plus(" ").
                plus(Preferences.userName),
                applicationContext
            )
            goToNotesFragment()
        } else {
            googleSignInSetup()
        }

    }

    private fun isLoggedIn(): Boolean {
        if(Preferences.loggedIn)
            return true
        return false
    }

    private fun googleSignInSetup() {
        val gso = GoogleSignInOptions.Builder(
             GoogleSignInOptions.DEFAULT_SIGN_IN
            ).requestEmail()
            .build()

        Global.mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        btn_gsignin.setOnClickListener{
            val intent = Global.mGoogleSignInClient!!.signInIntent
            startActivityForResult(intent, SIGN_IN_REQ_CODE)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQ_CODE) {
            val task = GoogleSignIn
                .getSignedInAccountFromIntent(data)

            try {
                //This object holds the newly logged-in user's google account details
                val account : GoogleSignInAccount?
                        = task.getResult(ApiException::class.java)
                Global.toast(
                    getString(R.string.welcome)
                    .plus(" ")
                    .plus(account?.displayName),
                    applicationContext
                )

                //Save name and email in shared pref
                Preferences.loggedIn = true
                Preferences.email = account?.email
                Preferences.userName = account?.
                    displayName
                    ?: getString(R.string.default_username)

                goToNotesFragment()
            } catch (e: ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                Log.e(LOG_TAG,"signInResult:failed code=" + e.statusCode)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.optionSignOut) {
            signOut()
            popAllFragmentsFromStack()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun popAllFragmentsFromStack() {
        supportFragmentManager
            .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun signOut() {
        Global.mGoogleSignInClient?.signOut()
        Global.mGoogleSignInClient?.revokeAccess()
        Global.toast(getString(R.string.success_sign_out), this@MainActivity)
        Preferences.loggedIn = false
        Preferences.email = ""
        Preferences.userName = ""
    }

    private fun goToNotesFragment() {
        notesFragment = NotesFragment()

        trasaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main_activity, notesFragment)
            .addToBackStack(null)

        trasaction.commit()
    }

}