package com.bcaf.roomdbtest

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.bcaf.roomdbtest.adapter.roomDBAdapter
import com.bcaf.roomdbtest.database.DummyDatabase
import com.bcaf.roomdbtest.model.PostDummyData
import com.bcaf.roomdbtest.model.ResponsePostDummyData
import com.bcaf.roomdbtest.service.NetworkConfig
import kotlinx.android.synthetic.main.activity_post_data_dummy.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostDataDummy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_data_dummy)
        txtTags.setOnClickListener(View.OnClickListener {

            createMultipleSelectDialog()
        })

        loadData()
        btnPost.setOnClickListener(View.OnClickListener { v->

            val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo =  cm.activeNetworkInfo
            if(networkInfo !=null && networkInfo.isConnected == true){
                postData()
            }else{

                GlobalScope.launch {
                    var dummyData = PostDummyData(0,"60d0fe4f5311236168a109f4",
                        txtUrlImage.text.toString(), txtText.text.toString(), tstLikes.text.toString().toInt(), selectionList)
                    DummyDatabase.getInstance(applicationContext).dummyDao().insertDummy(dummyData)
                    runOnUiThread({
                        Toast.makeText(applicationContext,"Maaf jaringan tidak ada,data tersimpan", Toast.LENGTH_LONG).show()
                        loadData()
                    })
                }
            }
            txtText.setText("")
            txtUrlImage.setText("")
            tstLikes.setText("")
            txtTags.setText("")
        })
    }

    fun loadData(){
        GlobalScope.launch {
            val data: List<PostDummyData> = DummyDatabase.getInstance(applicationContext).dummyDao().getAll()
            Log.d("Data", data.toString())
            runOnUiThread({
                var adapters = roomDBAdapter(data)
                recyclerViewResult.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = adapters
                }
                recyclerViewResult.adapter?.notifyDataSetChanged()
            })
            }
        }

    fun postData(){
        var dummyData = PostDummyData(0,"60d0fe4f5311236168a109f4",
            txtUrlImage.text.toString(),txtText.text.toString(), tstLikes.text.toString().toInt(),selectionList)

        NetworkConfig().getServiceDummy().postData(dummyData).enqueue( object : Callback<ResponsePostDummyData>{
            override fun onResponse(
                call: Call<ResponsePostDummyData>,
                response: Response<ResponsePostDummyData>
            ) {
                if (response.isSuccessful){
                    Toast.makeText(applicationContext,response.message(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponsePostDummyData>, t: Throwable) {
                Log.e("error post", t.printStackTrace().toString())
            }
        })
    }

    var selectionList = mutableListOf<String>()
    val listItem = arrayOf("Movies","Actor","Fun")
    val listChecked = booleanArrayOf(false,false,false)

    fun createMultipleSelectDialog(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Tags")

        builder.setMultiChoiceItems(listItem,listChecked){
                dialog,which,ischecked ->
            listChecked[which] = ischecked
        }

        builder.setCancelable(false)
        builder.setNegativeButton("Cancel"){
                dialog,which ->
        }
        builder.setPositiveButton("Submit"){
                dialog,which ->

            selectionList.clear()

            for((index,value) in listChecked.withIndex()){
                if(value){
                    selectionList.add(listItem[index])
                }
            }
            txtTags.setText("")
            for (listItem in selectionList){
                txtTags.append("${listItem}, ")
            }
            txtTags.setText(txtTags.text.toString().dropLast(2))
        }

        builder.create()

        val alertDialog = builder.create()
        alertDialog.show()
    }
}

