package com.goofy.goober

import android.app.Application
import com.goofy.goober.ui.PizzaRenderer
import com.goofy.goober.ui.viewmodel.PizzaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PizzaApplication : Application() {

    // TODO: Umm....Is Koin a service locator or a DI framework ?! :P same difference?
    private val appModule = module {
        factory<CoroutineScope> { GlobalScope }

        factory { ApplicationCoroutineScope(global = get()) }

        factory { PizzaRenderer() }

        factory { PizzaFlowEventConsumer(applicationCoroutineScope = get()) }

        factory { PizzaUiInitializer() }

        factory { PizzaUi(externalEventConsumer = get<PizzaFlowEventConsumer>().invoke()) }

        viewModel {
            PizzaViewModel(
                pizzaUi = get(),
                pizzaUiInitializer = get()
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PizzaApplication)
            modules(appModule)
        }
    }
}
