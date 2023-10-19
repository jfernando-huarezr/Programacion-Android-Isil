package com.huarezreyes.primeraapp.paginas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.huarezreyes.primeraapp.R
import com.huarezreyes.primeraapp.utils.Total
import org.json.JSONArray

class DirectoresActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        leerServicio()
    }

    private fun leerServicio() {
        val queue = Volley.newRequestQueue(this)
        val url = Total.rutaServicio+ "directores.php"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("DATOS", response)
                llenarLista(response)
            },
            {
                Log.d("DATOSERROR", it.message.toString())
            })

        queue.add(stringRequest)
    }

    private fun llenarLista(response: String?) {
        val jsonArray = JSONArray(response)
        val arrayList = ArrayList<HashMap<String, String>>() //hasmap? buscar

        for(i in 0 until jsonArray.length()) {

            //puedo usar cualquier nombre en el val
            val iddirector = jsonArray.getJSONObject(i).getString("iddirector")
            val nombres = jsonArray.getJSONObject(i).getString("nombres")
            val peliculas = jsonArray.getJSONObject(i).getString("peliculas")

            val map =  HashMap<String,String>()

            //puedo usar cualquier nombre
            map.put("iddirector", iddirector)
            map.put("nombres", nombres)
            map.put("peliculas", peliculas)

            arrayList.add(map)
        }

        dibujar(arrayList)
    }

    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {

            Box {

                Column {
                    Text(
                        text = stringResource(id = R.string.title_activity_directores),
                        style = MaterialTheme.typography.headlineLarge
                    )

                    //es como lazy grid
                    LazyColumn(
                        content ={
                            items(
                                items = arrayList,
                                itemContent = {director ->
                                    Row (
                                        modifier = Modifier
                                            .padding(horizontal = 32.dp, vertical = 8.dp)
                                            .fillMaxWidth()

                                    ) {
                                        Text(text = director["iddirector"].toString(),
                                            style = MaterialTheme.typography.titleLarge,
                                            modifier = Modifier.width(50.dp),
                                            color = Color.Red
                                        )

                                        Column {
                                            Text(text = director["nombres"].toString(),
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                            Text(text = director["peliculas"].toString())
                                        }

                                    }

                                }
                            )
                        }
                    )
                }

                FloatingActionButton(onClick = {
                    startActivity(Intent(this@DirectoresActivity, DirectoresInsertActivity::class.java))
                }, modifier = Modifier
                    .padding(all = 20.dp)
                    .align(Alignment.BottomEnd)) {
                    Icon(Icons.Filled.Add , contentDescription = null )

                }

            }


        }
    }
}