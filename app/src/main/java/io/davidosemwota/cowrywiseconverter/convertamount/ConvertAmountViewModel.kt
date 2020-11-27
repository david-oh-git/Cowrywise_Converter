package io.davidosemwota.cowrywiseconverter.convertamount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import io.davidosemwota.core.BuildConfig
import io.davidosemwota.core.data.source.SymbolsRepository
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ConvertAmountViewModel(
    private val repository: SymbolsRepository
) : ViewModel() {

    val fromCode = liveData {
        repository.fromCode.collect {
            emit(it)
        }
    }

    val toCode = liveData {
        repository.toCode.collect {
            emit(it)
        }
    }

    private val _convertedAmount = MutableLiveData<String>()
    val convertedAmount: LiveData<String> = _convertedAmount

    val inputAmount = MutableLiveData<String>()

    private val key = BuildConfig.FIXER_API_KEY
    private val todayDateFormat: String
        get() {
            val date = Calendar.getInstance().time

            return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date).trim()
        }

    fun convert() = viewModelScope.launch {
        if (fromCode.value == toCode.value || inputAmount.value.isNullOrEmpty()) {
            _convertedAmount.postValue(inputAmount.value?.toDouble()?.currencyFormat)
            return@launch
        }

        val amount = inputAmount.value?.toDouble() ?: 0.0
        if (amount == 0.0)
            return@launch
        val rates = repository.getHistoricalRate(
            date = todayDateFormat,
            key = key,
            symbols = "${fromCode.value},${toCode.value}"
        )

        val fromRate = rates.find { it.code == fromCode.value }?.rate ?: 0.0
        val toRate = rates.find { it.code == toCode.value }?.rate ?: 0.1
        val rate = fromRate / toRate
        _convertedAmount.postValue((amount / rate).currencyFormat)
    }

    private val Double.currencyFormat: String
        get() = DecimalFormat("#,###.##").apply { roundingMode = RoundingMode.CEILING }.format(this)
}

@Suppress("UNCHECKED_CAST")
class ConvertAmountViewModelFactory(
    private val repository: SymbolsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (ConvertAmountViewModel(repository)) as T
}
