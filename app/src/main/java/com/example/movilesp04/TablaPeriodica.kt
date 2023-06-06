package com.example.movilesp04

import android.content.Context
import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.movilesp04.ui.theme.LightCyan
import com.example.movilesp04.ui.theme.MovilesP04Theme
import com.example.movilesp04.ui.theme.Orange
import com.example.movilesp04.ui.theme.Rose
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStream


class TablaPeriodica : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovilesP04Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting2("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val elementos = LeerArchivo(context)
    val clicked = remember {
        mutableStateOf(false)
    }
    val filtros = remember {
        mutableStateListOf<Any>(true, false, false, "")
    }
    val listaElementos = remember { FiltrarElementos(elementos, filtros) }

    var elemClicked by remember{
        mutableStateOf(Elemento("","",0,"", "","",""))
    }
    var mExpanded by remember {
        mutableStateOf(false)
    }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}
    var mSelectedText by remember { mutableStateOf("") }
    val density = LocalDensity.current
    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top) {
        if (clicked.value){
            AlertSingleChoiceView(state = clicked, elemento = elemClicked)
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Top) {
            stringArrayResource(R.array.Filtros).forEach { filtro ->
                Button(onClick = {
                                 when(filtro){
                                     "Todos" -> {
                                         filtros[1] = false
                                         filtros[2] = false
                                         filtros[0] = true
                                         mSelectedText = ""
                                         filtros[3] = mSelectedText
                                         listaElementos.clear()
                                         listaElementos.addAll(FiltrarElementos(elementos, filtros))
                                     }
                                     "Metales" -> {
                                         filtros[0] = false
                                         filtros[2] = false
                                         filtros[1] = true
                                         mSelectedText = ""
                                         filtros[3] = mSelectedText
                                         listaElementos.clear()
                                         listaElementos.addAll(FiltrarElementos(elementos, filtros))
                                     }
                                     "No metales" -> {
                                         filtros[0] = false
                                         filtros[1] = false
                                         filtros[2] = true
                                         mSelectedText = ""
                                         filtros[3] = mSelectedText
                                         listaElementos.clear()
                                         listaElementos.addAll(FiltrarElementos(elementos, filtros))
                                     }
                                     "Estados" -> {
                                         mExpanded = true

                                     }
                                 }
                                 },
                modifier = Modifier
                    .height(30.dp)
                    .onGloballyPositioned {
                        if (filtro == "Estados") {
                            mTextFieldSize = it.size.toSize()
                        }
                    }) {
                    Text(text = filtro,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center)
                    if(filtro == "Estados"){
                        DropdownMenu(
                            expanded = mExpanded,
                            onDismissRequest = { mExpanded = false },
                            modifier = Modifier
                                .width(with(density){mTextFieldSize.width.toDp()})
                        ) {
                            stringArrayResource(R.array.Estados).forEach { label ->
                                DropdownMenuItem(text = { Text(text = label) }, onClick = {
                                    mSelectedText = label
                                    mExpanded = false
                                    filtros[0] = false
                                    filtros[1] = false
                                    filtros[2] = false
                                    filtros[3] = mSelectedText
                                    listaElementos.clear()
                                    listaElementos.addAll(FiltrarElementos(elementos, filtros))
                                })
                            }
                        }
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(15.dp))
        LazyColumn() {
            items(listaElementos) { elemento ->
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    elemento.forEach{
                        Box(
                            modifier = Modifier
                                .size(100.dp, 100.dp)
                                .background(ColorElemento(it))
                                .clickable(true) {
                                    clicked.value = true
                                    elemClicked = it
                                }
                        ) {
                            Column(modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly) {
                                Text(text = it.getSimbolo(),
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = it.getNombre())
                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

}

@Composable
fun CommonDialog(
    title: String?,
    subtitle: String?,
    state: MutableState<Boolean>,
    content: @Composable (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = {
            state.value = false
        },
        title = title?.let {
            {
                Column( Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = title,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                    subtitle?.let {
                        Text(text = subtitle,
                            fontSize = 20.sp
                            )
                    }
                }
            }
        },
        text = content,
        confirmButton = {
            TextButton(onClick = { state.value = false }) { Text("OK") }
        }
    )
}

@Composable
fun AlertSingleChoiceView(state: MutableState<Boolean>, elemento: Elemento) {
    CommonDialog(title = elemento.getSimbolo(), state = state, subtitle = elemento.getNombre()) { SingleChoiceView(elemento) }
}

@Composable
fun SingleChoiceView(elemento: Elemento) {
    Column(Modifier.wrapContentHeight()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                Text(
                    text = "Numero Atomico: ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Text(
                    text = elemento.getNumeroAtomico().toString(),
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                Text(
                    text = "Peso Atomico: ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp)
                Text(
                    text = elemento.getPesoAtomico(),
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                Text(
                    text = "N° Oxidacion: ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp)
                Text(
                    text = elemento.getEstadosDeOxidacion(),
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                Text(
                    text = "Estado: ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp)
                Text(
                    text = elemento.getEstado(),
                    fontSize = 15.sp
                )
            }

        }
    }

}

fun ColorElemento(elemento: Elemento): Color{
    val color = when(elemento.getEstado()){
        "solid" -> Orange
        "liquid" -> LightCyan
        "gas" -> Rose
        else -> LightCyan
    }
    return color
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
    clear()
    addAll(newList)
}

fun FiltrarElementos(elementos: ArrayDeque<Elemento>, filtros: SnapshotStateList<Any>): SnapshotStateList<MutableList<Elemento>>{
    val lista: SnapshotStateList<MutableList<Elemento>> = mutableStateListOf(mutableListOf())
    if(filtros[0] as Boolean){
        var listaAux: MutableList<Elemento> = mutableListOf()
        elementos.forEach{ elemento: Elemento ->
            if(listaAux.size == 2){
                lista.add(listaAux)
                listaAux = mutableListOf()
            }
            listaAux.add(elemento)
        }
        if(!lista.contains(listaAux)){
            lista.add(listaAux)
        }
    } else {
        var listaAux: MutableList<Elemento> = mutableListOf()
        elementos.forEach{ elemento: Elemento ->
            if(listaAux.size == 2){
                lista.add(listaAux)
                listaAux = mutableListOf()
            }
            if(elemento.isMetal() && filtros[1] as Boolean){
                listaAux.add(elemento)
            }
            if(!elemento.isMetal() && filtros[2] as Boolean){
                listaAux.add(elemento)
            }
            if(elemento.getEstado() == filtros[3] as String){
                listaAux.add(elemento)
            }

        }
        if(!lista.contains(listaAux)){
            lista.add(listaAux)
        }
    }
    return lista
}

fun LeerArchivo(context: Context): ArrayDeque<Elemento> {
    val listaElementos: ArrayDeque<Elemento> = ArrayDeque(50)
    try {
        val assetManager: AssetManager = context.assets
        val files = assetManager.list("")
        if(files != null){
            Log.i("Path", assetManager.toString())
            Log.i("Files", files.size.toString())
            files.forEach { item ->
                Log.d("File", item.toString())
            }
        }
        Log.d("Assests", assetManager.list("").toString())
        val instream: InputStream = assetManager.open("elements.csv")
        val br = BufferedReader(instream.reader())
        var texto = br.readLine()
        while (br.readLine().also { texto = it } != null) {
            val array = texto.split(",")
            val elementoTablaPeriodica = Elemento(array[1], array[2], array[0].toInt(), array[3], array[12].replace(";",","), array[13], array[18])
            if(listaElementos.size < 50){
                listaElementos.addLast(elementoTablaPeriodica)
            }
        }
        Log.e("Exito","Archivo leido")
    } catch (ex: FileNotFoundException) {
        Log.e("Archivo","Error, archivo no encontrado")
        ex.printStackTrace()
    } catch (e: Exception) {
        Log.e("Error", "Error al leer el archivo")
        e.printStackTrace()
    }
    return listaElementos
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MovilesP04Theme {
        Greeting2("Android")
    }
}