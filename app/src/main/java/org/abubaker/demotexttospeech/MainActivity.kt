package org.abubaker.demotexttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import org.abubaker.demotexttospeech.databinding.ActivityMainBinding
import java.util.*

// Extending our MainActivity with the TextToSpeech OnInitListener
class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    // Binding Object
    private lateinit var binding: ActivityMainBinding
    // private lateinit var bindContent: ContentMainBinding


    // Variable for TextToSpeech
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setSupportActionBar(binding.toolbar)

        // Initialize the Text To Speech
        tts = TextToSpeech(this, this)

        // Button: Speak
        binding.btnSpeak.setOnClickListener {

            // Check if TEXT Field is empty
            if (binding.etEnteredText.text.isEmpty()) {

                // Show a Toast message if no text to speak is provided
                Toast.makeText(
                    this@MainActivity,
                    "Enter a text to speak.",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                // Speak out the provided text
                speakOut(binding.etEnteredText.text.toString())
            }
        }

    }

    /**
     * This the TextToSpeech override function
     *
     * Called to signal the completion of the TextToSpeech engine initialization.
     */
    override fun onInit(status: Int) {

        //
        if (status == TextToSpeech.SUCCESS) {

            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }

    }

    /**
     * Here is Destroy function we will stop and shutdown the tts which is initialized above.
     */
    public override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        super.onDestroy()
    }

    /**
     * Function is used to speak the text what we pass to it.
     * QUEUE_FLUSH = It will clear the other elements in the Queue
     */
    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}