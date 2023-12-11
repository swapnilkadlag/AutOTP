package com.sk.autotp.screens.rules

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sk.autotp.data.Rule
import com.sk.autotp.screens.ComposableFragment
import com.sk.autotp.services.SmsForegroundService
import com.sk.autotp.utils.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RulesFragment : ComposableFragment() {

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {}

    private fun requestPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CONTACTS,
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        permissionLauncher.launch(permissions.toTypedArray())
    }

    private val viewModel by viewModels<RulesViewModel>()

    private var isSmsServiceBound: Boolean = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            Logger.logDebug(this@RulesFragment::class.java.simpleName, "onServiceConnected")
            isSmsServiceBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Logger.logDebug(this@RulesFragment::class.java.simpleName, "onServiceDisconnected")
            isSmsServiceBound = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requestPermissions()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        bindService()
    }

    override fun onStop() {
        super.onStop()
        unbindService()
    }

    private fun bindService() {
        if (SmsForegroundService.isRunning) {
            requireActivity().bindService(
                Intent(requireActivity(), SmsForegroundService::class.java),
                connection,
                Context.BIND_AUTO_CREATE,
            )
        }
    }

    private fun unbindService() {
        if (isSmsServiceBound) {
            requireActivity().unbindService(connection)
            isSmsServiceBound = false
        }
    }

    private fun startService() {
        Logger.logDebug(this::class.java.simpleName, "startService")
        requireActivity().startForegroundService(
            Intent(
                requireActivity(),
                SmsForegroundService::class.java
            )
        )
        bindService()
    }

    private fun stopService() {
        Logger.logDebug(this::class.java.simpleName, "stopService")
        requireActivity().stopService(Intent(requireActivity(), SmsForegroundService::class.java))
        unbindService()
    }

    @Composable
    override fun BoxScope.FragmentContent() {

        val rules by viewModel.rules.collectAsState(initial = emptyList())

        val (masterSwitchChecked, setMasterSwitchChecked) = remember {
            mutableStateOf(
                SmsForegroundService.isRunning
            )
        }

        LaunchedEffect(key1 = masterSwitchChecked) {
            if (masterSwitchChecked) startService()
            else stopService()
        }

        Content(
            rules = rules,
            masterSwitchChecked = masterSwitchChecked,
            onMasterSwitchCheckedChange = setMasterSwitchChecked,
            onHistoryButtonClick = ::navigateToHistoryFragment,
            onAddButtonClick = ::navigateToEditorFragment,
            onSettingsButtonClick = ::navigateToSettingsFragment,
            onAppIconClick = ::navigateToAboutFragment,
            onRuleClick = ::navigateToEditorFragment,
            onRuleStatusChange = ::updateRuleStatus,
        )
    }

    private fun navigateToEditorFragment(rule: Rule? = null) {
        val action = RulesFragmentDirections.actionRulesScreenToEditorScreen(ruleId = rule?.id)
        findNavController().navigate(action)
    }

    private fun navigateToHistoryFragment() {
        val action = RulesFragmentDirections.actionRulesScreenToHistoryScreen()
        findNavController().navigate(action)
    }

    private fun navigateToSettingsFragment() {
    }

    private fun navigateToAboutFragment() {
    }

    private fun updateRuleStatus(rule: Rule, enabled: Boolean) {
        viewModel.updateRuleStatus(rule, enabled)
    }
}
