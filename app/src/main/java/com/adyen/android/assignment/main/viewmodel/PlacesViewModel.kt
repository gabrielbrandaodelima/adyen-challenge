package com.adyen.android.assignment.main.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.adyen.android.assignment.core.domain.entities.ResponseWrapperModel
import com.adyen.android.assignment.core.domain.entities.ResultModel
import com.adyen.android.assignment.core.domain.usecase.PlacesUseCase
import com.adyen.android.assignment.main.ui.view.adapter.VenuesAdapter
import com.adyen.android.assignment.main.util.ViewModelResult

class PlacesViewModel(
    private val placesUseCase: PlacesUseCase
) : ViewModel() {

    private var location: Location? = null

    private val _responseWrapperModel: MutableLiveData<ResponseWrapperModel> = MutableLiveData()
    val responseWrapperModel: LiveData<ResponseWrapperModel> = MutableLiveData()

    private val _selectedVenue: MutableLiveData<ResultModel> = MutableLiveData()

    val selectedVenue: LiveData<ResultModel> = _selectedVenue

    fun getVenuesList(): ResponseWrapperModel? {
        return _responseWrapperModel.value
    }
    fun setSelectedVenue(resultModel: ResultModel) {
        _selectedVenue.postValue(resultModel)
    }
    fun getVenueRecommendations(queryMap: Map<String, String>) =
        liveData {
            if (_responseWrapperModel.value == null) {
                emit(ViewModelResult.Loading())
                try {
                    val result = placesUseCase.getVenueRecommendations(queryMap)
                    _responseWrapperModel.postValue(result)
                    emit(ViewModelResult.Success(result))
                } catch (re: Exception) {
                    emit(ViewModelResult.Failure(re))
                }
            }
        }

    fun setLocation(loc: Location) {
        this.location = loc
    }


}