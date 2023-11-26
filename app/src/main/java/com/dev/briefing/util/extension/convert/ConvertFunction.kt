package com.dev.briefing.util.extension.convert

fun formatTime(
    hour: Int,
    minute: Int,
): String {
    var tmpString = ""
    if (hour <= 12) {
        tmpString = "오전 " + "${hour}시 ${minute}분"
    } else {
        tmpString = "오후 " + "${hour - 12}시 ${minute}분"
    }
    return tmpString
}