package com.features.teeth.data

import com.features.teeth.domain.TeethRepository
import com.features.teeth.domain.model.Tooth
import com.features.teeth.domain.model.ToothStatus
import com.features.teeth.domain.model.ToothType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class TeethRepositoryImpl: TeethRepository {
    private val _teeth = MutableStateFlow(emptyList<Tooth>())
    override val teeth: StateFlow<List<Tooth>> = _teeth.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            _teeth.value = generateInitialTeeth()
        }
    }

    override fun getToothById(id: Int): Tooth? {
        return _teeth.value.find { it.id == id }
    }

    override fun updateToothStatus(id: Int, newStatus: ToothStatus, note: String) {
        _teeth.value = _teeth.value.map { tooth ->
            if (tooth.id == id)
                tooth.copy(status = newStatus, note = note)
            else
                tooth
        }
    }

    private fun generateInitialTeeth() : List<Tooth>{
        return listOf(11..18, 21..28, 31..38, 41..48)
            .flatMap { range ->
                range.map {
                    Tooth(
                        id = it,
                        type = ToothType.fromPosition(it)/*getToothTypeByPosition(it)*/
                    )
                }
            }
    }
}