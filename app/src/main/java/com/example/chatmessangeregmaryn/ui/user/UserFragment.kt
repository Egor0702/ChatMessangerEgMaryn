package com.example.chatmessangeregmaryn.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.databinding.FragmentAccountBinding
import com.example.chatmessangeregmaryn.databinding.FragmentUserBinding
import com.example.chatmessangeregmaryn.remote.service.ApiService
import com.example.chatmessangeregmaryn.ui.core.BaseFragment
import com.example.chatmessangeregmaryn.ui.core.GlideHelper
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : BaseFragment() {
    override val layoutId = R.layout.fragment_user
    override val titleToolbar = R.string.screen_user
    private lateinit var fragmentUserBinding: FragmentUserBinding

    init {
        Log.d("Egor", "Всем хло мы в UserFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("Egor", "LoginFragment onCreateView()")
        fragmentUserBinding = FragmentUserBinding.inflate(inflater, container, false)
        val view = fragmentUserBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("Egor", "LoginFragment onViewCreated()")
        super.onViewCreated(view, savedInstanceState)

        base {
            val args = intent.getBundleExtra("args")
            if (args != null) {
                val image = args.getString(ApiService.PARAM_IMAGE)
                val name = args.getString(ApiService.PARAM_NAME)
                val email = args.getString(ApiService.PARAM_EMAIL)
                val status = args.getString(ApiService.PARAM_STATUS)

                GlideHelper.loadImage(requireContext(), image, fragmentUserBinding.imgPhoto, R.drawable.ic_account_circle)

                fragmentUserBinding.tvName.text = name
                fragmentUserBinding.tvEmail.text = email
                fragmentUserBinding.tvStatus.text = status

                if (fragmentUserBinding.tvStatus.text.isEmpty()) {
                    fragmentUserBinding.tvStatus.visibility = View.GONE
                    fragmentUserBinding.tvHintStatus.visibility = View.GONE
                }
            }
        }
    }
}