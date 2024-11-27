package com.rjprog.snoozeloo.core.domain.models

data class Alarm(
    val id: Long =0,
    val timeMillis: Long = 0,
    val hr24: Int = -1,
    val min: Int = -1,
    val title: String = "New",
    val enabled: Boolean = true
)
