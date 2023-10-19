package com.huarezreyes.primeraapp.paginas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.huarezreyes.primeraapp.utils.Total

@OptIn(ExperimentalMaterial3Api::class)
class DirectoresInsertActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var nombres by remember{ mutableStateOf("") }
            var peliculas by remember{ mutableStateOf("") }

            Column (modifier = Modifier.padding(all=32.dp)) {

                TextField(value = nombres,
                    label = { Text(text = "Nombre del director")},
                    onValueChange = {
                        nombres = it
                    })
                
                Spacer(modifier = Modifier.size(16.dp))
                TextField(value = peliculas,
                    label = { Text(text = "Peliculas")},
                    onValueChange = {
                        peliculas = it
                    })

                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    leerServicioGuardar(nombres, peliculas)
                }) {
                    Text(text = "Registrar director")
                }

            }

        }
    }

    private fun leerServicioGuardar(nombres: String, peliculas: String) {
        //Toast.makeText(this, nombres + " - "+ peliculas, Toast.LENGTH_SHORT).show()
        val queue = Volley.newRequestQueue(this)
        val url = Total.rutaServicio+"directoresinsert.php"

        // Request a string response from the provided URL.
        val stringRequest = object: StringRequest(
            Request.Method.POST, url,
            { response ->
                Log.d("DATOS", response)
                Toast.makeText(this, "nuevo director " + response, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DirectoresActivity::class.java))
            },
            {
                Log.d("DATOSERROR", it.message.toString())
            }){
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params.put("nombres", nombres)
                    params.put("peliculas", peliculas)
                    return params
                }


            }

        queue.add(stringRequest)
    }
}


