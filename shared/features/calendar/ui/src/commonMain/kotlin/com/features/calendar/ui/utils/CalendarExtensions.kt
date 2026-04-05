package com.features.calendar.ui.utils

import androidx.compose.runtime.Composable
import com.features.calendar.domain.model.CalendarEventStatus
import com.features.ui.Res
import com.features.ui.april
import com.features.ui.aprilGenitive
import com.features.ui.august
import com.features.ui.augustGenitive
import com.features.ui.completedVisitToDoctor
import com.features.ui.december
import com.features.ui.decemberGenitive
import com.features.ui.february
import com.features.ui.februaryGenitive
import com.features.ui.fridayFull
import com.features.ui.january
import com.features.ui.januaryGenitive
import com.features.ui.july
import com.features.ui.julyGenitive
import com.features.ui.june
import com.features.ui.juneGenitive
import com.features.ui.march
import com.features.ui.marchGenitive
import com.features.ui.may
import com.features.ui.mayGenitive
import com.features.ui.mondayFull
import com.features.ui.november
import com.features.ui.novemberGenitive
import com.features.ui.october
import com.features.ui.octoberGenitive
import com.features.ui.plannedVisit
import com.features.ui.saturdayFull
import com.features.ui.september
import com.features.ui.septemberGenitive
import com.features.ui.sundayFull
import com.features.ui.teethConditionMarkProblem
import com.features.ui.teethConditionMarkSolution
import com.features.ui.thursdayFull
import com.features.ui.toothBrushChange
import com.features.ui.tuesdayFull
import com.features.ui.wednesdayFull
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.StringResource

fun Month.toStringResource(): StringResource =
    when (this) {
        Month.JANUARY -> Res.string.january
        Month.FEBRUARY -> Res.string.february
        Month.MARCH -> Res.string.march
        Month.APRIL -> Res.string.april
        Month.MAY -> Res.string.may
        Month.JUNE -> Res.string.june
        Month.JULY -> Res.string.july
        Month.AUGUST -> Res.string.august
        Month.SEPTEMBER -> Res.string.september
        Month.OCTOBER -> Res.string.october
        Month.NOVEMBER -> Res.string.november
        Month.DECEMBER -> Res.string.december
    }

fun Month.toGenitiveStringResource(): StringResource =
    when (this) {
        Month.JANUARY -> Res.string.januaryGenitive
        Month.FEBRUARY -> Res.string.februaryGenitive
        Month.MARCH -> Res.string.marchGenitive
        Month.APRIL -> Res.string.aprilGenitive
        Month.MAY -> Res.string.mayGenitive
        Month.JUNE -> Res.string.juneGenitive
        Month.JULY -> Res.string.julyGenitive
        Month.AUGUST -> Res.string.augustGenitive
        Month.SEPTEMBER -> Res.string.septemberGenitive
        Month.OCTOBER -> Res.string.octoberGenitive
        Month.NOVEMBER -> Res.string.novemberGenitive
        Month.DECEMBER -> Res.string.decemberGenitive
    }

fun DayOfWeek.toFullStringResource(): StringResource =
    when (this) {
        DayOfWeek.MONDAY -> Res.string.mondayFull
        DayOfWeek.TUESDAY -> Res.string.tuesdayFull
        DayOfWeek.WEDNESDAY -> Res.string.wednesdayFull
        DayOfWeek.THURSDAY -> Res.string.thursdayFull
        DayOfWeek.FRIDAY -> Res.string.fridayFull
        DayOfWeek.SATURDAY -> Res.string.saturdayFull
        DayOfWeek.SUNDAY -> Res.string.sundayFull
    }

@Composable
fun CalendarEventStatus.toStringResource(): StringResource =
    when (this) {
        CalendarEventStatus.VISITED_DOCTOR -> Res.string.completedVisitToDoctor
        CalendarEventStatus.CONDITION_CHANGE_PROBLEM -> Res.string.teethConditionMarkProblem
        CalendarEventStatus.CONDITION_CHANGE_SOLUTION -> Res.string.teethConditionMarkSolution
        CalendarEventStatus.BRUSH_CHANGE -> Res.string.toothBrushChange
        CalendarEventStatus.PLANNED_VISIT -> Res.string.plannedVisit
    }
