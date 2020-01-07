package com.example.gamecompanionenti


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_support.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class SupportFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_support, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseFirestore.getInstance()

        db.collection("messages").get()
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    task.result?.forEach {
                            documentSnapshot ->
                        val message = documentSnapshot.toObject(Message::class.java)
                        //assign it to recycleview
                    }
                }

            }


        button_send.setOnClickListener {

            val userText = edit_support.text.toString()
            val userMessage = Message(text = userText, createdAt = Date())
            db.collection("messages").add(userMessage).addOnSuccessListener {
                    task ->

            }.addOnFailureListener {
                    task->

            }

        }

    }


}
