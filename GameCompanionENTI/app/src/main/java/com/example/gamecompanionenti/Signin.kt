package com.example.gamecompanionenti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.GoogleAuthProvider

class Signin : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var gso: GoogleSignInOptions
    val rcsignin: Int = 1
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val btn3 = findViewById<Button>(R.id.button2)
        val btn9 = findViewById<SignInButton>(R.id.gmailsignin)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

        btn3.setOnClickListener {
            // Code here executes on main thread after user presses button

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

        btn9.setOnClickListener{
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent,rcsignin)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==rcsignin){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try{
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account!!)

        }catch(e: ApiException){
            Toast.makeText(this, e.toString(),Toast.LENGTH_LONG).show()
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {


        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user

                }

                // ...
            }
    }
}
