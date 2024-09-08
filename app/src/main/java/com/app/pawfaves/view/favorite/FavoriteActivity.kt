package com.app.pawfaves.view.favorite

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.pawfaves.databinding.ActivityFavoriteListBinding
import com.app.pawfaves.model.data.local.PawFavesSharedPreferencesImpl
import com.app.pawfaves.viewmodel.FavoriteViewModel
import com.app.pawfaves.viewmodel.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(PawFavesSharedPreferencesImpl(this))
    }

    private val binding: ActivityFavoriteListBinding by lazy {
        ActivityFavoriteListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configRecyclerView()
        configToolbar()
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.verifyIsFavorite()
    }

    private fun configToolbar() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun configRecyclerView() {
        binding.progressBar.visibility = View.VISIBLE
        val adapter = FavoriteAdapter(emptyList()) { favoriteViewModel.setFavoriteItem(it) }
        binding.recyclerFavoriteBreeds.adapter = adapter
        favoriteViewModel.favoriteBreedsList.observe(this) { list ->
            adapter.setList(list)
            binding.progressBar.visibility = View.GONE
            if (list.isNotEmpty()) {
                binding.textEmptyFavorites.visibility = View.GONE
            } else {
                binding.textEmptyFavorites.visibility = View.VISIBLE
            }
        }
        favoriteViewModel.verifyIsFavorite()
    }
}
