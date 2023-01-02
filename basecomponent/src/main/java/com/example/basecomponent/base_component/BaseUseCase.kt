package com.example.basecomponent.base_component

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.basecomponent.baseentities.Event
import com.example.basecomponent.baseentities.ResourceState
import com.example.data.entities.ResponseEntity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BaseUseCase<PARAM, CONTENT_TYPE> : CoroutineScope{

    /** UseCase Original Response LiveData**/
    private val _currentData = MutableLiveData<Event<ResourceState<CONTENT_TYPE>>>()
    val currentData : LiveData<Event<ResourceState<CONTENT_TYPE>>> = _currentData

    /** Job Context Scope **/
    protected var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    /** Execute UseCase api or process **/
    fun execute(run : suspend () -> ResponseEntity<CONTENT_TYPE>){
        launch(coroutineContext){
            val res : ResourceState<CONTENT_TYPE> = try {
                val data = run()
                if (data.code == 200) {
                    ResourceState.Success(data.content)
                } else {
                    ResourceState.Failure(
                        Throwable(data.status),
                        data.code,
                        body = data.content
                    )
                }
            } catch (e : Throwable){
                ResourceState.Failure(
                    Throwable(e.message),
                )
            }
            withContext(Dispatchers.Main){
                _currentData.postValue(Event(res))
            }
        }
    }

    /** Open function for user class to varies the execute call **/
    open fun setup(parameter: PARAM){ checkJob() }
    /** Common Job Control**/
    fun cancel() { job.cancel() }
    fun isCancelled(): Boolean { return job.isCancelled }
    fun checkJob(){
        if(job.isCancelled)
            job = Job() }
}