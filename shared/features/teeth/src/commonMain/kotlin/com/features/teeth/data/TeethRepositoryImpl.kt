package com.features.teeth.data

import com.features.teeth.domain.TeethRepository
import com.features.teeth.domain.model.Tooth
import com.features.teeth.domain.model.ToothStatus
import com.features.teeth.domain.model.ToothType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow

class TeethRepositoryImpl: TeethRepository {
    private val _teeth = MutableStateFlow(generateInitialTeeth())
    override val teeth: StateFlow<List<Tooth>> = _teeth.asStateFlow()

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

    private fun generateInitialTeeth(): List<Tooth>  {
        return listOf(10, 20, 30, 40).flatMap { quarter ->
            (1..8).map { i ->
                val id = quarter + i
                Tooth(
                    id = id,
                    type = getToothTypeByPosition(i)
                )
            }
        }
    }


    private fun getToothTypeByPosition(pos: Int): ToothType{
        return when(pos) {
            1, 2 -> ToothType.INCISOR
            3 -> ToothType.CANINE
            4, 5 -> ToothType.PREMOLAR
            6, 7 -> ToothType.MOLAR
            8 -> ToothType.WISDOM

            else -> ToothType.INCISOR // ?????????
        }
    }
}