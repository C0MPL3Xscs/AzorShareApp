package com.example.azorshareapp.services.network

interface NetworkTaskCallback {
    fun onNetworkTaskComplete(result: Boolean, json: String)
}
