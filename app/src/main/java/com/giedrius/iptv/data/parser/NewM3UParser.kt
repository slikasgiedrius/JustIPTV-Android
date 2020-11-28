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

                getId(playlistItem, dataArray[0])
                getLogo(playlistItem, dataArray[0])
                getGroup(playlistItem, dataArray[0])
                getName(playlistItem, dataArray[0])
                getUrl(playlistItem, dataArray[1])

                playlistItems.add(playlistItem)
            }
        }

        m3UPlaylist.playlistItems = playlistItems
        return m3UPlaylist
    }

    private fun getId(playlistItem: NewM3UItem, data: String) {
        try {
            val id = data
                .substring(data.indexOf(EXT_ID) + EXT_ID.length)
                .replace("=", "")
                .replace("\"", "")
                .substringBefore(" ")

            playlistItem.itemId = id
        } catch (e: Exception) {
            Timber.e("Error while parsing id $e")
        }
    }

    private fun getName(playlistItem: NewM3UItem, data: String) {
        try {
            val name = data
                .substring(data.indexOf(EXT_NAME) + EXT_NAME.length)
                .replace("=", "")
                .substringBefore("\" ")
                .replace("\"", "")

            playlistItem.itemName = name
        } catch (e: Exception) {
            Timber.e("Error while parsing name $e")
        }
    }

    private fun getLogo(playlistItem: NewM3UItem, data: String) {
        try {
            val icon = data
                .substring(data.indexOf(EXT_LOGO) + EXT_LOGO.length)
                .replace("=", "")
                .replace("\"", "")
                .substringBefore(" ")

            playlistItem.itemLogo = icon
        } catch (e: Exception) {
            Timber.e("Error while parsing logo $e")
        }
    }

    private fun getGroup(playlistItem: NewM3UItem, data: String) {
        try {
            val group = data
                .substring(data.indexOf(EXT_GROUP) + EXT_GROUP.length)
                .replace("=", "")
                .replace("\"", "")
                .substringBeforeLast("\" ")

            playlistItem.itemGroup = group
        } catch (e: Exception) {
            Timber.e("Error while parsing group $e")
        }
    }

    private fun getUrl(playlistItem: NewM3UItem, data: String) {
        try {
            val url = data
                .substring(data.indexOf(EXT_URL))
                .replace("\n", "")
                .replace("\r", "")

            playlistItem.itemUrl = url
        } catch (e: Exception) {
            when (e) {
                is StringIndexOutOfBoundsException -> {
                    Timber.e("Error URL is too long")
                }
                else -> {
                    Timber.e("Error while parsing url $e")
                }
            }
        }
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