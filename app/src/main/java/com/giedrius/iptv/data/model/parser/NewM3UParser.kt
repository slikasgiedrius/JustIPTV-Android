package com.giedrius.iptv.data.model.parser

import timber.log.Timber
import java.io.InputStream
import java.util.*
import kotlin.NoSuchElementException

class NewM3UParser {

    private val m3UPlaylist = M3UPlaylist()
    private val playlistItems = ArrayList<M3UItem>()

    fun parseFile(fileData: InputStream): M3UPlaylist {
        val data = convertStreamToString(fileData)
        val linesArray = data.split(EXT_INF).toTypedArray()

        for (currentLine in linesArray) {
            if (!currentLine.contains(EXT_M3U)) {
                val playlistItem = M3UItem()
                val dataArray = currentLine.split(",").toTypedArray()
                if (dataArray[0].contains(EXT_LOGO)) {
                    val duration = dataArray[0]
                        .substring(0, dataArray[0].indexOf(EXT_LOGO))
                        .replace(":", "")
                        .replace("\n", "")

                    val icon = dataArray[0]
                        .substring(dataArray[0].indexOf(EXT_LOGO) + EXT_LOGO.length)
                        .replace("=", "")
                        .replace("\"", "")
                        .substringBefore(" ")

                    playlistItem.itemDuration = duration
                    playlistItem.itemIcon = icon
                } else {
                    val duration = dataArray[0].replace(":", "").replace("\n", "")

                    playlistItem.itemDuration = duration
                    playlistItem.itemIcon = ""
                }
                try {
                    val url = dataArray[1].substring(dataArray[1].indexOf(EXT_URL)).replace("\n", "").replace("\r", "")
                    val name = dataArray[1].substring(0, dataArray[1].indexOf(EXT_URL)).replace("\n", "")
                    playlistItem.itemName = name
                    playlistItem.itemUrl = url
                } catch (e: Exception) {
                    Timber.e("Error while parsing file $e")
                }
                playlistItems.add(playlistItem)
            }
        }

        m3UPlaylist.playlistItems = playlistItems
        return m3UPlaylist
    }

    private fun convertStreamToString(inputStream: InputStream): String {
        return try {
            Scanner(inputStream).useDelimiter(CONVERSION_PATTERN).next()
        } catch (e: NoSuchElementException) {
            Timber.e("NoSuchElementException")
            ""
        }
    }

    companion object {
        private const val CONVERSION_PATTERN = "\\A"

        private const val EXT_M3U = "#EXTM3U"
        private const val EXT_INF = "#EXTINF:"
        private const val EXT_PLAYLIST_NAME = "#PLAYLIST"
        private const val EXT_LOGO = "tvg-logo"
        private const val EXT_GROUP = "group-title"
        private const val EXT_ID = "tvg-id"
        private const val EXT_URL = "http://"
    }
}