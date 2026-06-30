-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 30 Jun 2026 pada 11.57
-- Versi server: 10.4.25-MariaDB
-- Versi PHP: 8.0.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_parksmart`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `data_kendaraan`
--

CREATE TABLE `data_kendaraan` (
  `id_parkir` int(11) NOT NULL,
  `kode_tarif` varchar(20) NOT NULL,
  `plat_nomor` varchar(20) NOT NULL,
  `jenis_kendaraan` varchar(20) NOT NULL,
  `warna_kendaraan` varchar(30) DEFAULT NULL,
  `tanggal_masuk` varchar(20) DEFAULT NULL,
  `jam_masuk` varchar(20) DEFAULT NULL,
  `tarif` int(11) DEFAULT 0,
  `petugas_jaga` varchar(50) NOT NULL,
  `status` varchar(20) DEFAULT 'Terparkir'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `data_kendaraan`
--

INSERT INTO `data_kendaraan` (`id_parkir`, `kode_tarif`, `plat_nomor`, `jenis_kendaraan`, `warna_kendaraan`, `tanggal_masuk`, `jam_masuk`, `tarif`, `petugas_jaga`, `status`) VALUES
(19, 'MB-1734', 'F4475AAB', 'Mobil', 'Ungu', '2026-06-27', '14:54:30', 5000, 'Khabid', 'Terparkir'),
(20, 'MB-4535', 'B4788ADF', 'Mobil', 'Merah', '28-06-2026', '21:33:34', 5000, 'Khabid', 'Terparkir'),
(21, 'MT-5003', 'D9876YMI', 'Motor', 'Biru', '28-06-2026', '21:38:00', 2000, 'Dearis', 'Terparkir'),
(22, 'MT-3095', 'D4021AMN', 'Motor', 'Putih', '28-06-2026', '21:58:20', 2000, 'Dearis', 'Terparkir'),
(23, 'MB-4240', 'B8954HIT', 'Mobil', 'Putih', '29-06-2026', '11:04:39', 5000, 'Khabid', 'Terparkir');

-- --------------------------------------------------------

--
-- Struktur dari tabel `kendaraan_hilang`
--

CREATE TABLE `kendaraan_hilang` (
  `id_hilang` int(11) NOT NULL,
  `kode_tarif` varchar(20) NOT NULL,
  `plat_nomor` varchar(15) NOT NULL,
  `jenis_kendaraan` varchar(10) NOT NULL,
  `warna` varchar(20) DEFAULT NULL,
  `merk` varchar(50) DEFAULT NULL,
  `no_telp` varchar(20) DEFAULT NULL,
  `tanggal_lapor` date NOT NULL,
  `jam_lapor` time DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'Belum Ditemukan'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `kendaraan_hilang`
--

INSERT INTO `kendaraan_hilang` (`id_hilang`, `kode_tarif`, `plat_nomor`, `jenis_kendaraan`, `warna`, `merk`, `no_telp`, `tanggal_lapor`, `jam_lapor`, `status`) VALUES
(1, 'MT-9391', 'B 3745 ADE', 'Motor', 'Biru', 'Honda vario', '083140559728', '2026-06-27', '15:50:43', 'Hilang'),
(2, 'MT-3095', 'D 4021 AMN', 'Motor', 'Putih', 'Honda Vario', '083456678897', '2026-06-29', '11:22:50', 'Ditemukan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `log_aktivitas`
--

CREATE TABLE `log_aktivitas` (
  `id_log` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `jam` varchar(20) DEFAULT NULL,
  `petugas` varchar(50) NOT NULL,
  `status` text NOT NULL,
  `kode_tarif` varchar(10) DEFAULT '-'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `log_aktivitas`
--

INSERT INTO `log_aktivitas` (`id_log`, `tanggal`, `jam`, `petugas`, `status`, `kode_tarif`) VALUES
(1, '2026-06-21', '21:26:53', 'Dearis', 'Kendaraan Masuk', 'MB-3946'),
(2, '2026-06-21', '21:27:49', 'Dearis', 'Kendaraan Keluar', 'MT-7270'),
(3, '2026-06-21', '21:37:42', 'Dearis', 'Kendaraan Keluar', 'MB-4371'),
(4, '2026-06-24', '16:54:24', 'Rohman', 'Kendaraan Keluar', 'MB-9674'),
(5, '2026-06-24', '16:54:45', 'Rohman', 'Kendaraan Keluar', 'MT-2785'),
(6, '2026-06-25', '19:24:07', 'Khabid', 'Kendaraan Masuk', 'MT-3322'),
(7, '2026-06-26', '22:25:36', 'Rohman', 'Kendaraan Masuk', 'MT-9391'),
(8, '2026-06-26', '22:29:50', 'Rohman', 'Kendaraan Masuk', 'MB-8861'),
(9, '2026-06-26', '23:34:07', 'Rohman', 'Kendaraan Masuk', 'MT-1919'),
(10, '2026-06-26', '23:35:46', 'Rohman', 'Kendaraan Keluar', 'MT-3322'),
(11, '2026-06-26', '23:56:24', 'Rohman', 'Kendaraan Keluar', 'MB-3946'),
(12, '2026-06-27', '14:54:30', 'Khabid', 'Kendaraan Masuk', 'MB-1734'),
(13, '2026-06-27', '14:56:58', 'Khabid', 'Kendaraan Keluar', 'MT-1919'),
(14, '2026-06-28', '21:58:20', 'Dearis', 'Kendaraan Masuk', 'MT-3095'),
(15, '2026-06-28', '22:07:45', 'Dearis', 'Kendaraan Keluar', 'MT-9391'),
(16, '2026-06-29', '11:04:39', 'Khabid', 'Kendaraan Masuk', 'MB-4240'),
(17, '2026-06-29', '11:14:57', 'Dearis', 'Kendaraan Keluar', 'MB-8861');

-- --------------------------------------------------------

--
-- Struktur dari tabel `riwayat_parkir`
--

CREATE TABLE `riwayat_parkir` (
  `id_riwayat` int(11) NOT NULL,
  `kode_tarif` varchar(20) DEFAULT NULL,
  `plat_nomor` varchar(15) DEFAULT NULL,
  `jenis_kendaraan` varchar(20) DEFAULT NULL,
  `warna_kendaraan` varchar(20) DEFAULT NULL,
  `tanggal_masuk` date DEFAULT NULL,
  `jam_masuk` varchar(20) DEFAULT NULL,
  `tanggal_keluar` date DEFAULT NULL,
  `jam_keluar` varchar(20) DEFAULT NULL,
  `tarif` int(11) DEFAULT NULL,
  `petugas_jaga` varchar(50) DEFAULT NULL,
  `petugas_keluar` varchar(50) DEFAULT NULL,
  `durasi` varchar(20) DEFAULT NULL,
  `total_tarif` int(11) DEFAULT NULL,
  `denda` int(11) DEFAULT 0,
  `status` varchar(20) DEFAULT 'Keluar'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `riwayat_parkir`
--

INSERT INTO `riwayat_parkir` (`id_riwayat`, `kode_tarif`, `plat_nomor`, `jenis_kendaraan`, `warna_kendaraan`, `tanggal_masuk`, `jam_masuk`, `tanggal_keluar`, `jam_keluar`, `tarif`, `petugas_jaga`, `petugas_keluar`, `durasi`, `total_tarif`, `denda`, `status`) VALUES
(1, 'MB-7683', 'B2356UIN', 'Mobil', 'Merah', '2026-06-19', '20:08:50', '2026-06-21', '15:48:40', 5000, 'Dearis', 'Khabid', '44 Jam', 220000, 0, 'Keluar'),
(2, 'MT-5327', 'D8895ACE', 'Motor', 'Biru', '2026-06-21', '15:32:06', '2026-06-21', '20:48:50', 2000, 'Rohman', 'Dearis', '6 Jam', 37000, 0, 'Keluar'),
(3, 'MT-7270', 'E6780ABC', 'Motor', 'Hijau', '2026-06-21', '15:33:02', '2026-06-21', '21:27:33', 37000, 'Rohman', 'Dearis', '6 Jam', 37000, 0, 'Keluar'),
(4, 'MB-4371', 'D7790BDG', 'Mobil', 'Putih', '2026-06-21', '15:46:56', '2026-06-21', '21:37:28', 80000, 'Khabid', 'Dearis', '6 Jam', 80000, 0, 'Keluar'),
(5, 'MB-9674', 'B9977AHA', 'Mobil', 'Ungu', '2026-06-21', '20:45:13', '2026-06-24', '16:54:07', 345000, 'Dearis', 'Rohman', '69 Jam', 345000, 0, 'Keluar'),
(6, 'MT-2785', 'E3455CRB', 'Motor', 'Kuning', '2026-06-21', '20:46:23', '2026-06-24', '16:54:42', 163000, 'Dearis', 'Rohman', '69 Jam', 163000, 25000, 'Keluar'),
(7, 'MT-3322', 'D 9134 AMF', 'Motor', 'Hitam', '2026-06-25', '19:23:00', '2026-06-26', '23:35:46', 2000, 'Khabid', 'Rohman', '29 Jam', 83000, 0, 'Keluar'),
(8, 'MB-3946', 'B1234AGE', 'Mobil', 'Abu Abu', '2026-06-21', '21:25:56', '2026-06-26', '23:56:24', 5000, 'Dearis', 'Rohman', '123 Jam', 615000, 0, 'Keluar'),
(9, 'MT-1919', 'B2750KPE', 'Motor', 'Hijau', '2026-06-26', '23:34:07', '2026-06-27', '14:56:58', 2000, 'Rohman', 'Khabid', '16 Jam', 32000, 0, 'Keluar'),
(10, 'MT-9391', 'B3745ADE', 'Motor', 'Biru', '2026-06-26', '22:25:36', '2026-06-28', '22:07:45', 2000, 'Rohman', 'Dearis', '48 Jam', 121000, 25000, 'Keluar'),
(11, 'MB-8861', 'E 1739 ACB', 'Mobil', 'Putih', '2026-06-26', '22:29:50', '2026-06-29', '11:14:57', 5000, 'Rohman', 'Dearis', '61 Jam', 355000, 50000, 'Keluar');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tarif`
--

