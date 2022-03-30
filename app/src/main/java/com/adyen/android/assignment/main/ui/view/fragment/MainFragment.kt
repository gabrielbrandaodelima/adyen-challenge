package com.adyen.android.assignment.main.ui.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.adyen.android.assignment.R
import com.adyen.android.assignment.core.domain.entities.ResponseWrapperModel
import com.adyen.android.assignment.core.domain.entities.ResultModel
import com.adyen.android.assignment.core.extensions.setUpRecyclerView
import com.adyen.android.assignment.core.extensions.toToast
import com.adyen.android.assignment.core.extensions.viewBinding
import com.adyen.android.assignment.core.utils.VenueRecommendationsQueryBuilder
import com.adyen.android.assignment.databinding.MainFragmentBinding
import com.adyen.android.assignment.main.ui.view.MainActivity
import com.adyen.android.assignment.main.ui.view.MainActivity.Companion.REQUEST_LOCATION_PERMISSION
import com.adyen.android.assignment.main.ui.view.adapter.VenuesAdapter
import com.adyen.android.assignment.main.util.DialogPermissionRationale
import com.adyen.android.assignment.main.util.PermissionsUtil
import com.adyen.android.assignment.main.util.ViewModelResult
import com.adyen.android.assignment.main.viewmodel.PlacesViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    private var dialogPermission: DialogFragment? = null
    private val binding by viewBinding(MainFragmentBinding::bind)

    private val placesViewModel: PlacesViewModel by sharedViewModel()
    private val permissionLocations: Array<String> = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    var venuesAdapter: VenuesAdapter? = null

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    private val progressDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setCancelable(false)
            .setMessage(getString(R.string.loading))
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        verifyPermissions()
        setUpRecyclerView()
    }

    private fun verifyPermissions() {
        when {
            PermissionsUtil.hasPermissions(requireContext(), *permissionLocations) -> {
                loadVenuesByLocation()
            }
            PermissionsUtil.shouldShowRequestPermissionRationale(
                this,
                *permissionLocations
            ) -> {
                dialogPermission = DialogPermissionRationale.Builder(
                    this,
                    permissionLocations, REQUEST_LOCATION_PERMISSION
                )
                    .setTitle(R.string.location_permission_title)
                    .setMessage(R.string.location_permission_message)
                    .build()
                dialogPermission?.show(childFragmentManager, "DialogPermissionRationale")
            }
            else -> {
                requestPermissions(permissionLocations, REQUEST_LOCATION_PERMISSION)
            }

        }
    }


    @SuppressLint("MissingPermission")
    private fun loadVenuesByLocation() {
        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener { location: Location? ->
            location?.let {
                placesViewModel.setLocation(it)
                getVenues(it)
            }
        }
    }

    private fun getVenues(location: Location) {
        val map = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(location.latitude, location.longitude)
            .build()
        placesViewModel.getVenueRecommendations(map).observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewModelResult.Loading -> {
                    showProgress()
                }
                is ViewModelResult.Success -> {
                    hideProgress()
                    getString(R.string.success_venues).toToast(requireContext())
                    populateAdapter(it.value)
                    it.value?.results?.toString()?.let { it1 -> Log.d("Venues: ", it1) }
                }
                is ViewModelResult.Failure -> {
                    hideProgress()
                    it.cause?.message?.toToast(requireContext())
                }
            }
        })

    }

    private fun setUpRecyclerView() {
        val venuesList = placesViewModel.getVenuesList()
        venuesList?.let {
            populateAdapter(it)
        }
    }

    private fun populateAdapter(value: ResponseWrapperModel?) {
        venuesAdapter = VenuesAdapter(
            value?.results.orEmpty(),
            requireContext(),
            ::handleVenueClickListener
        ).also { adapter ->
            binding.recyclerVenueDetails.setUpRecyclerView(requireContext(), adapter)
        }

    }

    private fun handleVenueClickListener(resultModel: ResultModel) {
        placesViewModel.setSelectedVenue(resultModel)
        findNavController().navigate(R.id.venueDetailsFragment)
    }

    private fun hideProgress() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    private fun showProgress() {
        if (progressDialog.isShowing.not()) {
            progressDialog.show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putParcelable(
                MainActivity.RECYCLER_VIEW_STATE_KEY,
                binding.recyclerVenueDetails.layoutManager?.onSaveInstanceState()
            )
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val recyclerStateBundle =
            savedInstanceState?.getParcelable<Parcelable>(MainActivity.RECYCLER_VIEW_STATE_KEY)
        recyclerStateBundle?.let {
            binding.recyclerVenueDetails.layoutManager?.onRestoreInstanceState(it)
        }
    }

    override fun shouldShowRequestPermissionRationale(permission: String): Boolean {
        return super.shouldShowRequestPermissionRationale(permission)

    }

    fun isPermissionsGranted(): Boolean {
        return PermissionsUtil.hasPermissions(requireContext(), *permissionLocations)
    }

    private var snackbar: Snackbar? = null
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (PermissionsUtil.checkPermission(
                    permissions,
                    grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                || PermissionsUtil.checkPermission(
                    permissions,
                    grantResults,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                loadVenuesByLocation()
            } else {
                getView()?.let {
                    snackbar = Snackbar.make(
                        it,
                        R.string.location_permission_message,
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction(R.string.allow) {
                            if (!PermissionsUtil.shouldShowRequestPermissionRationale(
                                    this,
                                    *permissions
                                )
                            ) {
                                dialogPermission = DialogPermissionRationale.Builder(
                                    this,
                                    permissions,
                                    REQUEST_LOCATION_PERMISSION
                                )
                                    .goToAppConfig(true)
                                    .setTitle(R.string.location_permission_title)
                                    .setMessage(R.string.location_permission_message)
                                    .build()
                                dialogPermission?.show(
                                    childFragmentManager,
                                    "DialogPermissionRationale"
                                )
                            } else {
                                verifyPermissions()
                            }
                        }
                    snackbar?.setActionTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorAccent
                        )
                    )
                    val snackbarView = snackbar?.view
                    val tv =
                        snackbarView?.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    tv?.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                    snackbar?.show()
                }
            }
        }
    }

}