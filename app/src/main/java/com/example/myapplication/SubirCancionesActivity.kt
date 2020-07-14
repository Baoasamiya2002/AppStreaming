package com.example.myapplication

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_subir_canciones.*
import kotlinx.android.synthetic.main.lista_subir_item.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class SubirCancionesActivity : Fragment(), AdapterView.OnItemClickListener, ResultadoListener {

    //listasdeIDs
    var idAutores = ArrayList<Int>()
    var idAlbums = ArrayList<Int>()
    var idGeneros = ArrayList<Int>()

    var nombreArchivos:ArrayList<String> = ArrayList()
    var rutaArchivos:ArrayList<String> = ArrayList()

    lateinit var adaptador: ArrayAdapter<String>
    lateinit var dirRaiz:String

    var archivo64:String = ""

    //LISTA DE SELECCION
    var archivoCancion: File? = null
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
            subirCancion()
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

    fun subirCancion(){
        var nombreCancion = txtNombreCancion.text.toString()
        var nombreAutor:Int = 0
        var nombreAlbum:Int = 0
        var genero:Int = 0

        if (llNombreAutor.visibility == 0){
            //nombreAutor = txtNombreAutor.text.toString()
        } else {
            nombreAutor = idAutores.get(spinAutores.selectedItemPosition)
        }

        if (llNombreAlbum.visibility == 0){
            //nombreAlbum = txtNombreAlbum.text.toString()
        } else {
            nombreAlbum = idAlbums.get(spinAlbum.selectedItemPosition)
        }

        if (llGenero.visibility == 0){
            //genero = txtGenero.text.toString()
        } else {
            genero = idGeneros.get(spinGenero.selectedItemPosition)
        }

        //if(nombreCancion.equals("") or nombreAutor.equals("") or nombreAlbum.equals("") or genero.equals("")){
        if(nombreCancion.equals("")){
            Toast.makeText(activity, "Campos vacios", Toast.LENGTH_SHORT).show()
        } else {
            if(archivoCancion == null){
                Toast.makeText(activity, "Selecciona un archivo", Toast.LENGTH_SHORT).show()
            } else {
                var solicitud = Solicitud(activity)
                val jsonObject = JSONObject()
                jsonObject.put("Nombre_cancion", nombreCancion)
                jsonObject.put("AlbumId", nombreAutor)
                jsonObject.put("ArtistaId", nombreAlbum)
                jsonObject.put("GeneroId", genero)
                jsonObject.put("cancion64", archivo64)
                jsonObject.put("ruta", "")
                solicitud.solicitudPost("/cancion/crear", jsonObject, this)
            }
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var archivo:File = File(rutaArchivos[p2])

        if(archivo.isFile){
            Toast.makeText(activity, archivo.name, Toast.LENGTH_SHORT).show()
            //archivosSeleccionados.add(File(archivo.absolutePath))
            //cargarListaSelec(archivo)
            if(archivo.extension.equals("mp3")){
                archivoCancion = archivo

                //encode b64--------------------------------
                val bytesFile = archivo.readBytes()
                archivo64 = Base64.getEncoder().encodeToString(bytesFile)
                //var soli = Solicitud(activity)
                //soli.solicitudGet("/cancion/archivoCancion/" + archivo64, this)
                //-------------------------------------------

                //txtNombreCancion.setText(archivo.name)
                Toast.makeText(activity, "archivoSeleccionado: " + archivo.name, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Selecciona un archivo mp3", Toast.LENGTH_SHORT).show()
            }

        } else {
            verDir(rutaArchivos[p2])
        }
    }

    fun Cargar_spinner(){
        val solicitud = Solicitud(activity)
        solicitud.solicitudArrayGet("/artista", this)
        solicitud.solicitudArrayGet("/album", this)
        solicitud.solicitudArrayGet("/genero", this)
    }

    override fun getResult(respuesta: JSONObject?) {
        if(respuesta != null){
            if(respuesta.has("id")){
                Toast.makeText(activity,"Canción Subida al servidor",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getArrayResult(respuesta: JSONArray?) {
        if(respuesta != null){
            if(respuesta.getJSONObject(0).has("nombre_artista")){
                var count = 0
                while (count < respuesta.length()){
                    idAutores.add(respuesta.getJSONObject(count).getInt("id"))
                    count++
                }
                var autores = Array(respuesta.length(), { indice -> respuesta.getJSONObject(indice).getString("nombre_artista")})
                var adapterAutor = activity?.applicationContext?.let { ArrayAdapter<String>(it, R.layout.spinner_item, autores) }
                spinAutores.adapter = adapterAutor
            }
            if(respuesta.getJSONObject(0).has("nombre_album")){
                var count = 0
                while (count < respuesta.length()){
                    idAlbums.add(respuesta.getJSONObject(count).getInt("id"))
                    count++
                }
                var albums = Array(respuesta.length(), { indice -> respuesta.getJSONObject(indice).getString("nombre_album")})
                var adapterAlbum = activity?.applicationContext?.let { ArrayAdapter<String>(it, R.layout.spinner_item, albums) }
                spinAlbum.adapter = adapterAlbum
            }
            if(respuesta.getJSONObject(0).has("nombre_genero")){
                var count = 0
                while (count < respuesta.length()){
                    idGeneros.add(respuesta.getJSONObject(count).getInt("id"))
                    count++
                }
                var generos = Array(respuesta.length(), { indice -> respuesta.getJSONObject(indice).getString("nombre_genero")})
                var adapterGenero = activity?.applicationContext?.let { ArrayAdapter<String>(it, R.layout.spinner_item, generos) }
                spinGenero.adapter = adapterGenero
            }
        }
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