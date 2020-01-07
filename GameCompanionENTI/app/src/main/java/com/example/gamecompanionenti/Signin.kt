package com.example.gamecompanionenti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Signin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val btn3 = findViewById<Button>(R.id.button2)

        btn3.setOnClickListener {
            // Code here executes on main thread after user presses button
            val auth = FirebaseAuth.getInstance()

            auth.signInWithEmailAndPassword((findViewById<EditText>(R.id.editText4)).text.toString(), (findViewById<EditText>(R.id.editText5)).text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SignUpActivity", "createUserWithEmail:success")
                        val user = auth.currentUser
                        val context4 = this
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("SignUpActivity", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@Signin, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }


        }
    }
}
