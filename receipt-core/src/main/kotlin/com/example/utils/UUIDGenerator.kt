package com.example.utils

import java.util.*

fun generateUUID(prefix: String): String {
    return "$prefix-${UUID.randomUUID()}"
}
