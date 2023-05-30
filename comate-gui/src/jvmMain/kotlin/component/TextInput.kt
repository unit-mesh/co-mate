package component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun TextInput(

) {
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf(TextFieldValue("")) }

    Box() {
        Column {
            Divider(Modifier.height(0.2.dp))
            Box(
                Modifier
                    .padding(horizontal = 4.dp)
                    .padding(top = 6.dp, bottom = 10.dp)
            ) {
                Row {
                    TextField(
                        value = text,
                        onValueChange = {
                            text = it
                        },
                        label = null,
                        placeholder = { Text("Ask me anything", fontSize = 12.sp) },
                        shape = RoundedCornerShape(25.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp)
                            .weight(1f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                        ),
                    )
                    IconButton(onClick = {
                        scope.launch {
                            val textClone = text.text.toString()
                            text = TextFieldValue("")
                        }
                    }) {
                        Icon(
                            Icons.Filled.Send,
                            "sendMessage",
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }
        }
    }
}
