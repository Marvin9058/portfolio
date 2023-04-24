package com.howl.howlstagram_f16.navigation



import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.FacebookSdk
import com.google.firebase.database.*
import com.howl.howlstagram_f16.R
import com.howl.howlstagram_f16.adapter.RatingAlcohol
import com.howl.howlstagram_f16.adapter.RtAlcoholAdapter
import kotlinx.android.synthetic.main.fragment_evaluation.*

class EvaluationFragment : Fragment() {

    private var rt_Alcohol = ArrayList<RatingAlcohol>()

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_evaluation,container,false)

        return view
    }
    override fun onStart() {
        super.onStart()
        getRvServerData()
    }

    private fun getRvServerData(){
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val ratingRef : DatabaseReference = database.getReference("ratingBar")
        ratingRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i: DataSnapshot in snapshot.children) {

                    val rb_alcohol: RatingAlcohol? = i.getValue(RatingAlcohol::class.java)
                    rt_Alcohol.add(rb_alcohol!!)

                }
                setRecyclerView()
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(FacebookSdk.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setRecyclerView() {
        rv_alcohol.adapter = context?.let { RtAlcoholAdapter(it, rt_Alcohol) }
        rv_alcohol.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_alcohol.setHasFixedSize(true)
    }

}

