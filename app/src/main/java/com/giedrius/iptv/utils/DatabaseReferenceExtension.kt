import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

fun DatabaseReference.logValidUrl(value: String) {
    val timeStamp = System.currentTimeMillis().toString()
    Firebase.database.reference.child("validUrls").child(timeStamp).setValue(value)
}
