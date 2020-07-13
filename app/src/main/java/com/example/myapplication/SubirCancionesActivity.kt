package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_subir_canciones.*
import kotlinx.android.synthetic.main.lista_subir_item.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class SubirCancionesActivity : Fragment(), AdapterView.OnItemClickListener, ResultadoListener {
    var nombreArchivos:ArrayList<String> = ArrayList()
    var rutaArchivos:ArrayList<String> = ArrayList()

    lateinit var adaptador: ArrayAdapter<String>
    lateinit var dirRaiz:String

    //LISTA DE SELECCION
    lateinit var archivoCancion : File
    var archivosSeleccionados:ArrayList<File> = ArrayList()
    lateinit var adapter2: ArrayAdapter<String>
    var nombresSelec: ArrayList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_subir_canciones, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dirRaiz = Environment.getExternalStorageDirectory().path

        Cargar_spinner()
        lstFiles.setOnItemClickListener(this)
        verDir(dirRaiz)

        btnCargar.setOnClickListener(){

            var nombreCancion = txtNombreCancion.text.toString()
            var nombreAutor:String
            var nombreAlbum:String
            var genero:String

            if (llNombreAutor.visibility == 0){
                nombreAutor = txtNombreAutor.text.toString()
            } else {
                nombreAutor = spinAutores.getSelectedItem().toString()
            }

            if (llNombreAlbum.visibility == 0){
                 nombreAlbum = txtNombreAlbum.text.toString()
            } else {
                 nombreAlbum = spinAlbum.getSelectedItem().toString()
            }

            if (llGenero.visibility == 0){
                 genero = txtGenero.text.toString()
            } else {
                 genero = spinGenero.getSelectedItem().toString()
            }
            if(nombreCancion.equals("") or nombreAutor.equals("") or nombreAlbum.equals("") or genero.equals("")){
                Toast.makeText(activity, "Campos vacios", Toast.LENGTH_SHORT).show()
            } else {
                if(archivoCancion == null){
                    Toast.makeText(activity, "Selecciona un archivo", Toast.LENGTH_SHORT).show()
                } else {
                    var solicitud = Solicitud(activity)
                    val jsonObject = JSONObject()
                    jsonObject.put("NombreCancion", nombreCancion)
                    jsonObject.put("NombreAutor", nombreAutor)
                    jsonObject.put("NombreAlbum", nombreAlbum)
                    jsonObject.put("Genero", genero)
                    solicitud.solicitudPost("/cancion/crear", jsonObject, this)
                }
            }
        }

        //BOTONES ADD
        btnAddAutores.setOnClickListener(){
            llAutorSpinn.visibility = View.INVISIBLE
            llNombreAutor.visibility = View.VISIBLE
        }
        btnAddAlbum.setOnClickListener(){
            llAlbumSpinn.visibility = View.INVISIBLE
            llNombreAlbum.visibility = View.VISIBLE
        }
        btnAddGenero.setOnClickListener(){
            llGeneroSpinn.visibility = View.INVISIBLE
            llGenero.visibility = View.VISIBLE
        }
    }

    fun verDir(rutaDirectorio:String){
        nombreArchivos.clear()
        rutaArchivos.clear()
        var count = 0
        var dirActual = File(rutaDirectorio)

        if(dirActual.listFiles() == null){
            println("null")
            Toast.makeText(activity, "Directorio Invalido", Toast.LENGTH_SHORT).show()
        } else {

            var listaArchivos: Array<File> = dirActual.listFiles()

            if (rutaDirectorio != dirRaiz) {
                nombreArchivos.add("../")
                rutaArchivos.add(dirActual.parent)
                count = 1
            }

            for (archivo in listaArchivos) {
                rutaArchivos.add(archivo.path)
            }

            Collections.sort(rutaArchivos, String.CASE_INSENSITIVE_ORDER)

            var x = count
            while (x < rutaArchivos.size) {
                var archivo: File = File(rutaArchivos[x])
                if (archivo.isFile) {
                    nombreArchivos.add(archivo.name)
                } else {
                    nombreArchivos.add("/" + archivo.name)
                }
                x++
            }
            if (listaArchivos.isEmpty()) {
                nombreArchivos.add("No hay archivo")
                rutaArchivos.add(rutaDirectorio)
            }
        }
        adaptador = activity?.applicationContext?.let { File_Adapter(it, nombreArchivos, this.layoutInflater) }!!
        lstFiles.adapter = adaptador
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var archivo:File = File(rutaArchivos[p2])

        if(archivo.isFile){
            Toast.makeText(activity, archivo.name, Toast.LENGTH_SHORT).show()
            //archivosSeleccionados.add(File(archivo.absolutePath))
            //cargarListaSelec(archivo)
            archivoCancion = archivo
        } else {
            verDir(rutaArchivos[p2])
        }
    }

    fun Cargar_spinner(){
        var autores: Array<String> = arrayOf("autor1", "autor2")
        var albums: Array<String> = arrayOf("album1", "album2")
        var generos: Array<String> = arrayOf("genero1", "genero2")

        var adapterAutor = activity?.applicationContext?.let { ArrayAdapter<String>(it, R.layout.spinner_item, autores) }
        spinAutores.adapter = adapterAutor

        var adapterAlbum = activity?.applicationContext?.let { ArrayAdapter<String>(it, R.layout.spinner_item, albums) }
        spinAlbum.adapter = adapterAlbum

        var adapterGenero = activity?.applicationContext?.let { ArrayAdapter<String>(it, R.layout.spinner_item, generos) }
        spinGenero.adapter = adapterGenero
    }

    override fun getResult(respuesta: JSONObject?) {

    }

    override fun getArrayResult(respuesta: JSONArray?) {
        
    }
}

class File_Adapter(private val mContext: Context, private val files: ArrayList<String>,
                   private val mLayoutInflater: LayoutInflater) : ArrayAdapter<String>(mContext, 0, files){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.lista_subir_item, parent, false)

        val file = files[position]

        layout.txtFile.text = file

        return layout
    }

}