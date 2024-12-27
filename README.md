# Bike Rental App

## Deskripsi Proyek

Bike Rental App adalah aplikasi berbasis GUI (Graphical User Interface) yang memungkinkan pengguna untuk:

- Melihat daftar sepeda yang tersedia
- Menyewa sepeda
- Mengembalikan sepeda
- Membatalkan penyewaan sepeda

Aplikasi ini dirancang menggunakan Java Swing untuk tampilan antarmuka dan mendukung pengelolaan status sepeda dalam penyewaan.

---

## Fitur Utama

1. *Daftar Sepeda*

   - Menampilkan daftar sepeda beserta informasi status, harga, dan ID.

2. *Penyewaan Sepeda*

   - Pengguna dapat memilih sepeda yang tersedia, memasukkan nama, nomor telepon, dan durasi sewa.
   - Total harga dihitung berdasarkan durasi penyewaan.

3. *Pengembalian Sepeda*

   - Mengubah status sepeda dari "disewa" menjadi "tersedia" setelah dikembalikan.

4. *Pembatalan Penyewaan*

   - Membatalkan status penyewaan sepeda jika terjadi kesalahan atau perubahan rencana.

5. *GUI yang Interaktif*

   - Aplikasi menggunakan elemen GUI seperti tabel, tombol, dan form untuk meningkatkan pengalaman pengguna.

---

## Teknologi yang Digunakan

- *Bahasa Pemrograman*: Java
- *Framework GUI*: Java Swing

---

## Cara Menjalankan Aplikasi

1. Pastikan Anda memiliki JDK 8 atau yang lebih baru.
2. Clone atau unduh repository ini.
3. Buka terminal atau IDE Anda dan jalankan perintah berikut:
   
   javac org/example/BikeRentalApp.java
   java org.example.BikeRentalApp
   

---

## Struktur Kode

- *Main Class*: BikeRentalApp
  - Memuat logika utama aplikasi dan inisialisasi data sepeda.
- *Inner Classes*:
  - MainMenu: Menyediakan menu utama aplikasi.
  - BikeList: Menampilkan daftar sepeda.
  - RentBike: Form untuk menyewa sepeda.
  - ReturnBike: Form untuk mengembalikan sepeda.
  - CancelBike: Form untuk membatalkan penyewaan.
- *Helper Class*: Bike
  - Representasi data sepeda (ID, harga, status).

---

## Dokumentasi API

### rentBike(String bikeId, String customerName, String phoneNumber)

- *Deskripsi*: Menyewakan sepeda berdasarkan ID.
- *Parameter*:
  - bikeId: ID sepeda yang ingin disewa.
  - customerName: Nama penyewa.
  - phoneNumber: Nomor telepon penyewa.
- *Output*:
  - true: Jika penyewaan berhasil.
  - false: Jika sepeda tidak tersedia atau terjadi kesalahan.

### returnBike(String bikeId)

- *Deskripsi*: Mengembalikan sepeda berdasarkan ID.
- *Parameter*:
  - bikeId: ID sepeda yang ingin dikembalikan.
- *Output*:
  - true: Jika pengembalian berhasil.
  - false: Jika sepeda tidak dalam status "disewa".

### cancelRental(String bikeId)

- *Deskripsi*: Membatalkan penyewaan sepeda berdasarkan ID.
- *Parameter*:
  - bikeId: ID sepeda yang ingin dibatalkan penyewaannya.
- *Output*:
  - true: Jika pembatalan berhasil.
  - false: Jika tidak ada penyewaan yang ditemukan.
