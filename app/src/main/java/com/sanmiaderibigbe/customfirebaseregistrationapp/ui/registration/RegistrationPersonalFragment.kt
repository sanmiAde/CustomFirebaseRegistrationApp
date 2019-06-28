package com.sanmiaderibigbe.customfirebaseregistrationapp.ui.registration


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.sanmiaderibigbe.customfirebaseregistrationapp.R
import com.sanmiaderibigbe.customfirebaseregistrationapp.model.User
import kotlinx.android.synthetic.main.fragment_registration_personal.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/**
 * A simple [Fragment] subclass.
 *
 */
class RegistrationPersonalFragment : Fragment() {

    private val sharedViewModel : RegistrationSharedViewModel
            by viewModel<RegistrationSharedViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_personal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.userRegistrationData.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                txt_full_name.editText?.setText( user.fullName)
                txt_email.editText?.setText(user.email)
                txt_mobile.editText?.setText(user.mobile)
                txt_password.editText?.setText(user.password)
                txt_account_type.editText?.setText(user.verify_password)
            }

        })


        btn_continue.setOnClickListener {

            val name = txt_full_name.editText?.text.toString()
            val email = txt_email.editText?.text.toString()
            val phoneNUmber = txt_mobile.editText?.text.toString()
            val password = txt_password.editText?.text.toString()
            val veriftyPassword = txt_account_type?.editText?.text.toString()

            val user : User = User(name, email, phoneNUmber, password, veriftyPassword)


            sharedViewModel.updateUserData(user)

            Navigation.findNavController(it).navigate(
                RegistrationPersonalFragmentDirections.actionRegistrationPersonalFragmentToRegistrationBankFragment(user)
            )

            Toast.makeText(activity, user.toString(), Toast.LENGTH_SHORT).show()

        }
    }
}
