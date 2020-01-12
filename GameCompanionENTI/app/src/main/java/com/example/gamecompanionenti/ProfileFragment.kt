package com.example.gamecompanionenti


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        initUI()
        handlePopup()
    }

    private fun initUI(){
        if(FirebaseAuth.getInstance().currentUser!=null){
            //TODO: Show profile
            signoutbutton.setOnClickListener {

                FirebaseAuth.getInstance().signOut()

                val fragmentTransaction = fragmentManager?.beginTransaction()
                val fragment = HomeFragment()
                fragmentTransaction?.replace(R.id.fragment_container, fragment)
                fragmentTransaction?.commit()
                val context = this
            }

            val user = FirebaseAuth.getInstance().currentUser
            FirebaseFirestore.getInstance()
                .collection("users")
                .document((user?.uid).toString())
                .get()
                .addOnSuccessListener {documentSnapshot->
                    val usuario = documentSnapshot.toObject(com.example.gamecompanionenti.UserModel::class.java)
                    contnetName.text = usuario?.username
                    contnetEmail.text = usuario?.email
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun handlePopup(){
        usersettings.setOnClickListener {
            val builder = AlertDialog.Builder(view?.context)
            val builderView = layoutInflater.inflate(R.layout.custom_dialog,null)
            builder.setView(builderView)
            builder.setCancelable(false)
            builder.setPositiveButton("OK",{ dialogInterface: DialogInterface, i: Int -> })
            builder.show()
        }

    }


}
