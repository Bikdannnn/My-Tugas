package com.example.assignmenttrack.viewModel

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignmenttrack.database.TaskRepository
import com.example.assignmenttrack.database.UserRepository
import com.example.assignmenttrack.model.Task
import com.example.assignmenttrack.model.User
import com.example.assignmenttrack.uiStateData.defaultUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor
    (private val userRepository: UserRepository,
     private val taskRepository: TaskRepository
): ViewModel()
{
    private val _user = MutableStateFlow<User>(defaultUser)
    val user: StateFlow<User> = _user

    init {
        updateUser()
    }

    fun updatePhotoProfile(uri: Uri){
        _user.value.profilePictureUri = uri.toString()
        viewModelScope.launch{
            updateUser()
        }
    }

    fun updateName(name: String){
    }
    // Bug di StatScreen dimana tidak berubah biarpun ada tugas - Medium
    // Bug di Profile biarpun terganti tapi tidak selamanya dan harus ganti screen - Hard
    // Bug di calendar dimana semua tugas pada tanggal yang sama ditandai
    // serta catatan tanggal yang dipencet masih ada - Easy?
    fun updateUser(){
        viewModelScope.launch {
            combine(
                userRepository.getUser(),
                taskRepository.getAllTasks()
            ) { userData, tasks ->
                userData?.copy(
                    name = user.value.name,
                    profilePictureUri = user.value.profilePictureUri,
                    taskCompleted = tasks.count { it.status == true },
                    taskLate = tasks.count { it.status == false && it.deadline.isBefore(Instant.now()) },
                    taskPending = tasks.count { it.status == null },
                    tugasTotal = tasks.count { it.type == Task.TaskType.Tugas },
                    kerjaTotal = tasks.count { it.type == Task.TaskType.Kerja },
                    belajarTotal = tasks.count { it.type == Task.TaskType.Belajar },
                    taskTotal = tasks.size
                )
                    ?: defaultUser
            }.collect { updatedUser ->
                _user.value = updatedUser
                userRepository.updateUser(updatedUser)
            }
        }
    }
}