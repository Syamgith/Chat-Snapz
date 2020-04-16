package com.syamgith.chatsnapz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    var emailEditText: EditText? = null
    var passewordEditText: EditText? = null
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.emailEditText)
        passewordEditText = findViewById(R.id.passwordEditText)

        if (mAuth.currentUser != null) {
            logIn()
        }

    }

    fun goClicked(view: View) {
        //check if we can login the user
        mAuth.signInWithEmailAndPassword(emailEditText?.text.toString(), emailEditText?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    logIn()
                } else {
                    //Sign  up the user
                    mAuth.createUserWithEmailAndPassword(emailEditText?.text.toString(), emailEditText?.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                //add to data base
                                logIn()
                            } else {
                                Toast.makeText(this,"Login failed, try again.", Toast.LENGTH_SHORT).show()
                            }
                        }

                }
            }
    }

    fun logIn() {
        //move to next activity
        val intent = Intent(this,SnapsActivity::class.java)
        startActivity(intent)
    }

}
