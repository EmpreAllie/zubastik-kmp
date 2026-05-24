package com.features.root.ui.components

import coil3.ImageLoader
import coil3.PlatformContext

expect fun newImageLoader(context: PlatformContext, debug: Boolean): ImageLoader
