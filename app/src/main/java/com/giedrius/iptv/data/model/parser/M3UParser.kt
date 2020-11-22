package com.giedrius.iptv.data.model.parser

import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*

class M3UParser {
    private fun convertStreamToString(`is`: InputStream): String {
        return try {
            Scanner(`is`).useDelimiter("\\A").next()
        } catch (e: NoSuchElementException) {
            ""
        }
    }

    @Throws(FileNotFoundException::class)
    fun parseFile(inputStream: InputStream): M3UPlaylist {
        val m3UPlaylist = M3UPlaylist()
        val playlistItems = ArrayList<M3UItem>()
        val stream = convertStreamToString(inputStream)
        val linesArray = stream.split(EXT_INF).toTypedArray()
        for (currLine in linesArray) {
            if (currLine.contains(EXT_M3U)) {
                //header of file
                if (currLine.contains(EXT_PLAYLIST_NAME)) {
                    val fileParams = currLine.substring(
                        EXT_M3U.length, currLine.indexOf(
                            EXT_PLAYLIST_NAME
                        )
                    )
                    val playListName =
                        currLine.substring(currLine.indexOf(EXT_PLAYLIST_NAME) + EXT_PLAYLIST_NAME.length)
                            .replace(":", "")
                    m3UPlaylist.playlistName = playListName
                    m3UPlaylist.playlistParams = fileParams
                } else {
                    m3UPlaylist.playlistName = "Noname Playlist"
                    m3UPlaylist.playlistParams = "No Params"
                }
            } else {
                val playlistItem = M3UItem()
                val dataArray = currLine.split(",").toTypedArray()
                if (dataArray[0].contains(EXT_LOGO)) {
                    val duration =
                        dataArray[0].substring(0, dataArray[0].indexOf(EXT_LOGO)).replace(":", "")
                            .replace("\n", "")
                    val icon =
                        dataArray[0].substring(dataArray[0].indexOf(EXT_LOGO) + EXT_LOGO.length)
                            .replace("=", "").replace("\"", "").replace("\n", "")
                    playlistItem.itemDuration = duration
                    playlistItem.itemLogo = icon
                } else {
                    val duration = dataArray[0].replace(":", "").replace("\n", "")
                    playlistItem.itemDuration = duration
                    playlistItem.itemLogo = ""
                }
                try {
                    val url =
                        dataArray[1].substring(dataArray[1].indexOf(EXT_URL)).replace("\n", "")
                            .replace("\r", "")
                    val name =
                        dataArray[1].substring(0, dataArray[1].indexOf(EXT_URL)).replace("\n", "")
                    playlistItem.itemName = name
                    playlistItem.itemUrl = url
                } catch (e: Exception) {
                    //Bad items exception.
                }
                playlistItems.add(playlistItem)
            }
        }
        m3UPlaylist.playlistItems = playlistItems
        return m3UPlaylist
    }

    companion object {
        private const val EXT_M3U = "#EXTM3U"
        private const val EXT_INF = "#EXTINF:"
        private const val EXT_PLAYLIST_NAME = "#PLAYLIST"
        private const val EXT_LOGO = "tvg-logo"
        private const val EXT_GROUP = "group-title"
        private const val EXT_URL = "http://"
    }
}