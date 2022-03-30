package com.adyen.android.assignment.main.ui.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import com.adyen.android.assignment.R
import com.adyen.android.assignment.core.domain.entities.LocationModel
import com.adyen.android.assignment.core.domain.entities.ResponseWrapperModel
import com.adyen.android.assignment.core.domain.entities.ResultModel
import com.adyen.android.assignment.core.extensions.empty
import com.adyen.android.assignment.core.extensions.setUpRecyclerView
import com.adyen.android.assignment.core.extensions.toToast
import com.adyen.android.assignment.core.extensions.viewBinding
import com.adyen.android.assignment.core.utils.VenueRecommendationsQueryBuilder
import com.adyen.android.assignment.databinding.FragmentVenueDetailsBinding
import com.adyen.android.assignment.databinding.MainFragmentBinding
import com.adyen.android.assignment.main.ui.view.adapter.VenueDetailsAdapter
import com.adyen.android.assignment.main.ui.view.adapter.VenuesAdapter
import com.adyen.android.assignment.main.util.ViewModelResult
import com.adyen.android.assignment.main.viewmodel.PlacesViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class VenueDetailsFragment : Fragment(R.layout.fragment_venue_details) {

    private val placesViewModel: PlacesViewModel by sharedViewModel()


    private val binding by viewBinding(FragmentVenueDetailsBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers() {
        placesViewModel.selectedVenue.observe(viewLifecycleOwner, Observer {
            populateDetails(it)
        })
    }

    private fun populateDetails(result: ResultModel?) {
        result?.let {
            binding.apply {
                venueName.text = it.name ?: String.empty()
                venueTimezone.text = "Timezone: ${it.timezone}"
                venueLocation.text = getVenueLocationDetails(it.location)
                venueNeighborhoodRv.setUpRecyclerView(
                    requireContext(),
                    VenueDetailsAdapter(it.location?.neighbourhood)
                )
                venueGeocode.text =
                    "Geocode: \n latitude: ${it.geocode?.main?.latitude} \nlongitude ${it.geocode?.main?.longitude}"
                venueDistance.text = "Distance: ${it.distance} meters"
                venueCategoriesRv.setUpRecyclerView(
                    requireContext(),
                    VenueDetailsAdapter(it.categories?.map { it?.name })
                )
            }

        }
    }

    private fun getVenueLocationDetails(location: LocationModel?): String {
        var locationDetails = String.empty()
        location?.let {
            it.address?.let {
                locationDetails += "Address: $it"
            }
            it.country?.let {
                locationDetails += "\nCountry: $it"
            }
            it.locality?.let {
                locationDetails += "\nLocality: $it"
            }
            it.postcode?.let {
                locationDetails += "\nPostcode: $it"
            }
            it.region?.let {
                locationDetails += "\nRegion: $it"
            }
        }
        return locationDetails
    }


}