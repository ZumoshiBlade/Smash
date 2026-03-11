# SSB Tips — Super Smash Bros. Ultimate Companion App

Aplicación Android desarrollada en **Kotlin** como proyecto académico. Funciona como guía y panel de administración para el videojuego **Super Smash Bros. Ultimate**, permitiendo consultar personajes, ver tips de juego y gestionar contenido desde una interfaz de administración.

---

## Funcionalidades

| Sección | Descripción |
|---------|-------------|
| **Autenticación** | Login con email/contraseña y Google Sign-In via Firebase Auth. Registro de nuevos usuarios. |
| **Personajes** | Lista de personajes obtenida desde una API REST pública con búsqueda por nombre. |
| **Tips** | Fragmento con consejos/tips del juego almacenados en Firestore. |
| **Noticias** | Fragmento estático de noticias (en desarrollo). |
| **Ajustes** | Pantalla de configuración de la app. |
| **Admin — Personajes** | CRUD completo: crear, listar y eliminar personajes en Firestore. |
| **Admin — Tips** | CRUD completo: crear, listar y eliminar consejos en Firestore. |
| **Notificaciones** | Notificación local de bienvenida al iniciar sesión. |

---

## Stack técnico

- **Lenguaje:** Kotlin
- **Arquitectura:** MVVM (ViewModel + LiveData)
- **UI:** ViewBinding / DataBinding, Navigation Component, Bottom Navigation, Navigation Drawer
- **Backend/DB:** Firebase (Firestore, Auth, Realtime Database, Storage, Messaging, Analytics)
- **API REST:** Retrofit2 + Gson → `https://smashbros-unofficial-api.vercel.app/api/v1/ultimate/`
- **Imágenes:** Picasso, CircleImageView
- **Async:** Kotlin Coroutines
- **Splash Screen:** AndroidX SplashScreen API

---

## Requisitos para ejecutar

