package com.hackaprende.dogedex.doglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackaprende.dogedex.Dog
import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.databinding.ActivityDogListBinding
import com.hackaprende.dogedex.dogdetail.DogDetailActivity
import com.hackaprende.dogedex.dogdetail.DogDetailActivity.Companion.DOG_KEY

class DogListActivity : AppCompatActivity() {

    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingWheel = binding.loadingWheel

        val recycler = binding.dogRecycler
        recycler.layoutManager = LinearLayoutManager(this)

        val adapter = DogAdapter()

        adapter.setOnItemClickListener {
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }
        recycler.adapter = adapter

        dogListViewModel.dogList.observe(this) {
            dogList ->
            adapter.submitList(dogList)
        }

        dogListViewModel.status.observe(this) {
            status ->

            when(status) {
                ApiResponseStatus.LOADING -> {
                    loadingWheel.visibility = View.VISIBLE
                }
                ApiResponseStatus.ERROR -> {
                    loadingWheel.visibility = View.GONE
                    Toast.makeText(this, "Error al descargar datos", Toast.LENGTH_SHORT).show()
                }
                ApiResponseStatus.SUCCESS -> {
                    loadingWheel.visibility = View.GONE
                }
                else -> {
                    loadingWheel.visibility = View.GONE
                    Toast.makeText(this, "Status desconocido", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}