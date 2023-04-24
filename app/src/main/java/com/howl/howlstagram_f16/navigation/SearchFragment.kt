package com.howl.howlstagram_f16.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.FacebookSdk
import com.facebook.FacebookSdk.getApplicationContext
import com.google.firebase.database.*
import com.google.zxing.integration.android.IntentIntegrator
import com.howl.howlstagram_f16.R
import com.howl.howlstagram_f16.adapter.SearchRvAlcoholAdapter
import com.howl.howlstagram_f16.model.Alcohol
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.fragment_user.view.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SearchFragment : Fragment() {
    var typeList = ArrayList<String>()
    var typeIndex = 0
    var alcoholSearch: Alcohol? = null
    var alcoholSearchList = ArrayList<Alcohol>()
    var searchSuccessList: ArrayList<Alcohol>? = ArrayList<Alcohol>()
    var position = 0
    var searchCheck = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_search,container,false)

        view?.btn_qr?.setOnClickListener {
            IntentIntegrator(activity).initiateScan()
        }
        view?.search_button?.setOnClickListener {

            if (search_edit.length() < 1) {
                Toast.makeText(context, "검색어를 입력 후 클릭해주세요", Toast.LENGTH_SHORT).show()
            } else {
                empty_layout.visibility = View.GONE
                loading_circle.visibility = View.VISIBLE
                notFound_layout.visibility = View.INVISIBLE
                typeIndex = 0
                position = 0
                searchCheck = false
                alcoholSearchList.clear()
                searchSuccessList?.clear()
                getSearchData(search_edit.text.toString(), typeIndex)
            }

        }

        return view
    }


    private fun getSearchData(searchInput: String, index: Int) {

        typeList.apply {
            add("yakju")
            add("Makgeolli")
            add("etc")
            add("fruitWine")
            add("distilledBeverage")
        }

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val alcoholRef: DatabaseReference = database.getReference(typeList[index])
        alcoholRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for (i: DataSnapshot in snapshot.children) {
                    alcoholSearch = i.getValue(Alcohol::class.java)
                    alcoholSearchList.add(alcoholSearch!!)

                    Log.d("로그", alcoholSearchList[position].name.toString())

                    if (searchInput in alcoholSearchList[position].name.toString() ||
                        searchInput in alcoholSearchList[position].type.toString() ||
                        searchInput in alcoholSearchList[position].point.toString()
                    ) {
                        loading_circle.visibility = View.INVISIBLE
                        notFound_layout.visibility = View.INVISIBLE
                        searchCheck = true
                        searchSuccessList!!.add(alcoholSearchList[position])
                    }
                    position++
                }


                if (searchCheck && index == 4) {
                    rv_search.visibility = View.VISIBLE
                    setSearchRecyclerView(searchSuccessList!!)
                }


                if (index == 4 && !searchCheck) {
                    rv_search.visibility = View.INVISIBLE
                    loading_circle.visibility = View.INVISIBLE
                    notFound_layout.visibility = View.VISIBLE

                } else if (index < 4) {
                    getSearchData(searchInput, ++typeIndex)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(FacebookSdk.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun setSearchRecyclerView(searchSuccessList: ArrayList<Alcohol>) {
        rv_search.adapter = context?.let { activity?.let { it1 ->
            SearchRvAlcoholAdapter(it, searchSuccessList,
                it1
            )
        } }
        rv_search.layoutManager = LinearLayoutManager(context)
    }

    // Barcode , QR
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                Toast.makeText(getApplicationContext(), "다른 QR코드나 바코드를 찍어주세요", Toast.LENGTH_LONG).show()
            } else {
//                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                Toast.makeText(getApplicationContext(), "참이슬:이천공장", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

        if (result.contents == "8801048951017") {
            activity?.let {
                val intent = Intent(context, SearchMapActivity::class.java)
                startActivity(intent)
            }
        }
//        else {
//            Toast.makeText(this, "다른 QR코드나 바코드를 찍어주세요", Toast.LENGTH_LONG).show()
//        }
    }


}