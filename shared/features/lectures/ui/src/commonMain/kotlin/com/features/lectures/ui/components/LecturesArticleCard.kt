package com.features.lectures.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.features.lectures.domain.model.Article
import com.features.ui.ImageRemote
import com.features.ui.Res
import com.features.ui.author
import com.features.ui.ic_cross
import com.features.ui.ic_lectures
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun ArticleCard(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MainTheme.colors.containerBackground)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageRemote(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            url = article.imageUrl,
            placeHolder = Res.drawable.ic_lectures,
            error = Res.drawable.ic_cross,
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = article.title,
                style = MainTheme.typography.lectures.articleHeader,
                textAlign = TextAlign.Center,
                color = MainTheme.colors.black,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${stringResource(Res.string.author)}: ${article.authorUuid}",
                style = MainTheme.typography.lectures.articleAuthor,
                textAlign = TextAlign.Center,
                color = MainTheme.colors.gray,
            )
        }
    }
}