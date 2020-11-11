package com.tonycase.mytemplate.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.tonycase.mytemplate.databinding.MainFragmentBinding
import com.tonycase.mytemplate.ext.consume
import com.tonycase.mytemplate.ext.observeOnMain
import com.tonycase.mytemplate.ext.showDialog
import com.tonycase.mytemplate.miscutilview.playSnackbar
import com.tonycase.mytemplate.preferences.Prefs
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.main_fragment.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

   companion object {
      fun newInstance() = MainFragment()
   }

   @Inject
   lateinit var prefs: Prefs                 // included here only as an example of @Inject

   private var _binding: MainFragmentBinding? = null
   // This property is only valid between onCreateView and onDestroyView.
   private val b get() = _binding!!

   private val viewModel: MainViewModel by viewModels()
   private val disposables: CompositeDisposable = CompositeDisposable()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                             savedInstanceState: Bundle?): View {
      _binding = MainFragmentBinding.inflate(inflater, container, false)
      return b.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      if (savedInstanceState == null) {
         viewModel.load()
      }
      disposables.clear()

      disposables.addAll(
         viewModel.loadingState
            .observeOnMain()
            .consume { progressBar.isVisible = it },    // swallows errors.

         viewModel.dateState
            .observeOnMain()
            .subscribe { renderDate(it) },            // crashes on error (preferable)  TODO: pick approach

         viewModel.dialogActions
            .observeOnMain()
            .subscribe { displayDialog(it) },            // crashes on error (preferable)  TODO: pick approach
      )
   }

   private fun displayDialog(action: DialogAction) {

      action.snackbarSpec?.let {
         // if there's also a dialog specifed set it up for afterwards.
         playSnackbar(b.root, requireContext(), it)
      }

      action.dialogSpec?.let {
         requireContext().showDialog(it)
      }
   }

   private fun renderDate(viewState: DateViewState) {
      Timber.d("renderDate() with $viewState")        // sample Timber log message

      b.dayOfWeek.text = viewState.dayOfWeekStr
      b.month.text = viewState.monthStr
      b.day.text = viewState.dayStr
      b.year.text = viewState.yearStr
   }
}