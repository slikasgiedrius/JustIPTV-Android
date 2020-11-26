package com.giedrius.iptv.data.parser

import timber.log.Timber
import java.io.InputStream
import java.util.*

class NewM3UParser {

    private val m3UPlaylist = NewM3UPlaylist()
    private val playlistItems = ArrayList<NewM3UItem>()

    fun parseFile(fileData: InputStream): NewM3UPlaylist {
        val data = convertStreamToString(fileData)
        val linesArray = data.split(EXT_INF).toTypedArray()

        for (currentLine in linesArray) {
            if (!currentLine.contains(EXT_M3U)) {
                val playlistItem = NewM3UItem()
                val dataArray = currentLine.split(",").toTypedArray()

                when {
                    dataArray[0].contains(EXT_LOGO) -> handleLogo(playlistItem, dataArray[0])
                    !dataArray[0].contains(EXT_LOGO) -> handleNoLogo(playlistItem, dataArray[0])
                }

                extractNameAndUrl(playlistItem, dataArray[1])

            }
        }

        m3UPlaylist.playlistItems = playlistItems
        return m3UPlaylist
    }

    private fun handleLogo(playlistItem: NewM3UItem, data: String) {
        val duration = data
            .substring(0, data.indexOf(EXT_LOGO))
            .replace(":", "")
            .replace("\n", "")

        val icon = data
            .substring(data.indexOf(EXT_LOGO) + EXT_LOGO.length)
            .replace("=", "")
            .replace("\"", "")
            .substringBefore(" ")

        playlistItem.itemDuration = duration
        playlistItem.itemLogo = icon
    }

    private fun handleNoLogo(playlistItem: NewM3UItem, data: String) {
        val duration = data.replace(":", "").replace("\n", "")

        playlistItem.itemDuration = duration
        playlistItem.itemLogo = ""
    }

    private fun extractNameAndUrl(playlistItem: NewM3UItem, data: String) {
        try {
            val url = data.substring(data.indexOf(EXT_URL)).replace("\n", "").replace("\r", "")
            val name = data.substring(0, data.indexOf(EXT_URL)).replace("\n", "")
            playlistItem.itemName = name
            playlistItem.itemUrl = url
        } catch (e: Exception) {
            Timber.e("Error while parsing file $e")
        }
        playlistItems.add(playlistItem)
    }

    private fun convertStreamToString(inputStream: InputStream): String {
        return try {
            Scanner(inputStream).useDelimiter(CONVERSION_PATTERN).next()
        } catch (e: NoSuchElementException) {
            Timber.e("NoSuchElementException")
            ""
        }
    }

    /* Examples
    currentLine:
    -1 tvg-id="" tvg-name="[--- TURKiYE ULUSAL --]" tvg-logo="http://tv.iptvboss.net/ch_logo/Turkey2.png" group-title="|TR| TURK ULUSAL FHD/HD",[--- TURKiYE ULUSAL --]
    http://uran.iptvboss.net:80/GiedriusSlikas/GiedriusSlikas/1780

    data array:
    0
    -1 tvg-id="" tvg-name="[--- TURKiYE ULUSAL --]" tvg-logo="http://tv.iptvboss.net/ch_logo/Turkey2.png" group-title="|TR| TURK ULUSAL FHD/HD"

    1
    [--- TURKiYE ULUSAL --]
    http://uran.iptvboss.net:80/GiedriusSlikas/GiedriusSlikas/1780
     */

    companion object {
        private const val CONVERSION_PATTERN = "\\A"

        //Sorted by hierarchy
        private const val EXT_M3U = "#EXTM3U"
        private const val EXT_INF = "#EXTINF:"
        private const val EXT_ID = "tvg-id"
        private const val EXT_NAME = "tvg-name"
        private const val EXT_LOGO = "tvg-logo"
        private const val EXT_GROUP = "group-title"
        private const val EXT_URL = "http://"
    }
}