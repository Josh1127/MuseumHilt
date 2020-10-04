package com.eric.museumhilt.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.eric.museumhilt.R
import com.eric.museumhilt.model.Museum
import com.eric.museumhilt.viewmodel.MuseumViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_museum.*
import kotlinx.android.synthetic.main.layout_error.*

@AndroidEntryPoint
class MuseumActivity : AppCompatActivity() {

    private val museumViewModel: MuseumViewModel by viewModels()
    private lateinit var adapter: MuseumAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museum)

        setupViewModel()
        setupUI()
    }

    private fun setupViewModel() {
        adapter= MuseumAdapter(emptyList())
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter= adapter
    }

    private fun setupUI() {

        museumViewModel.museums.observe(this,renderMuseums)

        museumViewModel.isViewLoading.observe(this,isViewLoadingObserver)
        museumViewModel.onMessageError.observe(this,onMessageErrorObserver)
        museumViewModel.isEmptyList.observe(this,emptyListObserver)
    }

    private val renderMuseums= Observer<List<Museum>> {
        layoutError.visibility= View.GONE
        layoutEmpty.visibility=View.GONE
        adapter.update(it)
    }

    private val isViewLoadingObserver= Observer<Boolean> {
        val visibility=if(it)View.VISIBLE else View.GONE
        progressBar.visibility= visibility
    }

    private val onMessageErrorObserver= Observer<Any> {
        layoutError.visibility=View.VISIBLE
        layoutEmpty.visibility=View.GONE
        textViewError.text= "Error $it"
    }

    private val emptyListObserver= Observer<Boolean> {
        layoutEmpty.visibility=View.VISIBLE
        layoutError.visibility=View.GONE
    }

    override fun onResume() {
        super.onResume()
        museumViewModel.loadMuseums()
    }
}