package com.huarezreyes.primeraapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class EmpleadosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        leerServicio()
    } /*onCreate*/

    private fun leerServicio() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/empleados.php"

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
    } /*leerServicio*/


    private fun llenarLista(response: String?) {
        val jsonArray = JSONArray(response)
        val arrayList = ArrayList<HashMap<String, String>>() //hasmap? buscar

            for (i in 0 until jsonArray.length()) {

                //puedo usar cualquier nombre en el val
                val apellidos = jsonArray.getJSONObject(i).getString("apellidos")
                val nombres = jsonArray.getJSONObject(i).getString("nombres")
                val cargo = jsonArray.getJSONObject(i).getString("cargo")
                val foto = jsonArray.getJSONObject(i).getString("foto")

                val map = HashMap<String, String>()

                //puedo usar cualquier nombre
                map.put("apellidos", apellidos)
                map.put("nombres", nombres)
                map.put("cargo", cargo)
                map.put("foto", foto)

                arrayList.add(map)
            }

            dibujar(arrayList)
    }


    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            Column {
//                Text(
//                    text = stringResource(id = R.string.title_activity_empleados),
//                    style = MaterialTheme.typography.headlineLarge
//                )

                //es como lazy grid. lazygrid, lazycolumn y lazyrow
                LazyRow(
                    content ={
                        items(
                            items = arrayList,
                            itemContent = {empleado ->
                                Box (
                                    modifier = Modifier.fillParentMaxSize()

                                ) {
                                    Text(text = empleado["apellidos"].toString() + " " + empleado["nombres"].toString() ,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Color.Red
                                    )
                                    Text(
                                        text = empleado["cargo"].toString(),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }

                            }
                        )
                    }
                )
            }

        }
    }
}
