package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.CreationExtras
import kotlin.reflect.KClass

class ViewModelLazy {
    public class ViewModelLazy<VM : ViewModel> @JvmOverloads constructor(
        private val viewModelClass: KClass<VM>,
        private val storeProducer: () -> ViewModelStore,
        private val factoryProducer: () -> ViewModelProvider.Factory,
        private val extrasProducer: () -> CreationExtras = { CreationExtras.Empty }
    ) : Lazy<VM> {
        private var cached: VM? = null

        override val value: VM
            get() {
                val viewModel = cached
                return if (viewModel == null) {
                    val factory = factoryProducer()
                    val store = storeProducer()
                    ViewModelProvider(
                        store,
                        factory,
                        extrasProducer()
                    ).get(viewModelClass.java).also {
                        cached = it
                    }
                } else {
                    viewModel
                }
            }

        override fun isInitialized(): Boolean = cached != null
    }

}