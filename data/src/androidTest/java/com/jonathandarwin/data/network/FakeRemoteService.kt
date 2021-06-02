package com.jonathandarwin.data.network

import com.jonathandarwin.domain.dto.convert.ConvertItemResponse
import com.jonathandarwin.domain.dto.convert.ConvertResponse
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created By : Jonathan Darwin on June 02, 2021
 */
class FakeRemoteService : RemoteService {
    override suspend fun convert(
        from: String,
        to: String,
        amount: String,
        apiKey: String
    ): Response<ConvertResponse> {
        try {
            val amountDouble = amount.toDouble()
            if(from == "IDR" && to == "USD") {
                return Response.success(ConvertResponse("IDR", amountDouble,
                    ConvertItemResponse(amountDouble / 15000, null, null, null, null, null, null, null, null, 1.0), 1))
            }
            return Response.error(400, ResponseBody.create(null, "Error Saja"))
        }
        catch (e: Exception) {
            return Response.error(400, ResponseBody.create(null, e.message.toString()))
        }

    }
}