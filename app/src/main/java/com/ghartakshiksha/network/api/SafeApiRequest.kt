package com.ghartakshiksha.network.api

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import com.ghartakshiksha.utility.ApiException

abstract class SafeApiRequest {
    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T{
        val response = call.invoke()
        if(response.isSuccessful){
            return response.body()!!
        }else{
            val error = response.errorBody()?.string()
            Log.e("Response-Error",""+error)

            val message = StringBuilder()
            error?.let{
                try{
                    message.append(JSONObject(it).getString("message"))
                }catch(e: JSONException){ }
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }

}