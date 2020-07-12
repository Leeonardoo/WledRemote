package br.com.leonardo.wledremote.util

fun convertMillisToDisplay(millis: Long): String? {
    val s = millis % 60
    val m = millis / 60 % 60
    val h = millis / (60 * 60) % 24
    return String.format("%d:%02d:%02d", h, m, s)
}