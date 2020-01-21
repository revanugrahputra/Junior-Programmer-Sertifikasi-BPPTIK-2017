-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 27, 2017 at 08:42 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tokoreva`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `kode_barang` int(20) NOT NULL,
  `nama_barang` varchar(40) NOT NULL,
  `kategori` varchar(40) NOT NULL,
  `harga` int(20) NOT NULL,
  `stok` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`kode_barang`, `nama_barang`, `kategori`, `harga`, `stok`) VALUES
(2, 'sabun', 'rumah tangga', 5000, 0),
(3, 'beras', 'makanan', 10000, 77),
(4, 'telur', 'makanan', 6000, 12),
(5, 'shampo', 'rumah tangga', 5000, 10),
(6, 'pasta gigi', 'rumah tangga', 5000, 45),
(7, 'celana dalam', 'rumah tangga', 30000, 10),
(8, 'sapu', 'rumah tangga', 15000, 6),
(9, 'pulpen', 'atk', 3000, 50),
(19, 'bihun', 'makanan', 20000, 10),
(20, 'celana', 'pakaian', 30000, 20),
(25, 'sisir', 'rumah tangga', 3000, 5),
(30, 'piring', 'rumah tangga', 20000, 30),
(99, 'sabun colek', 'rumah tangga', 3000, 27);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `kode_transaksi` int(6) NOT NULL,
  `kode_barang` int(6) NOT NULL,
  `tgl_transaksi` date NOT NULL,
  `jml_barang` int(1) NOT NULL,
  `harga_barang` int(10) NOT NULL,
  `tot_transaksi` int(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`kode_transaksi`, `kode_barang`, `tgl_transaksi`, `jml_barang`, `harga_barang`, `tot_transaksi`) VALUES
(1, 19, '2017-01-09', 20, 10000, 200000),
(2, 5, '2017-06-11', 40, 1200, 48000),
(3, 6, '2017-08-08', 10, 1000, 10000),
(4, 8, '2017-09-09', 2, 3000, 6000),
(5, 30, '2017-11-11', 2, 4000, 8000),
(6, 20, '2017-12-12', 1, 6000, 6000),
(7, 4, '2017-02-02', 3, 6000, 18000),
(8, 3, '2017-06-06', 10, 10000, 100000),
(9, 3, '2017-06-06', 10, 10000, 100000),
(10, 3, '2017-01-01', 23, 2000, 46000),
(12, 99, '2017-11-12', 3, 3000, 9000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`kode_barang`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`kode_transaksi`),
  ADD KEY `kode_barang` (`kode_barang`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `kode_transaksi` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`kode_barang`) REFERENCES `barang` (`kode_barang`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
