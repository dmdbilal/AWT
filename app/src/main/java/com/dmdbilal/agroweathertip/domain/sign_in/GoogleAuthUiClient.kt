package com.dmdbilal.agroweathertip.domain.sign_in

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.dmdbilal.agroweathertip.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = FirebaseAuth.getInstance()

    // Sign In Request
    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                beginSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    private fun beginSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    // Sign In Response
    // The response will be a intent
    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credentials = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdtoken = credentials.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdtoken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePicUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    // Sign out
    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    // To get signed in user details
    fun getSignedInUser() : UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePicUrl = photoUrl?.toString()
        )
    }
}
