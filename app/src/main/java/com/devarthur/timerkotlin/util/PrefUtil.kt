package com.devarthur.timerkotlin.util

import android.content.Context
import android.preference.PreferenceManager
import com.devarthur.timerkotlin.MainActivity

/**
 * Created by Arthur Gomes at 2019-07-16
 * Contact : arthur.gomes_4718@hotmail.com
 * Copyright : SportsMatch 2019
 * Utilities class created to store user data inside the device.
 *
 */
class PrefUtil {
    companion object{

        fun getTimerLenght(context : Context): Int{
            //placeholder
            return 1
        }

        private const val PREVIOUS_TIMER_LENGHT_SECONDS_ID = "com.devarthur4718.timerkotlin.previous_time_lenght"

        fun getPreviousTimerLenghtInSeconds(context : Context): Long{
            val prefereces = PreferenceManager.getDefaultSharedPreferences(context)
            return prefereces.getLong(PREVIOUS_TIMER_LENGHT_SECONDS_ID, 0 )

        }

        fun setPreviousTimerLenghtInSeconds(seconds : Long, context: Context){

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_TIMER_LENGHT_SECONDS_ID, seconds)
            editor.apply()
        }

        private const val TIMER_STATE_ID = "com.devarthur4718.timerkotlin.timer_state"

        fun getTimerState(context: Context) : MainActivity.TimerState{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preferences.getInt(TIMER_STATE_ID, 0)
            return MainActivity.TimerState.values()[ordinal]
        }

        fun setTimerState(state : MainActivity.TimerState, context: Context){

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ordinal = state.ordinal
            editor.putInt(TIMER_STATE_ID, ordinal)
            editor.apply()
        }

        private const val SECONDS_REMAINING_ID = "com.devarthur4718.timerkotlin.seconds_remaining"

        fun getSecondsRemaining(context : Context): Long{
            val prefereces = PreferenceManager.getDefaultSharedPreferences(context)
            return prefereces.getLong(SECONDS_REMAINING_ID, 0 )

        }

        fun setSecondsRemaining(seconds : Long, context: Context){

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECONDS_REMAINING_ID, seconds)
            editor.apply()
        }

    }
}