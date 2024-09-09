package com.app.pawfaves.view.breed

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.pawfaves.databinding.ActivityBreedListBinding
import com.app.pawfaves.model.data.local.PawFavesSharedPreferencesImpl
import com.app.pawfaves.model.data.remote.RetrofitConfig
import com.app.pawfaves.model.data.remote.repository.PawFavRepositoryImpl
import com.app.pawfaves.utils.Constants.BREED_KEY
import com.app.pawfaves.utils.Constants.BREED_LABRADOR
import com.app.pawfaves.view.favorite.FavoriteActivity
import com.app.pawfaves.viewmodel.BreedListVieModelFactory
import com.app.pawfaves.viewmodel.BreedListViewModel

class BreedListActivity : AppCompatActivity() {

    private lateinit var breed: String

    private val breedListViewModel: BreedListViewModel by viewModels {
        BreedListVieModelFactory(
            PawFavRepositoryImpl(RetrofitConfig.getApiService()),
            PawFavesSharedPreferencesImpl(this)
        )
    }

    private val binding: ActivityBreedListBinding by lazy {
        ActivityBreedListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListener()
        getSelectedBreed()
        configRecyclerView()
        configToolbar()
    }

    override fun onResume() {
        super.onResume()
        breedListViewModel.verifyIsFavorite()
    }

    private fun setListener() {
        binding.fabFavorites.setOnClickListener {
            intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getSelectedBreed() {
        breed = intent.getStringExtra(BREED_KEY) ?: BREED_LABRADOR
    }

    private fun configToolbar() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun configRecyclerView() {
        binding.progressBar.visibility = View.VISIBLE
        breedListViewModel.getListByBreed(breed)
        var breedList = breedListViewModel.byBreedsList.value
        val adapter =
            BreedListAdapter(breedList ?: emptyList()) { breedListViewModel.setFavoriteItem(it) }
        binding.recyclerBreeds.adapter = adapter

        return breedListViewModel.byBreedsList.observe(this) { list ->
            if (!list.isNullOrEmpty()) {
                adapter.setList(list)
                binding.progressBar.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }
}
