package com.example.gamecompanionenti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_signup.*
import android.R.attr.button
import android.R.attr.onClick
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text


class Signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val btn = findViewById<Button>(R.id.button)
        val btn2 = findViewById<Button>(R.id.button3)

        btn.setOnClickListener {
            // Code here executes on main thread after user presses button
            val auth = FirebaseAuth.getInstance()

            auth.createUserWithEmailAndPassword((findViewById<EditText>(R.id.editText2)).text.toString(), (findViewById<EditText>(R.id.editText3)).text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SignUpActivity", "createUserWithEmail:success")
                        val user = auth.currentUser
                        val context3 = this;
                        val db = FirebaseFirestore.getInstance()
                        val uUser = hashMapOf(
                            "username" to (findViewById<EditText>(R.id.editText)).text.toString()
                        )
                        db.collection("Users")
                            .add(uUser)
                            .addOnSuccessListener { documentReference ->
                                Log.d("SignUpActivity", "Username added: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w("SignUpActivity", "Error adding username", e)
                            }

                        startActivity(Intent(context3, MainActivity::class.java))


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("SignUpActivity", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@Signup, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }


        }

        btn2.setOnClickListener{
            val context2 = this
            startActivity(Intent(context2, Signin::class.java))

        }
    }
}

