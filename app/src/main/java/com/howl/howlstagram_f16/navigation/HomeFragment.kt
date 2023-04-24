package com.howl.howlstagram_f16.navigation

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.facebook.FacebookSdk.getApplicationContext
import com.google.firebase.database.*
import com.howl.howlstagram_f16.R
import com.howl.howlstagram_f16.adapter.HomeAlcoholAdapter
import com.howl.howlstagram_f16.adapter.HomeViewPagerAdapter
import com.howl.howlstagram_f16.model.Alcohol
import com.howl.howlstagram_f16.model.EventPage
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_home_vp.*


class HomeFragment : Fragment() {
    lateinit var pref: SharedPreferences

    lateinit var toast: Toast

    private var backKeyPressedTime: Long = 0

    private var alcoholList = ArrayList<Alcohol>()
    private var eventPageList = ArrayList<EventPage>()
    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    var view = LayoutInflater.from(activity).inflate(R.layout.fragment_home,container,false)

    return view
    }

    override fun onStart() {
        super.onStart()
        getViewPagerData()

        makgeolli_btn.setOnClickListener {
            getRvServerData("Makgeolli")
        }

        yakju_btn.setOnClickListener {
            getRvServerData("yakju") // 아직 데이터 없음
        }

        distilledBeverage_btn.setOnClickListener {
            getRvServerData("distilledBeverage") // 아직 데이터 없음
        }

        fruitWine_btn.setOnClickListener {
            getRvServerData("fruitWine") // 아직 데이터 없음
        }

        etc_btn.setOnClickListener {
            getRvServerData("etc") // 아직 데이터 없음
        }
        makgeolli_btn.performClick()
    }
    private fun getViewPagerData(){
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val eventRef : DatabaseReference = database.getReference("eventPage")

        eventRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i : DataSnapshot in snapshot.children) {
                    val page: EventPage? = i.getValue(EventPage::class.java)
                    eventPageList.add(page!!)
                }
                setViewPager()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getRvServerData(alcoholText : String){

        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val alcoholRef : DatabaseReference = database.getReference(alcoholText)
        alcoholRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                alcoholList.clear()
                for (i: DataSnapshot in snapshot.children) {
                    val alcohol: Alcohol? = i.getValue(Alcohol::class.java)
                    alcoholList.add(alcohol!!)
                }
                setRecyclerView()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setRecyclerView() {
        alcohol_item_rv.adapter = context?.let { activity?.let { it1 ->
            HomeAlcoholAdapter(it,alcoholList,
                it1
            )
        } }
        alcohol_item_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        alcohol_item_rv.setHasFixedSize(true)
    }

    private fun setViewPager(){
        image_slide.adapter = context?.let { HomeViewPagerAdapter(it, eventPageList) }
        image_slide.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        dots_indicator.setViewPager2(image_slide)
    }


}
