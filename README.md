# SAFELINE - Dokumentasi Aplikasi Deteksi Jatuh

## Hallo SAFELINE Fellas <3

Selamat datang di dokumentasi proyek **SAFELINE**! 🎉 Proyek ini adalah hasil kolaborasi antara dua mahasiswi dari **Telkom University**, bagian dari **Tim Mobile Development C242-PS251 Bangkit Academy 2024**. Kami mengembangkan aplikasi yang terintegrasi dengan perangkat **IoT** untuk membantu mengurangi risiko keterlambatan pertolongan bagi lansia yang terdeteksi jatuh. 🚶‍♂️💥

Tujuan utama aplikasi ini adalah untuk memberikan respons yang cepat dan tepat saat mendeteksi kejadian jatuh, sehingga mempercepat proses pertolongan yang sangat dibutuhkan oleh lansia. 💡

## Dokumentasi Proyek 📑

Untuk dokumentasi lebih lanjut mengenai pengembangan aplikasi ini, termasuk **Figma Design**, **Prototype**, dan **Aset Slicing**, silakan kunjungi link berikut:

- [**Figma Design**](https://www.figma.com/design/q1uDRxX19evdjJmn932QBs/CAPSTONE-PROJECT?node-id=0-1&t=be0i8Vs616fyYv4V-1) ![Figma Logo](https://img.shields.io/badge/Figma-Design-blue?style=for-the-badge&logo=figma&logoColor=white) 🖼️
- [**Figma Prototype**](https://www.figma.com/proto/q1uDRxX19evdjJmn932QBs/CAPSTONE-PROJECT?node-id=18-615&node-type=canvas&t=dtOJ0fuKtYxkNb5E-1&scaling=min-zoom&content-scaling=fixed&page-id=0%3A1&starting-point-node-id=15%3A593) ![Figma Logo](https://img.shields.io/badge/Figma-Prototype-blue?style=for-the-badge&logo=figma&logoColor=white) 🚀
- [**Aset Slicing**](https://drive.google.com/drive/folders/1IEdn7bTztCLCTzpakqFCnUCU9llmJ7W2?usp=drive_link) 📂

# Library Usage in the Application

This repository integrates modern libraries and frameworks to build a robust and maintainable Android application. Below is a detailed explanation of the libraries used and their specific roles in the application.

---

## **1. Koin (Dependency Injection Framework)**

### Library Used:
- `platform(libs.koin.bom)`
- `libs.koin.core`
- `libs.koin.android`

### Usage:
- **MainRepository**:
  - Manages dependencies like `AccountPreferences` and `FirebaseHelper`.
- **ViewModels**:
  - `HomeViewModel`, `MetricsViewModel`, `ProfileViewModel` utilize dependency injection for seamless initialization.
- **Dependency Injection Setup:**
  ```kotlin
  val repositoryModules = module {
      factory { MainRepository.getInstance(androidContext()) }
  }
  val viewModelModules = module {
      viewModel { HomeViewModel(get()) }
      viewModel { MetricsViewModel(get()) }
      viewModel { ProfileViewModel(get()) }
  }
  ```

### Functionality:
- Simplifies dependency management.
- Supports modular and testable application architecture.

---

## **2. DataStore (Android Jetpack)**

### Library Used:
- `libs.androidx.datastore.preferences`

### Usage:
- **AccountPreferences**:
  - Stores and retrieves user profile data like profile image, username, and phone number.
  ```kotlin
  class AccountPreferences(private val dataStore: DataStore<Preferences>) {
      fun getImageProfile() = dataStore.data.map { it[IMAGE_PREFERENCES] ?: "" }
      suspend fun saveImage(image: String) {
          dataStore.edit { prefs -> prefs[IMAGE_PREFERENCES] = image }
      }
  }
  ```

### Functionality:
- Modern, secure storage for user preferences.
- Replaces SharedPreferences for better scalability.

---

## **3. Android Jetpack Components**

### Libraries Used:
- **Lifecycle:**
  - `libs.androidx.lifecycle.livedata.ktx`
  - `libs.androidx.lifecycle.viewmodel.ktx`
- **Navigation:**
  - `libs.androidx.navigation.fragment.ktx`
  - `libs.androidx.navigation.ui.ktx`
- **Activity:**
  - `libs.androidx.activity`
- **Core:**
  - `libs.androidx.core.ktx`
- **AppCompat:**
  - `libs.androidx.appcompat`
- **ConstraintLayout:**
  - `libs.androidx.constraintlayout`

### Usage:
- **Lifecycle:**
  - Manages LiveData and ViewModel for user activities and fall history.
  ```kotlin
  class HomeViewModel(private val repository: MainRepository) : ViewModel() {
      val userActivity: LiveData<String> = _userActivity
      private fun observeUserActivity() {
          viewModelScope.launch {
              repository.observeUserActivity().collect { status -> _userActivity.postValue(status) }
          }
      }
  }
  ```
- **Navigation:**
  - Navigates between fragments (HomeFragment, MetricsFragment, ProfileFragment).
  ```kotlin
  btnMetrics.setOnClickListener {
      val action = HomeFragmentDirections.actionNavigationHomeToNavigationMetrics()
      findNavController().navigate(action)
  }
  ```
- **ConstraintLayout:**
  - Creates complex UI layouts in XML files like `fragment_home.xml`.

### Functionality:
- Facilitates MVVM architecture.
- Simplifies screen-to-screen navigation.
- Provides reactive data handling and lifecycle management.

---

## **4. Material Components**

### Library Used:
- `libs.material`

### Usage:
- Implements Material Design UI components, such as buttons and text views.
  ```xml
  <com.google.android.material.button.MaterialButton
      android:id="@+id/btnMetrics"
      style="@style/Widget.MaterialComponents.Button"
      android:text="Metrics" />
  ```

### Functionality:
- Ensures modern UI design in compliance with Material Design standards.

---

## **5. Firebase**

### Libraries Used:
- `libs.firebase.messaging`
- `libs.firebase.database`

### Usage:
- **Messaging Service:**
  - Sends push notifications via Firebase Cloud Messaging (FCM).
  ```kotlin
  class FallGuardMessagingService : FirebaseMessagingService() {
      override fun onMessageReceived(remoteMessage: RemoteMessage) {
          val title = remoteMessage.notification?.title ?: "No Title"
          val text = remoteMessage.notification?.body ?: "No Content"
          // Create and display notification
      }
  }
  ```
- **Realtime Database:**
  - Synchronizes user activity and fall history in real-time.
  ```kotlin
  class FirebaseHelper {
      fun observeUserFallHistory(): Flow<List<Long>> = callbackFlow {
          val listener = object : ValueEventListener {
              override fun onDataChange(snapshot: DataSnapshot) { /* Handle data updates */ }
          }
          firebaseDatabase.getReference("history").addValueEventListener(listener)
      }
  }
  ```

### Functionality:
- Provides push notification services.
- Manages real-time user data.

---

## **6. Testing Libraries**

### Libraries Used:
- `libs.junit`
- `libs.androidx.junit`
- `libs.androidx.espresso.core`

### Usage:
- **JUnit:**
  - Performs unit testing.
- **Espresso:**
  - Conducts UI testing to validate user interactions.

### Functionality:
- Ensures correctness of business logic and UI.

---

## Summary
This application leverages modern libraries and frameworks to deliver:
- **MVVM Architecture** supported by Koin and Jetpack Components.
- **Secure Data Storage** using DataStore.
- **Real-Time Communication** with Firebase.
- **Modern UI Design** using Material Components.
- **Efficient Navigation** with Jetpack Navigation.

## Cara Menjalankan Proyek

1. **Clone Repository**
   ```bash
   git clone https://github.com/zulfanurmalaa/SAFELINE_FIX.git
   ```
2. **Buka di Android Studio**
   - Pilih **File > Open** dan arahkan ke folder hasil clone.
3. **Sinkronisasi Gradle**
   - Android Studio akan otomatis meminta sinkronisasi. Klik **Sync Now**.
4. **Firebase**
   - File `google-services.json` sudah disertakan, tidak perlu konfigurasi tambahan.
5. **Jalankan Aplikasi**
   - Sambungkan perangkat atau gunakan emulator, lalu klik tombol **Run** di Android Studio.
