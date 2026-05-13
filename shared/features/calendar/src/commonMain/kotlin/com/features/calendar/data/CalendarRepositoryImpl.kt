package com.features.calendar.data

import com.features.calendar.domain.CalendarRepository
import com.features.calendar.domain.model.CalendarDay
import com.features.calendar.domain.model.CalendarEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.isoDayNumber
import kotlinx.coroutines.flow.Flow
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import com.features.calendar.domain.model.CalendarEventStatus
import com.network.api.apis.CalendarApi
import com.network.api.models.CreateCalendarEventRequest
import com.network.data.exception.CustomResponseException
import com.network.domain.model.isConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CalendarRepositoryImpl(
    private val calendarApi: CalendarApi
) : CalendarRepository {

    private val _allEvents = MutableStateFlow<Map<LocalDate, List<CalendarEvent>>>(emptyMap())
    override val allEvents = _allEvents.asStateFlow()

    override fun getMonthDays(
        date: LocalDate,
        allEvents: Map<LocalDate, List<CalendarEvent>>,
    ): List<CalendarDay> {
        val daysInMonth = date.month.length(isLeap = isLeapYear(date.year))

        val firstDayOfMonth = LocalDate(date.year, date.month, 1)
        val emptyDaysInTheBeginning = firstDayOfMonth.dayOfWeek.isoDayNumber - 1

        val result = mutableListOf<CalendarDay>()

        repeat(emptyDaysInTheBeginning) {
            result.add(
                CalendarDay(date = null),
            )
        }

        for (day in 1..daysInMonth) {
            val currentDate = LocalDate(date.year, date.month, day)

            val dayStatuses =
                allEvents[currentDate]
                    ?.sortedBy { it.time }
                    ?.map { it.status } ?: emptyList()

            result.add(
                CalendarDay(
                    date = LocalDate(date.year, date.month, day),
                    statuses = dayStatuses,
                ),
            )
        }

        return result
    }


    override fun saveEvent(
        date: LocalDate,
        newEvent: CalendarEvent,
    ): Flow<Result<CalendarEvent, Error>> = flow {
        emit(Result.Loading)

        try {
            val serverEvent = calendarApi.apiV1CalendarEventsPost(
                CreateCalendarEventRequest(
                    date = date.toString(),
                    time = newEvent.time,
                    status = CreateCalendarEventRequest.Status.valueOf(newEvent.status.name),
                    description = newEvent.description
                )
            ).body()

            val savedEvent = newEvent.copy(id = serverEvent.ID ?: "")

            val map = _allEvents.value.toMutableMap()
            val dayEvents = map[date] ?: emptyList()
            map[date] = (dayEvents + savedEvent).sortedBy { it.time }
            _allEvents.value = map

            emit(Result.Success(savedEvent))

        } catch (e: CustomResponseException) {
            emit(Error.OTHER(e.message.orEmpty()).toResult())

        } catch (e: Exception) {
            val result = if (e.isConnectionException())
                Result.ConnectionError
            else
                Error.OTHER(e.message.orEmpty()).toResult()

            emit(result)
        }
    }.flowOn(Dispatchers.IO)




    override fun deleteEvent(
        date: LocalDate,
        event: CalendarEvent,
    ) : Flow<Result<Unit, Error>> = flow {
        emit(Result.Loading)

        try {
            calendarApi.apiV1CalendarEventsEventIdDelete(eventId = event.id)

            val map = _allEvents.value.toMutableMap()
            val dayEvents = map[date] ?: emptyList()
            val updatedEvents = dayEvents - event

            if (updatedEvents.isEmpty()) {
                map.remove(date)
            } else {
                map[date] = updatedEvents
            }

            _allEvents.value = map

            emit(Result.Success(Unit))
        } catch (e: CustomResponseException) {
            emit(Error.OTHER(e.message.orEmpty()).toResult())

        } catch (e: Exception) {
            val result = if (e.isConnectionException())
                Result.ConnectionError
            else
                Error.OTHER(e.message.orEmpty()).toResult()

            emit(result)
        }

    }.flowOn(Dispatchers.IO)




    override fun loadEventsFromServer(): Flow<Result<Unit, Error>> = flow {
        emit(Result.Loading)
        try {
            val serverEvents = calendarApi.apiV1CalendarEventsGet().body()

            val map = mutableMapOf<LocalDate, MutableList<CalendarEvent>>()

            serverEvents.forEach { serverEvent ->
                val localDate = LocalDate.parse(serverEvent.date ?: return@forEach)
                val localEvent = CalendarEvent(
                    id = serverEvent.ID ?: "",
                    time = serverEvent.time ?: "",
                    description = serverEvent.description ?: "",
                    status = CalendarEventStatus.valueOf(serverEvent.status?.value ?: "VISITED_DOCTOR")
                )

                val dateListRef = map.getOrPut(localDate) { mutableListOf() }
                dateListRef.add(localEvent)
            }

            _allEvents.value = map

            emit(Result.Success(Unit))
        } catch (e: CustomResponseException) {
            emit(Error.OTHER(e.message.orEmpty()).toResult())

        } catch (e: Exception) {
            val result = if (e.isConnectionException())
                Result.ConnectionError
            else
                Error.OTHER(e.message.orEmpty()).toResult()

            emit(result)
        }
    }.flowOn(Dispatchers.IO)



    private fun Month.length(isLeap: Boolean): Int =
        when (this) {
            Month.FEBRUARY -> if (isLeap) 29 else 28
            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
            else -> 31
        }



    private fun isLeapYear(year: Int): Boolean = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}
