package ru.netology.kotlinandroid.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.kotlinandroid.databinding.FragmentNewPostBinding
import ru.netology.kotlinandroid.util.StringArg
import ru.netology.kotlinandroid.viewmodel.PostViewModel

class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
arguments?.textArg?.let(binding.edit::setText)
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            val text =  binding.edit.text.toString()
            if(text.isNotBlank()){
                viewModel.applyChangesAndSave(text)
            }
          findNavController().navigateUp()
        }
        return binding.root
    }
companion object{
    var Bundle.textArg by StringArg
}
}