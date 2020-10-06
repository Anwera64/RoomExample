package com.anwera97.roomexample.presentation.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import com.anwera97.roomexample.R
import com.anwera97.roomexample.data.entites.CountryWithCities
import com.anwera97.roomexample.databinding.FragmentRecyclerViewBinding
import com.anwera97.roomexample.util.SwipeToDeleteCallback

class CityFragment : Fragment() {

    private val args: CityFragmentArgs by navArgs()
    private val viewModel: CityViewModel by activityViewModels()

    private lateinit var binding: FragmentRecyclerViewBinding
    private val cityAdapter: CityAdapter = CityAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recycler_view,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvList.adapter = cityAdapter
        binding.btnAdd.setOnClickListener { viewModel.createRandomCity(args.countryId) }

        val swipeCallback = SwipeToDeleteCallback(this::onSwipeToDelete)
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvList)

        viewModel.getCities(args.countryId).observe(viewLifecycleOwner, this::onCities)
    }

    private fun onSwipeToDelete(index: Int) {
        viewModel.deleteCountry(cityAdapter.cities[index])
    }
    private fun onCities(countryWithCities: CountryWithCities) {
        cityAdapter.cities = countryWithCities.cities
    }
}