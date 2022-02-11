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

    var currentPeopleId: Int? = null
        private set

    private var getPostsJob: Job? = null

    var relationsList: List<Relations> = emptyList()
        private set

    private val _familiarType = mutableStateOf<Relations>(Relations(-1, ""))
    val familiarType: State<Relations> = _familiarType

    var relativesList: List<Relatives> = emptyList()
        private set

    private val _relativeType = mutableStateOf<Relatives>(Relatives(-1, ""))
    val relativeType: State<Relatives> = _relativeType

    private val _currentPost = mutableStateOf<Post>(Post(-1, "", 0))
    val currentPost: State<Post> = _currentPost

    private val _postName = mutableStateOf("")
    val postName: State<String> = _postName

    private val _posts = mutableStateOf<List<Post>>(emptyList<Post>())
    val posts: State<List<Post>> = _posts

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _secondName = mutableStateOf("")
    val secondName: State<String> = _secondName

    private val _patronymic = mutableStateOf<String>("")
    val patronymic: State<String> = _patronymic

    private val _dateOfBirth = mutableStateOf<String>("")
    val dateOfBirth: State<String> = _dateOfBirth

    private val _phoneNumber = mutableStateOf<String>("")
    val phoneNumber: State<String> = _phoneNumber

    private val _address = mutableStateOf<String>("")
    val address: State<String> = _address

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
                _address.value = event.address
            }
            is AddEditEntryEvent.EnteredDate -> {
                _dateOfBirth.value = event.dateOfBirth
            }
            is AddEditEntryEvent.EnteredName -> {
                _name.value = event.name
            }
            is AddEditEntryEvent.EnteredPatronymic -> {
                _patronymic.value = event.patronymic
            }
            is AddEditEntryEvent.EnteredPhoneNumber -> {
                _phoneNumber.value = event.phoneNumber
            }
            is AddEditEntryEvent.EnteredSecondName -> {
                _secondName.value = event.secondName
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
                            getPosts("*")
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