package com.huarezreyes.primeraapp.paginas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.huarezreyes.primeraapp.R
import com.huarezreyes.primeraapp.utils.Total
import org.json.JSONArray

class ProductoDetalleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //para recuperar los datos qu recibe este activity con la clase Bundle
        val bundle = intent.extras
        val idproducto = bundle!!.getString("idproducto")

        leerServicio(idproducto)
    }

    private fun leerServicio(idproducto : String?) {

        val queue = Volley.newRequestQueue(this)
        val url = Total.rutaServicio+"productos.php?idproducto="+idproducto

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("DATOS", response)
                dibujar(response)
            },
            {
                Log.d("DATOSERROR", it.message.toString())
            })

        queue.add(stringRequest)
    }

    private fun dibujar(response: String?) {

        val jsonArray = JSONArray(response)

        val idcategoria = jsonArray.getJSONObject(0).getString("idproducto")
        val nombre = jsonArray.getJSONObject(0).getString("nombre")
        val precio = jsonArray.getJSONObject(0).getString("precio")
        val imagengrande = jsonArray.getJSONObject(0).getString("imagengrande")
        val preciorebajado = jsonArray.getJSONObject(0).getString("preciorebajado")
        val detalle = jsonArray.getJSONObject(0).getString("detalle")
        val unidadesenexistencia = jsonArray.getJSONObject(0).getString("unidadesenexistencia")
        val categoria = jsonArray.getJSONObject(0).getString("categoria")
        val proveedor = jsonArray.getJSONObject(0).getString("proveedor")
        val pais = jsonArray.getJSONObject(0).getString("pais")
        val telefono = jsonArray.getJSONObject(0).getString("telefono")
        val descripcion = jsonArray.getJSONObject(0).getString("descripcion")

        setContent {
            Column (
                modifier = Modifier.padding(all = 16.dp)
            ) {
                if(imagengrande == "null"){
                    Image(painter = painterResource(id = R.drawable.noimage), contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop)
                }
                else {
                    AsyncImage(
                        model = Total.rutaServicio + imagengrande,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop)
                }


                Text(text = nombre,
                    modifier = Modifier.width(200.dp),
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(text = detalle)

                Row {
                    Text(text = "Stock",
                        modifier = Modifier.width(200.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = unidadesenexistencia)
                }

                Row {
                    Text(text = "Categoria",
                        modifier = Modifier.width(200.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = categoria)
                }

                Row {
                    Text(text = "Marca",
                        modifier = Modifier.width(200.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = proveedor)
                }

                Row {
                    Text(text = "Origen",
                        modifier = Modifier.width(200.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = pais)
                }

                Row {
                    Text(text = "Atencion al cliente",
                        modifier = Modifier.width(200.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = telefono)
                }

                Text(text = HtmlCompat.fromHtml(descripcion, HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_DIV).toString())
            }
        }
    }
}
