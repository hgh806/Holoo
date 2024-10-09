package com.holoo.map.domain.use_cases

import com.borna.dotinfilm.core.data.remote.adapter.Failure
import com.borna.dotinfilm.core.data.remote.adapter.Response
import com.borna.dotinfilm.core.data.remote.adapter.Success
import com.holoo.map.core.data.remote.adapter.GeneralError
import com.holoo.map.core.data.remote.mapper.toDirection
import com.holoo.map.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.neshan.common.model.LatLng

class GetDirectionUseCase(
    private val repository: MainRepository,
) {
    operator fun invoke(origin: LatLng, destination: LatLng): Flow<Response<List<LatLng>>> = flow {
        val originString = "${origin.latitude},${origin.longitude}"
        val destinationString = "${destination.latitude},${destination.longitude}"

        val response = repository.getDirection(originString, destinationString)
        if (response.isSuccessful) {
            val route = response.body()!!.routes.firstOrNull()?.toDirection() ?: emptyList()
            emit(Success(route))
        } else
            emit(Failure(GeneralError.ApiError(response.message(), response.code())))
    }.catch {
        it.printStackTrace()
        emit(Failure(GeneralError.UnknownError(it)))
    }.flowOn(IO)
}