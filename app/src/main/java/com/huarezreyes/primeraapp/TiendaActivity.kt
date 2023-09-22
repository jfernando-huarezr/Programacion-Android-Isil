package com.huarezreyes.primeraapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class TiendaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        leerServicio()
    }

    private fun leerServicio() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/categorias.php"

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
            val idcategoria = jsonArray.getJSONObject(i).getString("idcategoria")
            val nombre = jsonArray.getJSONObject(i).getString("nombre")
            val descripcion = jsonArray.getJSONObject(i).getString("descripcion")
            val total = jsonArray.getJSONObject(i).getString("total")

            val map =  HashMap<String,String>()

            //puedo usar cualquier nombre
            map.put("idcategoria", idcategoria)
            map.put("nombre", nombre)
            map.put("descripcion", descripcion)
            map.put("total", total)

            arrayList.add(map)
        }

        dibujar(arrayList)
    }

    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            Column {
                Text(
                    text = stringResource(id = R.string.title_activity_tienda),
                    style = MaterialTheme.typography.headlineLarge
                )

                //es como lazy grid
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    content ={
                        items(
                            items = arrayList,
                            itemContent = {categoria ->
                                Card (
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0,250,200, 255)
                                    ),
                                    modifier = Modifier
                                        .padding(all = dimensionResource(id = R.dimen.espacio2))
                                        .fillMaxWidth()
                                        .clickable {
                                            seleccionarCategoria(categoria)
                                        }
                                ) {
                                    Column (
                                        modifier = Modifier.padding(all = dimensionResource(id = R.dimen.espacio))
                                    ) {
                                        Text(text = categoria["nombre"].toString(),
                                            style = MaterialTheme.typography.titleLarge,
                                            color = Color.Red
                                        )
                                        Text(text = categoria["descripcion"].toString(),
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(text = stringResource(id = R.string.cantidad_productos) + " "+ categoria["total"].toString())
                                    }

                                }

                            }
                        )
                    }
                )
            }

        }
    }

    private fun seleccionarCategoria(categoria: HashMap<String, String>) {
        val idcategoria = categoria["idcategoria"]
        val nombre = categoria["nombre"]
        val descripcion = categoria["descripcion"]

        Toast.makeText(this,nombre,Toast.LENGTH_SHORT).show()

        val bundle = Bundle().apply {
            putString("idcategoria", idcategoria)
            putString("nombre", nombre)
            putString("descripcion", descripcion)
        }

        startActivity(Intent(this@TiendaActivity, ProductosActivity::class.java))
    }
}
