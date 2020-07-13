package com.example.myapplication

import android.app.usage.ExternalStorageStats
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.EnvironmentCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_subir_canciones.*
import kotlinx.android.synthetic.main.lista_subir_item.view.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class SubirCancionesActivity : Fragment(), AdapterView.OnItemClickListener {
    var nombreArchivos:ArrayList<String> = ArrayList()
    var rutaArchivos:ArrayList<String> = ArrayList()

    lateinit var adaptador: ArrayAdapter<String>
    lateinit var dirRaiz:String

    //LISTA DE SELECCION
    var archivosSeleccionados:ArrayList<File> = ArrayList()
    lateinit var adapter2: ArrayAdapter<String>
    var nombresSelec: ArrayList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_subir_canciones, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dirRaiz = Environment.getExternalStorageDirectory().path
        //dirRaiz = Environment.getStorageDirectory().absolutePath + "/0B6E-1917"

        Cargar_spinner()
        lstFiles.setOnItemClickListener(this)
        verDir(dirRaiz)


        /*
        btnSD.setOnClickListener{
            dirSD()
        }
        btnInterno.setOnClickListener{
            dirInterno()
        }*/

        btnCargar.setOnClickListener(){

            var nombreCancion = txtNombreCancion.text

            if (llNombreAutor.visibility == 0){
                var nombreAutor = txtNombreAutor.text
            } else {
                var nombreAutor = spinAutores.getSelectedItem()
            }

            if (llNombreAlbum.visibility == 0){
                var nombreAlbum = txtNombreAlbum.text
            } else {
                var nombreAlbum = spinAlbum.getSelectedItem()
            }

            if (llGenero.visibility == 0){
                var genero = txtGenero.text
            } else {
                var genero = spinGenero.getSelectedItem()
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
    /*
    fun dirSD(){
        //dirRaiz = Environment.getStorageDirectory().absolutePath  + "/0B6E-1917"

        verDir(dirRaiz)
    }

    fun dirInterno(){
        dirRaiz = Environment.getExternalStorageDirectory().path
        verDir(dirRaiz)
    }*/

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
        } else {
            verDir(rutaArchivos[p2])
        }
    }
    /*
    fun cargarListaSelec(archivo: File){

        archivosSeleccionados.add(archivo)
        nombresSelec.add(archivo.name)
        adapter2 = activity?.applicationContext?.let { File_Adapter(it, nombresSelec, this.layoutInflater) }!!
        lstSelect.adapter = adapter2
    }*/

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