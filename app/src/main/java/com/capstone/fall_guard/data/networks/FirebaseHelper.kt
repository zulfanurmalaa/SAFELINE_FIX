package com.capstone.fall_guard.data.networks

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseHelper {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun observeUserActivity(): Flow<String> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val status = snapshot.getValue(String::class.java)
                status?.let { trySend(it) }
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        firebaseDatabase.getReference(SENSOR_DATA_CHILD).child(STATUS_CHILD)
            .addValueEventListener(listener)

        awaitClose {
            firebaseDatabase.getReference(SENSOR_DATA_CHILD).child(STATUS_CHILD)
                .removeEventListener(listener)
        }
    }

    fun observeUserFallHistory(): Flow<List<Long>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val timestampList = snapshot.children.mapNotNull {
                    it.child("timestamp").getValue(Long::class.java)
                }
                trySend(timestampList).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        firebaseDatabase.getReference(SENSOR_DATA_CHILD).child(HISTORY_CHILD).addValueEventListener(listener)

        awaitClose {
            firebaseDatabase.getReference(SENSOR_DATA_CHILD).child(HISTORY_CHILD).removeEventListener(listener)
        }
    }

    companion object {
        private const val SENSOR_DATA_CHILD = "sensor_data"

        private const val STATUS_CHILD = "status"
        private const val HISTORY_CHILD = "history"

        @Volatile
        private var INSTANCE: FirebaseHelper? = null

        fun getInstance() = INSTANCE ?: synchronized(this) {
            val instance = FirebaseHelper()
            INSTANCE = instance
            instance
        }
    }
}