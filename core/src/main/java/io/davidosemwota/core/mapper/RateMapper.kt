package io.davidosemwota.core.mapper

import io.davidosemwota.core.data.Rate
import io.davidosemwota.core.network.responses.rates.RatesListResponse

class RateMapper : Mapper<RatesListResponse, List<Rate>> {

    override suspend fun transform(from: RatesListResponse): List<Rate> {
        val result = from.rates ?: return emptyList()

        return mapToRate(result.data)
    }

    private fun mapToRate(data: Map<String, String>): List<Rate> {
        val listOfRates = mutableListOf<Rate>()

        for ((key, value) in data) {
            val rate = Rate(
                code = key,
                rate = value.trim().toDouble()
            )
            listOfRates.add(rate)
        }

        return listOfRates
    }
}