### 1. Android Studio
Instalar [Android Studio](https://developer.android.com/studio) (versión Ladybug o superior recomendada para AGP 8.7+).

### 2. Firebase
El archivo `google-services.json` ya está incluido en `app/`. La app se conecta al proyecto Firebase `smashfb` que incluye:
- **Firebase Authentication** — email/contraseña y Google Sign-In
- **Cloud Firestore** — almacenamiento de personajes y tips
- **Firebase Realtime Database**
- **Firebase Storage**
- **Firebase Cloud Messaging**

> **Nota:** Para que **Google Sign-In funcione**, debes registrar el SHA-1 de tu keystore de debug en la consola de Firebase:
> ```
> # Obtener SHA-1 del debug keystore
> keytool -list -v -keystore %USERPROFILE%\.android\debug.keystore -alias androiddebugkey -storepass android -keypass android
> ```
> Luego ve a [Firebase Console](https://console.firebase.google.com) → Proyecto `smashfb` → Configuración del proyecto → Agregar huella digital.

### 3. API externa
La sección de **Personajes** consume una API pública:
```
https://smashbros-unofficial-api.vercel.app/api/v1/ultimate/characters
```
Si la API está caída o sin conexión, la búsqueda de personajes no mostrará resultados pero la app seguirá funcionando.

---

## Cómo ejecutar la app

### Opción A — Emulador de Android Studio (recomendado)
1. Abrir el proyecto en Android Studio
2. Ir a **Device Manager** → crear un emulador (API 26 mínimo, API 35 recomendado)
3. Presionar **Run** (▶) o `Shift + F10`

### Opción B — Dispositivo físico
1. Activar **Depuración USB** en el dispositivo (Ajustes → Opciones de desarrollador)
2. Conectar por USB
3. Seleccionar el dispositivo en Android Studio y ejecutar

### Opción C — Generar APK y transferir
```bash
# Desde la raíz del proyecto
./gradlew assembleDebug
# El APK se genera en: app/build/outputs/apk/debug/app-debug.apk
```

> La app es exclusiva de Android — no existe versión web ni de escritorio. Se requiere un emulador o dispositivo Android para probarla.

---

## Estructura del proyecto

```
app/src/main/java/com/zushi/smash/
├── AuthActivity.kt          — Pantalla de login (email + Google)
├── SignUpActivity.kt         — Registro de nuevos usuarios
├── InicioActivity.kt         — Activity principal con drawer + bottom nav
├── Characters.kt             — Fragment: lista de personajes (API REST)
├── CharactersAdapter.kt      — Adapter RecyclerView para personajes
├── CharacterViewHolder.kt    — ViewHolder para items de personaje
├── News.kt                   — Fragment: noticias
├── Tips.kt                   — Fragment: tips/consejos
├── Settings.kt               — Fragment: ajustes
├── Adm_Characters.kt         — Activity admin de personajes
├── Adm_Tips.kt               — Activity admin de tips
├── Create_character.kt       — Fragment: crear personaje en Firestore
├── List_character.kt         — Fragment: listar personajes desde Firestore
├── Delete_character.kt       — Fragment: eliminar personaje de Firestore
├── Create_tip.kt             — Fragment: crear tip en Firestore
├── List_tip.kt               — Fragment: listar tips desde Firestore
├── Delete_tip.kt             — Fragment: eliminar tip de Firestore
├── APIService.kt             — Interfaz Retrofit para la API REST
└── models/
    ├── CharactersResponseItem.kt  — Modelo de personaje (API)
    ├── CharactersResponse.kt      — Tipo alias de lista
    ├── Personajes.kt              — Modelo Firestore de personaje
    ├── Consejos.kt                — Modelo Firestore de tip
    ├── Images.kt                  — Modelo de imágenes (API)
    └── Series.kt                  — Modelo de serie (API)
```

---

## Plan de migración de dependencias

A continuación se documentan los cambios realizados respecto a la versión original del proyecto.

| Componente | Versión anterior | Versión actualizada | Notas |
|------------|-----------------|---------------------|-------|
| Android Gradle Plugin | 7.2.2 | **8.7.3** | |
| Kotlin | 1.7.10 | **2.1.0** | |
| Gradle Wrapper | 7.3.3 | **8.11.1** | Requerido por AGP 8.7 |
| Google Services plugin | 4.3.14 | **4.4.2** | |
| `compileSdk` / `targetSdk` | 32 | **35** | Android 15 |
| Java / JVM target | 1.8 | **17** | Requerido por AGP 8+ |
| `core-ktx` | 1.7.0 | **1.15.0** | |
| `appcompat` | 1.5.1 | **1.7.0** | |
| `material` | 1.6.1 | **1.12.0** | |
| `constraintlayout` | 2.1.4 | **2.2.0** | |
| `core-splashscreen` | 1.0.0-rc01 | **1.0.1** | Salió de RC |
| `firebase-bom` | 30.4.1 (duplicado x2) | **33.9.0** (único) | Deduplicado |
| `lifecycle-extensions` | 2.2.0 | **eliminado** | Deprecated |
| `lifecycle-*-ktx` | 2.5.1 | **2.8.7** | |
| `navigation-*-ktx` | 2.5.2 | **2.8.5** | |
| `play-services-auth` | 19.0.0 + 20.3.0 (dup.) | **21.2.0** (único) | Deduplicado |
| `picasso` | 2.71828 | **2.8** | |
| `retrofit2` | 2.9.0 | **2.11.0** | |
| `kotlinx-coroutines-android` | 1.3.6 | **1.9.0** | |
| `junit-ext` | 1.1.3 | **1.2.1** | |
| `espresso-core` | 3.4.0 | **3.6.1** | |
| `buildToolsVersion` | 33.0.0 (manual) | **eliminado** | AGP lo gestiona |

---

## Notas adicionales

- **Reglas de Firestore:** Asegúrate de que las reglas de seguridad de Firestore permitan lectura/escritura durante el desarrollo. En producción deberías restringirlas por usuario autenticado.
- **`lifecycle-extensions` eliminado:** Era una librería monolítica deprecated. Sus funciones equivalentes están disponibles en `lifecycle-livedata-ktx` y `lifecycle-viewmodel-ktx`.
- **BOM deduplicado:** El proyecto original declaraba `firebase-bom` y `play-services-auth` dos veces. Se consolidaron en una única declaración.
