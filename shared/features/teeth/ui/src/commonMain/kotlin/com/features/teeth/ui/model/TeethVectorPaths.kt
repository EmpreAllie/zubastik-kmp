package com.features.teeth.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Path

@Composable
fun rememberIncisor1Path() : Path {
    return remember {
        Path().apply {
//android:pathData=
            // "M22.186,5.281
            // C20.752,5.281 15.014,3.989 4.495,6.238
            // M0.67,13.144
            // C1.626,17.447 3.252,24.742 9.755,28.567
            // C16.257,32.392 21.07,28.567 22.664,26.177
            // C23.444,25.285 25.285,22.412 26.489,16.969
            // C27.006,14.631 27.445,12.187 27.445,8.486
            // C27.445,1.223 20.008,-0.104 13.101,0.712
            // C6.753,1.463 0.899,3.951 0.67,6.928
            // C0.287,11.9 0.67,12.985 0.67,13.144Z"

            moveTo(22.186f, 5.281f)
            cubicTo(20.752f, 5.281f, 15.014f, 3.989f, 4.495f, 6.238f)

            moveTo(0.67f, 13.144f)
            cubicTo(1.626f, 17.447f, 3.252f, 24.742f, 9.755f, 28.567f)

            cubicTo(16.257f, 32.392f, 21.07f, 28.567f, 22.66f, 26.177f)
            cubicTo(23.444f, 25.285f, 25.285f, 22.412f, 26.489f, 16.969f)
            cubicTo(27.006f, 14.631f, 27.445f, 12.187f, 27.445f, 8.486f)
            cubicTo(27.445f, 1.223f, 20.008f, -0.104f, 13.101f, 0.712f)
            cubicTo(6.753f, 1.463f, 0.899f, 3.951f, 0.67f, 6.928f)
            cubicTo(0.287f, 11.9f, 0.67f, 12.985f, 0.67f, 13.144f)

            close()
        }
    }
}

@Composable
fun rememberIncisor2Path(): Path {
//android:pathData="M21.538,24.337
    // C23.833,19.365 24.406,12.862 24.406,11.906V11.906
    // C24.406,11.426 24.406,7.603 23.45,5.212
    // C22.494,2.821 21.664,1.69 21.059,1.388
    // C20.103,0.909 19.147,-0.047 13.887,0.909
    // C8.628,1.865 3.369,2.344 1.934,5.212
    // C0.5,8.081 0.5,8.559 0.5,11.906
    // C0.5,15.253 5.759,23.381 9.106,25.772
    // C12.294,27.365 19.243,29.31 21.538,24.337Z"
    return remember {
        Path().apply {
            moveTo(21.538f, 24.337f)
            cubicTo(23.833f,19.365f, 24.406f,12.862f, 24.406f,11.906f)
            cubicTo(24.406f,11.426f, 24.406f,7.603f, 23.45f,5.212f)
            cubicTo(22.494f,2.821f, 21.664f,1.69f, 21.059f,1.388f)
            cubicTo(20.103f,0.909f, 19.147f,-0.047f, 13.887f,0.909f)
            cubicTo(8.628f,1.865f, 3.369f,2.344f, 1.934f,5.212f)
            cubicTo(0.5f,8.081f, 0.5f,8.559f, 0.5f,11.906f)
            cubicTo(0.5f,15.253f, 5.759f,23.381f, 9.106f,25.772f)
            cubicTo(12.294f,27.365f, 19.243f,29.31f, 21.538f,24.337f)

            close()
        }
    }
}

@Composable
fun rememberCaninePath(): Path {
    //android:pathData="M2.258,9.224C4.171,6.355 7.996,2.052 11.821,1.096C13.733,0.617 17.825,-0.876 20.905,3.965C24.252,9.224 25.152,12.614 25.686,14.483C26.643,17.83 26.643,20.699 26.643,22.133C26.643,24.428 24.252,27.233 23.774,27.393C22.18,28.03 18.298,28.81 15.168,27.871C10.387,26.436 5.605,25.002 1.302,18.786C-0.059,16.821 0.346,12.093 2.258,9.224Z"
    return remember {
        Path().apply {
            moveTo(2.258f,9.224f)
            cubicTo(4.171f, 6.355f, 7.996f, 2.052f, 11.821f, 1.096f)
            cubicTo(13.733f, 0.617f, 17.825f, -0.876f, 20.905f, 3.965f)
            cubicTo(24.252f, 9.224f, 25.152f, 12.614f, 25.686f, 14.483f)
            cubicTo(26.643f, 17.83f, 26.643f, 20.699f, 26.643f, 22.133f)
            cubicTo(26.643f, 24.428f, 24.252f, 27.233f, 23.774f, 27.393f)
            cubicTo(22.18f, 28.03f, 18.298f, 28.81f, 15.168f, 27.871f)
            cubicTo(10.387f, 26.436f, 5.605f, 25.002f, 1.302f, 18.786f)
            cubicTo(-0.059f, 16.821f, 0.346f, 12.093f, 2.258f, 9.224f)

            close()
        }
    }
}

