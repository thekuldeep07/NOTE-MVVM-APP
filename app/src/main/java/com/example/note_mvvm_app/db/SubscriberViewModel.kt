package com.example.note_mvvm_app.db

import android.util.Patterns
import androidx.lifecycle.*
import com.example.note_mvvm_app.Event
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {



    val subscribers = repository.subscribers.asLiveData()

    private var  isUpdateOrDelete=false

    private lateinit var subscriberToUpdateOrDelete:Subscriber

    val inputName = MutableLiveData<String?>()

    val inputEmail = MutableLiveData<String?>()

    val saveOrUpdateButtonText = MutableLiveData<String>()

    val clearAllOrDeleteButtonText = MutableLiveData<String>()


    private  val statusMessage = MutableLiveData<Event<String>>()

    val  message :LiveData<Event<String>>
    get()=statusMessage


    init {
        saveOrUpdateButtonText.value="Save"
        clearAllOrDeleteButtonText.value="ClearAll"
    }

    fun savedOrUpdate() {
        if (inputName.value == null) {
            statusMessage.value = Event("Please enter subscriber's name")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter subscriber's email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter a correct email address")
        } else {


        if (isUpdateOrDelete) {
            subscriberToUpdateOrDelete.name=inputName.value!!
            subscriberToUpdateOrDelete.email=inputEmail.value!!
            update(subscriberToUpdateOrDelete)
        } else {
            val name: String = inputName.value!!
            val email: String = inputEmail.value!!
            insert(Subscriber(0, name, email))

            inputName.value = null
            inputEmail.value = null

        }
    }
    }

    fun clearOrDelete(){
        if(isUpdateOrDelete){
            delete(subscriberToUpdateOrDelete)
        }
        else{
            clearAll()
        }

    }

    fun insert(subscriber: Subscriber){
        viewModelScope.launch {
            val newRowId = repository.insert(subscriber)
            if (newRowId > -1) {
                statusMessage.value = Event("Subscriber Inserted Successfully $newRowId")
            } else {
                statusMessage.value = Event("Error Occurred")
            }
        }
    }


    fun update(subscriber: Subscriber){
        viewModelScope.launch {
            val noOfRows = repository.update(subscriber)
            if (noOfRows > 0) {
                inputName.value = ""
                inputEmail.value = ""
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"
                statusMessage.value = Event("$noOfRows Row Updated Successfully")
            } else {
                statusMessage.value = Event("Error Occurred")
            }
        }
    }

    fun delete(subscriber: Subscriber){
        viewModelScope.launch {
            val noOfRowsDeleted = repository.delete(subscriber)
            if (noOfRowsDeleted > 0) {
                inputName.value = ""
                inputEmail.value = ""
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"
                statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
            } else {
                statusMessage.value = Event("Error Occurred")
            }
    }
    }

    fun clearAll(){
        viewModelScope.launch {
            val noOfRowsDeleted = repository.deleteAll()
            if (noOfRowsDeleted > 0) {
                statusMessage.value = Event("$noOfRowsDeleted Subscribers Deleted Successfully")
            } else {
                statusMessage.value = Event("Error Occurred")
            }}
    }

    fun initUpdateAndDelete(subscriber: Subscriber){
        inputName.value=subscriber.name
        inputEmail.value=subscriber.email
        isUpdateOrDelete=true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value="Update"
        clearAllOrDeleteButtonText.value="Delete"
    }




}