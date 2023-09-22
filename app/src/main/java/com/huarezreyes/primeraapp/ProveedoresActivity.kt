package com.huarezreyes.primeraapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class ProveedoresActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        leerServicio()

    }

    private fun leerServicio() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/proveedores.php"

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
            val nombreempresa = jsonArray.getJSONObject(i).getString("nombreempresa")
            val nombrecontacto = jsonArray.getJSONObject(i).getString("nombrecontacto")
            val cargocontacto = jsonArray.getJSONObject(i).getString("cargocontacto")

            val map =  HashMap<String,String>()

            //puedo usar cualquier nombre
            map.put("nombreempresa", nombreempresa)
            map.put("nombrecontacto", nombrecontacto)
            map.put("cargocontacto", cargocontacto)

            arrayList.add(map)
        }
        
        dibujar(arrayList)
    }

    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            Column {
                Text(
                    text = stringResource(id = R.string.title_activity_proveedores),
                    style = MaterialTheme.typography.headlineLarge
                )

                //es como lazy grid
                LazyColumn(
                    content ={
                       items(
                           items = arrayList,
                           itemContent = {proveedor ->
                               Column (
                                   modifier = Modifier.padding(all = dimensionResource(id = R.dimen.espacio2))
                                       .border(width = 1.dp, color = Color.Gray, shape = RectangleShape)
                                       .padding(all = dimensionResource(id = R.dimen.espacio))
                                       .fillMaxWidth()
                                   
                               ) {
                                   Text(text = proveedor["nombreempresa"].toString(),
                                       style = MaterialTheme.typography.titleLarge,
                                       color = Color.Red
                                   )
                                   Text(text = proveedor["nombrecontacto"].toString(),
                                       style = MaterialTheme.typography.titleMedium
                                   )
                                   Text(text = proveedor["cargocontacto"].toString())
                               }
                               
                           }
                       )
                    }
                )
            }

        }
    }
}


