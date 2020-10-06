package com.anwera97.roomexample.presentation.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.anwera97.roomexample.R
import com.anwera97.roomexample.data.entites.Country
import com.anwera97.roomexample.databinding.FragmentRecyclerViewBinding
import com.anwera97.roomexample.util.SwipeToDeleteCallback


class CountryFragment : Fragment(), CountryAdapter.CountryInteractor {

    private val viewModel: CountryViewModel by activityViewModels()
    private lateinit var binding: FragmentRecyclerViewBinding
    private lateinit var countryAdapter: CountryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_recycler_view, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryAdapter = CountryAdapter(this)
        binding.rvList.adapter = countryAdapter
        binding.btnAdd.setOnClickListener { viewModel.createRandomCountry() }

        val swipeCallback = SwipeToDeleteCallback(this::onSwipeToDelete)
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvList)

        viewModel.countries.observe(viewLifecycleOwner, this::onCountries)
    }

    private fun onSwipeToDelete(index: Int) {
        viewModel.deleteCountry(countryAdapter.countries[index])
    }

    private fun onCountries(countries: List<Country>) {
        countryAdapter.countries = countries
    }

    override fun onClick(id: String) {
        val action = CountryFragmentDirections.actionCountryFragmentToCityFragment(id)
        findNavController().navigate(action)
    }
}

