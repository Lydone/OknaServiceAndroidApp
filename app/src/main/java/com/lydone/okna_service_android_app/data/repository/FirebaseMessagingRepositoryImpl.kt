package com.lydone.okna_service_android_app.data.repository

import com.google.firebase.messaging.FirebaseMessaging
import com.lydone.okna_service_android_app.domain.repository.FirebaseMessagingRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseMessagingRepositoryImpl @Inject constructor() : FirebaseMessagingRepository {

    override suspend fun getToken(): String = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(task.result)
            } else {
                continuation.resumeWithException(Exception())
            }
        }
    }
}