package com.example.chatmessangeregmaryn.ui.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.ui.activity.BaseActivity
import com.example.chatmessangeregmaryn.ui.activity.base
import com.example.chatmessangeregmaryn.ui.core.navigation.Navigator
import javax.inject.Inject

abstract class BaseFragment : Fragment() {
    open val titleToolbar = R.string.app_name
    open val showToolbar = true
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var navigator: Navigator


    override fun onResume() {
        super.onResume()

        base {
            if (showToolbar) supportActionBar?.show() else supportActionBar?.hide()
            supportActionBar?.title = getString(titleToolbar)
        }
    }

    open fun onBackPressed() {}


    fun showProgress() = base { progressStatus(View.VISIBLE) }

    fun hideProgress() = base { progressStatus(View.GONE) }


    fun hideSoftKeyboard() = base { hideSoftKeyboard() }


    fun handleFailure(failure: Failure?) = base { handleFailure(failure) }

    fun showMessage(message: String) = base { showMessage(message) }


    inline fun base(block: BaseActivity.() -> Unit) {
        activity.base(block) // activity== getActivity()
    }


    inline fun <reified T : ViewModel> viewModel(body: T.() -> Unit): T {
        val vm = ViewModelProvider(this, viewModelFactory)[T::class.java]
        vm.body()
        return vm
    }

    fun close() = parentFragmentManager.popBackStack() // верно ли указал parent?
}
