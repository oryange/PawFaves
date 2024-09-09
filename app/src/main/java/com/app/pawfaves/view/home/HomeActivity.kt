package com.app.pawfaves.view.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.pawfaves.R
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

class HomeActivity : AppCompatActivity() {
    private val spinnerValues = mutableListOf<String>()

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(PawFavRepositoryImpl(RetrofitConfig.getApiService()))
    }

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupObservers()
        setListeners()
        setupSpinner()
        homeViewModel.getAllBreeds()
    }

    private fun setupObservers() {
        homeViewModel.ageResult.observe(this) { age ->
            binding.ageResultTextView.text = age
        }

        homeViewModel.randomDog.observe(this) { breed ->
            if (!breed.isNullOrEmpty()) {
                binding.progressBar.visibility = View.GONE
                binding.randomDog.visibility = View.VISIBLE
                Picasso.get().load(breed).into(binding.randomDog)
            } else {
                binding.progressBar.visibility = View.VISIBLE
                binding.randomDog.visibility = View.GONE
            }
        }
    }

    private fun setListeners() {

        val breedClickListener = View.OnClickListener { view ->
            val breed = when (view.id) {
                R.id.labrador -> BREED_LABRADOR
                R.id.akita -> BREED_AKITA
                R.id.bulldog -> BREED_BULLDOG
                R.id.hound -> BREED_HOUND
                else -> null
            }
            breed?.let { onClickBreeds(it) }
        }

        binding.randomDog.setOnClickListener { homeViewModel.getRandom() }
        binding.labrador.setOnClickListener(breedClickListener)
        binding.akita.setOnClickListener(breedClickListener)
        binding.bulldog.setOnClickListener(breedClickListener)
        binding.hound.setOnClickListener(breedClickListener)
        binding.fabFavorites.setOnClickListener { onClickFavorite() }
        binding.calculateButton.setOnClickListener {
            val age = binding.ageEditText.text.toString()
            homeViewModel.ageCalculator(age)
        }
    }

    private fun setupSpinner() {
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerValues)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.actionBarSpinner.adapter = spinnerAdapter


        homeViewModel.allBreedsList.observe(this) { list ->
            if (!list.isNullOrEmpty()) {
                spinnerAdapter.clear()
                spinnerValues.addAll(list)
                spinnerAdapter.notifyDataSetChanged()
            }
        }

        var isFirstSelection = true
        binding.actionBarSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
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
}