# Zubastik KMP

Multiplatform telemedicine and dental app built with Kotlin Multiplatform 
and Compose Multiplatform, targeting Android and iOS from a single codebase.

## Screenshots

<img width="1785" height="713" alt="one" src="https://github.com/user-attachments/assets/f2c5e5be-e6d3-40ce-b153-b80667868b06" />

<img width="1387" height="692" alt="two" src="https://github.com/user-attachments/assets/0d2f66ff-45ea-41e8-bb54-cdcb597bfcad" />


## About

Zubastik is a cross-platform mobile application designed to help users 
monitor their dental health, communicate with doctors, and access 
educational content — all in one place. The app is built entirely in 
Kotlin using Kotlin Multiplatform, sharing business logic and UI across 
Android and iOS.

## Features

- Interactive tooth map with per-tooth condition tracking
- Custom event calendar for dental appointments
- AI assistant for dental health guidance
- Real-time chat with doctors
- Educational content library with articles
- Phone number authentication with OTP verification

## Tech Stack

| Layer | Technology |
|-------|-----------|
| UI | Compose Multiplatform |
| Networking | Ktor |
| Dependency Injection | Koin |
| Async | Coroutines + Flow |
| Serialization | Kotlinx Serialization |
| Architecture | MVI + Clean Architecture |

## Architecture

The project follows Clean Architecture with MVI pattern, organized into 
three layers: data, domain, and presentation. All business logic is 
shared across platforms via the common module, while platform-specific 
entry points remain minimal.

## Requirements

- Android 8.0 (API 26)+
- iOS 14.0+
