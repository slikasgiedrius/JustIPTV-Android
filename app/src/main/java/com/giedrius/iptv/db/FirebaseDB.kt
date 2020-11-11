package com.giedrius.iptv.db

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import logValidUrl
import javax.inject.Inject

class FirebaseDB @Inject constructor() {

    private var databaseReference: DatabaseReference = Firebase.database.reference

    fun logValidUrl(url: String) {
        databaseReference.logValidUrl(url)
    }
}