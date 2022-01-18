package com.mytodolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import kotlinx.android.synthetic.main.activity_pie_chart.*

class PieChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)

        var unfinished = 0
        var finished = 0
        var deleted = 0

        for (item in getItemsList()){
            if (item.isFinished == 1) finished++
            else if (item.isDeleted == 1) deleted++
            else unfinished++
        }

        val numbers = listOf(unfinished, finished, deleted)
        val type = listOf("Unfinished", "Finished", "Deleted")
        var chart: AnyChartView = pieChart

        configChartView(numbers, type, chart)

        btnBackToAnalyse.setOnClickListener {
            finish()
        }
    }

    private fun configChartView(numbers: List<Int>, type: List<String>, chart: AnyChartView) {
        val pie : Pie = AnyChart.pie()

        val dataPieChart: MutableList<DataEntry> = mutableListOf()

        for(index in numbers.indices){
            dataPieChart.add(ValueDataEntry(type.elementAt(index), numbers.elementAt(index)))
        }

        pie.data(dataPieChart)
        chart!!.setChart(pie)
    }

    private fun getItemsList(): ArrayList<TodoModelClass> {
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        return databaseHandler.viewTodo()
    }
}