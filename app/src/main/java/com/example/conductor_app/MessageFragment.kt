package com.example.conductor_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.resource.gif.GifDrawable


class MessageFragment : Fragment() {

    private lateinit var gifImageView: ImageView
    private lateinit var messageTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_message, container, false)

        // Retrieve the message and GIF resource ID passed from the previous fragment/activity
        val message = arguments?.getString("message")
        val gifResId = arguments?.getInt("gifResId")

        // Initialize the views
        gifImageView = view.findViewById(R.id.gifImageView)
        messageTextView = view.findViewById(R.id.messageTextView)

        // Set the message text
        messageTextView.text = message

        // Load the GIF image and display it in the ImageView
        gifResId?.let { resourceId ->
            val gifDrawable = ContextCompat.getDrawable(requireContext(), resourceId) as? GifDrawable
            gifImageView.setImageDrawable(gifDrawable)
        }

        return view
    }

    companion object {

                }


}