
# Widmeyer Template Project

Основные элементы архитектуры разделены на модули и папки, каждая из которых выполняет определённые функции.

## Содержание
1. [Структура проекта](#структура-проекта)
2. [Android App](#android-app)
3. [iOS App](#ios-app)
4. [Shared](#shared)
5. [iosExport](#iosexport)
6. [Templates](#templates)

## Структура проекта
![Архитектура drawio](https://github.com/user-attachments/assets/adab02e3-3c4c-4275-b553-56184033230c)

## Скрипты автоматического внедрения и настройки ресурсов в проект

### Плагин Widmeyer Template
В папке plugin располагается плагин, который может быть установлен в вашу IDE для автоматизации создания новых модулей и обновления ресурсов. Для обновления ресурсов необходимо нажать CMD/CTRL + ALT + SHIFT + P или же выбрать в меню Tools

#### Строковые ресурсы
При добавлении новой строки в `shared/resources/moko-resources/base/strings.xml`, строковые значения будут скопированы в:

- `androidApp/ui/res/values/strings.xml` 
- `shared/resources/moko-resources/`

#### Цветовые ресурсы
При добавлении нового цвета в файл `shared/resources/moko-resources/colors/colors.xml`, цветовые значения будут скопирвоаны в:

- **shared**: `androidApp/ui/res/values/colors.xml` 
- **android**: `androidApp/ui/Colors.kt` 
- **iOS**: `iosApp/Resources/Colors.swift`

#### Модули
Для создания новых экранов можно создать новый модуль, который называется Widmeyer. Например, для создания View Model + Screen для экрана "Авторизации" будут следующие настройки:
* Название пакета/проекта. Например, widmeyer. На выходе -> com.widmeyer.***
* Название модуля. Например, authorization На выходе -> com.widmeyer.authorization
* Название экрана. Например, Authorization. На выходе -> com.widmeyer.authorization.AuthorizationScreen

### rename_project.sh
Скрипт для переименования проекта и модуля.

#### Функции:
1. Спрашивает название проекта и автоматически переименовывает текущую папку проекта, а также меняет название в файле `settings.gradle` без пробелов. 
   Например, если вы введете "Widmeyer Template", скрипт переименует проект в "WidmeyerTemplate".
   
2. Спрашивает название модуля и выполняет следующие действия:
   - Заменяет в проекте пакетное пространство, например, меняет все вхождения `com.widmeyertemplate` на новое, например `com.newproject`.

#### Использование:
- Запустите скрипт в терминале с помощью команды:
  ```bash
  ./rename_project.sh
  ```


## update_colors.sh и update_strings.sh
Файлы автоматического внедрение ресурсов в проект. Для запуска необходимо прописать в терминале ./***.sh
### update_strings.sh
При добавлении новой строки в shared/resources/moko-resources/base автоматически строковые значения добавляются в папку:
- androidApp/ui/res/values/strings.xml 
- shared/resources/moko-resources/ru/strings.xml 
- shared/resources/moko-resources/en/strings.xml (необходимо удалить из .sh скрипта, если нет локализации. Если есть, то необходимо перевести на соответствующий язык)
### update_colors.sh
При добавлении новой строки в shared/resources/moko-resources/colors автоматически цветовые значения добавляются в папку:
- androidApp/ui/res/values/colors.xml 
- androidApp/ui/Colors.kt (включая Light и Dark режим)
- iosApp/Resources/Colors (не реализовано)

### Android App
- **BuildGradle**: файл сборки для Android-приложения.
- **src**
- AndroidManifest: файл конфигурации Android-приложения.
- **ui**: пользовательский интерфейс Android-приложения (диалоговые окна, кастомные элементы).
- **features**
  - **root**: корневой модуль, который знает обо всех экранах. В нем располагается файл Application, который реализует логику перехода между экранами.
  - **screen N**: отдельные экраны приложения. Screen N не знает о root.
---
### iOS App
- **Pods**: файл сборки для iOS-приложения.
- **Di**: внедрение зависимостей.
- **Extensions**: расширения.
- **Features**: функции приложения.
- **Helpers**: вспомогательные функции.
- **Models**: модели данных.
- **Preview** Content: контент для предварительного просмотра.
- **Resources**: ресурсы приложения.
- **Routes**: маршрутизация.
---
### Shared
Используется только для Android приложения

- **entity**: сущности, используемые в приложении.
  - **Entity N**: отдельные сущности.
- **resources**
  - **constants**: общие константы.
  - **images**: изображения.
  - **strings**: строковые ресурсы.
- **features**
  - **Screen** N: экраны приложения.
    - presentation: слой представления.
    - di: внедрение зависимостей.
- **src**
  - **api**
    - **OpenApi.yaml**: спецификация API.
  - **commonMain**: общий код для обеих платформ.
    - **domain**: доменная логика.
      - **repositories**: репозитории для работы с данными.
      - **mapping**: маппинг данных.
      - **utils**: утилиты.
    - **data**: работа с данными.
    - **di**: внедрение зависимостей.
  - **androidMain**: специфический код для Android.
    - **jni**
  - **iosMain**: специфический код для iOS.
---
### iosExport
Используется только для iOS приложения.

- **BuildGradle**: файл сборки shared модуля для iOS-приложения.

## Templates

Для быстрого создания экранов и view model рекомендуется изменить название папок в templates. Дополнительную информацию о папках и инструкция к их использованию располагается по пути: [./templates/README.md](./templates/README.md)
