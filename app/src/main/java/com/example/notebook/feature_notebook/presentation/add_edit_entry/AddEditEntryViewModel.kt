package com.example.notebook.feature_notebook.presentation.add_edit_entry

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_notebook.domain.model.InvalidEntryException
import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.model.entities.People
import com.example.notebook.feature_notebook.domain.model.entities.Post
import com.example.notebook.feature_notebook.domain.model.entities.Relations
import com.example.notebook.feature_notebook.domain.model.entities.Relatives
import com.example.notebook.feature_notebook.domain.use_case.entries_use_case.EntryUseCases
import com.example.notebook.feature_notebook.domain.use_case.familiar_type_use_case.GetFamiliarTypes
import com.example.notebook.feature_notebook.domain.use_case.posts_use_case.PostsUseCases
import com.example.notebook.feature_notebook.domain.use_case.relative_type_use_case.GetRelativeTypes
import com.example.notebook.feature_notebook.presentation.util.phoneNumberFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditEntryViewModel @Inject constructor(
    private val entryUseCases: EntryUseCases,
    private val getFamiliarTypesUseCase: GetFamiliarTypes,
    private val getRelativeTypes: GetRelativeTypes,
    private val postsUseCases: PostsUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var currentPeopleId: Int = 0

    private var getPostsJob: Job? = null

    var relationsList: List<Relations> = emptyList()
        private set

    private val _familiarType = mutableStateOf<Relations>(Relations(1, "Не указывать"))
    val familiarType: State<Relations> = _familiarType

    var relativesList: List<Relatives> = emptyList()
        private set

    private val _relativeType = mutableStateOf<Relatives>(Relatives(1, "Не указывать"))
    val relativeType: State<Relatives> = _relativeType

    private val _currentPost = mutableStateOf<Post>(Post(-1, "", 0))
    val currentPost: State<Post> = _currentPost

    private val _postName = mutableStateOf("")
    val postName: State<String> = _postName

    private val _posts = mutableStateOf<List<Post>>(emptyList<Post>())
    val posts: State<List<Post>> = _posts


    //Состояние для имени
    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _nameErrorStatus = mutableStateOf<Boolean>(false)
    val nameErrorStatus: State<Boolean> = _nameErrorStatus

    private val _nameErrorMessage = mutableStateOf<String>("")
    val nameErrorMessage: State<String> = _nameErrorMessage


    //Состояние для фамилии
    private val _secondName = mutableStateOf("")
    val secondName: State<String> = _secondName

    private val _secondNameErrorStatus = mutableStateOf<Boolean>(false)
    val secondNameErrorStatus: State<Boolean> = _secondNameErrorStatus

    private val _secondNameErrorMessage = mutableStateOf<String>("")
    val secondNameErrorMessage: State<String> = _nameErrorMessage

    //Состояние для отчества
    private val _patronymic = mutableStateOf<String>("")
    val patronymic: State<String> = _patronymic

    private val _patronymicErrorStatus = mutableStateOf<Boolean>(false)
    val patronymicErrorStatus: State<Boolean> = _patronymicErrorStatus

    private val _patronymicErrorMessage = mutableStateOf<String>("")
    val patronymicErrorMessage: State<String> = _patronymicErrorMessage

    //Состояние для дня рождения
    private val _dateOfBirth = mutableStateOf<String>("")
    val dateOfBirth: State<String> = _dateOfBirth

    private val _dateOfBirthErrorStatus = mutableStateOf<Boolean>(false)
    val dateOfBirthErrorStatus: State<Boolean> = _dateOfBirthErrorStatus

    private val _dateOfBirthErrorMessage = mutableStateOf<String>("")
    val dateOfBirthErrorMessage: State<String> = _dateOfBirthErrorMessage

    //Состояние для адреса
    private val _address = mutableStateOf<String>("")
    val address: State<String> = _address

    private val _addressErrorStatus = mutableStateOf<Boolean>(false)
    val addressErrorStatus: State<Boolean> = _addressErrorStatus

    private val _addressErrorMessage = mutableStateOf<String>("")
    val addressErrorMessage: State<String> = _addressErrorMessage

    //Состояние для номера телефона
    private val _phoneNumber = mutableStateOf<String>("")
    val phoneNumber: State<String> = _phoneNumber

    private val _phoneNumberErrorStatus = mutableStateOf<Boolean>(false)
    val phoneNumberErrorStatus: State<Boolean> = _phoneNumberErrorStatus

    private val _phoneNumberErrorMessage = mutableStateOf<String>("")
    val phoneNumberErrorMessage: State<String> = _phoneNumberErrorMessage


    var organizationId = -1
        private set

    var organizationName = ""
        private set

    var organizationType = ""
        private set

    var workersAmount = 0
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: AddEditEntryEvent) {
        when (event) {
            is AddEditEntryEvent.EnteredAddress -> {
                _addressErrorStatus.value = false
                _address.value = event.address
                if (address.value.length > 40) {
                    _addressErrorStatus.value = true
                    _addressErrorMessage.value = "слишком длинный адрес!"
                }
                else _addressErrorStatus.value = false
            }
            is AddEditEntryEvent.EnteredDate -> {
                _dateOfBirthErrorStatus.value = false
                _dateOfBirth.value = event.dateOfBirth
            }
            is AddEditEntryEvent.EnteredName -> {
                _nameErrorStatus.value = false
                _name.value = event.name
                if(name.value.length>15){
                    _nameErrorStatus.value = true
                    _nameErrorMessage.value = "слишком длинное имя!"
                }
                else _nameErrorStatus.value = false
            }
            is AddEditEntryEvent.EnteredPatronymic -> {
                _patronymicErrorStatus.value = false
                _patronymic.value = event.patronymic
                if (patronymic.value.length > 20) {
                    _patronymicErrorStatus.value = true
                    _patronymicErrorMessage.value = "слишком длинное отчество"
                }
                else _patronymicErrorStatus.value = false
            }
            is AddEditEntryEvent.EnteredPhoneNumber -> {
                _phoneNumberErrorStatus.value = false
                if(event.phoneNumber.length <= 11)
                    _phoneNumber.value = event.phoneNumber

            }
            is AddEditEntryEvent.EnteredSecondName -> {
                _secondNameErrorStatus.value = false
                _secondName.value = event.secondName
                if (secondName.value.length > 20) {
                    _secondNameErrorStatus.value = true
                    _nameErrorMessage.value = "слишком длинная фамилия!"
                }
                else _secondNameErrorStatus.value = false
            }

            AddEditEntryEvent.SaveEntry -> {
                viewModelScope.launch {
                    try {
                        //Проверка на существование введенной должности в БД и
                        // и её добавление при отсутствии
                        val existingPost = posts.value.find {
                            it.postName == postName.value
                        }
                        if (existingPost != null) {
                            _currentPost.value = existingPost
                            saveEntry(_currentPost.value.postId)
                        } else if (postName.value.isNotBlank()) {
                            val id: Long = postsUseCases.addPost(Post(0, postName.value, 0))
                           saveEntry(id.toInt())
                        } else {
                            _currentPost.value = Post(-1, "", 0)
                            saveEntry(-1)
                        }

                    } catch (e: InvalidEntryException) {

                    }
                }
            }
            is AddEditEntryEvent.GotBackResult -> {
                organizationId = event.organizationInfo.organizationId
                organizationName = event.organizationInfo.organizationName
                organizationType = event.organizationInfo.organizationType
                workersAmount = event.organizationInfo.workersAmount
            }
            is AddEditEntryEvent.ChosenFamiliarType -> {
                _familiarType.value = event.familiarType
            }
            is AddEditEntryEvent.ChosenRelativeType -> {
                _relativeType.value = event.relativeType
            }
            is AddEditEntryEvent.ChosenPost -> {
                _currentPost.value = event.post
                _postName.value = event.post.postName
                getPosts(postName.value)
            }
            is AddEditEntryEvent.EnteredPostName -> {
                _postName.value = event.searchQuery
                getPosts(event.searchQuery)
            }
            is AddEditEntryEvent.DeletedPost -> {
                viewModelScope.launch {
                    postsUseCases.deletePost(event.post)
                }
            }
        }
    }

    private suspend fun saveEntry(postId: Int){
        if(validation()) {
            entryUseCases.addEntry(
                People(
                    name = name.value,
                    secondName = secondName.value,
                    patronymic = patronymic.value,
                    dateOfBirth = dateOfBirth.value,
                    address = address.value,
                    phoneNumber = phoneNumber.value,
                    timestamp = System.currentTimeMillis(),
                    organizationId = organizationId,
                    postId = postId,
                    relativeId = relativeType.value.relativeId,
                    familiarId = familiarType.value.familiarId,
                    peopleId = currentPeopleId
                )
            )
            _eventFlow.emit(UiEvent.SaveNote)
        }
    }


    init {
        savedStateHandle.get<PeopleInfo>("entry")?.let { entry ->
            currentPeopleId = entry.peopleId
            _name.value = entry.name
            _secondName.value = entry.secondName
            _patronymic.value = entry.patronymic
            _dateOfBirth.value = entry.dateOfBirth
            _address.value = entry.address
            _phoneNumber.value = entry.phoneNumber
            _familiarType.value = Relations(entry.familiarId, entry.familiarType)
            _relativeType.value = Relatives(entry.relativeId, entry.relativeType)
            _postName.value = entry.postName ?: ""
            organizationId = entry.organizationId
            organizationName = entry.organizationName ?: ""
            organizationType = entry.organizationType ?: ""
            workersAmount = entry.workersAmount ?: 0
        }
        getPosts(_postName.value)
        viewModelScope.launch {
            relationsList = getFamiliarTypesUseCase()
        }
        viewModelScope.launch {
            relativesList = getRelativeTypes()
        }

    }

    private fun validation(): Boolean{
        if(name.value.isBlank()){
            _nameErrorStatus.value = true
            _nameErrorMessage.value = "данное поле не может быть пустым!"
        }
        if(secondName.value.isBlank()){
            _secondNameErrorStatus.value = true
            _secondNameErrorMessage.value = "данное поле не может быть пустым!"
        }
        if (patronymic.value.isBlank()) {
            _patronymicErrorStatus.value = true
            _patronymicErrorMessage.value = "данное поле не может быть пустым!"
        }
        if (dateOfBirth.value.isBlank()) {
            _dateOfBirthErrorStatus.value = true
            _dateOfBirthErrorMessage.value = "данное поле не может быть пустым!"
        }
        if (address.value.isBlank()) {
            _addressErrorStatus.value = true
            _addressErrorMessage.value = "данное поле не может быть пустым!"
        }
        if (phoneNumber.value.isBlank()) {
            _phoneNumberErrorStatus.value = true
            _phoneNumberErrorMessage.value = "данное поле не может быть пустым!"
        } else if (!((phoneNumber.value[0] == '7' || phoneNumber.value[0] == '8') && phoneNumber.value.length == 11)) {
            _phoneNumberErrorStatus.value = true
            _phoneNumberErrorMessage.value = "неверный формат номера!"
        }
        return !(nameErrorStatus.value || secondNameErrorStatus.value || patronymicErrorStatus.value
                || addressErrorStatus.value || phoneNumberErrorStatus.value)
    }

    private fun getPosts(searchQuery: String) {
        getPostsJob?.cancel()
        getPostsJob = postsUseCases.getPosts(searchQuery)
            .onEach { posts ->
                this._posts.value = posts
            }.launchIn(viewModelScope)
    }

    sealed class UiEvent {
        object SaveNote : UiEvent()
    }


}