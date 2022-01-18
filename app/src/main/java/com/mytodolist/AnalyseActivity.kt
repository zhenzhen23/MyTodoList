package com.mytodolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_analyse.*
import kotlinx.android.synthetic.main.activity_main.*

class AnalyseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyse)

        btnFinishedList.setOnClickListener {
            Intent(this, FinishedListActivity::class.java).also {
                startActivity(it)
            }
        }

        btnDeletedList.setOnClickListener {
            Intent(this, DeletedListActivity::class.java).also {
                startActivity(it)
            }
        }

        btnBackToMain.setOnClickListener {
            finish()
        }

        btnAnalyse.setOnClickListener {
            Intent(this, PieChartActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}