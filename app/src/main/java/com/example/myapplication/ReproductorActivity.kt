package com.example.myapplication

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.MediaController
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_reproductor.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class ReproductorActivity : AppCompatActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener, ResultadoListener {

    var flagPP = false

    //SOBRE  ESCRITURA DEL METODO OnClick
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnPlayPause -> {
                if (flagPP == false) {
                    audioPlay()
                    flagPP = true
                } else {
                    if (flagPP == true) {
                        audioPause()
                        flagPP = false
                    }
                }
            }
            R.id.btnNext -> {
                audioNext()
                flagPP = true
            }
            R.id.btnPrev -> {
                audioPrev()
                flagPP = true
            }
        }
    }

    //SOBREESCRITURA DE LOS METODOS DE LA SEEKBAR
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
    }
    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }
    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        seekBar?.progress?.let { mediaPlayer.seekTo(it)}
    }

    //COLA GRLOBAL
    //var colaG = ColaReproduccion()
    var cancionesCola:ArrayList<Cancion> = ArrayList()



    //COLA DEL REPRODUCTOR
    //var colaReproduccion:ArrayList<Int> = ArrayList()        // val colaReproduccion = intArrayOf(R.raw.song1, R.raw.song2, R.raw.song3)
    var colaReproduccion:ArrayList<Int> = ArrayList()
    var colaNombres:ArrayList<String> = ArrayList()              //val nombres = arrayListOf<String>("cancion1", "cancion2", "cancion3")
    var colaAlbunes:ArrayList<String> = ArrayList()
    var colaImagenes:ArrayList<Int> = ArrayList()

    var posCola = 0

    var handler = Handler()

    var mediaPlayer: MediaPlayer = MediaPlayer()
    lateinit var  mediaController: MediaController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reproductor)
        supportActionBar?.hide()

        /* CODIGO PARA LA COLA GLOBAL
        *
        * cr = intent.getSerializableExtra("cr") as ColaReproduccion
        * cancionesCola = cr.getCanciones()
        *
        * */
        cancionesCola = intent.getSerializableExtra("lista") as ArrayList<Cancion>


        //REPRODUCTOR
        mediaController = MediaController(this)
        seekSong.max = 100
        seekSong.progress = 0
        seekSong.setOnSeekBarChangeListener(this)
        btnPlayPause.setOnClickListener(this)
        btnPrev.setOnClickListener(this)
        btnNext.setOnClickListener(this)

        if(cancionesCola.size == 0){
            Toast.makeText(applicationContext,"No hay canciones en la cola", Toast.LENGTH_SHORT).show()
        } else {
            cargarColas()
            //audioStart(posCola)
            flagPP = true
        }

    }

    fun cargarColas(){
        var x = 0
        while (x < cancionesCola.size){
            pedirCancion(cancionesCola[x].id)
            colaNombres.add(cancionesCola[x].nombreCancion)
            colaAlbunes.add(cancionesCola[x].album)
            colaImagenes.add(cancionesCola[x].imagen)
            x++
        }
    }

    fun pedirCancion(id: Int){
        val solicitud = Solicitud(this)
        solicitud.solicitudGet("/cancion/byId/" + id, this)
    }

    fun milliToString(ms : Int):String{
        var segundos:Long = TimeUnit.MILLISECONDS.toSeconds(ms.toLong())
        val minutos:Long = TimeUnit.SECONDS.toMinutes(segundos)
        segundos = segundos % 60
        return "$minutos : $segundos"
    }

    fun audioStart(pos : Int){
        //colaReproduccion = intArrayOf(R.raw.song1, R.raw.song2, R.raw.song3)
        mediaPlayer = MediaPlayer.create(this, colaReproduccion[pos])
        seekSong.max = mediaPlayer.duration
        txtMaxTime.setText(milliToString(seekSong.max))
        txtCrTime.setText(milliToString(mediaPlayer.currentPosition))
        seekSong.progress = mediaPlayer.currentPosition
        txtNombreCancion.setText(colaNombres[pos])
        txtNombreAlbum.setText(colaAlbunes[pos])
        btnPlayPause.setImageResource(R.drawable.pause64px)
        imgPortada.setImageResource(colaImagenes[pos])
        mediaPlayer.start()
        var updateSeekBarThread = UpdateSeekBarProgress()
        handler.postDelayed(updateSeekBarThread, 50)
    }

    fun audioNext(){
        if(mediaPlayer.isPlaying)mediaPlayer.stop()
        if(posCola < (colaReproduccion.size -1)){
            posCola++
        } else {
            posCola = 0
        }
        audioStart(posCola)
    }
    fun audioPrev(){
        if(mediaPlayer.isPlaying)mediaPlayer.stop()
        if(posCola > 0 ){
            posCola--
        } else {
            posCola = colaReproduccion.size -1
        }
        audioStart(posCola)
    }
    fun audioPlay(){
        if(mediaPlayer.isPlaying){
            //mediaPlayer.stop()
        } else {
            mediaPlayer.start()
            btnPlayPause.setImageResource(R.drawable.pause64px)
        }
    }
    fun audioPause(){
        if(mediaPlayer.isPlaying)mediaPlayer.pause()
        btnPlayPause.setImageResource(R.drawable.play64px)
    }


    inner class UpdateSeekBarProgress : Runnable{
        override fun run() {
            var currTime:Int = mediaPlayer.currentPosition
            txtCrTime.setText(milliToString(currTime))
            seekSong.progress = currTime
            if(currTime != mediaPlayer.duration) handler.postDelayed(this, 50)
        }

    }

    fun PlayAudio(base64EncodedString: String) {
        try {
            println("ya esta aqui")
            val url = "data:audio/mp3;base64,$base64EncodedString"
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (ex: Exception) {
            print(ex.message)
        }
    }

    private fun playMp3(mp3SoundByteArray: ByteArray) {
        try {
            
            // create temp file that will hold byte array
            val tempMp3: File = File.createTempFile("kurchina", "mp3", cacheDir)
            tempMp3.deleteOnExit()
            val fos = FileOutputStream(tempMp3)
            fos.write(mp3SoundByteArray)
            fos.close()

            // resetting mediaplayer instance to evade problems
            mediaPlayer.reset()
/*
            // In case you run into issues with threading consider new instance like:
            // MediaPlayer mediaPlayer = new MediaPlayer();

            // Tried passing path directly, but kept getting
            // "Prepare failed.: status=0x1"
            // so using file descriptor instead*/
            val fis = FileInputStream(tempMp3)
            mediaPlayer.setDataSource(fis.getFD())

            mediaPlayer.prepare()

            seekSong.max = mediaPlayer.duration
            txtMaxTime.setText(milliToString(seekSong.max))
            txtCrTime.setText(milliToString(mediaPlayer.currentPosition))
            seekSong.progress = mediaPlayer.currentPosition

            mediaPlayer.start()
            var updateSeekBarThread = UpdateSeekBarProgress()
            handler.postDelayed(updateSeekBarThread, 50)
        } catch (ex: IOException) {
            val s: String = ex.toString()
            ex.printStackTrace()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun getResult(respuesta: JSONObject?) {
        if(respuesta != null){
            val cancionByteArray = Base64.getDecoder().decode(respuesta.getString("cancion64"))
            //PlayAudio(respuesta.getString("cancion64"))
            txtNombreCancion.setText(respuesta.getString("nombre_cancion"))
            txtNombreAlbum.setText(respuesta.getString("albumId"))
            playMp3(cancionByteArray)
        }
    }

    override fun getArrayResult(respuesta: JSONArray?) {

    }
}

