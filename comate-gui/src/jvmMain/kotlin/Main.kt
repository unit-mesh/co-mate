import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import component.MessageList
import component.TextInput
import data.remote.OpenAIRepositoryImpl
import model.ConversationViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import theme.LightTheme
//import org.kodein.di.compose.localDI

class MainScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
//        val screenModel = rememberScreenModel<ConversationViewModel>()
//        val scopedDependency by localDI().on(ScreenContext(this)).instance<KodeinScopedDependencySample>()

        LightTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxSize()) {
                    Column(Modifier.fillMaxSize()) {
                        MessageList(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp),
//                            conversationViewModel = screenModel
                        )
                        TextInput(ConversationViewModel(OpenAIRepositoryImpl()))
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Navigator(MainScreen())
    }
}
