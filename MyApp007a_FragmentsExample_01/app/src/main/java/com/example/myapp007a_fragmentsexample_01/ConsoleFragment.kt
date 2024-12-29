package com.example.myapp007a_fragmentsexample_01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapp007a_fragmentsexample_01.databinding.ConsoleFragmentBinding

class ConsoleFragment : Fragment() {

    private var _binding: ConsoleFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_IMAGE_RES_ID = "imageResId"

        fun newInstance(name: String, imageResId: Int): ConsoleFragment {
            val fragment = ConsoleFragment()
            val args = Bundle()
            args.putString(ARG_NAME, name)
            args.putInt(ARG_IMAGE_RES_ID, imageResId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ConsoleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString(ARG_NAME)
        val imageResId = arguments?.getInt(ARG_IMAGE_RES_ID)

        binding.textView.text = name
        imageResId?.let { binding.imageView.setImageResource(it) }

        // Obsluha tlačítka pro návrat na úvodní obrazovku
        binding.btnBack.setOnClickListener {
            (activity as? MainActivity)?.showIntroScreen()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
