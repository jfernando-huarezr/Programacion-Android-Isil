package com.huarezreyes.primeraapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huarezreyes.primeraapp.ui.theme.PrimeraAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //para ponerlos en columna
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(id = R.dimen.espacio)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {


                Text(
                    text = stringResource(id = R.string.mensaje),
                    style = TextStyle(
                        fontSize = 32.sp
                    )
                )
                Text(
                    text = stringResource(id = R.string.autor),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Column(
                //darle todo el ancho de la pantalla
                modifier = Modifier.fillMaxSize(),
                //centrarlo horizontalmente
                horizontalAlignment = Alignment.CenterHorizontally,
                //centrarlo verticalmente
                verticalArrangement = Arrangement.Center
            ) {// para poder ordenarlos en columna

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(id = R.string.logo)
                );

                Text(
                    text = stringResource(id = R.string.saludo),
                    style = TextStyle(
                        //tamaño de la letra
                        fontSize = 48.sp,
                        //grosor de la letra
                        fontWeight = FontWeight.Bold
                    )
                );

                Button(onClick = {
                    startActivity(Intent(this@MainActivity, EmpezarActivity::class.java))
                }) {
                    Text(text = stringResource(id = R.string.inicio))
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = stringResource(id = R.string.derechos),
                    modifier = Modifier.padding(
                        bottom = dimensionResource(
                            id = R.dimen.espacio
                        )
                    )
                )
                //usualmente se usan medidas fijas.
            }


        }
    }
}
