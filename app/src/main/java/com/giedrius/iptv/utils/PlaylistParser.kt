package com.giedrius.iptv.utils

import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.data.model.Playlist
import timber.log.Timber
import java.io.InputStream
import java.util.*

class PlaylistParser {

    private val playlistItems = ArrayList<Channel>()
    private var autoIncrement = 0

    fun parseFile(fileData: InputStream): Playlist {
        val data = convertStreamToString(fileData)
        val linesArray = data.split(EXT_INF).toTypedArray()

        for (currentLine in linesArray) {
            if (!currentLine.contains(EXT_M3U)) {
                val dataArray = currentLine.split(",").toTypedArray()

                val id = getId(dataArray[0])
                val name = getName(dataArray[0])
                val logo = getLogo(dataArray[0])
                val group = getGroup(dataArray[0])
                val url = getUrl(dataArray[1])

                playlistItems.add(Channel(autoIncrement, id, name, logo, group, url, ""))
                autoIncrement++
            }
        }

        return Playlist(playlistItems = playlistItems)
    }

    private fun getId(data: String): String? {
        var id: String? = null
        try {
            val formattedId = data
                .substring(data.indexOf(EXT_ID) + EXT_ID.length)
                .replace("=", "")
                .replace("\"", "")
                .substringBefore(" ")

            id = formattedId
        } catch (e: Exception) {
            Timber.e("Error while parsing id $e")
        }
        return id
    }

    private fun getName(data: String): String? {
        var name: String? = null
        try {
            val formattedName = data
                .substring(data.indexOf(EXT_NAME) + EXT_NAME.length)
                .replace("=", "")
                .substringBefore("\" ")
                .replace("\"", "")

            name = formattedName
        } catch (e: Exception) {
            Timber.e("Error while parsing name $e")
        }
        return name
    }

    private fun getLogo(data: String): String? {
        var logo: String? = null
        try {
            val formattedLogo = data
                .substring(data.indexOf(EXT_LOGO) + EXT_LOGO.length)
                .replace("=", "")
                .replace("\"", "")
                .substringBefore(" ")

            logo = formattedLogo
        } catch (e: Exception) {
            Timber.e("Error while parsing logo $e")
        }
        return logo
    }

    private fun getGroup(data: String): String? {
        var group: String? = null
        try {
            val formattedGroup = data
                .substring(data.indexOf(EXT_GROUP) + EXT_GROUP.length)
                .replace("=", "")
                .replace("\"", "")
                .substringBeforeLast("\" ")

            group = formattedGroup
        } catch (e: Exception) {
            Timber.e("Error while parsing group $e")
        }
        return group
    }

    private fun getUrl(data: String): String? {
        var url: String? = null
        try {
            val formattedUrl = data
                .substring(data.indexOf(EXT_URL))
                .replace("\n", "")
                .replace("\r", "")

            url = formattedUrl
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

        return url
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