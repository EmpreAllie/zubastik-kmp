package com.features.ai.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.features.ai.domain.model.AiChatMessage
import com.features.ui.Res
import com.features.ui.theme.MainTheme
import com.features.ui.user
import com.features.ui.zub
import com.features.ui.zubastik
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private const val TIME_STRING_LENGTH = 5

@Composable
fun AiChatBubble(
    modifier: Modifier = Modifier,
    curMessage: AiChatMessage
) {
    val isUser = curMessage.role == "user"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom,
    ) {
        if (!isUser) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(Res.drawable.zub),
                contentDescription = "Zubastik in AI chat",
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = if (isUser) Alignment.End else Alignment.Start,
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        if (isUser)
                            MainTheme.colors.containerBackground
                        else
                            MainTheme.colors.aiMessageLightGray
                    )
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (!isUser) {
                    Text(
                        text = stringResource(Res.string.zubastik),
                        style = MainTheme.typography.ai.messageAuthorHeader,
                        color = MainTheme.colors.secondary
                    )
                }

                Text(
                    text = curMessage.content.parseMarkdown(),
                    style = MainTheme.typography.ai.messageContent,
                    color = MainTheme.colors.secondary
                )

                Text(
                    modifier = Modifier
                        .align(if (isUser) Alignment.Start else Alignment.End),
                    text = curMessage.createdAt.substringAfter('T').take(TIME_STRING_LENGTH),
                    style = MainTheme.typography.ai.messageTimeStamp,
                    color = MainTheme.colors.gray,
                )
            }
        }

        if (isUser) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(Res.drawable.user),
                contentDescription = "User in AI chat",
            )
        }
    }
}

private fun String.parseMarkdown(): AnnotatedString = buildAnnotatedString {
    val lines = this@parseMarkdown.split("\n")
    lines.forEachIndexed { index, line ->
        val trimmed = line.trimStart()
        when {
            trimmed.startsWith("### ") -> {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(trimmed.removePrefix("### "))
                }
            }
            trimmed.startsWith("## ") -> {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(trimmed.removePrefix("## "))
                }
            }
            trimmed.startsWith("# ") -> {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(trimmed.removePrefix("# "))
                }
            }
            else -> append(parseBoldItalic(line))
        }
        if (index < lines.lastIndex) append("\n")
    }
}

private fun parseBoldItalic(text: String): AnnotatedString = buildAnnotatedString {
    val pattern = Regex("""\*\*(.+?)\*\*|\*(.+?)\*""")
    var lastEnd = 0
    for (match in pattern.findAll(text)) {
        append(text.substring(lastEnd, match.range.first))
        val boldGroup = match.groupValues[1]
        if (boldGroup.isNotEmpty()) {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(boldGroup) }
        } else {
            withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                append(match.groupValues[2]) }
        }
        lastEnd = match.range.last + 1
    }
    append(text.substring(lastEnd))
}