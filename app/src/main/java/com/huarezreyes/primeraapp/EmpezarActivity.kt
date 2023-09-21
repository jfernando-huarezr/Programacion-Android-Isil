package com.huarezreyes.primeraapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huarezreyes.primeraapp.ui.theme.PrimeraAppTheme

class EmpezarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Box {

                    Image(
                        painter = painterResource(id = R.drawable.imagen_empezar),
                        contentDescription = stringResource(
                            id = R.string.imagen_empezar
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        contentScale = ContentScale.Crop
                    );
                    Surface(
                        color = Color.Black.copy(alpha = 0.7f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart)

                    ) {


                        Text(
                            text = stringResource(id = R.string.title_activity_empezar),
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White,
                            modifier = Modifier.padding(
                                horizontal = dimensionResource(id = R.dimen.espacio1),
                                vertical = 8.dp
                            )
                        )
                    }
                }
                
                Text(text = stringResource(id = R.string.frase_empezar),
                    Modifier.padding(all = dimensionResource(id = R.dimen.espacio)),
                    textAlign = TextAlign.Center)

                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ){
                    Row(
                        modifier = Modifier.padding(all = dimensionResource(id = R.dimen.espacio1)).fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = {
                            startActivity(Intent(this@EmpezarActivity, TerminosActivity::class.java))
                        }, modifier = Modifier.padding(end = 4.dp)) {
                            Text(text = stringResource(id = R.string.termino))
                        }

                        Button(onClick = {
                            startActivity(Intent(this@EmpezarActivity, EscritorioActivity::class.java))
                        }) {
                            Text(text = stringResource(id = R.string.empiece))
                        }
                    }
                }


            }

        }
    }
}

