package com.example.gamecompanionenti


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_support.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    lateinit var mAdView : AdView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val newsRecycle: RecyclerView? = view?.findViewById(R.id.newsRecycle)
        newsRecycle?.layoutManager = LinearLayoutManager(this.context)

        val db = FirebaseFirestore.getInstance()
        var listSy:ArrayList<News> = arrayListOf<News>()

        db.collection("news")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.forEach { documentSnapshot ->
                        val newsNode = documentSnapshot.toObject(News::class.java)
                        //assign it to recycleview
                        listSy.add(newsNode)
                    }
                    newsRecycle?.adapter = NewsAdapter(listSy.toList())

                }
            }

        //val newsRecycle: RecyclerView? = view?.findViewById(R.id.newsRecycle)
        //newsRecycle?.layoutManager = LinearLayoutManager(this.context)
        //newsRecycle?.adapter = NewsAdapter(listSy)

    }

}
