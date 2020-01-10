package com.example.gamecompanionenti


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_support.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class SupportFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recycleAdapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_support, container, false)
    }

    //private val adapter = RecyclerAdapter(emptyList());

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseFirestore.getInstance()

        db.collection("messages").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.forEach { documentSnapshot ->
                        val message = documentSnapshot.toObject(Message::class.java)
                        //assign it to recycleview
                    }

                }


                button_send.setOnClickListener {

                    val userText = edit_support.text.toString()
                    val userMessage = Message(text = userText, createdAt = Date(), userID = FirebaseAuth.getInstance().currentUser?.uid)
                    db.collection("messages").add(userMessage).addOnSuccessListener {


                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG)
                            .show()

                    }
                }
            }
    }

}
