# <center>Employee Explorer</center>

<p>
Employee Explorer merupakan aplikasi penampilan karyawan
</p>

## <u>Requirement</u>

Sebelum memulai, pastikan Anda memiliki :

- **Android Studio** : Versi terbaru atau versi yang direkomendasikan.
- **JDK** : Versi 17 atau lebih baru.
- **Android SDK** : minimum di SDK 31 (Android 12).
- **Koneksi Internet** : Untuk mengunduh dependensi dan plugin, serta berinteraksi dengan aplikasi
- **Android Device Android / Emulator Android** : dengan minimum versi Android yakni Android 12

## <u>Cara Instalasi Aplikasi via Android Studio</u>

Ikuti langkah-langkah berikut untuk menginstal dan menjalankan aplikasi:

1. **Clone Repository**:
   ```bash
   https://github.com/pangondion-k-naibaho/Employee-Explorer.git

2. **Buka Project di Android Studio**:
   - Pilih "Open an existing Android Studio Project" dan arahkan ke folder project yang baru saja di-clone
3. **Sync Gradle**:
   - Klik "Sync Project with Gradle Files" di toolbar Android Studio untuk mengunduh dependensi yang diperlukan
4. **Konfigurasi Emulator atau Perangkat**:
   - Pilih emulator atau sambungkan perangkat Android untuk pengujian.
5. **Run 'app'** :
   - Jalan kan app dengan klik "Run 'app'". Biarkan proses build berjalan hingga APK terinstall di device/emulator

## <u>Cara Instalasi Aplikasi secara langsung ke perangkat Android</u>

1. **Unduh APK**:
   - APK Employee Explorer versi terbaru dapat diunduh di sini : [https://bit.ly/3SM7qZ1](https://bit.ly/3Ya1hJg)
2. **Install APK**:
   - Simpan file .apk ini ke direktori lokal device Android
   - Buka file .apk dan ikuti petunjuk dari Android untuk instalasi.
   - Setelah terinstall, silahkan jalankan aplikasi dengan syarat device terhubung ke Internet

## <u>Struktur Aplikasi</u>
<b>1. 'src/main/java'</b>
- Merupakan tempat kode sumber Kotlin. terdiri atas beberapa paket seperti :
  - '**com.employeeexplorer.client**': Package utama aplikasi
     - 'data' : berfungsi untuk penanganan data yang diperlukan untuk berjalannya aplikasi, berisikan model, remote, repository
     -  'ui' : berfungsi untuk penampilan data yang diperoleh dari komunikasi via WebService, bersisikan activities, custom component & adapter, dan viewmodel
  - '**res**' : Berisi resource yang mendukung pembuatan ui aplikasi, yang berisikan anim, drawable, font, layout, menu, mipmap, values, dan xml.

## <u>Fokus Area Aplikasi</u>
Adapun fokus area aplikasi ini adalah :
1. **Penampilan data Karyawan** :
   - Di aplikasi ini ditampilkan data data employee dalam bentuk daftar, yang di mana tiap item ditampilkan foto, nama, email, nomor telepon, bagian team, dan tipe kayawan.
2. **Pemuatan ulang data Karyawan** :
   - Terdapat interaksi <i>swipe down</i> yang untuk merefresh. Fungsi refresh ini untuk memuat ulang kembali data karyawan.
3. **Handling error** :
   - Terdapat error handling apabila data tidak bisa di-fetch, yakni berupa toast (untuk real error, bukan skenario error)
4. **Status Loading, Kosong, dan Error** :
   - Terdapat ui yang menunjukkan loading, yakni penggunaan <i>dimmed background overlay</i>.
   - Terdapat skenario pengosongan data, yang jika data telah kosong, maka akan menampilkan teks bahwa data karyawan kosong. Anda dapat me-refresh kembali untuk fetch data karyawan
   - Skenario error terdapat ketika berinteraksi di search bar. Dimana ketika kita mencoba fetch data berdasarkan kotak pencarian, maka akan memunculkan skenario error berupa pop up dan data yang di-fetch seolah-olah adalah data yang tidak sesuai.
