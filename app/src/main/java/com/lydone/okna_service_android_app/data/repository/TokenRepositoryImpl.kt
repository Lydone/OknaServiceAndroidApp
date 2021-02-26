package com.lydone.okna_service_android_app.data.repository
//
//import com.lydone.okna_service_android_app.data.remote.ApiMapper
//import com.lydone.okna_service_android_app.data.remote.model.RefreshTokenRequest
//import com.lydone.okna_service_android_app.data.storage.TokenSharedPreferencesStorage
//import com.lydone.okna_service_android_app.domain.repository.TokenRepository
//import javax.inject.Inject
//
//class TokenRepositoryImpl @Inject constructor(
//    tokenStorage: TokenSharedPreferencesStorage,
//    private val apiMapper: ApiMapper
//) : TokenRepository {
//
//    override var accessToken by tokenStorage::accessToken
//
//    override var refreshToken by tokenStorage::refreshToken
//
//    override suspend fun refreshAccessToken() {
//        apiMapper.refreshToken(RefreshTokenRequest(refreshToken ?: "")).let { response ->
//            accessToken = requireNotNull(response.accessToken)
//        }
//    }
//}