CREATE TABLE `tarif` (
  `jenis_kendaraan` enum('Mobil','Motor') NOT NULL,
  `tarif_per_jam` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tarif`
--

INSERT INTO `tarif` (`jenis_kendaraan`, `tarif_per_jam`) VALUES
('Mobil', 5000),
('Motor', 2000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id_petugas` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id_petugas`, `username`, `password`) VALUES
(24, 'Rohman', 'rohman024'),
(29, 'Khabid', 'khabid029'),
(36, 'Dearis', 'dearis036');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `data_kendaraan`
--
ALTER TABLE `data_kendaraan`
  ADD PRIMARY KEY (`id_parkir`);

--
-- Indeks untuk tabel `kendaraan_hilang`
--
ALTER TABLE `kendaraan_hilang`
  ADD PRIMARY KEY (`id_hilang`);

--
-- Indeks untuk tabel `log_aktivitas`
--
ALTER TABLE `log_aktivitas`
  ADD PRIMARY KEY (`id_log`);

--
-- Indeks untuk tabel `riwayat_parkir`
--
ALTER TABLE `riwayat_parkir`
  ADD PRIMARY KEY (`id_riwayat`);

--
-- Indeks untuk tabel `tarif`
--
ALTER TABLE `tarif`
  ADD PRIMARY KEY (`jenis_kendaraan`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_petugas`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `data_kendaraan`
--
ALTER TABLE `data_kendaraan`
  MODIFY `id_parkir` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT untuk tabel `kendaraan_hilang`
--
ALTER TABLE `kendaraan_hilang`
  MODIFY `id_hilang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `log_aktivitas`
--
ALTER TABLE `log_aktivitas`
  MODIFY `id_log` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT untuk tabel `riwayat_parkir`
--
ALTER TABLE `riwayat_parkir`
  MODIFY `id_riwayat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
