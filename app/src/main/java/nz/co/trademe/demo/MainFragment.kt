package nz.co.trademe.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveEvents.observe(this, Observer { event ->
            when (event) {
                MainEvent.Pause -> Toast.makeText(context, R.string.countdown_paused, Toast.LENGTH_SHORT).show()
                MainEvent.Resume -> Toast.makeText(context, R.string.countdown_resumed, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.liveState.observe(this, Observer { state ->
            timerTextView.text = "${state.time}"
            when {
                state.paused -> {
                    toggleButton.text = getText(R.string.resume)
                    toggleButton.setOnClickListener { viewModel.resume() }
                }
                else -> {
                    toggleButton.text = getText(R.string.pause)
                    toggleButton.setOnClickListener { viewModel.pause() }
                }
            }
        })

        incrementButton.setOnClickListener {
            viewModel.increment()
        }
    }
}
