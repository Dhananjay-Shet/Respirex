package com.respirex.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.respirex.data.ReportRepository

@Composable
fun HistoryScreen(report: (Int) -> Unit) {

    val reportList = ReportRepository.report?.observeAsState()?.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (reportList==null) {
            Text(
                ReportRepository.value.observeAsState().value.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(50.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                ReportRepository.value.observeAsState().value.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(top = 50.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            LazyColumn(modifier = Modifier.padding(top = 20.dp)){
                items(reportList.size) { item ->
                    Card(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 5.dp
                        ),
                        modifier = Modifier
                            .clickable {report(item)}
                            .width(350.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Column(Modifier.padding(5.dp)) {
                            Row {
                                Text(
                                    "Name : ",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 20.sp
                                )
                                Text(
                                    reportList[item].patient.firstname+" "+reportList[item].patient.lastname,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 20.sp
                                )
                            }
                            Row {
                                Text(
                                    "Result : ",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 20.sp
                                )
                                Text(
                                    reportList[item].result,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

        }
    }
}
