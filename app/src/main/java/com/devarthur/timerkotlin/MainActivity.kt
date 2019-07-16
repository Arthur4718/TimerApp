package com.devarthur.timerkotlin

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.devarthur.timerkotlin.util.PrefUtil

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

//From: https://www.youtube.com/watch?v=VnRxq4Fpl64&t=143s
class MainActivity : AppCompatActivity() {

    enum class TimerState{
        Stopped, Paused, Running
    }

    private lateinit var timer : CountDownTimer
    private var timerLenghtSeconds =  0L
    private var timerState = TimerState.Stopped
    private var secondsRemaining = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setIcon(R.drawable.ic_timer_black_24dp)
        supportActionBar?.title = "      Timer"


        fab_play.setOnClickListener { v ->
            startTimer()
            timerState = TimerState.Running
            updateButtons()

        }

        fab_pause.setOnClickListener { v ->
            timer.cancel()
            timerState = TimerState.Paused
            updateButtons()
        }

        fab_stop.setOnClickListener { v ->
            timer.cancel()
            onTimerFinished()

        }



    }

    override fun onResume() {
        super.onResume()

        initTimer()

        //Todo : remove background timer, hide nofitication.
    }

    override fun onPause() {
        super.onPause()

        if(timerState == TimerState.Running){
            timer.cancel()
            //Todo : start background timer and show notificatio
        }else if (timerState == TimerState.Paused){
            //Todo show notification
        }

        PrefUtil.setPreviousTimerLenghtInSeconds(timerLenghtSeconds, this@MainActivity)
        PrefUtil.setSecondsRemaining(secondsRemaining, this@MainActivity)
        PrefUtil.setTimerState(timerState, this@MainActivity)
    }

    private fun initTimer(){
        timerState = PrefUtil.getTimerState(this@MainActivity)

        if(timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviusTimerLenght()

        secondsRemaining = if(timerState == TimerState.Running || timerState == TimerState.Paused)
            PrefUtil.getSecondsRemaining(this@MainActivity)
        else
            timerLenghtSeconds

        //TODO : change secondsRemaining while in background.

        if(timerState == TimerState.Running)
            startTimer()

        updateButtons()
        updateCountDownUI()
    }

    private fun onTimerFinished(){
        timerState = TimerState.Stopped

        setNewTimerLength()

        //Progress..

        PrefUtil.setSecondsRemaining(timerLenghtSeconds, this)
        secondsRemaining = timerLenghtSeconds

        updateButtons()
        updateCountDownUI()

    }

    private fun updateButtons(){

        when(timerState){
            TimerState.Running -> {
                fab_play.isEnabled = false
                fab_pause.isEnabled = true
                fab_stop.isEnabled = true
            }

            TimerState.Stopped -> {
                fab_play.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = false

            }
            TimerState.Paused -> {
                fab_play.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = true
            }
        }

    }

    private fun startTimer(){
        timerState = TimerState.Running

        timer = object : CountDownTimer(secondsRemaining / 1000 , 1000){
            override fun onFinish() { onTimerFinished() }

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                updateCountDownUI()
            }

        }.start()


    }

    private fun setNewTimerLength(){
        val lengthInMinutes = PrefUtil.getTimerLenght(this@MainActivity)
        timerLenghtSeconds = (lengthInMinutes * 60L)

    }
    private fun setPreviusTimerLenght(){
        timerLenghtSeconds = PrefUtil.getPreviousTimerLenghtInSeconds(this@MainActivity)
    }

    private fun updateCountDownUI(){
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()

        tvTimerCountdown.text = "$minutesUntilFinished:${
        if (secondsStr.length == 2) secondsStr
        else "0" + secondsStr
        }"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
