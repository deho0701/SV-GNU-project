package com.example.sv_project1.csv_data

import com.example.sv_project1.BuildConfig
import com.opencsv.CSVReader
import java.io.FileReader
import java.io.IOException

class CsvHelper(private val filePath: String) {
    fun readCsv(fileName: String) : List<Array<String>> {
        return try {
            FileReader("$filePath/$fileName").use { fr ->
                CSVReader(fr).use {
                    it.readAll()
                }
            }
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
            listOf()
        }
    }
}