package com.lydone.okna_service_android_app.presentation.firebasemessaging

import com.google.firebase.messaging.FirebaseMessagingService
import com.lydone.okna_service_android_app.domain.interactor.FirebaseMessagingInteractor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var interactor: FirebaseMessagingInteractor

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        interactor.clearTokens()
    }
}