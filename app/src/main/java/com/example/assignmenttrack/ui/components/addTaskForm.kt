package com.example.assignmenttrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
//import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import java.time.Instant
import java.time.ZoneId
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import java.time.LocalDateTime
import java.time.LocalTime



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TaskForm(modifier: Modifier = Modifier){
    var assignmentType by remember { mutableStateOf("") }
    var assignmentTitle by remember { mutableStateOf("") }
    var assignmentDescription by remember { mutableStateOf("") }

    // Store input form for Date amd time
    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            FormField1(
                title = "Assignment Type",
                value = assignmentType,
                onValueChange = { assignmentType = it }
            )
        }

        item {
            Spacer(modifier = Modifier.padding(8.dp))
        }

        item {
            FormField1(
                title = "Assignment Title",
                value = assignmentTitle,
                onValueChange = { assignmentTitle = it }
            )
        }

        item {
            Spacer(modifier = Modifier.padding(8.dp))
        }

        item {
            FormFieldDateTime(
                title = "Due Date & Time",
                dateTime = selectedDateTime,
                onDateClick = { showDatePicker = true },
                onTimeClick = { showTimePicker = true }
            )
        }

        item {
            Spacer(modifier = Modifier.padding(8.dp))
        }

        item {
            FormField2(
                title = "Description",
                value = assignmentDescription,
                onValueChange = { assignmentDescription = it }
            )
        }


        item {
            Spacer(modifier = Modifier.padding(16.dp))
        }

        item {
            GeneralSubmitButton(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp),
                text = "Tambah Tugas",
                onClick = { /* TODO: Implement form submission logic */ }
            )
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val selectedDate = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                        selectedDateTime = selectedDateTime.with(selectedDate)
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }



//    Handle the time picker dialogue
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = selectedDateTime.hour,
            initialMinute = selectedDateTime.minute,
            is24Hour = false
        )
        Dialog(onDismissRequest = { showTimePicker = false }) {
            Column(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = timePickerState)
                Row {
                    TextButton(onClick = { showTimePicker = false }) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                        selectedDateTime = selectedDateTime.with(selectedTime)
                        showTimePicker = false
                    }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}
