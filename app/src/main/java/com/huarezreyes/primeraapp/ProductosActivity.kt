package com.huarezreyes.primeraapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class ProductosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras //para recuperar los datos que reicbe este activity con la clase Bundle
        val idcategoria = bundle!!.getString("idcategoria")
        val nombre = bundle!!.getString("nombre")
        val descripcion = bundle!!.getString("descripcion")

        Log.d("idcategoria", idcategoria.toString())

        leerServicio(idcategoria)

        setContent {
            Column {
                Text(text = nombre!!,
                    style = MaterialTheme.typography.headlineLarge
                    )
                Text(text = descripcion!!)
            }
        }
    }

    private fun leerServicio(idcategoria : String?) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/productos.php?idcategoria="+idcategoria

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("DATOS", response)
                //llenarLista(response)
            },
            {
                Log.d("DATOSERROR", it.message.toString())
            })

        queue.add(stringRequest)
    }
}

