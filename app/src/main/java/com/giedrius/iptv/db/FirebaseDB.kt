package com.giedrius.iptv.db

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import logValidUrl
import javax.inject.Inject

@ActivityScoped
class FirebaseDB @Inject constructor() {

    private var databaseReference: DatabaseReference = Firebase.database.reference

    fun logValidUrl(url: String) {
        databaseReference.logValidUrl(url)
    }
}