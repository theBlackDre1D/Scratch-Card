package co.init.common.extensions

fun <T> T?.orDefault(defaultValue: T): T = this ?: defaultValue