@Composable
fun rememberPremolar1Path(): Path {

    return remember {
        Path().apply {
            moveTo(17.732f, 10.617f)
            cubicTo(16.775f, 11.414f, 14.863f, 14.442f, 14.385f, 18.745f)

            moveTo(15.341f, 0.577f)
            cubicTo(21.079f, 1.055f, 27.613f, 5.835f, 29.207f, 7.748f)
            cubicTo(33.988f, 13.485f, 29.732f, 22.608f, 27.772f, 24.96f)
            cubicTo(25.382f, 27.83f, 23.947f, 28.307f, 22.035f, 28.785f)
            cubicTo(15.987f, 30.298f, 13.531f, 28.501f, 7.213f, 26.395f)
            cubicTo(2.156f, 24.709f, 0.519f, 17.789f, 0.519f, 16.832f)
            cubicTo(0.519f, 15.876f, 0.041f, 11.095f, 3.866f, 5.357f)
            cubicTo(6.926f, 0.767f, 13.11f, 0.258f, 15.341f, 0.577f)

            close()
        }
    }
}


@Composable
fun rememberPremolar2Path(): Path {

    return remember {
        Path().apply {
            moveTo(17.708f, 12.976f)
            cubicTo(16.295f, 14.359f, 14.839f, 16.323f, 15.317f, 21.583f)

            moveTo(11.492f, 0.545f)
            cubicTo(15.317f, 0.068f, 26.77f, 3.438f, 32.986f, 8.219f)
            cubicTo(38.412f, 12.393f, 30.533f, 28.101f, 27.749f, 30.189f)
            cubicTo(22.011f, 34.492f, 17.636f, 32.489f, 11.492f, 31.145f)
            cubicTo(-3.808f, 27.798f, 0.017f, 12.976f, 4.32f, 6.761f)
            cubicTo(5.302f, 5.344f, 7.222f, 1.078f, 11.492f, 0.545f)

            close()
        }
    }
}

@Composable
fun rememberMolar1Path(): Path {

    return remember {
        Path().apply {
            moveTo(19.893f, 10.724f)
            cubicTo(19.415f, 13.114f, 17.262f, 25.334f, 15.59f, 30.327f)

            moveTo(12.243f, 19.33f)
            cubicTo(13.434f, 18.288f, 20.996f, 19.871f, 23.24f, 20.764f)

            moveTo(8.418f, 2.118f)
            cubicTo(14.732f, -1.899f, 26.918f, 2.275f, 32.803f, 6.899f)
            cubicTo(39.496f, 12.158f, 34.217f, 28.639f, 32.325f, 31.284f)
            cubicTo(28.425f, 36.733f, 24.196f, 39.002f, 12.721f, 36.543f)
            cubicTo(-1.588f, 33.476f, 0.066f, 21.035f, 1.246f, 14.071f)
            cubicTo(1.832f, 10.617f, 3.159f, 5.464f, 8.418f, 2.118f)

            close()
        }
    }
}

@Composable
fun rememberMolar2Path(): Path {

    return remember {
        Path().apply {
            moveTo(17.109f, 12.966f)
            cubicTo(16.24f, 14.596f, 16.153f, 20.616f, 14.481f, 25.609f)

            moveTo(8.025f, 17.747f)
            cubicTo(9.216f, 16.705f, 19.168f, 19.723f, 21.412f, 20.616f)

            moveTo(8.503f, 2.447f)
            cubicTo(14.817f, -1.57f, 22.221f, 1.171f, 28.106f, 5.794f)
            cubicTo(34.8f, 11.053f, 30.477f, 28.012f, 28.584f, 30.657f)
            cubicTo(24.684f, 36.107f, 19.5f, 38.307f, 12.806f, 36.873f)
            cubicTo(-1.503f, 33.806f, 0.043f, 20.994f, 1.224f, 14.03f)
            cubicTo(1.809f, 10.576f, 3.244f, 5.794f, 8.503f, 2.447f)

            close()
        }
    }
}

@Composable
fun rememberWisdomPath(): Path {
    return remember {
        Path().apply {

            moveTo(15.035f, 10.945f)
            cubicTo(14.166f, 12.575f, 13.421f, 14.735f, 15.329f, 19.286f)

            moveTo(10.872f, 15.999f)
            cubicTo(12.063f, 14.957f, 15.329f, 14.659f, 17.573f, 15.552f)

            moveTo(5.996f, 1.62f)
            cubicTo(9.269f, 0.118f, 20.422f, -0.106f, 27.306f, 2.403f)
            cubicTo(33.316f, 4.594f, 30.72f, 20.807f, 28.827f, 23.452f)
            cubicTo(24.928f, 28.902f, 20.466f, 28.349f, 14.614f, 28.882f)
            cubicTo(0.04f, 30.209f, -0.726f, 15.927f, 1.357f, 9.178f)
            cubicTo(1.832f, 7.639f, 2.342f, 3.298f, 5.996f, 1.62f)

            close()
        }
    }
}