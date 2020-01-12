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
import com.google.firebase.firestore.core.FirestoreClient
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*
import kotlin.collections.ArrayList


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

        val msgRecycle: RecyclerView? = view?.findViewById(R.id.myRecycler1)
        msgRecycle?.layoutManager = LinearLayoutManager(this.context)
        //msgRecycle?.adapter = RecyclerAdapter()
        var listy:ArrayList<Message> = arrayListOf<Message>()

        db.collection("messages")
            .orderBy("createdAt")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.forEach { documentSnapshot ->
                        val message = documentSnapshot.toObject(Message::class.java)
                        //assign it to recycleview
                        listy.add(message)
                    }
                    msgRecycle?.adapter = RecyclerAdapter(listy.toList())

                }


                button_send.setOnClickListener {

                    val userText = edit_support.text.toString()
                    //val userName =
                    val user = FirebaseAuth.getInstance().currentUser
                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document((user?.uid).toString())
                        .get()
                        .addOnSuccessListener {documentSnapshot->
                            val usuario = documentSnapshot.toObject(com.example.gamecompanionenti.UserModel::class.java)

                            val userMessage = Message(text = userText, createdAt = Date(), userID = FirebaseAuth.getInstance().currentUser?.uid, userName = usuario?.username )
                            db.collection("messages").add(userMessage).addOnSuccessListener {


                            }.addOnFailureListener {
                                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG)
                                    .show()

                            }.addOnSuccessListener {
                                listy.add(userMessage)
                                msgRecycle?.adapter = RecyclerAdapter(listy.toList())
                                msgRecycle?.adapter?.notifyDataSetChanged()

                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
                        }


                }
            }
    }

}
