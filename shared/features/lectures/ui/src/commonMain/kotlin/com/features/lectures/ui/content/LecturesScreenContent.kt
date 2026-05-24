package com.features.lectures.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.features.lectures.presentation.model.LecturesEvents
import com.features.lectures.presentation.model.LecturesState
import com.features.lectures.ui.components.ArticleCard
import com.features.ui.Res
import com.features.ui.educationMaterials
import com.features.ui.noArticlesYet
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun LecturesScreenContent(
    state: LecturesState,
    onEvent: (LecturesEvents) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(Res.string.educationMaterials),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            textAlign = TextAlign.Center,
            style = MainTheme.typography.lectures.screenHeader,
            color = MainTheme.colors.secondary,
        )

        if (state.articles.isEmpty() && !state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.noArticlesYet),
                    style = MainTheme.typography.lectures.noArticlesYet,
                    color = MainTheme.colors.gray
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(state.articles, key = { it.id }) { article ->
                    ArticleCard(
                        article = article,
                        onClick = { onEvent(LecturesEvents.OnArticleClick(article)) }
                    )
                }
            }
        }
    }
}