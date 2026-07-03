# Zubastik KMP

Multiplatform telemedicine and dental app built with Kotlin Multiplatform 
and Compose Multiplatform, targeting Android and iOS from a single codebase.

## Screenshots

<img width="1785" height="713" alt="one" src="https://github.com/user-attachments/assets/f2c5e5be-e6d3-40ce-b153-b80667868b06" />

<img width="1387" height="692" alt="two" src="https://github.com/user-attachments/assets/0d2f66ff-45ea-41e8-bb54-cdcb597bfcad" />


## Features

- Interactive tooth map with condition tracking per tooth
- Custom event calendar for dental appointments
- AI assistant for dental health guidance
- Real-time chat with doctors
- Educational content library
- Phone number authentication with OTP

## Tech Stack

| Layer | Technology |
|-------|-----------|
| UI | Compose Multiplatform |
| Networking | Ktor |
| DI | Koin |
| Async | Coroutines + Flow |
| Serialization | Kotlinx Serialization |
| Architecture | MVI + Clean Architecture |

## Architecture

The project follows Clean Architecture principles with MVI pattern, 
separating concerns into data, domain, and presentation layers. 
Shared business logic runs on both Android and iOS platforms.

## Requirements

- Android 8.0+
- iOS 14.0+
