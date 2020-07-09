package com.example.myapplication

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.MediaController
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_reproductor.*
import java.util.concurrent.TimeUnit

class ReproductorActivity : AppCompatActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    var flagPP = false

    //SOBRE  ESCRITURA DEL METODO OnClick
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnPlayPause -> {
                if (flagPP == false) {
                    audioPlay()
                    flagPP = true
                    //btnPlayPause.setBackgroundResource()
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
    var colaReproduccion:ArrayList<Int> = ArrayList()        // val colaReproduccion = intArrayOf(R.raw.song1, R.raw.song2, R.raw.song3)
    var colaNombres:ArrayList<String> = ArrayList()              //val nombres = arrayListOf<String>("cancion1", "cancion2", "cancion3")
    var colaAlbunes:ArrayList<String> = ArrayList()
    var colaImagenes:ArrayList<Int> = ArrayList()

    var posCola = 0

    var handler = Handler()

    lateinit var mediaPlayer: MediaPlayer
    lateinit var  mediaController: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reproductor)


        cancionesCola = intent.getSerializableExtra("lista") as ArrayList<Cancion>
        cargarColas()

        //REPRODUCTOR
        mediaController = MediaController(this)
        seekSong.max = 100
        seekSong.progress = 0
        seekSong.setOnSeekBarChangeListener(this)
        btnPlayPause.setOnClickListener(this)
        btnPrev.setOnClickListener(this)
        btnNext.setOnClickListener(this)

        audioStart(posCola)
        flagPP = true
    }

    fun cargarColas(){
        var x = 0
        while (x < cancionesCola.size){
            colaReproduccion.add(cancionesCola[x].song)
            colaNombres.add(cancionesCola[x].nombreCancion)
            colaAlbunes.add(cancionesCola[x].album)
            colaImagenes.add(cancionesCola[x].imagen)
            x++
        }
    }

    fun milliToString(ms : Int):String{
        var segundos:Long = TimeUnit.MILLISECONDS.toSeconds(ms.toLong())
        val minutos:Long = TimeUnit.SECONDS.toMinutes(segundos)
        segundos = segundos % 60
        return "$minutos : $segundos"
    }

    fun audioStart(pos : Int){
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
}