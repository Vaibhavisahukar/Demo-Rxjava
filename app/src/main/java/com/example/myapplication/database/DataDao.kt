package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.model.ResultsItem

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: List<ResultsItem?>?)

    @Query("SELECT * from ResultsItem")
    fun getData(): LiveData<List<ResultsItem>>

    @Query("DELETE FROM ResultsItem")
    fun deleteUsers()

    @Delete
    fun delete(resultsItem: ResultsItem): Int
}
