package com.sanmiaderibigbe.customfirebaseregistrationapp.ui.registration


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.sanmiaderibigbe.customfirebaseregistrationapp.Navigation2Directions
import com.sanmiaderibigbe.customfirebaseregistrationapp.R
import com.sanmiaderibigbe.customfirebaseregistrationapp.model.User
import com.sanmiaderibigbe.customfirebaseregistrationapp.repo.Resource
import com.sanmiaderibigbe.customfirebaseregistrationapp.repo.Status
import kotlinx.android.synthetic.main.fragment_registration_bank.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 *
 */
class RegistrationBankFragment : Fragment() {

    private val sharedViewModel : RegistrationSharedViewModel
            by viewModel()
    private val viewModel by viewModel<RegistrationBankViewModel>()

    private val args: RegistrationBankFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_bank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btn_register.setOnClickListener {
            val updatedUser = getUserInfo()
            Toast.makeText(activity, "${updatedUser.toString()}", Toast.LENGTH_SHORT).show()
            registerUser(updatedUser, view)

        }
    }

    private fun getUserInfo(): User {
        val accountName = txt_account_name.editText?.text.toString()
        val accountNumber = txt_account_number.editText?.text.toString()
        val bvn = txt_bvn.editText?.text.toString()
        val bankName = txt_bank_name.editText?.text.toString()
        val accountType = txt_account_type.text.toString()


        val user = args.user

        val updatedUser = user.copy(
            accountName = accountName,
            accountNumber = accountNumber,
            BVN = bvn,
            accountType = accountType,
            BankName = bankName
        )
        return updatedUser
    }

    private fun registerUser(
        updatedUser: User,
        view: View
    ) {


        viewModel.register(updatedUser).observe(this, Observer {registrationResource ->
            when (registrationResource.status) {
                Status.SUCCESS -> {
                    if (registrationResource.data!!){
                        Navigation.findNavController(view).navigate(Navigation2Directions.actionGlobalHomeFragment())
                    }

                }
                Status.ERROR -> {
                    if (registrationResource.message != null) {
                        Toast.makeText(activity, "${registrationResource.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                Status.LOADING -> {Toast.makeText(activity, "Loading", Toast.LENGTH_SHORT).show()}
                Status.LOADED -> {}
            }
        })
    }


}
