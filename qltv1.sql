-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 23, 2023 lúc 04:05 AM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `qltv1`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `admin`
--

CREATE TABLE `admin` (
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `Ten` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `admin`
--

INSERT INTO `admin` (`Username`, `Password`, `Ten`) VALUES
('admin', '2', '012345');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `lop`
--

CREATE TABLE `lop` (
  `MaLop` varchar(10) NOT NULL,
  `Ten` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `lop`
--

INSERT INTO `lop` (`MaLop`, `Ten`) VALUES
('ML01', 'Công nghệ thông tin'),
('ML02', 'Quản trị kinh doanh'),
('ML03', 'Ngôn ngữ Anh'),
('ML04', 'Tài chính - Ngân hàng'),
('ML05', 'Quản trị du lịch và lữ khách');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieumuon`
--

CREATE TABLE `phieumuon` (
  `MaPhieuMuon` varchar(10) NOT NULL,
  `MaSV` varchar(10) DEFAULT NULL,
  `MaSach` varchar(10) DEFAULT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `NgayMuon` date DEFAULT curdate(),
  `NgayHenTra` date DEFAULT (curdate() + interval 10 day)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phieumuon`
--

INSERT INTO `phieumuon` (`MaPhieuMuon`, `MaSV`, `MaSach`, `SoLuong`, `NgayMuon`, `NgayHenTra`) VALUES
('PM01', 'a38004', 'S04', 3, '2023-03-12', '2023-03-15'),
('PM02', 'a39003', 'S02', 4, '2023-03-05', '2023-03-11'),
('PM03', 'a38004', 'S03', 10, '2023-03-01', '2023-03-01'),
('PM04', 'a39173', 'S05', 5, '2023-03-01', '2023-03-08'),
('PM05', 'a39003', 'S01', 5, '2023-03-01', '2023-03-03');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sach`
--

CREATE TABLE `sach` (
  `MaSach` varchar(10) NOT NULL,
  `TenSach` varchar(50) DEFAULT NULL,
  `MaTheLoai` varchar(10) DEFAULT NULL,
  `TacGia` varchar(50) DEFAULT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `MaNXB` varchar(50) DEFAULT NULL,
  `NgayNhap` date DEFAULT NULL,
  `NDTT` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sach`
--

INSERT INTO `sach` (`MaSach`, `TenSach`, `MaTheLoai`, `TacGia`, `SoLuong`, `MaNXB`, `NgayNhap`, `NDTT`) VALUES
('S01', 'Doraemon', 'TL01', 'HuanRose', 10, 'Thiếu niên', '2023-03-01', 'Truyện tranh nhật bản'),
('S02', 'Tiếng Hàn cơ bản', 'TL04', 'Thanh Chúc', 16, 'Việt Nam', '2019-03-11', 'Dành cho người mới học'),
('S03', 'Toán Logic', 'TL03', 'Thu Hồng', 50, 'DH Thăng Long', '2015-03-17', 'Tư duy logic'),
('S04', 'Nguyên lý kế toán', 'TL03', 'Minh Phương', 53, ' Việt Nam', '2018-03-07', 'Lý thuyết'),
('S05', 'Bóng ma bên cửa sổ', 'TL05', 'Ngọc Ngạn', 15, ' Việt Nam', '2019-03-08', 'Truyện ma kinh dị ');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sinhvien`
--

CREATE TABLE `sinhvien` (
  `MaSV` varchar(10) NOT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `MaLop` varchar(10) DEFAULT NULL,
  `HoTen` varchar(50) DEFAULT NULL,
  `NgaySinh` date DEFAULT NULL,
  `GioiTinh` bit(1) DEFAULT NULL,
  `DiaChi` varchar(50) DEFAULT NULL,
  `SDT` varchar(11) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sinhvien`
--

INSERT INTO `sinhvien` (`MaSV`, `Password`, `MaLop`, `HoTen`, `NgaySinh`, `GioiTinh`, `DiaChi`, `SDT`, `Email`) VALUES
('a38004', '111', 'ML04', 'Phạm Thu Trang', '2002-09-11', b'0', 'Lai Châu', '0345678221', 'trang234@gmail.com'),
('a39003', '54321', 'ML02', 'Phạm Đức Anh', '2002-11-14', b'1', 'Tuyên Quang', '0345678765', 'anhpham23@gmail.com'),
('a39006', '666', 'ML01', 'Vũ Văn Trung', '2002-03-01', b'1', 'Hà Nội', '0987654321', 'trung@gmail.com'),
('a39173', '333', 'ML03', 'Lê Anh Tú', '2012-10-09', b'1', 'Phú Thọ', '0376854321', 'tule2@gmail.com');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `theloaisach`
--

CREATE TABLE `theloaisach` (
  `MaTheLoai` varchar(10) NOT NULL,
  `TenTheLoai` varchar(50) DEFAULT NULL,
  `ViTri` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `theloaisach`
--

INSERT INTO `theloaisach` (`MaTheLoai`, `TenTheLoai`, `ViTri`) VALUES
('TL01', 'Sách Thiếu Nhi', 'Dãy 1'),
('TL02', 'Sách Trinh Thám', 'Dãy 2'),
('TL03', 'Sách Khoa Học', 'Dãy 3'),
('TL04', 'Sách Dịch Thuật', 'Dãy 4'),
('TL05', 'Sách Kinh Dị', 'Dãy 5');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vipham`
--

CREATE TABLE `vipham` (
  `MaViPham` varchar(10) NOT NULL,
  `MaSV` varchar(10) DEFAULT NULL,
  `Ten` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `vipham`
--

INSERT INTO `vipham` (`MaViPham`, `MaSV`, `Ten`) VALUES
('VP01', 'a39003', 'Rách sách'),
('VP02', 'a38004', 'Trả sách muộn'),
('VP03', 'a39173', 'Vẽ lên sách'),
('VP04', 'a39003', 'Trả sách muộn');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`Username`);

--
-- Chỉ mục cho bảng `lop`
--
ALTER TABLE `lop`
  ADD PRIMARY KEY (`MaLop`);

--
-- Chỉ mục cho bảng `phieumuon`
--
ALTER TABLE `phieumuon`
  ADD PRIMARY KEY (`MaPhieuMuon`),
  ADD KEY `MaSV` (`MaSV`),
  ADD KEY `MaSach` (`MaSach`),
  ADD KEY `SoLuong` (`SoLuong`);

--
-- Chỉ mục cho bảng `sach`
--
ALTER TABLE `sach`
  ADD PRIMARY KEY (`MaSach`),
  ADD KEY `MaTheLoai` (`MaTheLoai`),
  ADD KEY `MaNXB` (`MaNXB`),
  ADD KEY `SoLuong` (`SoLuong`),
  ADD KEY `TacGia` (`TacGia`);

--
-- Chỉ mục cho bảng `sinhvien`
--
ALTER TABLE `sinhvien`
  ADD PRIMARY KEY (`MaSV`),
  ADD KEY `MaLop` (`MaLop`);

--
-- Chỉ mục cho bảng `theloaisach`
--
ALTER TABLE `theloaisach`
  ADD PRIMARY KEY (`MaTheLoai`);

--
-- Chỉ mục cho bảng `vipham`
--
ALTER TABLE `vipham`
  ADD PRIMARY KEY (`MaViPham`),
  ADD KEY `MaSV` (`MaSV`),
  ADD KEY `Ten` (`Ten`);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `phieumuon`
--
ALTER TABLE `phieumuon`
  ADD CONSTRAINT `phieumuon_ibfk_1` FOREIGN KEY (`MaSach`) REFERENCES `sach` (`MaSach`),
  ADD CONSTRAINT `phieumuon_ibfk_2` FOREIGN KEY (`MaSV`) REFERENCES `sinhvien` (`MaSV`);

--
-- Các ràng buộc cho bảng `sach`
--
ALTER TABLE `sach`
  ADD CONSTRAINT `sach_ibfk_1` FOREIGN KEY (`MaTheLoai`) REFERENCES `theloaisach` (`MaTheLoai`);

--
-- Các ràng buộc cho bảng `sinhvien`
--
ALTER TABLE `sinhvien`
  ADD CONSTRAINT `sinhvien_ibfk_1` FOREIGN KEY (`MaLop`) REFERENCES `lop` (`MaLop`);

--
-- Các ràng buộc cho bảng `vipham`
--
ALTER TABLE `vipham`
  ADD CONSTRAINT `vipham_ibfk_1` FOREIGN KEY (`MaSV`) REFERENCES `sinhvien` (`MaSV`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
