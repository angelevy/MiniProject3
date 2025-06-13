package com.angellevyne0045.miniproject3.ui.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angellevyne0045.miniproject3.model.Buku
import com.angellevyne0045.miniproject3.network.ApiStatus
import com.angellevyne0045.miniproject3.network.BukuApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class MainViewModel : ViewModel() {

    var data = MutableStateFlow<List<Buku>>(emptyList())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set


    fun retrieveData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                data.value = BukuApi.service.getBuku(userId)
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun saveData(email: String, title: String, author: String, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = BukuApi.service.postBuku(
                    email,
                    title.toRequestBody("text/plain".toMediaTypeOrNull()),
                    author.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultipartBody()
                )

                if(result.status == "success")
                    retrieveData(email)
                else
                    throw Exception(result.message)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
            }
        }
    }

    fun updateData(userId: String, bukuId: String, title: String, author: String, bitmap: Bitmap?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = BukuApi.service.editBuku(
                    userId,
                    bukuId,
                    title.toRequestBody("text/plain".toMediaTypeOrNull()),
                    author.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap?.toMultipartBody()
                )

                if(result.status == "success") {
                    retrieveData(userId)
                } else {
                    throw Exception(result.message ?: "Gagal mengupdate buku")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Update error: ${e.message}")
                errorMessage.value = "Gagal update: ${e.message}"
            }
        }
    }

    fun deleteData(userId: String, bukuId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = BukuApi.service.deleteBuku(
                    userId,
                    bukuId
                )

                if(result.status == "success"){
                    retrieveData(userId)
                } else {
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                Log.d("MainViewModel", "Error delete: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }
    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData("image", "image.jpg", requestBody)
    }
    fun clearMessage() { errorMessage.value = null }
}
