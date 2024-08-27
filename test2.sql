CREATE DATABASE  IF NOT EXISTS `libmanagement` ;
USE `libmanagement`;

-- Table structure for table `admin`
CREATE TABLE `admin` (
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `Ten` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `admin` VALUES ('admin','a','Nguyễn Đắc Khánh');

CREATE TABLE `lop` (
  `MaLop` varchar(10) NOT NULL,
  `Ten` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`MaLop`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `lop` VALUES 
('ML01','Công nghệ thông tin'),
('ML02','Quản trị kinh doanh'),
('ML03','Ngôn ngữ Anh'),
('ML04','Tài chính - Ngân hàng'),
('ML05','Quản trị du lịch và lữ khách');

CREATE TABLE `theloaisach` (
  `MaTheLoai` varchar(10) NOT NULL,
  `TenTheLoai` varchar(50) DEFAULT NULL,
  `ViTri` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MaTheLoai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `theloaisach` VALUES 
('TL01','Sách Thiếu Nhi','Dãy 1'),
('TL02','Sách Trinh Thám','Dãy 2'),
('TL03','Sách Khoa Học','Dãy 3'),
('TL04','Sách Dịch Thuật','Dãy 4'),
('TL05','Sách Kinh Dị','Dãy 5');

CREATE TABLE `sach` (
  `MaSach` varchar(10) NOT NULL,
  `TenSach` varchar(50) DEFAULT NULL,
  `MaTheLoai` varchar(10) DEFAULT NULL,
  `TacGia` varchar(50) DEFAULT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `MaNXB` varchar(50) DEFAULT NULL,
  `NgayNhap` date DEFAULT NULL,
  `NDTT` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`MaSach`),
  KEY `MaTheLoai` (`MaTheLoai`),
  KEY `MaNXB` (`MaNXB`),
  KEY `SoLuong` (`SoLuong`),
  KEY `TacGia` (`TacGia`),
  CONSTRAINT `sach_ibfk_1` FOREIGN KEY (`MaTheLoai`) REFERENCES `theloaisach` (`MaTheLoai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `sach` (`MaSach`, `TenSach`, `MaTheLoai`, `TacGia`, `SoLuong`, `MaNXB`, `NgayNhap`, `NDTT`) VALUES
('S01', 'Doraemon', 'TL01', 'HuanRose', 10, 'Thiếu niên', '2023-03-01', 'Truyện tranh nhật bản'),
('S02', 'Tiếng Hàn cơ bản', 'TL04', 'Thanh Chúc', 16, 'Việt Nam', '2019-03-11', 'Dành cho người mới học'),
('S03', 'Toán Logic', 'TL03', 'Thu Hồng', 50, 'DH Thăng Long', '2015-03-17', 'Tư duy logic'),
('S04', 'Nguyên lý kế toán', 'TL03', 'Minh Phương', 53, ' Việt Nam', '2018-03-07', 'Lý thuyết'),
('S05', 'Bóng ma bên cửa sổ', 'TL05', 'Ngọc Ngạn', 15, ' Việt Nam', '2019-03-08', 'Truyện ma kinh dị ');

CREATE TABLE `sinhvien` (
  `MaSV` varchar(10) NOT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `MaLop` varchar(10) DEFAULT NULL,
  `HoTen` varchar(50) DEFAULT NULL,
  `NgaySinh` date DEFAULT NULL,
  `GioiTinh` bit(1) DEFAULT NULL,
  `DiaChi` varchar(50) DEFAULT NULL,
  `SDT` varchar(11) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MaSV`),
  KEY `MaLop` (`MaLop`),
  CONSTRAINT `sinhvien_ibfk_1` FOREIGN KEY (`MaLop`) REFERENCES `lop` (`MaLop`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `sinhvien` (`MaSV`, `Password`, `MaLop`, `HoTen`, `NgaySinh`, `GioiTinh`, `DiaChi`, `SDT`, `Email`) VALUES
('a38004', '111', 'ML04', 'Nguyễn Văn A', '2002-09-11', b'0', 'Lai Châu', '0345678221', 'nguyenva@gmail.com'),
('a39003', '54321', 'ML02', 'Phạm Đức B', '2002-11-14', b'1', 'Tuyên Quang', '0345678765', 'ducpham23@gmail.com'),
('a39006', '666', 'ML01', 'Lê Xuân C', '2002-03-01', b'1', 'Hà Nội', '0987654321', 'xuanle@gmail.com'),
('a39173', '333', 'ML03', 'Nguyễn Đắc Khánh', '2002-10-09', b'1', 'Phú Thọ', '0376854321', 'nguyendackhanh@gmail.com');

CREATE TABLE `phieumuon` (
  `MaPhieuMuon` varchar(10) NOT NULL,
  `MaSV` varchar(10) DEFAULT NULL,
  `MaSach` varchar(10) DEFAULT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `NgayMuon` date DEFAULT NULL,
  `NgayHenTra` date DEFAULT NULL,
  PRIMARY KEY (`MaPhieuMuon`),
  KEY `MaSV` (`MaSV`),
  KEY `MaSach` (`MaSach`),
  KEY `SoLuong` (`SoLuong`),
  CONSTRAINT `phieumuon_ibfk_1` FOREIGN KEY (`MaSach`) REFERENCES `sach` (`MaSach`),
  CONSTRAINT `phieumuon_ibfk_2` FOREIGN KEY (`MaSV`) REFERENCES `sinhvien` (`MaSV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `phieumuon` VALUES 
('PM01','a38004','S04',3,'2024-03-12','2024-03-15'),
('PM02','a39003','S02',4,'2024-03-05','2024-03-11'),
('PM03','a38004','S03',10,'2024-03-01','2024-03-01'),
('PM04','a39173','S05',5,'2024-03-01','2024-03-08'),
('PM05','a39003','S01',5,'2024-03-01','2024-03-03');

CREATE TABLE `vipham` (
  `MaViPham` varchar(10) NOT NULL,
  `MaSV` varchar(10) DEFAULT NULL,
  `Ten` varchar(30) DEFAULT NULL,
  `Phat` float DEFAULT 0,
  PRIMARY KEY (`MaViPham`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `vipham` VALUES 
('VP01','a39003','Rách sách',30000),
('VP02','a38004','Trả sách muộn',20000),
('VP03','a39173','Vẽ lên sách',20000),
('VP04','a39003','Trả sách muộn',20000);
