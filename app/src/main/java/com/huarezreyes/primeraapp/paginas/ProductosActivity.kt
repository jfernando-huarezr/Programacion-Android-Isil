package com.huarezreyes.primeraapp.paginas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.huarezreyes.primeraapp.R
import com.huarezreyes.primeraapp.utils.Total
import org.json.JSONArray

class ProductosActivity : ComponentActivity() {

    var nombre = ""
    var descripcion = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras //para recuperar los datos que reicbe este activity con la clase Bundle
        val idcategoria = bundle!!.getString("idcategoria")
        nombre = bundle.getString("nombre").toString()
        descripcion = bundle.getString("descripcion").toString()

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
        val url = Total.rutaServicio+"productos.php?idcategoria="+idcategoria

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
        val arrayList = ArrayList<HashMap<String, String>>() //hashmap? buscar

        for(i in 0 until jsonArray.length()) {

            //puedo usar cualquier nombre en el val
            val idcategoria = jsonArray.getJSONObject(i).getString("idproducto")
            val nombre = jsonArray.getJSONObject(i).getString("nombre")
            val precio = jsonArray.getJSONObject(i).getString("precio")
            val imagenchica = jsonArray.getJSONObject(i).getString("imagenchica")
            val preciorebajado = jsonArray.getJSONObject(i).getString("preciorebajado")

            val map =  HashMap<String,String>()

            //puedo usar cualquier nombre
            map.put("idproducto", idcategoria)
            map.put("nombre", nombre)
            map.put("precio", precio)
            map.put("imagenchica", imagenchica)
            map.put("preciorebajado", preciorebajado)

            arrayList.add(map)
        }

        dibujar(arrayList)
    }

    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            Column {
                Text(text = nombre,
                style = MaterialTheme.typography.headlineLarge)

                Text(text = descripcion)
                LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
                    items(arrayList.size) { index ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(all = dimensionResource(id = R.dimen.espacio2))
                                .shadow(
                                    elevation = dimensionResource(id = R.dimen.espacio2)
                                )
                                //para que vaya al ProductoDetalleActivity
                                .clickable {
                                    seleccionarProducto(
                                        arrayList[index]
                                            .get("idproducto")
                                            .toString()
                                    )
                                }

                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                                    .height(235.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {


                                val precio: Float = arrayList[index].get("precio")!!.toFloat()
                                val precioRebajado: Float = arrayList[index].get("preciorebajado")!!.toFloat()



                                Box {

                                    if(arrayList[index].get("imagenchica").toString() =="null") {
                                        Image(painter = painterResource(id = R.drawable.noimage), contentDescription = null,
                                            )
                                    }

                                    else {
                                        AsyncImage(model = Total.rutaServicio + arrayList[index].get("imagenchica").toString() , contentDescription = null,
                                            modifier = Modifier.height(130.dp)
                                        )
                                    }


                                    if(precioRebajado !== 0f) {
                                        val descuento: Float = (1- precioRebajado/precio)*100

                                        Text(text = String.format("%.0f", descuento) + "%",
                                            color = Color.White,
                                            modifier = Modifier
                                                .background(Color.Red)
                                                .padding(
                                                    5.dp
                                                ))

//                                        Text(text = "OFERTA!!",
//                                            color = Color.White,
//                                            modifier = Modifier
//                                                .background(Color.Red)
//                                                .padding(
//                                                    5.dp
//                                                ))
                                    }
                                }

                                
                                Text(
                                    text = arrayList[index].get("nombre").toString(),
                                    modifier= Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                var precioVenta = 0f
                                if(precioRebajado == 0f){
                                    precioVenta = precio
                                }
                                else {
                                    precioVenta = precioRebajado
                                }

                                Text(
                                    text = "S/" + String.format("%.2f", precioVenta),
                                )

                                if(precioRebajado !== 0f) {
                                    Text(
                                        text = "S/" + String.format("%.2f", precio),
                                        textDecoration = TextDecoration.LineThrough,
                                        color = Color.Gray
                                    )
                                }

                            }


                        }
                    }
                }) //LazyVerticalGrid
            }
        }
    }

    private fun seleccionarProducto(idproducto: String) {
//      Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show()
        val bundle = Bundle().apply{
            putString("idproducto", idproducto)
        }
        val intent = Intent(this, ProductoDetalleActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}

