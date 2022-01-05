package com.example.notebook.feature_notebook.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.notebook.feature_notebook.data.data_source.NotebookDao
import com.example.notebook.feature_notebook.data.data_source.NotebookDatabase
import com.example.notebook.feature_notebook.domain.model.entities.People
import com.example.notebook.ui.theme.NotebookTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var database: NotebookDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao: NotebookDao = database.notebookDao
        database.notebookDao.getEntries()

      GlobalScope.launch{
          database.notebookDao.insertEntry(People(2, "Иван", "Алексеенко",
          "Игоревич", "21.02.2000", "Москва; Ул. Чайковского 45", "89504547865", 3232, 1, 1, 1, 1))
      }
        setContent {
            NotebookTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotebookTheme {
        Greeting("Android")
    }
}