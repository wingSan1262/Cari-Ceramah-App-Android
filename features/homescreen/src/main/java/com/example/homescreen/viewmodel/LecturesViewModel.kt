package com.example.homescreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.basecomponent.base_component.*
import com.example.basecomponent.base_component.ErrorType.*
import com.example.basecomponent.base_component.utils.LocationService
import com.example.basecomponent.baseentities.ResourceState
import com.example.common_usecase.LocationUseCase
import com.example.common_usecase.RetrieveSaveLocationUseCase
import com.example.data.entities.request.LectureRequest
import com.example.domain.FetchLectureUseCase
import com.example.domain.SearchLectureUseCase
import com.example.domain.model.LectureModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class LecturesViewModel @Inject constructor(
    val searchLectureUseCase: SearchLectureUseCase,
    val fetchLectureUseCase: FetchLectureUseCase,
    val locationUseCase: LocationUseCase,
    val retrieveSaveLocationUseCase: RetrieveSaveLocationUseCase,
) : ViewModel() {

    val _isSearch = MutableLiveData<Boolean>()
    val isSearch : LiveData<Boolean> = _isSearch
    fun setSearch(isSearch : Boolean){
        if(isSearch){
            searchPage = -1; currentQuery = ""
        }
        _isSearch.value = isSearch
    }

    var isQuerying = false

    var currentQuery : String = ""
    var searchPage = -1
    var currentPage = -1

    var _isError = MutableLiveData(NO_ERROR)
    val isError : LiveData<ErrorType> = _isError
    fun setError(status : ErrorType){
        _isError.value = status
    }

    val _searchData = Transformations.switchMap(searchLectureUseCase.currentData){
        val data = MutableLiveData<ResourceState<List<BaseListModel>>>()
        val currentList = searchData.value?.getContent()
        it.nonFilteredContent().let { content ->
            when(content){
                is ResourceState.Success ->{
                    _isError.value =
                        if(currentList.isNullOrEmpty() && content.body.items.isNullOrEmpty())
                            EMPTY_DATA else NO_ERROR
                    data.value  = ResourceState.Success(
                        ArrayList<BaseListModel>(
                            if(searchPage == 0 || currentList.isNullOrEmpty())
                                listOf() else currentList
                        ).apply{
                        addAll(this.size, content.body.items) }) }

                is ResourceState.Failure -> {
                    data.value = ResourceState.Failure(
                        content.exception,
                        body = ArrayList<BaseListModel>(currentList ?: listOf()).apply{
                        addAll(this.size, content.body?.items ?: listOf()) })
                    _isError.value = CONNECTION_ERROR
                    if(content.body?.items == null || content.body?.items!!.isEmpty())
                        searchPage --
                }
            }
            isQuerying = false
        }
        return@switchMap data
    }
    val searchData : LiveData<ResourceState<List<BaseListModel>>> = _searchData

    fun searchLectures() {
        if(isQuerying) return
        isQuerying = true
        searchPage ++
        locationData.value?.getContent()?.run {
            searchLectureUseCase.setup(
                LectureRequest(query = currentQuery, lat = lat, lng = lon,
                    searchingDate = Date().incrementDate(-50).getDateForRequest(),
                    page = searchPage,
                    pageItemCount = 10
                )
            )
        }
    }

    val _nearestData = Transformations.switchMap(fetchLectureUseCase.currentData) {
        val data = MutableLiveData<ResourceState<List<BaseListModel>>>()
        val currentList = nearestData.value?.getContent()
        it.nonFilteredContent().let { content ->
            when(content){

                is ResourceState.Success ->{
                    _isError.value =
                        if(currentList.isNullOrEmpty() && content.body.items.isNullOrEmpty())
                            EMPTY_DATA else NO_ERROR

                    data.value  = ResourceState.Success(
                        ArrayList<BaseListModel>(currentList ?: listOf()).apply{
                            addAll(this.size, content.body.items) }
                    )
                }

                is ResourceState.Failure -> {
                    data.value = ResourceState.Failure(
                        content.exception, body = ArrayList<BaseListModel>(currentList ?: listOf()).apply{
                            addAll(this.size, content.body?.items ?: listOf()) }
                    )

                    _isError.value = CONNECTION_ERROR

                    if(content.body?.items == null || content.body?.items!!.isEmpty()){
                        currentPage --
                    }
                }
            }
            isQuerying = false
        }
        return@switchMap data
    }

    val nearestData : LiveData<ResourceState<List<BaseListModel>>> = _nearestData
    fun fetchNearestLecture() {
        if(isQuerying) return
        isQuerying = true
        currentPage ++
        locationData.value?.getContent()?.run {
            fetchLectureUseCase.setup(
                LectureRequest(lat = lat, lng = lon,
                    searchingDate = Date().incrementHour(-7).getDateForRequest(),
                    page = currentPage,
                    pageItemCount = 10
                )
            )
        }
    }

    val gpsLocation = Transformations.switchMap(locationUseCase.currentData){
        val data = MutableLiveData<ResourceState<LocationService.coordinateData>>()
        it.nonFilteredContent().let{ content ->
            if(content is ResourceState.Success){
                data.value = ResourceState.Success(content.body)
                _locationData.value = ResourceState.Success(content.body)
            }
            if(content is ResourceState.Failure){
                data.value = ResourceState.Failure(content.exception)
                _locationData.value = ResourceState.Failure(content.exception)
            }
        }
        return@switchMap data
    }

    private val _locationData = MutableLiveData<ResourceState<LocationService.coordinateData>>()
    val locationData : LiveData<ResourceState<LocationService.coordinateData>> = _locationData

    fun fetchLocation(){
        locationUseCase.setup(null)
    }

}

