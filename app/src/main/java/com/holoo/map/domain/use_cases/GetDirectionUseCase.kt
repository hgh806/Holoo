package com.holoo.map.domain.use_cases

import com.borna.dotinfilm.core.data.remote.adapter.Failure
import com.borna.dotinfilm.core.data.remote.adapter.Response
import com.borna.dotinfilm.core.data.remote.adapter.Success
import com.holoo.map.core.data.remote.adapter.GeneralError
import com.holoo.map.core.data.remote.response.Route
import com.holoo.map.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetDirectionUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(
        originLat: Double,
        originLng: Double,
        destinationLat: Double,
        destinationLng: Double
    ): Flow<Response<List<Route>>> = flow {
        val origin = "$originLat,$originLng"
        val destination = "$destinationLat,$destinationLng"

        val response = repository.getDirection(origin, destination)
        if(response.isSuccessful) {
            val route = response.body()!!.routes
            emit(Success(route))
        } else
            emit(Failure(GeneralError.ApiError(response.message(), response.code())))
    }.flowOn(IO)
}