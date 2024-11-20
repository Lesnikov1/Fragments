package ru.netology.kotlinandroid.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.kotlinandroid.R
import ru.netology.kotlinandroid.activity.NewPostFragment.Companion.textArg
import ru.netology.kotlinandroid.adapter.OnInteractionListener
import ru.netology.kotlinandroid.adapter.PostViewHolder
import ru.netology.kotlinandroid.databinding.FragmentFeedBinding
import ru.netology.kotlinandroid.databinding.FragmentOnePostBinding
import ru.netology.kotlinandroid.dto.Post
import ru.netology.kotlinandroid.viewmodel.PostViewModel

class OnePostFragment : Fragment() {

    val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOnePostBinding.inflate(inflater, container, false)

val viewHolder = PostViewHolder(binding.onePost, object : OnInteractionListener {

    override fun onVideo(post: Post) {
        val videoUrl = post.video
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
        startActivity(intent)
    }

    override fun onLike(post: Post) {
        viewModel.like(post.id)
    }

    override fun onShare(post: Post) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, post.content)
            type = "text/plain"
        }
        val shareIntent =
            Intent.createChooser(intent, getString(R.string.chooser_share_post))
        startActivity(shareIntent)
    }

    override fun onRemove(post: Post) {
        viewModel.removeById(post.id)
    }

    override fun onEdit(post: Post) {
        viewModel.edit(post)
        findNavController().navigate(R.id.action_onePostFragment_to_newPostFragment,
            Bundle().apply { textArg = post.content })
    }

    override fun onClearEdit() {
        viewModel.clearEdit()
    }

    override fun onOpen(post: Post) {
    }

})

//        viewModel.edited.observe(viewLifecycleOwner){
//            if(it.id != 0L){
//                findNavController().navigate(R.id.action_onePostFragment_to_newPostFragment,
//                    Bundle().apply { textArg = it.content })
//            }
//        }

        val id = arguments?.textArg?.toLong() ?: -1
        viewModel.data.observe(viewLifecycleOwner){ posts ->
val post  = posts.find { it.id == id } ?: run  {findNavController().navigateUp()
return@observe
}
            viewHolder.bind(post)
        }
        return binding.root
    }
}