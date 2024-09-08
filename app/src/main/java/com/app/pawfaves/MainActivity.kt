package com.app.pawfaves

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.pawfaves.databinding.ActivityHomeBinding
import com.app.pawfaves.model.data.remote.RetrofitConfig
import com.app.pawfaves.model.data.remote.repository.PawFavRepositoryImpl
import com.app.pawfaves.utils.Constants.BREED_AKITA
import com.app.pawfaves.utils.Constants.BREED_BULLDOG
import com.app.pawfaves.utils.Constants.BREED_HOUND
import com.app.pawfaves.utils.Constants.BREED_KEY
import com.app.pawfaves.utils.Constants.BREED_LABRADOR
import com.app.pawfaves.view.breed.BreedListActivity
import com.app.pawfaves.view.favorite.FavoriteActivity
import com.app.pawfaves.viewmodel.HomeViewModel
import com.app.pawfaves.viewmodel.HomeViewModelFactory
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private val spinnerValues = mutableListOf<String>()
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private var isFirstSelection = true

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(PawFavRepositoryImpl(RetrofitConfig.getApiService()))
    }

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        setupSpinner()
    }

    private fun setupSpinner() {
        spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerValues)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = binding.actionBarSpinner
        spinner.adapter = spinnerAdapter

        getBreedListForSpinner()
        itemSelectorConfiguration()
    }

    private fun getBreedListForSpinner() {
        homeViewModel.getAllBreeds()
        homeViewModel.allBreedsList.observe(this) { list ->
            if (list != null) {
                spinnerValues.addAll(list)
                spinnerAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun itemSelectorConfiguration() {
        val spinner = binding.actionBarSpinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (isFirstSelection) {
                    isFirstSelection = false
                    return
                }
                val selectedValue = spinnerValues[position]
                onClickBreeds(selectedValue)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Not implemented
            }
        }
    }

    private fun setListeners() {
        binding.randomDog.setOnClickListener { onClickRandom() }
        binding.labrador.setOnClickListener { onClickBreeds(BREED_LABRADOR) }
        binding.akita.setOnClickListener { onClickBreeds(BREED_AKITA) }
        binding.bulldog.setOnClickListener { onClickBreeds(BREED_BULLDOG) }
        binding.hound.setOnClickListener { onClickBreeds(BREED_HOUND) }
        binding.toolbarFavorite.setOnClickListener { onClickFavorite() }
    }

    private fun onClickBreeds(breed: String) {
        val intent = Intent(this, BreedListActivity::class.java)
        intent.putExtra(BREED_KEY, breed)
        startActivity(intent)
    }

    private fun onClickFavorite() {
        intent = Intent(this, FavoriteActivity::class.java)
        startActivity(intent)
    }

    private fun onClickRandom() {
        homeViewModel.getRandom()
        val randomDogImageView = binding.randomDog
        homeViewModel.randomDog.observe(this) { breed ->
            if (breed != null) {
                binding.progressBar.visibility = View.GONE
                binding.randomDog.visibility = View.VISIBLE
                Picasso.get().load(breed).into(randomDogImageView)
            } else {
                binding.progressBar.visibility = View.VISIBLE
                binding.randomDog.visibility = View.GONE
            }
        }
    }
}