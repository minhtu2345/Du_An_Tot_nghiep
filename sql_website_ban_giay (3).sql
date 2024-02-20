drop database website_ban_giay
go
create database website_ban_giay
go
use website_ban_giay
go
create table thuong_hieu (
	id uniqueidentifier primary key default newid(),
	ten_url varchar(20) default null,
	ma varchar(20) unique,
	ten nvarchar(50) default null,
	trangthai int null
)
create table gioi_tinh (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	ten nvarchar(50) default null,
	trangthai int null
)
create table de_giay (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	ten nvarchar(50) default null,
	trangthai int null
)
create table xuat_xu (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	ten nvarchar(50) default null,
	trangthai int null
)
create table kieu_dang (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	ten nvarchar(50) default null,
	trangthai int null
)
create table chat_lieu (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	ten nvarchar(50) default null,
	trangthai int null
)
create table mau_sac (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	ten nvarchar(50) default null,
	trangthai int null
)
create table giay (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	ten nvarchar(200) default null,
	id_thuong_hieu uniqueidentifier,
	id_gioi_tinh uniqueidentifier,
	id_chat_lieu uniqueidentifier,
	id_de_giay uniqueidentifier,
	id_mau_sac uniqueidentifier,
	id_xuat_xu uniqueidentifier,
	id_kieu_dang uniqueidentifier,
	mota nvarchar(255) null,
	gianhap decimal,
	giaban decimal,
	trangthai int null,
	gia_sau_khuyen_mai decimal,
	ngay_nhap date,
	do_hot int null,
	foreign key (id_thuong_hieu) references thuong_hieu(id),
	foreign key (id_gioi_tinh) references gioi_tinh(id),
	foreign key (id_chat_lieu) references chat_lieu(id),
	foreign key (id_de_giay) references de_giay(id),
	foreign key (id_xuat_xu) references xuat_xu(id),
	foreign key (id_kieu_dang) references kieu_dang(id),
	foreign key (id_mau_sac) references mau_sac(id)
)
create table anh_giay (
	id uniqueidentifier primary key default newid(),
	ten_url varchar(20) unique,
	id_giay uniqueidentifier,
	trangthai int null,
	foreign key (id_giay) references giay(id)
)
create table kich_co (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	ten nvarchar(50) default null,
	trangthai int null
)
create table giay_chi_tiet (
	id uniqueidentifier primary key default newid(),
	id_giay uniqueidentifier,
	id_kich_co uniqueidentifier,
	so_luong_ton int default null,
	trangthai int null,
	foreign key (id_giay) references giay(id),
	foreign key (id_kich_co) references kich_co(id)
)
create table chuc_vu (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	ten nvarchar(50) default null,
	trangthai int null
)
create table nhan_vien (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	ho_ten nvarchar(50) default null,
	ngay_sinh date null,
	dia_chi nvarchar(255) null,
	xa nvarchar(250) default null,
	huyen nvarchar(50) default null,
	thanh_pho nvarchar(255) null,
	anh varchar(20) null,
	sdt nvarchar(15) null,
	email nvarchar(255) null,
	id_chuc_vu uniqueidentifier,
	mat_khau nvarchar(255) null,
	ngay_vao_lam date null,
	ngay_nghi_viec date null,
	trangthai int null,
	foreign key (id_chuc_vu) references chuc_vu(id),
)
create table chuong_tring_giam_gia_san_pham (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	ten nvarchar(50) default null,
	phan_tram_giam int,
	ngay_bat_dau date,
	ngay_ket_thuc date,
	id_nhan_vien_create uniqueidentifier,
	id_nhan_vien_update uniqueidentifier,
	trangthai int null,
	foreign key (id_nhan_vien_create) references nhan_vien(id),
	foreign key (id_nhan_vien_update) references nhan_vien(id)
)
create table chuong_trinh_giam_gia_chi_tiet_san_pham (
	id uniqueidentifier primary key default newid(),
	id_giay uniqueidentifier,
	id_chuong_trinh_giam_gia uniqueidentifier,
	so_tien_da_giam decimal,
	trangthai int null,
	foreign key (id_giay) references giay(id),
	foreign key (id_chuong_trinh_giam_gia) references chuong_tring_giam_gia_san_pham(id)
)
create table hang_khach_hang (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	ten nvarchar(50) null,
	dieu_kien money null,
	trangthai int null,
)
create table khach_hang (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	avatar varchar(20) null,
	ho_ten nvarchar(50) default null,
	ngay_sinh date null,
	sdt nvarchar(15) null,
	email nvarchar(255) null,
	mat_khau nvarchar(255) null,
	id_hkh uniqueidentifier null,
	trangthai int null,
	foreign key (id_hkh) references hang_khach_hang(id)
)

create table y_kien_dong_gop (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	id_khach_hang uniqueidentifier,
	ten_nguoi_dg nvarchar(50) null,
	so_dien_thoai nvarchar(20) null,
	email nvarchar(255) null,
	noi_dung nvarchar(255) null,
	trangthai int null,
	foreign key (id_khach_hang) references khach_hang(id),
	)
  
create table vi_diem (
	id uniqueidentifier primary key default newid(),
	id_khach_hang uniqueidentifier unique,
	tong_diem int null,
	so_diem_da_dung int null,
	so_diem_da_cong int null,
	trangthai int null,
	foreign key (id_khach_hang) references khach_hang(id),
)
create table quy_doi_diem (
	id uniqueidentifier primary key default newid(),
	so_tien_tuong_ung money null,
	so_diem_tuong_ung int null,
	trangthai int null,
)


create table danh_gia(
	id uniqueidentifier primary key default newid(),
	id_giay uniqueidentifier not null,
	sao int,
	ten_nguoi_danh_gia nvarchar(50),
	noi_dung nvarchar(max),
	trang_thai int,
	foreign key (id_giay) references giay(id)
)
create table dia_chi (
	id uniqueidentifier primary key default newid(),
	ma nvarchar(50) default null,
	id_khach_hang uniqueidentifier,
	ten_dia_chi nvarchar(250) default null,
	ten_nguoi_nhan nvarchar(250) default null,
	sdt_nguoi_nhan nvarchar(250) default null,
	xa nvarchar(250) default null,
	huyen nvarchar(50) default null,
	thanh_pho nvarchar(50) default null,
	trangthai int null,
	foreign key (id_khach_hang) references khach_hang(id)
)
create table gio_hang (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	id_khach_hang uniqueidentifier null,
	ngay_tao date null,
	ngay_cap_nhap date null,
	ghi_chu nvarchar(255) null,
	trangthai int null,
	foreign key (id_khach_hang) references khach_hang(id)
)
create table gio_hang_chi_tiet (
	id uniqueidentifier primary key default newid(),
	id_gio_hang uniqueidentifier null,
	id_giay_chi_tiet uniqueidentifier null,
	so_luong int null,
	ghi_chu nvarchar(255) null,
	trangthai int null,
	foreign key (id_gio_hang) references gio_hang(id),
	foreign key (id_giay_chi_tiet) references giay_chi_tiet(id)
)
create table hoa_don (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	ngay_tao date null,
	ngay_thanh_toan date null,
	id_nhan_vien uniqueidentifier,
	id_khach_hang uniqueidentifier,
	mo_ta nvarchar(255) null,
	ten_nguoi_nhan nvarchar(255) null,
	sdt_nguoi_nhan nvarchar(255) null,
	dia_chi nvarchar(255) null,
	tong_tien decimal,
	hinh_thuc_mua int,
	hinh_thuc_thanh_toan int,
	so_tien_giam money,
	phi_ship money,
	so_diem_su_dung int,
	so_tien_quy_doi int,
	trangthai int null,
	foreign key (id_nhan_vien) references nhan_vien(id),
	foreign key (id_khach_hang) references khach_hang(id)
)
create table hoa_don_chi_tiet (
	id uniqueidentifier primary key default newid(),
	id_hoa_don uniqueidentifier,
	id_giay_chi_tiet uniqueidentifier,
	so_luong int,
	gia_nhap decimal,
	don_gia decimal,
	trangthai int null,
	foreign key (id_hoa_don) references hoa_don(id),
	foreign key (id_giay_chi_tiet) references giay_chi_tiet(id)
)
create table lich_su_tich_diem (
	id uniqueidentifier primary key default newid(),
	so_diem_da_dung int null,
	ngay_su_dung date null,
	so_diem_cong int null,
	trangthai int null,
	id_vi_diem uniqueidentifier null,
	id_hoa_don uniqueidentifier null,
	id_quy_doi uniqueidentifier null,
	foreign key (id_vi_diem) references vi_diem(id),
	foreign key (id_hoa_don) references hoa_don(id),
	foreign key (id_quy_doi) references quy_doi_diem(id))
create table chuong_trinh_giam_gia_hoa_don (
	id uniqueidentifier primary key default newid(),
	ma varchar(20) unique,
	ten nvarchar(50) default null,
	dieu_kien decimal default null, --gia hoa don nho nhat de duoc phep sd giam gia vd :100000vnd
	so_tien_giam_max decimal  default null,
	ngay_bat_dau date  default null,
	ngay_ket_thuc date  default null,
	phan_tram_giam int  default null,
	so_luong int  default null,
	trangthai int null
)
create table chuong_trinh_giam_gia_chi_tiet_hoa_don (
	id uniqueidentifier primary key default newid(),
	id_hoa_don uniqueidentifier unique,
	id_chuong_trinh_giam_gia_hoa_don uniqueidentifier,
	tong_tien decimal  default null,
	so_tien_da_giam decimal  default null,
	tong_tien_thanh_toan decimal  default null,
	trangthai int null,
	foreign key (id_hoa_don) references hoa_don(id),
	foreign key (id_chuong_trinh_giam_gia_hoa_don) references chuong_trinh_giam_gia_hoa_don(id)
)
create table gio_hang_san_pham_yeu_thich(
	id uniqueidentifier primary key default newid(),
	id_khach_hang uniqueidentifier,
	ngay_tao date null,
		trangthai int null,
	foreign key (id_khach_hang) references khach_hang(id)
)
create table gio_hang_san_pham_yeu_thich_chi_tiet(
	id uniqueidentifier primary key default newid(),
	id_gio_hang_san_pham_yeu_thich uniqueidentifier,
	id_giay uniqueidentifier,
	ngay_tao date null,
	trangthai int null,
	foreign key (id_gio_hang_san_pham_yeu_thich) references gio_hang_san_pham_yeu_thich(id),
		foreign key (id_giay) references giay(id)
)
USE [website_ban_giay]
GO
INSERT [dbo].[chuc_vu] ([id], [ma], [ten], [trangthai]) VALUES (N'7b52f69d-5508-4745-926f-a22f9907e523', N'CV02', N'USER', 1)
INSERT [dbo].[chuc_vu] ([id], [ma], [ten], [trangthai]) VALUES (N'f09e9132-7f9e-48bd-a30c-e58c9e16717e', N'CV01', N'ADMIN', 1)
GO
INSERT [dbo].[nhan_vien] ([id], [ma], [ho_ten], [ngay_sinh], [dia_chi], [xa], [huyen], [thanh_pho], [anh], [sdt], [email], [id_chuc_vu], [mat_khau], [ngay_vao_lam], [ngay_nghi_viec], [trangthai]) VALUES (N'9d8372b8-aaa4-464a-a938-3c6927210010', N'NV01', N'Nguyễn Văn Đạt', CAST(N'2003-12-12' AS Date), N'Cấn Hữu-Quốc Oai-Hà Nội', NULL, NULL, NULL, 'avatar.jpg', N'0385090080', N'nv01@gmail.com', N'f09e9132-7f9e-48bd-a30c-e58c9e16717e', N'$2a$10$6xUrgawUAXvPCohIWbL9e.lD8LKI1.ZyeZyo8hKRzQqlqVJ3CpBtq', CAST(N'2020-12-12' AS Date), NULL, 1)
INSERT [dbo].[nhan_vien] ([id], [ma], [ho_ten], [ngay_sinh], [dia_chi], [xa], [huyen], [thanh_pho], [anh], [sdt], [email], [id_chuc_vu], [mat_khau], [ngay_vao_lam], [ngay_nghi_viec], [trangthai]) VALUES (N'5d89f310-6d41-41ee-b5b8-57f5b6db7a9a', N'NV02', N'Hoàng Đại Ka', CAST(N'2003-09-09' AS Date), N'Cấn Hữu-Quốc Oai-Hà Nội', NULL, NULL, NULL, 'avatar.jpg', N'0385090080', N'nv02@gmail.com', N'f09e9132-7f9e-48bd-a30c-e58c9e16717e', N'$2a$10$6xUrgawUAXvPCohIWbL9e.lD8LKI1.ZyeZyo8hKRzQqlqVJ3CpBtq', CAST(N'2023-09-12' AS Date), NULL, 1)
GO
INSERT [dbo].[chuong_tring_giam_gia_san_pham] ([id], [ma], [ten], [phan_tram_giam], [ngay_bat_dau], [ngay_ket_thuc], [id_nhan_vien_create], [id_nhan_vien_update], [trangthai]) VALUES (N'f00c57e5-b909-4ce4-b252-2fb5db12f181', N'GGSP02', N'Mừng 8-3', 20, CAST(N'2023-11-06' AS Date), CAST(N'2023-12-12' AS Date), N'9d8372b8-aaa4-464a-a938-3c6927210010', N'5d89f310-6d41-41ee-b5b8-57f5b6db7a9a', 1)
INSERT [dbo].[chuong_tring_giam_gia_san_pham] ([id], [ma], [ten], [phan_tram_giam], [ngay_bat_dau], [ngay_ket_thuc], [id_nhan_vien_create], [id_nhan_vien_update], [trangthai]) VALUES (N'952d1932-0bb0-4259-bad8-5e373e0bc427', N'GGSP01', N'Mừng Quốc Khánh', 10, CAST(N'2023-11-06' AS Date), CAST(N'2023-12-12' AS Date), N'9d8372b8-aaa4-464a-a938-3c6927210010', N'5d89f310-6d41-41ee-b5b8-57f5b6db7a9a', 1)
INSERT [dbo].[chuong_tring_giam_gia_san_pham] ([id], [ma], [ten], [phan_tram_giam], [ngay_bat_dau], [ngay_ket_thuc], [id_nhan_vien_create], [id_nhan_vien_update], [trangthai]) VALUES (N'74e890cd-8e00-4094-bcda-9eedc589be4a', N'GGSP04', N'Mừng 20-11', 13, CAST(N'2023-11-06' AS Date), CAST(N'2023-12-12' AS Date), N'5d89f310-6d41-41ee-b5b8-57f5b6db7a9a', N'5d89f310-6d41-41ee-b5b8-57f5b6db7a9a', 1)
INSERT [dbo].[chuong_tring_giam_gia_san_pham] ([id], [ma], [ten], [phan_tram_giam], [ngay_bat_dau], [ngay_ket_thuc], [id_nhan_vien_create], [id_nhan_vien_update], [trangthai]) VALUES (N'cd909688-7796-4afd-9943-a1410acd9999', N'GGSP03', N'Mừng Tết', 15, CAST(N'2023-11-06' AS Date), CAST(N'2023-12-12' AS Date), N'9d8372b8-aaa4-464a-a938-3c6927210010', N'5d89f310-6d41-41ee-b5b8-57f5b6db7a9a', 1)
INSERT [dbo].[chuong_tring_giam_gia_san_pham] ([id], [ma], [ten], [phan_tram_giam], [ngay_bat_dau], [ngay_ket_thuc], [id_nhan_vien_create], [id_nhan_vien_update], [trangthai]) VALUES (N'b8cfdf48-7f88-40ba-a227-cc72e40a0294', N'GGSP05', N'Mừng 9-11', 17, CAST(N'2023-11-06' AS Date), CAST(N'2023-12-12' AS Date), N'5d89f310-6d41-41ee-b5b8-57f5b6db7a9a', N'5d89f310-6d41-41ee-b5b8-57f5b6db7a9a', 1)
GO
INSERT [dbo].[hang_khach_hang] ([id], [ma], [ten],[dieu_kien], [trangthai]) VALUES (N'5b52f69d-5508-4745-926f-a22f9907e523', N'HKH01', N'Thành Viên',0, 1)
INSERT [dbo].[hang_khach_hang] ([id], [ma], [ten],[dieu_kien], [trangthai]) VALUES (N'4b52f69d-5508-4745-926f-a22f9907e523', N'HKH02', N'Đồng',10000000, 1)
INSERT [dbo].[hang_khach_hang] ([id], [ma], [ten],[dieu_kien], [trangthai]) VALUES (N'3b52f69d-5508-4745-926f-a22f9907e523', N'HKH03', N'Bạc',25000000, 1)
INSERT [dbo].[hang_khach_hang] ([id], [ma], [ten],[dieu_kien], [trangthai]) VALUES (N'3b52f69d-5508-4745-926f-a22f9907e524', N'HKH04', N'Vàng',50000000, 1)
INSERT [dbo].[hang_khach_hang] ([id], [ma], [ten],[dieu_kien], [trangthai]) VALUES (N'3b52f69d-5508-4745-926f-a22f9907e525', N'HKH05', N'Bạch Kim',100000000, 1)

GO
INSERT [dbo].[quy_doi_diem] ([id], [so_tien_tuong_ung], [so_diem_tuong_ung], [trangthai]) VALUES (N'1b52f69d-5508-4745-926f-a22f9907e523', 1000, 10, 1)

GO
INSERT [dbo].[khach_hang] ([id], [ma], [avatar], [ho_ten], [ngay_sinh], [sdt], [email], [mat_khau],[id_hkh], [trangthai]) VALUES (N'bb772ace-91ff-4cbe-83de-ea5e80fa3843', N'KH01', 'avatar.jpg', N'Nguyễn Danh', CAST(N'2003-09-09' AS Date), N'0385090080', N'kh01@gmail.com', N'$2a$10$6xUrgawUAXvPCohIWbL9e.lD8LKI1.ZyeZyo8hKRzQqlqVJ3CpBtq',N'5b52f69d-5508-4745-926f-a22f9907e523', 1)
INSERT [dbo].[khach_hang] ([id], [ma], [avatar], [ho_ten], [ngay_sinh], [sdt], [email], [mat_khau],[id_hkh], [trangthai]) VALUES (N'019427f5-1e52-402f-b5dc-ec17ad5f139a', N'KH02', 'avatar.jpg', N'Nguyễn Đại', CAST(N'2003-08-08' AS Date), N'0387090080', N'kh02@gmail.com', N'$2a$10$6xUrgawUAXvPCohIWbL9e.lD8LKI1.ZyeZyo8hKRzQqlqVJ3CpBtq',N'5b52f69d-5508-4745-926f-a22f9907e523', 1)
INSERT [dbo].[khach_hang] ([id], [ma], [avatar], [ho_ten], [ngay_sinh], [sdt], [email], [mat_khau],[id_hkh], [trangthai]) VALUES (N'22b427f5-1e52-402f-b5dc-ec17ad5f139a', N'KH03', 'avatar.jpg', N'Đào Gia Phong', CAST(N'2003-09-16' AS Date), N'0385370656', N'kh03@gmail.com', N'$2a$10$6xUrgawUAXvPCohIWbL9e.lD8LKI1.ZyeZyo8hKRzQqlqVJ3CpBtq',N'5b52f69d-5508-4745-926f-a22f9907e523', 1)
GO
	INSERT INTO website_ban_giay.dbo.thuong_hieu (id,ma,ten,trangthai,ten_url) VALUES
	 (N'F6F425A0-AD73-421F-B76B-09787A50808F',N'TH07',N'Gucci',1,N'Logogucci.jpg'),
	 (N'C41E486F-1EB2-40FD-9D2A-1F8F12298488',N'TH08',N'Calvin Klein',1,N'Logocalvin klein.jpg'),
	 (N'EC0054D9-433D-4E85-8190-4582CDEBF593',N'TH03',N'MLB',1,N'LogoMLB.jpg'),
	 (N'4EDE9E53-D595-4893-BDDF-46CCA4147D45',N'TH09',N'YSL',1,N'LogoYSL.jpg'),
	 (N'5A6426F1-A954-4F40-ACE4-47646087F8BB',N'TH04',N'Lacoste',1,N'LogoLacoste.jpg'),
	 (N'12FB3324-EBA7-4F9F-995D-675CAD5097F3',N'TH02',N'Versace',1,N'LogoVersace.jpg'),
	 (N'18364555-4334-4232-997D-8880592056AD',N'TH10',N'Nike',1,N'LogoNike.jpg'),
	 (N'BD4301E2-A17E-4F4D-AAEC-A44534AA7FF5',N'TH12',N'Puma',1,N'LogoPuma.png'),
	 (N'9F92ABD8-69FB-4AC0-9D81-A77C5D525252',N'TH05',N'Adidas',1,N'Logoadidas.jpg'),
	  (N'26E76171-C2CD-4766-97B8-E814FFA80EA6',N'TH06',N'Chanel',1,N'LogoChanel.jpg'),
	 (N'B83D14A1-57D9-4966-A111-E913E7574FD4',N'TH01',N'Dior',1,N'Logodior.jpg'),
	 (N'C3685B9F-8E3E-462B-975E-CC54FDF6DC9E',N'TH11',N'Balenciaga',1,N'LogoBalenciaga.jpg');
GO
INSERT [dbo].[gioi_tinh] ([id], [ma], [ten], [trangthai]) VALUES (N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'GT2', N'Nữ', 1)
INSERT [dbo].[gioi_tinh] ([id], [ma], [ten], [trangthai]) VALUES (N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'GT3', N'Unisex', 1)
INSERT [dbo].[gioi_tinh] ([id], [ma], [ten], [trangthai]) VALUES (N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'GT1', N'Nam', 1)
INSERT [dbo].[gioi_tinh] ([id], [ma], [ten], [trangthai]) VALUES (N'c352570b-c9ba-6b43-b579-a24833969874', N'GT4', N'Trẻ em', 1)
GO
INSERT [dbo].[de_giay] ([id], [ma], [ten], [trangthai]) VALUES (N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'DG5', N'Đế kếp', 1)
INSERT [dbo].[de_giay] ([id], [ma], [ten], [trangthai]) VALUES (N'265bd4e5-0f66-491c-80a3-27c4514f481f', N'DG7', N'Đế giày Christy Wedge', 1)
INSERT [dbo].[de_giay] ([id], [ma], [ten], [trangthai]) VALUES (N'd2732979-a06b-4cb0-9061-329b411586f3', N'DG4', N'Đế phíp', 1)
INSERT [dbo].[de_giay] ([id], [ma], [ten], [trangthai]) VALUES (N'd0767b24-fa85-4d04-bea6-3562b9997ef2', N'DG3', N'Đế da', 1)
INSERT [dbo].[de_giay] ([id], [ma], [ten], [trangthai]) VALUES (N'8c8121cb-da54-47ad-ae66-81295b27e40e', N'DG2', N'Đế Pu', 1)
INSERT [dbo].[de_giay] ([id], [ma], [ten], [trangthai]) VALUES (N'075de902-e04d-4495-9aca-a8c237bea5d7', N'DG6', N'Đế Commando', 1)
INSERT [dbo].[de_giay] ([id], [ma], [ten], [trangthai]) VALUES (N'6c26b653-b861-439f-aeee-ad31e40eae7d', N'DG9', N'Đế giày Cork Nitrile', 1)
INSERT [dbo].[de_giay] ([id], [ma], [ten], [trangthai]) VALUES (N'061ec5b1-b1bf-433d-b0ca-ce800dfaf3e9', N'DG8', N'Đế giày Rubber', 1)
INSERT [dbo].[de_giay] ([id], [ma], [ten], [trangthai]) VALUES (N'6df06d0d-5366-420e-abee-e06b16e193dd', N'DG10', N'Đế giày Crepe', 1)
INSERT [dbo].[de_giay] ([id], [ma], [ten], [trangthai]) VALUES (N'67eb0bd0-732b-4882-9913-fc86f05f733d', N'DG1', N'Đế cao su', 1)
GO
INSERT [dbo].[xuat_xu] ([id], [ma], [ten], [trangthai]) VALUES (N'2d3c1855-497d-47c0-80f1-26500c8f1514', N'XS3', N'Ý', 1)
INSERT [dbo].[xuat_xu] ([id], [ma], [ten], [trangthai]) VALUES (N'0345f695-5836-4185-a2d6-3005156dd0e3', N'XS7', N'Nhật Bản', 1)
INSERT [dbo].[xuat_xu] ([id], [ma], [ten], [trangthai]) VALUES (N'eab91fa5-29b9-4dfe-908e-39c48f11ed65', N'XS8', N'Việt Nam', 1)
INSERT [dbo].[xuat_xu] ([id], [ma], [ten], [trangthai]) VALUES (N'01bccf59-5947-4d84-a465-3d1662853418', N'XS5', N'Đức', 1)
INSERT [dbo].[xuat_xu] ([id], [ma], [ten], [trangthai]) VALUES (N'344b6975-429d-4f20-9287-3d9c95938000', N'XS2', N'Pháp', 1)
INSERT [dbo].[xuat_xu] ([id], [ma], [ten], [trangthai]) VALUES (N'938faa96-3ed2-4385-9a93-52250376c599', N'XS9', N'Tây Ban Nha', 1)
INSERT [dbo].[xuat_xu] ([id], [ma], [ten], [trangthai]) VALUES (N'46f694df-1d74-4973-8421-9afad92cc9af', N'XS6', N'Anh', 1)
INSERT [dbo].[xuat_xu] ([id], [ma], [ten], [trangthai]) VALUES (N'3e32910b-3fa6-43f1-9218-aa83ecbb55de', N'XS10', N'Thụy Sỹ', 1)
INSERT [dbo].[xuat_xu] ([id], [ma], [ten], [trangthai]) VALUES (N'7f30834b-812f-49fa-aa3e-b55b043d7b43', N'XS4', N'Hàn Quốc', 1)
INSERT [dbo].[xuat_xu] ([id], [ma], [ten], [trangthai]) VALUES (N'5232fdef-6d86-471b-b921-ededcded72eb', N'XS1', N'Mỹ', 1)
GO
INSERT [dbo].[kieu_dang] ([id], [ma], [ten], [trangthai]) VALUES (N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'KD6', N'Giày thể thao', 1)
INSERT [dbo].[kieu_dang] ([id], [ma], [ten], [trangthai]) VALUES (N'c03f6c3d-084d-4e22-8c17-20edcf6c15cb', N'KD1', N'Giày Lười', 1)
INSERT [dbo].[kieu_dang] ([id], [ma], [ten], [trangthai]) VALUES (N'5e6b2dbe-916d-4aa5-a9e0-305f3bf66cf7', N'KD10', N'Giày Slip On', 1)
INSERT [dbo].[kieu_dang] ([id], [ma], [ten], [trangthai]) VALUES (N'8dcb4009-0545-4505-85fe-4190d1fcfd02', N'KD5', N'Giày bít mũi', 1)
INSERT [dbo].[kieu_dang] ([id], [ma], [ten], [trangthai]) VALUES (N'06d5b281-9078-4ad5-ae5a-43d26f732976', N'KD4', N'Sneakers', 1)
INSERT [dbo].[kieu_dang] ([id], [ma], [ten], [trangthai]) VALUES (N'4f492d14-468e-4a03-ac38-72e72bd165f4', N'KD8', N'Giày tây', 1)
INSERT [dbo].[kieu_dang] ([id], [ma], [ten], [trangthai]) VALUES (N'79b2042d-9875-4bee-a2e7-747bff370d3a', N'KD7', N'Giày chạy bộ', 1)
INSERT [dbo].[kieu_dang] ([id], [ma], [ten], [trangthai]) VALUES (N'c89f9c86-9c06-4f10-82dd-9c0198dec178', N'KD2', N'Giày cao gót', 1)
INSERT [dbo].[kieu_dang] ([id], [ma], [ten], [trangthai]) VALUES (N'89e541b6-d7b1-4efc-9e0a-9c1fa04f7b43', N'KD3', N'Sandals', 1)
INSERT [dbo].[kieu_dang] ([id], [ma], [ten], [trangthai]) VALUES (N'33777c09-7ab4-4065-872a-e45d6b449b6f', N'KD9', N'Giày bóng rổ', 1)
GO
INSERT [dbo].[chat_lieu] ([id], [ma], [ten], [trangthai]) VALUES (N'2637f26c-a204-42c1-ac7f-12b8fc937c9a', N'CL7', N'Da tổng hợp', 1)
INSERT [dbo].[chat_lieu] ([id], [ma], [ten], [trangthai]) VALUES (N'be5186a7-5389-4fd3-b7af-2bc0db126295', N'CL11', N'Vải lưới', 1)
INSERT [dbo].[chat_lieu] ([id], [ma], [ten], [trangthai]) VALUES (N'cc292b68-7435-4206-96cc-38ad77f2abbc', N'CL6', N'Polyester', 1)
INSERT [dbo].[chat_lieu] ([id], [ma], [ten], [trangthai]) VALUES (N'81189f90-e2c9-481f-9dab-5d71cbe35e0b', N'CL8', N'Canvas', 1)
INSERT [dbo].[chat_lieu] ([id], [ma], [ten], [trangthai]) VALUES (N'40c6e20a-77fe-4194-837d-749cd2c09eed', N'CL2', N'Cao su', 1)
INSERT [dbo].[chat_lieu] ([id], [ma], [ten], [trangthai]) VALUES (N'a93c9f0b-23c4-4f85-bf73-7f735b7e730c', N'CL9', N'Da bò', 1)
INSERT [dbo].[chat_lieu] ([id], [ma], [ten], [trangthai]) VALUES (N'db7162ea-a549-44af-ab96-93d83031f7f1', N'CL5', N'Vải dệt', 1)
INSERT [dbo].[chat_lieu] ([id], [ma], [ten], [trangthai]) VALUES (N'6e43a2a1-006f-43eb-b242-99908da9c1fe', N'CL4', N'Vải cao cấp', 1)
INSERT [dbo].[chat_lieu] ([id], [ma], [ten], [trangthai]) VALUES (N'5d450bc9-283a-452d-aa09-9c0dc9e65ccf', N'CL3', N'Da nhân tạo', 1)
INSERT [dbo].[chat_lieu] ([id], [ma], [ten], [trangthai]) VALUES (N'35130268-9650-4fab-9d9e-a28158d5bce7', N'CL10', N'Da lộn', 1)
INSERT [dbo].[chat_lieu] ([id], [ma], [ten], [trangthai]) VALUES (N'd46378e2-9894-405b-8082-ceeef95345b2', N'CL1', N'Da cao cấp', 1)
GO
INSERT [dbo].[mau_sac] ([id], [ma], [ten], [trangthai]) VALUES (N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'MS2', N'Trắng', 1)
INSERT [dbo].[mau_sac] ([id], [ma], [ten], [trangthai]) VALUES (N'ae8450f7-4fce-4ae9-89c9-0c0b7e8b777f', N'MS9', N'Vàng Hồng', 1)
INSERT [dbo].[mau_sac] ([id], [ma], [ten], [trangthai]) VALUES (N'74d19f0c-fecd-4cb6-9ee9-1e7bcb784f10', N'MS5', N'Bạc', 1)
INSERT [dbo].[mau_sac] ([id], [ma], [ten], [trangthai]) VALUES (N'567e8057-5837-45a1-a76f-3fe524e62eae', N'MS6', N'Xám', 1)
INSERT [dbo].[mau_sac] ([id], [ma], [ten], [trangthai]) VALUES (N'8a4d64b5-42c6-4e91-8c77-4266246b2b52', N'MS11', N'Xanh Blue', 1)
INSERT [dbo].[mau_sac] ([id], [ma], [ten], [trangthai]) VALUES (N'bb9b5a33-457d-4479-a673-7a4018d80ddd', N'MS7', N'Hồng', 1)
INSERT [dbo].[mau_sac] ([id], [ma], [ten], [trangthai]) VALUES (N'310d7290-3b6b-4acd-9ae6-8940e11a3c90', N'MS3', N'Nâu', 1)
INSERT [dbo].[mau_sac] ([id], [ma], [ten], [trangthai]) VALUES (N'a12276c1-86fd-4863-87e6-8e843601d359', N'MS8', N'Đỏ gạch', 1)
INSERT [dbo].[mau_sac] ([id], [ma], [ten], [trangthai]) VALUES (N'83f02a8d-13be-4295-a8ca-a1f02d9ff39a', N'MS10', N'Đen trắng', 1)
INSERT [dbo].[mau_sac] ([id], [ma], [ten], [trangthai]) VALUES (N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'MS1', N'Đen', 1)
INSERT [dbo].[mau_sac] ([id], [ma], [ten], [trangthai]) VALUES (N'79e20a76-3e91-4c53-bd41-ecfef4245fad', N'MS4', N'Xanh navy', 1)
GO
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'0480ba2e-fb54-3c43-8d0c-035be4710c56', N'SP4', N'Giày Sneaker Dior Walk''nDior Sneaker Deep Blue Dior Oblique', N'b83d14a1-57d9-4966-a111-e913e7574fd4', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'6e43a2a1-006f-43eb-b242-99908da9c1fe', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'344b6975-429d-4f20-9287-3d9c95938000', N'06d5b281-9078-4ad5-ae5a-43d26f732976', N'Là đôi giày thời trang đến từ thương hiệu Dior nổi tiếng. Với thiết kế đơn giản, kiểu dáng hiện đại trẻ trung, đôi giày mang đến sự trải nghiệm tuyệt vời nhất cho khách hàng khi đi lên chân.', CAST(26000000 AS Decimal(18, 0)), CAST(31000000 AS Decimal(18, 0)), 1, CAST(24800000 AS Decimal(18, 0)), CAST(N'2023-11-15' AS Date), 2)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'597a9673-6929-d342-96dc-0a4c47e3cc7b', N'SP9', N'Giày Thể Thao MLB Chunky Liner Basic LA', N'ec0054d9-433d-4e85-8190-4582cdebf593', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'd46378e2-9894-405b-8082-ceeef95345b2', N'8c8121cb-da54-47ad-ae66-81295b27e40e', N'79e20a76-3e91-4c53-bd41-ecfef4245fad', N'7f30834b-812f-49fa-aa3e-b55b043d7b43', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N' là mẫu dành cho cả nam và nữ đến từ Hàn Quốc đang được săn đón trên thị trường. Sở hữu thiết kế đẹp mắt cùng sự phối màu trẻ trung ', CAST(3500000 AS Decimal(18, 0)), CAST(3850000 AS Decimal(18, 0)), 1, CAST(3465000 AS Decimal(18, 0)), CAST(N'2023-11-06' AS Date), 3)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'e5e235cd-17e0-8f41-9957-0e4e116df55a', N'SP13', N'Giày Sneaker Nam Gucci Rhyton GG Supreme Canvas Beige Blue', N'f6f425a0-ad73-421f-b76b-09787a50808f', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'81189f90-e2c9-481f-9dab-5d71cbe35e0b', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'310d7290-3b6b-4acd-9ae6-8940e11a3c90', N'2d3c1855-497d-47c0-80f1-26500c8f1514', N'06d5b281-9078-4ad5-ae5a-43d26f732976', N'là đôi giày thời trang đến từ thương hiệu Gucci nổi tiếng. Đôi giày thiết kế theo xu hướng chunky nên được nhiều tín đồ yêu thích và đang thực sự "làm mưa làm gió" trên sàn diễn thời trang đường phố.', CAST(23000000 AS Decimal(18, 0)), CAST(26000000 AS Decimal(18, 0)), 1, CAST(23400000 AS Decimal(18, 0)), CAST(N'2023-11-13' AS Date), 4)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'903fac14-7083-364c-8c2d-144a4f41712b', N'SP21', N'Giày Cao Gót Nữ Yves Saint Laurent YSL Opyum Pointed Toe Pumps', N'4ede9e53-d595-4893-bddf-46cca4147d45', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'344b6975-429d-4f20-9287-3d9c95938000', N'c89f9c86-9c06-4f10-82dd-9c0198dec178', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(3150000 AS Decimal(18, 0)), CAST(N'2023-11-21' AS Date), 2)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'f2e2785b-d430-aa4a-8439-187f202a73e6', N'SP42', N'Giày Thể Thao Puma Erupt Trail Running Shoes', N'bd4301e2-a17e-4f4d-aaec-a44534aa7ff5', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'6e43a2a1-006f-43eb-b242-99908da9c1fe', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'01bccf59-5947-4d84-a465-3d1662853418', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(1500000 AS Decimal(18, 0)), CAST(2000000 AS Decimal(18, 0)), 1, CAST(1800000 AS Decimal(18, 0)), CAST(N'2023-11-17' AS Date), 5)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'4a5281da-8e3c-f945-97ea-22ec86a3c01a', N'SP17', N'Giày Sneaker Nam Calvin Klein CK ', N'c41e486f-1eb2-40fd-9d2a-1f8f12298488', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'2637f26c-a204-42c1-ac7f-12b8fc937c9a', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'5232fdef-6d86-471b-b921-ededcded72eb', N'06d5b281-9078-4ad5-ae5a-43d26f732976', N'Thiết kế giày C&K đơn giản, trẻ trung cùng chất liệu cao cấp sẽ cho bạn trải nghiệm tuyệt vời khi đi lên chân.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(3150000 AS Decimal(18, 0)), CAST(N'2023-11-21' AS Date), 6)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'7e456810-1c13-a342-a811-2bc7c95c1a4d', N'SP20', N'Giày Lười Nữ Calvin Klein CK Womens Lacy Flats', N'c41e486f-1eb2-40fd-9d2a-1f8f12298488', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'310d7290-3b6b-4acd-9ae6-8940e11a3c90', N'5232fdef-6d86-471b-b921-ededcded72eb', N'c03f6c3d-084d-4e22-8c17-20edcf6c15cb', N'Thiết kế giày đơn giản, trẻ trung cùng chất liệu cao cấp, mang lại vẻ đẹp sang trọng và thoải mái cho người dùng.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(3150000 AS Decimal(18, 0)), CAST(N'2023-11-21' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'b5598739-ead0-9943-a729-2efb96fbc8c6', N'SP15', N'Giày Lười Nữ Gucci Leather Espadrille With Double G', N'f6f425a0-ad73-421f-b76b-09787a50808f', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'2637f26c-a204-42c1-ac7f-12b8fc937c9a', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'2d3c1855-497d-47c0-80f1-26500c8f1514', N'c03f6c3d-084d-4e22-8c17-20edcf6c15cb', N'là đôi giày cao cấp dành cho nữ đến từ thương hiệu Gucci nổi tiếng. Mẫu giày với thiết kế gam màu trẻ trung, sang trọng nên được các cô gái yêu thích và săn đón.', CAST(13200000 AS Decimal(18, 0)), CAST(15000000 AS Decimal(18, 0)), 1, CAST(13500000 AS Decimal(18, 0)), CAST(N'2023-11-22' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'0934530d-5de9-9d4e-be5a-347ac95a971f', N'SP5', N'Giày Sneaker Nam Dior Beige & Grey With Logo CD Printed', N'b83d14a1-57d9-4966-a111-e913e7574fd4', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'344b6975-429d-4f20-9287-3d9c95938000', N'06d5b281-9078-4ad5-ae5a-43d26f732976', N'Thiết kế đơn giản, kiểu dáng hiện đại trẻ trung, đôi giày mang đến sự trải nghiệm tuyệt vời cho khách hàng khi đi lên chân.', CAST(26000000 AS Decimal(18, 0)), CAST(31000000 AS Decimal(18, 0)), 1, CAST(27900000 AS Decimal(18, 0)), CAST(N'2023-11-23' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'b55241cb-d126-8741-b3af-35b6f12e3cf1', N'SP45', N'Giày Sneaker Unisex Puma RS-X Tracks', N'bd4301e2-a17e-4f4d-aaec-a44534aa7ff5', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'2637f26c-a204-42c1-ac7f-12b8fc937c9a', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'567e8057-5837-45a1-a76f-3fe524e62eae', N'01bccf59-5947-4d84-a465-3d1662853418', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(3150000 AS Decimal(18, 0)), CAST(N'2023-11-23' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'390ecab5-666a-8c49-8b8c-3c894b85ea05', N'SP52', N'Giày Lười Nữ Chanel CC Gray Patent Loafers', N'26e76171-c2cd-4766-97b8-e814ffa80ea6', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'344b6975-429d-4f20-9287-3d9c95938000', N'c03f6c3d-084d-4e22-8c17-20edcf6c15cb', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(35000000 AS Decimal(18, 0)), CAST(40000000 AS Decimal(18, 0)), 1, CAST(36000000 AS Decimal(18, 0)), CAST(N'2023-11-24' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'7dd94f65-b809-924e-a9ca-3d24ff849c5a', N'SP53', N'Giày Lười Nữ Chanel CC Gold Logo Turn Buckle Design Waxed Lambskin Loafers', N'26e76171-c2cd-4766-97b8-e814ffa80ea6', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'd46378e2-9894-405b-8082-ceeef95345b2', N'd0767b24-fa85-4d04-bea6-3562b9997ef2', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'344b6975-429d-4f20-9287-3d9c95938000', N'c03f6c3d-084d-4e22-8c17-20edcf6c15cb', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(38500000 AS Decimal(18, 0)), CAST(40000000 AS Decimal(18, 0)), 1, CAST(32000000 AS Decimal(18, 0)), CAST(N'2023-11-26' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'3f71490b-511f-7b44-84c9-3fe4413b3188', N'SP8', N'Giày Thể Thao MLB Chunky Classic P LA Dodger', N'ec0054d9-433d-4e85-8190-4582cdebf593', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'd46378e2-9894-405b-8082-ceeef95345b2', N'd2732979-a06b-4cb0-9061-329b411586f3', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'7f30834b-812f-49fa-aa3e-b55b043d7b43', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N' là mẫu dành cho cả nam và nữ đến từ Hàn Quốc đang làm mưa làm gió thời gian qua trên thị trường. Sở hữu thiết kế đẹp mắt cùng chất liệu cao cấp ', CAST(3000000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(2800000 AS Decimal(18, 0)), CAST(N'2023-11-06' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'9a19ff2b-786c-a142-a356-403777815763', N'SP36', N'Giày Thể Thao Nike Air Jordan 312 Low Tech Grey CD7069-101', N'18364555-4334-4232-997d-8880592056ad', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'567e8057-5837-45a1-a76f-3fe524e62eae', N'5232fdef-6d86-471b-b921-ededcded72eb', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(7500000 AS Decimal(18, 0)), CAST(9000000 AS Decimal(18, 0)), 1, CAST(7200000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'265c44e3-380b-c340-a940-4719e1b1ba86', N'SP29', N'Giày Slip On Lacoste Jump Serve Canvas Slip On', N'5a6426f1-a954-4f40-ace4-47646087f8bb', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'79e20a76-3e91-4c53-bd41-ecfef4245fad', N'5232fdef-6d86-471b-b921-ededcded72eb', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(2800000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'd94e2cc2-dbd9-4e4e-aa1a-488ece55e70d', N'SP28', N'Giày Thể Thao Lacoste L005 222 Màu Trắng - Xanh', N'5a6426f1-a954-4f40-ace4-47646087f8bb', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'5232fdef-6d86-471b-b921-ededcded72eb', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(2800000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'97444936-0840-914b-9900-4d1bfe39ab40', N'SP49', N'Giày Thể Thao Adidas Solarboost 5 Shoes GW1962 ', N'9f92abd8-69fb-4ac0-9d81-a77c5d525252', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'db7162ea-a549-44af-ab96-93d83031f7f1', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'567e8057-5837-45a1-a76f-3fe524e62eae', N'01bccf59-5947-4d84-a465-3d1662853418', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(3500000 AS Decimal(18, 0)), CAST(4500000 AS Decimal(18, 0)), 1, CAST(3600000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'3289f7c5-cbc1-d244-a347-519a69bfeb76', N'SP57', N'Giày Balenciaga Speed Sneaker Màu Đen', N'c3685b9f-8e3e-462b-975e-cc54fdf6dc9e', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'938faa96-3ed2-4385-9a93-52250376c599', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(12000000 AS Decimal(18, 0)), CAST(13000000 AS Decimal(18, 0)), 1, CAST(10400000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'909849bf-6822-8242-b385-5bdc8953d684', N'SP7', N'Giày Thể Thao MLB Big Ball Chunky A Line New York', N'ec0054d9-433d-4e85-8190-4582cdebf593', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'2637f26c-a204-42c1-ac7f-12b8fc937c9a', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'2d3c1855-497d-47c0-80f1-26500c8f1514', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N' là một trong những bộ sưu tập nổi tiếng của thương hiệu thời trang Hàn Quốc - MLB Korea12. Được rất nhiều tín đồ thời trang thế giới ưa chuộng bởi phong cách đặc biệt cá tính, mang đậm phong cách đường phố đương đại12.', CAST(2640000 AS Decimal(18, 0)), CAST(3680000 AS Decimal(18, 0)), 1, CAST(2944000 AS Decimal(18, 0)), CAST(N'2023-11-06' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'5032713b-839d-c746-914e-5c29004fc3c7', N'SP18', N'Giày Lười Nam Calvin Klein CK Ori Driving Style Loafer', N'c41e486f-1eb2-40fd-9d2a-1f8f12298488', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'5232fdef-6d86-471b-b921-ededcded72eb', N'c03f6c3d-084d-4e22-8c17-20edcf6c15cb', N'Thiết kế giày đơn giản, trẻ trung cùng chất liệu cao cấp, mang lại vẻ đẹp sang trọng và thoải mái cho người dùng.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(2800000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'1fde7a06-9aed-ee40-a7ae-605296f1ce68', N'SP54', N'Giày Sục Nam Balenciaga Slipper Cosy Black Leather Logo BB', N'c3685b9f-8e3e-462b-975e-cc54fdf6dc9e', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'938faa96-3ed2-4385-9a93-52250376c599', N'c03f6c3d-084d-4e22-8c17-20edcf6c15cb', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(12500000 AS Decimal(18, 0)), CAST(15000000 AS Decimal(18, 0)), 1, CAST(13050000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'74e74ba3-6285-c247-b588-62e0cd12e1e1', N'SP48', N'Giày Thể Thao Adidas Grand Court F36392/F36483/GW6511', N'9f92abd8-69fb-4ac0-9d81-a77c5d525252', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'2637f26c-a204-42c1-ac7f-12b8fc937c9a', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'01bccf59-5947-4d84-a465-3d1662853418', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(1500000 AS Decimal(18, 0)), CAST(2000000 AS Decimal(18, 0)), 1, CAST(1740000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'a0e7fe26-c8e0-b348-ac67-6440f74b2b08', N'SP19', N'Giày Lười Nam Calvin Klein CK Ori Driving Style Loafer', N'c41e486f-1eb2-40fd-9d2a-1f8f12298488', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'5232fdef-6d86-471b-b921-ededcded72eb', N'c03f6c3d-084d-4e22-8c17-20edcf6c15cb', N'Thiết kế giày đơn giản, trẻ trung cùng chất liệu cao cấp, mang lại vẻ đẹp sang trọng và thoải mái cho người dùng.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(3045000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'12300a57-8b03-2846-927e-6cfc2ae3b52e', N'SP37', N'Giày Thể Thao Nike Court Vision Low Next Nature', N'18364555-4334-4232-997d-8880592056ad', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'5232fdef-6d86-471b-b921-ededcded72eb', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(1500000 AS Decimal(18, 0)), CAST(2500000 AS Decimal(18, 0)), 1, CAST(2175000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'3af55b31-a9a6-e548-a8f7-6e598f233d98', N'SP23', N'Giày Cói Slip On Nữ Yves Saint Laurent YSL Espadrilles In Leather With YSL Monogram', N'4ede9e53-d595-4893-bddf-46cca4147d45', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'40c6e20a-77fe-4194-837d-749cd2c09eed', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'344b6975-429d-4f20-9287-3d9c95938000', N'5e6b2dbe-916d-4aa5-a9e0-305f3bf66cf7', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(7500000 AS Decimal(18, 0)), CAST(9500000 AS Decimal(18, 0)), 1, CAST(8265000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'47d0e3c9-7e14-b446-af5d-747f9fc3d1c8', N'SP22', N'Giày Sneakers Yves Saint Laurent YSL Court Classic SL/06 Smooth Leather', N'4ede9e53-d595-4893-bddf-46cca4147d45', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'83f02a8d-13be-4295-a8ca-a1f02d9ff39a', N'344b6975-429d-4f20-9287-3d9c95938000', N'06d5b281-9078-4ad5-ae5a-43d26f732976', N'Giày được làm từ chất liệu cao cấp mang đến cảm giác thoải mái cho người sử dụng.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(3045000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'6942e418-c92f-d945-85ab-74a6a381a95d', N'SP47', N'iày Thể Thao Adidas Grand Court Cloudfoam Lifestyle Court Comfort', N'9f92abd8-69fb-4ac0-9d81-a77c5d525252', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'01bccf59-5947-4d84-a465-3d1662853418', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(1500000 AS Decimal(18, 0)), CAST(2500000 AS Decimal(18, 0)), 1, CAST(2175000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'3923e251-e5b0-ac4a-a017-7b0379b9f242', N'SP34', N'Giày Slip On Nam Versace DSU8140S', N'12fb3324-eba7-4f9f-995d-675cad5097f3', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'd46378e2-9894-405b-8082-ceeef95345b2', N'd0767b24-fa85-4d04-bea6-3562b9997ef2', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'2d3c1855-497d-47c0-80f1-26500c8f1514', N'c03f6c3d-084d-4e22-8c17-20edcf6c15cb', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(7500000 AS Decimal(18, 0)), CAST(9000000 AS Decimal(18, 0)), 1, CAST(7830000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'cb626472-c91c-ec4a-9369-7b0942e94edd', N'SP3', N'Giày Cao Gót Nữ Dior J’adior Slingback Pump', N'b83d14a1-57d9-4966-a111-e913e7574fd4', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'6e43a2a1-006f-43eb-b242-99908da9c1fe', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'ae8450f7-4fce-4ae9-89c9-0c0b7e8b777f', N'344b6975-429d-4f20-9287-3d9c95938000', N'c89f9c86-9c06-4f10-82dd-9c0198dec178', N' là đôi giày thời trang đến từ thương hiệu  của Pháp. Mang thiết kế cổ điển, đôi giày không chỉ nâng niu gót ngọc, tôn lên vẻ nữ tính, uyển chuyển mà còn lại mang lại cảm giác thoải mái, êm ái khi mang.', CAST(20000000 AS Decimal(18, 0)), CAST(25900000 AS Decimal(18, 0)), 1, CAST(22015000 AS Decimal(18, 0)), CAST(N'2023-11-06' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'fc4c744b-aede-b645-b118-7ea9140b8249', N'SP2', N'Giày Sneaker Dior Walk''nDior Black Cotton ', N'b83d14a1-57d9-4966-a111-e913e7574fd4', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'81189f90-e2c9-481f-9dab-5d71cbe35e0b', N'8c8121cb-da54-47ad-ae66-81295b27e40e', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'344b6975-429d-4f20-9287-3d9c95938000', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N' là đôi giày thời trang đến từ thương hiệu Dior nổi tiếng. Với thiết kế đơn giản, kiểu dáng hiện đại trẻ trung, đôi giày mang đến sự trải nghiệm tuyệt vời nhất cho khách hàng khi đi lên chân.', CAST(20000000 AS Decimal(18, 0)), CAST(22730000 AS Decimal(18, 0)), 1, CAST(19320500 AS Decimal(18, 0)), CAST(N'2023-11-06' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'8720d28f-5e84-7047-a0cd-886872b4dbc1', N'SP51', N'Giày Lười Nữ Chanel CC NIB Black Leather Loafers Gold Logo', N'26e76171-c2cd-4766-97b8-e814ffa80ea6', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'344b6975-429d-4f20-9287-3d9c95938000', N'c03f6c3d-084d-4e22-8c17-20edcf6c15cb', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(35000000 AS Decimal(18, 0)), CAST(40000000 AS Decimal(18, 0)), 1, CAST(34000000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'9e436193-c9ea-9941-85ca-96ada4db4bac', N'SP44', N'Giày Sneaker Unisex Puma RS-X 3D Harbor Mist', N'bd4301e2-a17e-4f4d-aaec-a44534aa7ff5', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'2637f26c-a204-42c1-ac7f-12b8fc937c9a', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'01bccf59-5947-4d84-a465-3d1662853418', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(2975000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'c00d80a0-1adb-7845-9f3b-9ac42c2e2d1c', N'SP1', N'Giày Dior Homme B06 Leather Slip-On', N'b83d14a1-57d9-4966-a111-e913e7574fd4', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'be5186a7-5389-4fd3-b7af-2bc0db126295', N'075de902-e04d-4495-9aca-a8c237bea5d7', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'344b6975-429d-4f20-9287-3d9c95938000', N'5e6b2dbe-916d-4aa5-a9e0-305f3bf66cf7', N'có thiết kế năng động, hiện đại. Đặc biệt với kiểu dáng ôm chân vừa vặn mang đến sự trải nghiệm tốt nhất cho đôi chân của bạn.', CAST(10000000 AS Decimal(18, 0)), CAST(15000000 AS Decimal(18, 0)), 1, CAST(12750000 AS Decimal(18, 0)), CAST(N'2023-11-06' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'91a7f37b-8538-e24f-8299-a06bd6640095', N'SP24', N'Giày Thể Thao Nam Yves Saint Laurent YSL Court Classic', N'4ede9e53-d595-4893-bddf-46cca4147d45', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'2637f26c-a204-42c1-ac7f-12b8fc937c9a', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'344b6975-429d-4f20-9287-3d9c95938000', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Trắng có thiết kế đơn giản, kiểu dáng trẻ trung, hiện đại mang nét đặc trưng của thương hiệu YSL nổi tiếng.', CAST(3500000 AS Decimal(18, 0)), CAST(4500000 AS Decimal(18, 0)), 1, CAST(3825000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'6de72fc4-57ac-4e47-b37a-a5d31d05c5e9', N'SP10', N'Giày Sneaker MLB Chunky Wide New York Yankees', N'ec0054d9-433d-4e85-8190-4582cdebf593', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'2637f26c-a204-42c1-ac7f-12b8fc937c9a', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'7f30834b-812f-49fa-aa3e-b55b043d7b43', N'06d5b281-9078-4ad5-ae5a-43d26f732976', N'là đôi giày thời trang dành cho cả nam và nữ đến từ thương hiệu MLB nổi tiếng của Hàn Quốc. Đôi giày có thiết kế đẹp mắt, cùng gam màu trang nhã sẽ cho bạn trải nghiệm tuyệt vời khi đi lên chân.', CAST(2500000 AS Decimal(18, 0)), CAST(3000000 AS Decimal(18, 0)), 1, CAST(2550000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'51e08f27-5698-704a-b703-a6dc5d16a3a8', N'SP43', N'Giày Sneaker Nữ Puma RS-X 3D Shoes', N'bd4301e2-a17e-4f4d-aaec-a44534aa7ff5', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'2637f26c-a204-42c1-ac7f-12b8fc937c9a', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'83f02a8d-13be-4295-a8ca-a1f02d9ff39a', N'01bccf59-5947-4d84-a465-3d1662853418', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(2975000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'046dc72b-3057-ff4d-bbf3-a865de58b6a8', N'SP11', N'Giày Lười Nam Gucci Leather Signature Driver', N'f6f425a0-ad73-421f-b76b-09787a50808f', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'2637f26c-a204-42c1-ac7f-12b8fc937c9a', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'2d3c1855-497d-47c0-80f1-26500c8f1514', N'c03f6c3d-084d-4e22-8c17-20edcf6c15cb', N'là mẫu giày dành cho nam đến từ thương hiệu Gucci. Đôi giày được làm từ chất liệu cao cấp, mang đến cho người dùng sự thoải mái khi đi lên chân.', CAST(9000000 AS Decimal(18, 0)), CAST(12200000 AS Decimal(18, 0)), 1, CAST(10370000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'8d1d8b62-1395-5d43-b474-ab88c5bd7a39', N'SP56', N'Giày Cao Gót Balenciaga Black BB Pumps Velvet', N'c3685b9f-8e3e-462b-975e-cc54fdf6dc9e', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'd46378e2-9894-405b-8082-ceeef95345b2', N'd0767b24-fa85-4d04-bea6-3562b9997ef2', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'938faa96-3ed2-4385-9a93-52250376c599', N'c89f9c86-9c06-4f10-82dd-9c0198dec178', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(11500000 AS Decimal(18, 0)), CAST(1300000 AS Decimal(18, 0)), 1, CAST(1079000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'6049b327-e697-d74d-a3ab-bb1f7543b139', N'SP6', N'Giày Thể Thao MLB Chunky Liner Diamond', N'ec0054d9-433d-4e85-8190-4582cdebf593', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'd46378e2-9894-405b-8082-ceeef95345b2', N'd2732979-a06b-4cb0-9061-329b411586f3', N'bb9b5a33-457d-4479-a673-7a4018d80ddd', N'7f30834b-812f-49fa-aa3e-b55b043d7b43', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'  là mẫu dành cho cả nam và nữ đến từ Hàn Quốc. Sở hữu thiết kế đẹp mắt cùng chất liệu cao cấp, mẫu giày cho người dùng thêm năng động và thoải mái khi đi lên chân.', CAST(3000000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(2905000 AS Decimal(18, 0)), CAST(N'2023-11-06' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'7603d98a-9e2a-954e-aa88-bb7c598fca5e', N'SP12', N'Giày Sneaker Nữ Gucci GG Tennis 1977', N'f6f425a0-ad73-421f-b76b-09787a50808f', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'6e43a2a1-006f-43eb-b242-99908da9c1fe', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'8a4d64b5-42c6-4e91-8c77-4266246b2b52', N'2d3c1855-497d-47c0-80f1-26500c8f1514', N'06d5b281-9078-4ad5-ae5a-43d26f732976', N'Thiết kế sang trọng và thời trang cùng chất liệu cao cấp mang lại phong cách thời trang ấn tượng và thoải mái khi đeo lên chân.', CAST(14500000 AS Decimal(18, 0)), CAST(17000000 AS Decimal(18, 0)), 1, CAST(14110000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'a890b541-d210-7a42-bfa9-bb9ab4a73f60', N'SP30', N'Giày Thể Thao Lacoste L004 Canvas', N'5a6426f1-a954-4f40-ace4-47646087f8bb', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'6e43a2a1-006f-43eb-b242-99908da9c1fe', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'5232fdef-6d86-471b-b921-ededcded72eb', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(2905000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'1a254d4f-632c-c045-a84b-bbdb94e75eae', N'SP25', N'Giày Thể Thao Nam Yves Saint Laurent YSL Court Classic', N'4ede9e53-d595-4893-bddf-46cca4147d45', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'2637f26c-a204-42c1-ac7f-12b8fc937c9a', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'344b6975-429d-4f20-9287-3d9c95938000', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Có thiết kế đơn giản, kiểu dáng trẻ trung, hiện đại mang nét đặc trưng của thương hiệu YSL nổi tiếng.', CAST(10500000 AS Decimal(18, 0)), CAST(14500000 AS Decimal(18, 0)), 1, CAST(12035000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'39774a40-f33b-8f41-b22e-bc50ca964779', N'SP35', N'Giày Slip On Versace DSU8153S', N'12fb3324-eba7-4f9f-995d-675cad5097f3', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'74d19f0c-fecd-4cb6-9ee9-1e7bcb784f10', N'2d3c1855-497d-47c0-80f1-26500c8f1514', N'c03f6c3d-084d-4e22-8c17-20edcf6c15cb', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(7500000 AS Decimal(18, 0)), CAST(9000000 AS Decimal(18, 0)), 1, CAST(7470000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'cbf8ab37-e9bb-8b47-ae14-bfe98eb6a711', N'SP50', N'Giày Thể Thao Adidas Samba OG Shoes B75806 ', N'9f92abd8-69fb-4ac0-9d81-a77c5d525252', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'01bccf59-5947-4d84-a465-3d1662853418', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(3500000 AS Decimal(18, 0)), CAST(4500000 AS Decimal(18, 0)), 1, CAST(3735000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'2e5e0e52-896b-304b-af47-c00efb474bb4', N'SP26', N'Giày Lười Nam Lacoste Concours 118 Leather Loafer Shoes', N'5a6426f1-a954-4f40-ace4-47646087f8bb', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'310d7290-3b6b-4acd-9ae6-8940e11a3c90', N'5232fdef-6d86-471b-b921-ededcded72eb', N'c03f6c3d-084d-4e22-8c17-20edcf6c15cb', N'là đôi giày cao cấp đến từ thương hiệu Lacoste nổi tiếng. Với gam màu đen hiện đại cùng kiểu dáng trẻ trung, Lacoste Concours 118 đang là đôi giày HOT được nhiều tín đồ thời trang săn đón.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(2905000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'90545bde-c344-5949-aba2-c14c7d673bd2', N'SP46', N'Giày Thể Thao Nữ Adidas Court Platform H06299', N'9f92abd8-69fb-4ac0-9d81-a77c5d525252', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'01bccf59-5947-4d84-a465-3d1662853418', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(2905000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'c840d102-65e6-0847-b251-c854e12434bf', N'SP38', N'Giày Thể Thao Nike Air Force 1 ’07 ‘White Black’ CT2302-100', N'18364555-4334-4232-997d-8880592056ad', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'83f02a8d-13be-4295-a8ca-a1f02d9ff39a', N'5232fdef-6d86-471b-b921-ededcded72eb', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(1500000 AS Decimal(18, 0)), CAST(2500000 AS Decimal(18, 0)), 1, CAST(2075000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'e4cb0d64-a60f-134d-a0c8-cbc0c047f23f', N'SP14', N'Giày Sneaker Nam Gucci Men’s 100 Rhyton', N'f6f425a0-ad73-421f-b76b-09787a50808f', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'81189f90-e2c9-481f-9dab-5d71cbe35e0b', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'310d7290-3b6b-4acd-9ae6-8940e11a3c90', N'2d3c1855-497d-47c0-80f1-26500c8f1514', N'06d5b281-9078-4ad5-ae5a-43d26f732976', N'là đôi giày thời trang đến từ thương hiệu Gucci nổi tiếng. Với thiết kế kiểu dáng hiện đại và trẻ trung, đôi giày mang đến sự trải nghiệm tuyệt vời cho khách hàng khi đi lên chân.', CAST(22000000 AS Decimal(18, 0)), CAST(26000000 AS Decimal(18, 0)), 1, CAST(21580000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'feaf547d-1b1d-5141-b413-ced86da78e9f', N'SP31', N'Giày Cao Gót Nữ Versace Jeans Couture Multicolour Patterned Stiletto Pumps', N'12fb3324-eba7-4f9f-995d-675cad5097f3', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'ae8450f7-4fce-4ae9-89c9-0c0b7e8b777f', N'2d3c1855-497d-47c0-80f1-26500c8f1514', N'c89f9c86-9c06-4f10-82dd-9c0198dec178', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(4500000 AS Decimal(18, 0)), CAST(5500000 AS Decimal(18, 0)), 1, CAST(4565000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'edca9d8a-9b70-4d49-9dd0-d145eda8805c', N'SP55', N'Giày Sục Nữ Balenciaga Women''s Square Knife Bb Leather Mules', N'c3685b9f-8e3e-462b-975e-cc54fdf6dc9e', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'938faa96-3ed2-4385-9a93-52250376c599', N'c89f9c86-9c06-4f10-82dd-9c0198dec178', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(11500000 AS Decimal(18, 0)), CAST(1300000 AS Decimal(18, 0)), 1, CAST(1105000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'fcd2baf8-80ab-6e4b-92b8-d24377ead408', N'SP39', N'Giày Thể Thao Nike Downshifter 11 Road Running Shoes', N'18364555-4334-4232-997d-8880592056ad', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'be5186a7-5389-4fd3-b7af-2bc0db126295', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'ae8450f7-4fce-4ae9-89c9-0c0b7e8b777f', N'5232fdef-6d86-471b-b921-ededcded72eb', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(1500000 AS Decimal(18, 0)), CAST(2500000 AS Decimal(18, 0)), 1, CAST(2125000 AS Decimal(18, 0)), CAST(N'2023-11-27' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'68767e95-effc-ee48-8d3e-d7693b44324f', N'SP16', N'Giày Sneaker Nam Calvin Klein CK', N'c41e486f-1eb2-40fd-9d2a-1f8f12298488', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'2637f26c-a204-42c1-ac7f-12b8fc937c9a', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'5232fdef-6d86-471b-b921-ededcded72eb', N'06d5b281-9078-4ad5-ae5a-43d26f732976', N'Thiết kế giày C&K đơn giản, trẻ trung cùng chất liệu cao cấp sẽ cho bạn trải nghiệm tuyệt vời khi đi lên chân.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(2975000 AS Decimal(18, 0)), CAST(N'2023-11-28' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'e6d07e36-852b-0448-adaf-da91ad40a604', N'SP40', N'Giày Thể Thao Nữ Nike WMNS Air Zoom Pegasus 37 ''Pale Ivory''', N'18364555-4334-4232-997d-8880592056ad', N'4ec08b9f-d257-7a49-a72d-0d6bd38033b4', N'6e43a2a1-006f-43eb-b242-99908da9c1fe', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'ae8450f7-4fce-4ae9-89c9-0c0b7e8b777f', N'5232fdef-6d86-471b-b921-ededcded72eb', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(1500000 AS Decimal(18, 0)), CAST(2500000 AS Decimal(18, 0)), 1, CAST(2125000 AS Decimal(18, 0)), CAST(N'2023-11-28' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'c9bc38bf-69d3-4f4f-9cd8-daead9d2270b', N'SP41', N'Giày Thể Thao Puma Anzarun 2.0 Open Road', N'bd4301e2-a17e-4f4d-aaec-a44534aa7ff5', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'6e43a2a1-006f-43eb-b242-99908da9c1fe', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'01bccf59-5947-4d84-a465-3d1662853418', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(3500000 AS Decimal(18, 0)), CAST(5500000 AS Decimal(18, 0)), 1, CAST(4785000 AS Decimal(18, 0)), CAST(N'2023-11-28' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'd2af8592-0420-854f-acdc-db812b0f4d8d', N'SP32', N'Giày Sneaker Nam Versace White With Blue Heel Printed', N'12fb3324-eba7-4f9f-995d-675cad5097f3', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'2d3c1855-497d-47c0-80f1-26500c8f1514', N'06d5b281-9078-4ad5-ae5a-43d26f732976', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(7500000 AS Decimal(18, 0)), CAST(9500000 AS Decimal(18, 0)), 1, CAST(8265000 AS Decimal(18, 0)), CAST(N'2023-11-28' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'194056c9-4dce-ad4c-9619-e6b9762d05a9', N'SP33', N'Giày Lười Nam Versace Medusa Mules', N'12fb3324-eba7-4f9f-995d-675cad5097f3', N'48bc5fb5-819d-5748-9ce6-310ae8cf4579', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'51711a46-897d-4f59-a4dd-ab9ef99515fc', N'2d3c1855-497d-47c0-80f1-26500c8f1514', N'c03f6c3d-084d-4e22-8c17-20edcf6c15cb', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(17500000 AS Decimal(18, 0)), CAST(20500000 AS Decimal(18, 0)), 1, CAST(17835000 AS Decimal(18, 0)), CAST(N'2023-11-28' AS Date), 1)
INSERT [dbo].[giay] ([id], [ma], [ten], [id_thuong_hieu], [id_gioi_tinh], [id_chat_lieu], [id_de_giay], [id_mau_sac], [id_xuat_xu], [id_kieu_dang], [mota], [gianhap], [giaban], [trangthai], [gia_sau_khuyen_mai], [ngay_nhap], [do_hot]) VALUES (N'c6eaf65e-9efd-404b-b5fb-fb5b59200f7b', N'SP27', N'Giày Thể Thao Lacoste Active 4851 2221', N'5a6426f1-a954-4f40-ace4-47646087f8bb', N'd523ebba-d211-0345-8ee8-15ecd456dbd9', N'd46378e2-9894-405b-8082-ceeef95345b2', N'4b9e5750-a2cf-4a8f-ae41-027e0fdf88ef', N'5ff95118-af6a-484c-94ba-064c3bb96fdc', N'5232fdef-6d86-471b-b921-ededcded72eb', N'5d0bc152-d2bd-4d80-91de-1820e24b6ff6', N'Đôi giày được làm từ chất liệu cao cấp mang lại cảm giác thoải mái khi đi lên chân.', CAST(2500000 AS Decimal(18, 0)), CAST(3500000 AS Decimal(18, 0)), 1, CAST(3045000 AS Decimal(18, 0)), CAST(N'2023-11-28' AS Date), 1)
GO
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'd49da5a9-81e1-574d-92f4-008f40d22d10', N'9e436193-c9ea-9941-85ca-96ada4db4bac', N'cd909688-7796-4afd-9943-a1410acd9999', CAST(525000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'b1474ed8-b90a-884d-96ab-009048bca587', N'046dc72b-3057-ff4d-bbf3-a865de58b6a8', N'cd909688-7796-4afd-9943-a1410acd9999', CAST(1830000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'f3ef7427-1d72-4141-b66f-0ea22c670a5d', N'194056c9-4dce-ad4c-9619-e6b9762d05a9', N'74e890cd-8e00-4094-bcda-9eedc589be4a', CAST(2665000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'770e2857-5264-a247-bd06-0ea86f002243', N'68767e95-effc-ee48-8d3e-d7693b44324f', N'cd909688-7796-4afd-9943-a1410acd9999', CAST(525000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'774f8031-90a5-7742-a5a7-0ebb01751f70', N'7603d98a-9e2a-954e-aa88-bb7c598fca5e', N'b8cfdf48-7f88-40ba-a227-cc72e40a0294', CAST(2890000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'0612ad82-7906-1a46-84ed-10e9adb94172', N'8720d28f-5e84-7047-a0cd-886872b4dbc1', N'cd909688-7796-4afd-9943-a1410acd9999', CAST(6000000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'a18bd4f5-f150-bf45-8e95-1851fb7d8cf1', N'1fde7a06-9aed-ee40-a7ae-605296f1ce68', N'74e890cd-8e00-4094-bcda-9eedc589be4a', CAST(1950000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'2ff09f47-8aae-1b4c-aee7-26c837dff37c', N'c9bc38bf-69d3-4f4f-9cd8-daead9d2270b', N'74e890cd-8e00-4094-bcda-9eedc589be4a', CAST(715000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'a2cdfa09-25ad-a541-9893-2ccc1d1e6529', N'f2e2785b-d430-aa4a-8439-187f202a73e6', N'952d1932-0bb0-4259-bad8-5e373e0bc427', CAST(200000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'c669a1f6-2998-cb42-ab1f-2fed7f2f90d6', N'c6eaf65e-9efd-404b-b5fb-fb5b59200f7b', N'74e890cd-8e00-4094-bcda-9eedc589be4a', CAST(455000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'7b251600-5df8-464a-b05a-311fc3fb5304', N'97444936-0840-914b-9900-4d1bfe39ab40', N'f00c57e5-b909-4ce4-b252-2fb5db12f181', CAST(900000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'f5015c97-3102-9247-988a-3bb7c5bb486e', N'903fac14-7083-364c-8c2d-144a4f41712b', N'952d1932-0bb0-4259-bad8-5e373e0bc427', CAST(350000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'd6efdd59-bdf2-da44-8360-3fe779f19e8f', N'90545bde-c344-5949-aba2-c14c7d673bd2', N'b8cfdf48-7f88-40ba-a227-cc72e40a0294', CAST(595000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'b5a3ff54-4a18-8b40-9605-4889a8e2713a', N'39774a40-f33b-8f41-b22e-bc50ca964779', N'b8cfdf48-7f88-40ba-a227-cc72e40a0294', CAST(1530000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'44353820-c565-dd47-b300-491bb6d58639', N'edca9d8a-9b70-4d49-9dd0-d145eda8805c', N'cd909688-7796-4afd-9943-a1410acd9999', CAST(195000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'57c5917c-a138-bc4a-9205-4deb149b78ba', N'74e74ba3-6285-c247-b588-62e0cd12e1e1', N'74e890cd-8e00-4094-bcda-9eedc589be4a', CAST(260000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'5737bb68-ac65-3843-88e5-55f45964d8ea', N'4a5281da-8e3c-f945-97ea-22ec86a3c01a', N'952d1932-0bb0-4259-bad8-5e373e0bc427', CAST(350000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'19154ecc-3d37-b248-b062-5aef1e3efa27', N'12300a57-8b03-2846-927e-6cfc2ae3b52e', N'74e890cd-8e00-4094-bcda-9eedc589be4a', CAST(325000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'05090fce-62da-0e48-ad3c-5c884e17917d', N'8d1d8b62-1395-5d43-b474-ab88c5bd7a39', N'b8cfdf48-7f88-40ba-a227-cc72e40a0294', CAST(221000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'fdadd8da-ead7-444e-988a-5c8ea32d5df8', N'c840d102-65e6-0847-b251-c854e12434bf', N'b8cfdf48-7f88-40ba-a227-cc72e40a0294', CAST(425000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'd0dfcea3-4c56-0248-b540-5dbefac2f457', N'feaf547d-1b1d-5141-b413-ced86da78e9f', N'b8cfdf48-7f88-40ba-a227-cc72e40a0294', CAST(935000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'e25cfe64-6150-264b-ab12-5ed72962dc7c', N'cb626472-c91c-ec4a-9369-7b0942e94edd', N'cd909688-7796-4afd-9943-a1410acd9999', CAST(3885000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'868d988b-2ecc-a348-a4c2-64b6c4795aed', N'3af55b31-a9a6-e548-a8f7-6e598f233d98', N'74e890cd-8e00-4094-bcda-9eedc589be4a', CAST(1235000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'a23fb7d3-c478-2c4e-a479-65db46180107', N'e6d07e36-852b-0448-adaf-da91ad40a604', N'cd909688-7796-4afd-9943-a1410acd9999', CAST(375000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'61378ffc-b411-c04c-a5de-6a98c72ee258', N'd94e2cc2-dbd9-4e4e-aa1a-488ece55e70d', N'f00c57e5-b909-4ce4-b252-2fb5db12f181', CAST(700000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'808f1e29-b2f0-a341-a0d6-6d73aac7ca74', N'6049b327-e697-d74d-a3ab-bb1f7543b139', N'b8cfdf48-7f88-40ba-a227-cc72e40a0294', CAST(595000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'58d42cd7-b952-7e4c-ac3a-6fbeff7002d3', N'3289f7c5-cbc1-d244-a347-519a69bfeb76', N'f00c57e5-b909-4ce4-b252-2fb5db12f181', CAST(2600000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'c7f7ab8e-6f2b-174b-a9bb-70a3c1ab4fc4', N'cbf8ab37-e9bb-8b47-ae14-bfe98eb6a711', N'b8cfdf48-7f88-40ba-a227-cc72e40a0294', CAST(765000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'a654b9c8-983d-974d-9d9f-739505e2fda7', N'7e456810-1c13-a342-a811-2bc7c95c1a4d', N'952d1932-0bb0-4259-bad8-5e373e0bc427', CAST(350000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'80d60cf1-7918-aa4d-83e2-74fa2a2f6cee', N'3923e251-e5b0-ac4a-a017-7b0379b9f242', N'74e890cd-8e00-4094-bcda-9eedc589be4a', CAST(1170000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'c523de46-0488-8144-850a-7f26cc8ebd0a', N'51e08f27-5698-704a-b703-a6dc5d16a3a8', N'cd909688-7796-4afd-9943-a1410acd9999', CAST(525000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'21672013-7f85-6d46-829d-8d8b1540cc7a', N'0480ba2e-fb54-3c43-8d0c-035be4710c56', N'f00c57e5-b909-4ce4-b252-2fb5db12f181', CAST(6200000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'e42902b2-1eb2-6040-95b1-8ec0738f7fe4', N'0934530d-5de9-9d4e-be5a-347ac95a971f', N'952d1932-0bb0-4259-bad8-5e373e0bc427', CAST(3100000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'828ac613-f3e6-2b42-be2e-90f24c95480d', N'9a19ff2b-786c-a142-a356-403777815763', N'f00c57e5-b909-4ce4-b252-2fb5db12f181', CAST(1800000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'a31322c8-9734-c04e-91ff-9a209d65ca2f', N'5032713b-839d-c746-914e-5c29004fc3c7', N'f00c57e5-b909-4ce4-b252-2fb5db12f181', CAST(700000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'7411ef60-beaf-b949-9342-9b1f546bcfae', N'2e5e0e52-896b-304b-af47-c00efb474bb4', N'b8cfdf48-7f88-40ba-a227-cc72e40a0294', CAST(595000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'cee97ce5-6565-f647-929b-a32c26b0351b', N'1a254d4f-632c-c045-a84b-bbdb94e75eae', N'b8cfdf48-7f88-40ba-a227-cc72e40a0294', CAST(2465000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'fecd43da-6169-ba4a-911a-a4a9d928f9ce', N'fc4c744b-aede-b645-b118-7ea9140b8249', N'cd909688-7796-4afd-9943-a1410acd9999', CAST(3409500 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'3eeef217-4179-cc42-84de-a8dce4f6c6bf', N'a890b541-d210-7a42-bfa9-bb9ab4a73f60', N'b8cfdf48-7f88-40ba-a227-cc72e40a0294', CAST(595000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'43da0929-55cb-cf4a-9fbf-b85b496ab77b', N'e4cb0d64-a60f-134d-a0c8-cbc0c047f23f', N'b8cfdf48-7f88-40ba-a227-cc72e40a0294', CAST(4420000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'47ddbc16-1b17-2b42-b6f8-c3d070fed086', N'b55241cb-d126-8741-b3af-35b6f12e3cf1', N'952d1932-0bb0-4259-bad8-5e373e0bc427', CAST(350000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'dbb117f5-0940-b342-a13e-c56f22375baf', N'265c44e3-380b-c340-a940-4719e1b1ba86', N'f00c57e5-b909-4ce4-b252-2fb5db12f181', CAST(700000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'1d0afb67-449f-9149-8ab3-c6ef99a64ff7', N'390ecab5-666a-8c49-8b8c-3c894b85ea05', N'952d1932-0bb0-4259-bad8-5e373e0bc427', CAST(4000000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'4e9542c7-4de9-4f47-a28e-c8ac6e88a01b', N'47d0e3c9-7e14-b446-af5d-747f9fc3d1c8', N'74e890cd-8e00-4094-bcda-9eedc589be4a', CAST(455000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'c01ea968-1ca1-0445-9bed-c94cf1533d07', N'b5598739-ead0-9943-a729-2efb96fbc8c6', N'952d1932-0bb0-4259-bad8-5e373e0bc427', CAST(1500000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'e176eae8-65e9-a445-9934-ca335abb0690', N'a0e7fe26-c8e0-b348-ac67-6440f74b2b08', N'74e890cd-8e00-4094-bcda-9eedc589be4a', CAST(455000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'60006694-63c2-b047-bac0-d49e5daa97dc', N'7dd94f65-b809-924e-a9ca-3d24ff849c5a', N'f00c57e5-b909-4ce4-b252-2fb5db12f181', CAST(8000000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'98e27c80-c795-3940-b26f-d4ea71f841f4', N'909849bf-6822-8242-b385-5bdc8953d684', N'f00c57e5-b909-4ce4-b252-2fb5db12f181', CAST(736000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'918752ea-bcf4-714a-be9b-d874583377d2', N'6942e418-c92f-d945-85ab-74a6a381a95d', N'74e890cd-8e00-4094-bcda-9eedc589be4a', CAST(325000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'32b31d3d-54de-a94e-b410-dd400a72b0f4', N'6de72fc4-57ac-4e47-b37a-a5d31d05c5e9', N'cd909688-7796-4afd-9943-a1410acd9999', CAST(450000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'91284c0e-d45a-6b49-b443-e21fa3cc5586', N'd2af8592-0420-854f-acdc-db812b0f4d8d', N'74e890cd-8e00-4094-bcda-9eedc589be4a', CAST(1235000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'e9ffd15e-04d9-af4e-98cf-e2260b00e0ff', N'597a9673-6929-d342-96dc-0a4c47e3cc7b', N'952d1932-0bb0-4259-bad8-5e373e0bc427', CAST(385000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'96f7b8ee-df07-6149-8edb-ed5fd3f154a8', N'91a7f37b-8538-e24f-8299-a06bd6640095', N'cd909688-7796-4afd-9943-a1410acd9999', CAST(675000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'6f5fc8f9-2405-bc42-bf02-ef951555e6bb', N'fcd2baf8-80ab-6e4b-92b8-d24377ead408', N'cd909688-7796-4afd-9943-a1410acd9999', CAST(375000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'f732e0b1-e20a-d243-90ec-f2dc5edb3980', N'3f71490b-511f-7b44-84c9-3fe4413b3188', N'f00c57e5-b909-4ce4-b252-2fb5db12f181', CAST(700000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'6a2e563e-0bff-c442-8675-f4113a880203', N'c00d80a0-1adb-7845-9f3b-9ac42c2e2d1c', N'cd909688-7796-4afd-9943-a1410acd9999', CAST(2250000 AS Decimal(18, 0)), 1)
INSERT [dbo].[chuong_trinh_giam_gia_chi_tiet_san_pham] ([id], [id_giay], [id_chuong_trinh_giam_gia], [so_tien_da_giam], [trangthai]) VALUES (N'7364c49f-828e-1942-8bcb-ff42da759873', N'e5e235cd-17e0-8f41-9957-0e4e116df55a', N'952d1932-0bb0-4259-bad8-5e373e0bc427', CAST(2600000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[dia_chi] ([id], [ma], [id_khach_hang], [ten_dia_chi], [ten_nguoi_nhan], [sdt_nguoi_nhan], [xa], [huyen], [thanh_pho], [trangthai]) VALUES (N'fd4a021b-4231-419c-9ade-3f4fb6e86de1', N'DC05', N'019427f5-1e52-402f-b5dc-ec17ad5f139a', N'Xóm 1 Triều Đông', N'Nguyễn Thành Danh', N'0385090080', N'Xã Tân Minh', N'Huyện Thường Tín', N'Thành phố Hà Nội', 0)
INSERT [dbo].[dia_chi] ([id], [ma], [id_khach_hang], [ten_dia_chi], [ten_nguoi_nhan], [sdt_nguoi_nhan], [xa], [huyen], [thanh_pho], [trangthai]) VALUES (N'8b5ac57c-583e-4d62-88b4-558ef2fe3181', N'DC01', N'bb772ace-91ff-4cbe-83de-ea5e80fa3843', N'Xóm 2 Triều Đông', N'Nguyễn Hữu Nam', N'0385090080', N'Xã Tân Minh', N'Huyện Thường Tín', N'Thành phố Hà Nội', 1)
INSERT [dbo].[dia_chi] ([id], [ma], [id_khach_hang], [ten_dia_chi], [ten_nguoi_nhan], [sdt_nguoi_nhan], [xa], [huyen], [thanh_pho], [trangthai]) VALUES (N'1b1d2309-170b-46ca-b4a8-60c8e78d7962', N'DC02', N'bb772ace-91ff-4cbe-83de-ea5e80fa3843', N'Xóm 3 Triều Đông', N'Nguyễn An Nhiên', N'0385090080', N'Xã Tân Minh', N'Huyện Thường Tín', N'Thành phố Hà Nội', 0)
INSERT [dbo].[dia_chi] ([id], [ma], [id_khach_hang], [ten_dia_chi], [ten_nguoi_nhan], [sdt_nguoi_nhan], [xa], [huyen], [thanh_pho], [trangthai]) VALUES (N'db77537f-1e64-4850-b464-d570cf525128', N'DC04', N'019427f5-1e52-402f-b5dc-ec17ad5f139a', N'Xóm 4 Triều Đông', N'Nguyễn Phan Can', N'0385090080', N'Xã Tân Minh', N'Huyện Thường Tín', N'Thành phố Hà Nội', 1)
INSERT [dbo].[dia_chi] ([id], [ma], [id_khach_hang], [ten_dia_chi], [ten_nguoi_nhan], [sdt_nguoi_nhan], [xa], [huyen], [thanh_pho], [trangthai]) VALUES (N'ffa06f58-24f6-47e5-81bf-e30725a85957', N'DC03', N'bb772ace-91ff-4cbe-83de-ea5e80fa3843', N'Xóm 5 Triều Đông', N'Nguyễn Thành Đạt', N'0385090080', N'Xã Tân Minh', N'Huyện Thường Tín', N'Thành phố Hà Nội', 0)
INSERT [dbo].[dia_chi] ([id], [ma], [id_khach_hang], [ten_dia_chi], [ten_nguoi_nhan], [sdt_nguoi_nhan], [xa], [huyen], [thanh_pho], [trangthai]) VALUES (N'faa06f58-24f6-47e5-81bf-e30725a85957', N'DC03', N'22B427F5-1E52-402F-B5DC-EC17AD5F139A', N'Xóm 6 Triều Đông', N'Nguyễn Thị Linh', N'0385090080', N'Xã Tân Minh', N'Huyện Thường Tín', N'Thành phố Hà Nội', 0)
INSERT [dbo].[dia_chi] ([id], [ma], [id_khach_hang], [ten_dia_chi], [ten_nguoi_nhan], [sdt_nguoi_nhan], [xa], [huyen], [thanh_pho], [trangthai]) VALUES (N'fba06f58-24f6-47e5-81bf-e30725a85957', N'DC03', N'22B427F5-1E52-402F-B5DC-EC17AD5F139A', N'Xóm 7 Triều Đông', N'Nguyễn Văn Anh', N'0385090080', N'Xã Tân Minh', N'Huyện Thường Tín', N'Thành phố Hà Nội', 1)
GO
INSERT [dbo].[gio_hang] ([id], [ma], [id_khach_hang], [ngay_tao], [ngay_cap_nhap], [ghi_chu], [trangthai]) VALUES (N'd40a87d4-f3ff-4f92-8394-f793f75b1639', N'GH01', N'bb772ace-91ff-4cbe-83de-ea5e80fa3843', CAST(N'2023-11-06' AS Date), CAST(N'2023-11-06' AS Date), N'1', 1)
INSERT [dbo].[gio_hang] ([id], [ma], [id_khach_hang], [ngay_tao], [ngay_cap_nhap], [ghi_chu], [trangthai]) VALUES (N'e40a87d4-f3ff-4f92-8394-f793f75b1639', N'GH02', N'019427F5-1E52-402F-B5DC-EC17AD5F139A', CAST(N'2023-11-06' AS Date), CAST(N'2023-11-06' AS Date), N'1', 1)
INSERT [dbo].[gio_hang] ([id], [ma], [id_khach_hang], [ngay_tao], [ngay_cap_nhap], [ghi_chu], [trangthai]) VALUES (N'f40a87d4-f3ff-4f92-8394-f793f75b1639', N'GH03', N'22B427F5-1E52-402F-B5DC-EC17AD5F139A', CAST(N'2023-11-06' AS Date), CAST(N'2023-11-06' AS Date), N'1', 1)
GO
INSERT [dbo].[vi_diem] ([id],[id_khach_hang], [tong_diem], [so_diem_da_dung], [so_diem_da_cong], [trangthai]) VALUES (N'a40a87d4-f3ff-4f92-8394-f793f75b1639',N'bb772ace-91ff-4cbe-83de-ea5e80fa3843', 0, 0, 0, 1)
INSERT [dbo].[vi_diem] ([id],[id_khach_hang], [tong_diem], [so_diem_da_dung], [so_diem_da_cong], [trangthai]) VALUES (N'b40a87d4-f3ff-4f92-8394-f793f75b1639',N'019427F5-1E52-402F-B5DC-EC17AD5F139A', 0, 0, 0, 1)
INSERT [dbo].[vi_diem] ([id],[id_khach_hang], [tong_diem], [so_diem_da_dung], [so_diem_da_cong], [trangthai]) VALUES (N'c40a87d4-f3ff-4f92-8394-f793f75b1639', N'22B427F5-1E52-402F-B5DC-EC17AD5F139A', 0, 0, 0, 1)
GO
INSERT [dbo].[gio_hang_san_pham_yeu_thich] ([id], [id_khach_hang], [ngay_tao], [trangthai]) VALUES (N'72ae816c-5fe7-6b42-8971-4fd24759445c', N'22b427f5-1e52-402f-b5dc-ec17ad5f139a', CAST(N'2023-11-10' AS Date), 1)
GO
INSERT [dbo].[kich_co] ([id], [ma], [ten], [trangthai]) VALUES (N'09fd4a50-b9f7-4292-8a89-0db59c0936a3', N'KC4', N'35', 1)
INSERT [dbo].[kich_co] ([id], [ma], [ten], [trangthai]) VALUES (N'0627d97b-f97d-40e1-9fa6-15e914d5c594', N'KC3', N'30', 1)
INSERT [dbo].[kich_co] ([id], [ma], [ten], [trangthai]) VALUES (N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', N'KC7', N'38', 1)
INSERT [dbo].[kich_co] ([id], [ma], [ten], [trangthai]) VALUES (N'c4c4b808-00c8-48fb-b043-2cc3240a71b3', N'KC6', N'37', 1)
INSERT [dbo].[kich_co] ([id], [ma], [ten], [trangthai]) VALUES (N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', N'KC10', N'41', 1)
INSERT [dbo].[kich_co] ([id], [ma], [ten], [trangthai]) VALUES (N'5da656b5-827e-450d-b5bd-3e02cc1db251', N'KC5', N'36', 1)
INSERT [dbo].[kich_co] ([id], [ma], [ten], [trangthai]) VALUES (N'b32d3113-2dc3-4697-a410-4a4fd4fe59a1', N'KC1', N'20', 1)
INSERT [dbo].[kich_co] ([id], [ma], [ten], [trangthai]) VALUES (N'e296b3a2-3a8b-4321-b9b4-4b668f661164', N'KC2', N'25', 1)
INSERT [dbo].[kich_co] ([id], [ma], [ten], [trangthai]) VALUES (N'6a438602-1aad-4431-be21-62bafbc2d838', N'KC11', N'42', 1)
INSERT [dbo].[kich_co] ([id], [ma], [ten], [trangthai]) VALUES (N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', N'KC8', N'39', 1)
INSERT [dbo].[kich_co] ([id], [ma], [ten], [trangthai]) VALUES (N'6e878efe-1924-424c-b578-844dcc22a1b8', N'KC9', N'40', 1)
INSERT [dbo].[kich_co] ([id], [ma], [ten], [trangthai]) VALUES (N'c76f964d-6c71-4774-93d0-9143964315b4', N'KC12', N'43', 1)
INSERT [dbo].[kich_co] ([id], [ma], [ten], [trangthai]) VALUES (N'7c55c58f-70c6-4fd8-9cd2-e2bb49794d53', N'KC13', N'44', 1)
GO
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'8adf9bdf-d25b-bb4f-9875-0408037556e1', N'6de72fc4-57ac-4e47-b37a-a5d31d05c5e9', N'7c55c58f-70c6-4fd8-9cd2-e2bb49794d53', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'572d1559-032a-cf4b-8ea9-0524f28f8d1f', N'6de72fc4-57ac-4e47-b37a-a5d31d05c5e9', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'5d182997-ed27-014a-aa5b-0526595ecbaf', N'b5598739-ead0-9943-a729-2efb96fbc8c6', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'e4f458cf-98d0-f847-bb59-07ae707f25bd', N'597a9673-6929-d342-96dc-0a4c47e3cc7b', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 20, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'0f034cf9-8d35-1846-bbfb-087be7ab5dc0', N'fc4c744b-aede-b645-b118-7ea9140b8249', N'c4c4b808-00c8-48fb-b043-2cc3240a71b3', 20, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'6eb1a245-d42b-2145-8eab-08d7d1970df9', N'a0e7fe26-c8e0-b348-ac67-6440f74b2b08', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'1d520fa7-14fb-a441-8912-09e7b448aaec', N'8720d28f-5e84-7047-a0cd-886872b4dbc1', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'21513c4e-1746-b94b-8280-0ebd017648f8', N'a890b541-d210-7a42-bfa9-bb9ab4a73f60', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'a536091f-10a3-6c42-9cdb-0f20554ba599', N'3289f7c5-cbc1-d244-a347-519a69bfeb76', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'37d29b8a-ad21-e34a-9817-0ff8f515c14a', N'fcd2baf8-80ab-6e4b-92b8-d24377ead408', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'b4ce1944-1e2f-cc4b-93a1-11294ac99109', N'1a254d4f-632c-c045-a84b-bbdb94e75eae', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'bda8a961-a8f6-e54a-a4eb-112dd1a22282', N'0480ba2e-fb54-3c43-8d0c-035be4710c56', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'fccd981f-ceac-6349-9e8e-121448e867b1', N'2e5e0e52-896b-304b-af47-c00efb474bb4', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'610d84d5-3371-9848-afd8-133f2fe53313', N'68767e95-effc-ee48-8d3e-d7693b44324f', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'd58224a7-66a1-3f41-a7f5-1668859f4c5b', N'9e436193-c9ea-9941-85ca-96ada4db4bac', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'281db996-bde4-fb41-86ea-18150da6b9d1', N'90545bde-c344-5949-aba2-c14c7d673bd2', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'c7bb1b91-d0fa-5a49-94de-1cb761fd6009', N'903fac14-7083-364c-8c2d-144a4f41712b', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'6be66dbc-3821-c545-8681-1e846fdd2388', N'c6eaf65e-9efd-404b-b5fb-fb5b59200f7b', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'd1a5f2b1-0187-af44-a5e2-20522b04d3a8', N'9a19ff2b-786c-a142-a356-403777815763', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'c83bf587-5499-8b49-9c26-219862c52458', N'cb626472-c91c-ec4a-9369-7b0942e94edd', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 20, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'dfc08e8a-41c7-f54f-ad6d-21eae092b6e4', N'cb626472-c91c-ec4a-9369-7b0942e94edd', N'09fd4a50-b9f7-4292-8a89-0db59c0936a3', 20, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'52dd2029-d585-624a-9a36-21fe56a880c5', N'8d1d8b62-1395-5d43-b474-ab88c5bd7a39', N'c4c4b808-00c8-48fb-b043-2cc3240a71b3', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'0a12853e-edc6-fb4f-bbc4-225061edfa77', N'90545bde-c344-5949-aba2-c14c7d673bd2', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'85c526bb-61d2-db4f-ad77-235cea165a83', N'6049b327-e697-d74d-a3ab-bb1f7543b139', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 20, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'a43967e5-53df-0c49-9559-23e57a9b6d58', N'39774a40-f33b-8f41-b22e-bc50ca964779', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'6a5e14ca-4f6d-d140-bbc4-2557b394e55f', N'91a7f37b-8538-e24f-8299-a06bd6640095', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'97aaa70d-b764-c741-a730-26b09fdc5ec6', N'e6d07e36-852b-0448-adaf-da91ad40a604', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'c8cfda15-cbf7-5a44-b584-26ccac0bac39', N'68767e95-effc-ee48-8d3e-d7693b44324f', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'c802a6bd-5244-4046-af52-28ec641bd146', N'6de72fc4-57ac-4e47-b37a-a5d31d05c5e9', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'75a0fc24-db46-6e4d-84a4-291506ba0d4b', N'3923e251-e5b0-ac4a-a017-7b0379b9f242', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'957068eb-3899-6149-aa38-2928ba20df37', N'046dc72b-3057-ff4d-bbf3-a865de58b6a8', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'99bc0c9c-e543-914c-acf8-2989c1aea2c9', N'8d1d8b62-1395-5d43-b474-ab88c5bd7a39', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'5067bf17-26ba-9945-ab29-29e85536d39f', N'a0e7fe26-c8e0-b348-ac67-6440f74b2b08', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'17ee8e17-1e91-8f4b-b880-2a5019521c34', N'4a5281da-8e3c-f945-97ea-22ec86a3c01a', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'f2a57a97-8a5a-6943-a9d2-2a67bc0ecbef', N'5032713b-839d-c746-914e-5c29004fc3c7', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'442090ce-0889-8f47-89b4-2b31d34abe78', N'feaf547d-1b1d-5141-b413-ced86da78e9f', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'fbc2fe1f-6a49-4943-8f62-2c387e38631e', N'd94e2cc2-dbd9-4e4e-aa1a-488ece55e70d', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'a6de5ec7-0cb4-b34b-a58b-2d2304dbc5a5', N'7e456810-1c13-a342-a811-2bc7c95c1a4d', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'09d85736-afd6-9c47-be1d-2d96d3fd8cfa', N'cbf8ab37-e9bb-8b47-ae14-bfe98eb6a711', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'690458f6-d639-ad4a-873d-2db698850b0d', N'7e456810-1c13-a342-a811-2bc7c95c1a4d', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'e96cb374-884e-ef4c-9f92-2dfa2eca62c4', N'feaf547d-1b1d-5141-b413-ced86da78e9f', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'af29b897-08af-aa4d-a399-2f244b81733f', N'fc4c744b-aede-b645-b118-7ea9140b8249', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 20, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'2ef85884-582e-e548-a78f-2f86a79a0974', N'0480ba2e-fb54-3c43-8d0c-035be4710c56', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'e0b12ecc-868e-2144-89ff-30812b5bf869', N'390ecab5-666a-8c49-8b8c-3c894b85ea05', N'c4c4b808-00c8-48fb-b043-2cc3240a71b3', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'bfa23274-03d7-4741-9329-33505f7845aa', N'903fac14-7083-364c-8c2d-144a4f41712b', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'0a6aab64-01c7-6047-a5a3-35b80f1adf79', N'c9bc38bf-69d3-4f4f-9cd8-daead9d2270b', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'ce841f24-cc08-e64e-b124-36dca697f22c', N'4a5281da-8e3c-f945-97ea-22ec86a3c01a', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'4ac0fbf4-e1ff-3e43-a8ca-370c996fb6b0', N'1a254d4f-632c-c045-a84b-bbdb94e75eae', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'6db4b4c0-7f5b-814c-b3a2-38c50dc237c2', N'8d1d8b62-1395-5d43-b474-ab88c5bd7a39', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'aebd6471-cf7b-4c4a-9199-38fc4f870551', N'7e456810-1c13-a342-a811-2bc7c95c1a4d', N'c76f964d-6c71-4774-93d0-9143964315b4', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'838cab8c-367b-f94f-aeff-3922ed344cd5', N'51e08f27-5698-704a-b703-a6dc5d16a3a8', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'cec03ff3-6fa6-1945-b60c-398c22dc829b', N'47d0e3c9-7e14-b446-af5d-747f9fc3d1c8', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'f6267c16-c844-554f-8a29-399a176a6c81', N'b55241cb-d126-8741-b3af-35b6f12e3cf1', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'ca1b7f15-e9ed-0949-bcde-3a971d7e9d2c', N'47d0e3c9-7e14-b446-af5d-747f9fc3d1c8', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'7f33b9b3-fc9a-1245-a86a-3be043adce87', N'c00d80a0-1adb-7845-9f3b-9ac42c2e2d1c', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'df231e0d-f81d-024f-86b5-3c7e689c294e', N'597a9673-6929-d342-96dc-0a4c47e3cc7b', N'6a438602-1aad-4431-be21-62bafbc2d838', 20, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'5ea6b32e-3757-1041-8708-3c89d8120041', N'12300a57-8b03-2846-927e-6cfc2ae3b52e', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'376c10eb-595b-4848-8166-3d4777318bdc', N'6942e418-c92f-d945-85ab-74a6a381a95d', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'2f17619c-942d-9941-a5af-3effc1f74cdf', N'046dc72b-3057-ff4d-bbf3-a865de58b6a8', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'46059950-50ec-3348-ab0c-408ca548ca6b', N'3af55b31-a9a6-e548-a8f7-6e598f233d98', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'43e9dac0-243e-7347-869e-414a5f47095e', N'b5598739-ead0-9943-a729-2efb96fbc8c6', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'43a58260-05c7-4f43-a8b8-41d713216258', N'97444936-0840-914b-9900-4d1bfe39ab40', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'87546e5c-353b-2649-9191-43190ee6552f', N'd2af8592-0420-854f-acdc-db812b0f4d8d', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'f3c1ffb5-3129-764e-ae63-44b7525c256e', N'b55241cb-d126-8741-b3af-35b6f12e3cf1', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'4730e579-81a6-9e47-88c6-455a3efbd06b', N'47d0e3c9-7e14-b446-af5d-747f9fc3d1c8', N'c76f964d-6c71-4774-93d0-9143964315b4', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'88c34317-3049-8d4e-801c-48870b23f314', N'597a9673-6929-d342-96dc-0a4c47e3cc7b', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 20, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'8ed4fc3a-1ac5-6847-84db-4d822a04bb31', N'903fac14-7083-364c-8c2d-144a4f41712b', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'2f2619df-1e81-a344-9919-4f1a75d583d5', N'e4cb0d64-a60f-134d-a0c8-cbc0c047f23f', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'54e79455-f109-5642-b1b6-4f4dc63b7922', N'903fac14-7083-364c-8c2d-144a4f41712b', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'd03cf241-be89-0f46-9010-5093d1d32b46', N'c9bc38bf-69d3-4f4f-9cd8-daead9d2270b', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'f53ce6a2-8436-7443-8a68-52944b978f42', N'0934530d-5de9-9d4e-be5a-347ac95a971f', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'f2f445e5-2f0f-1947-83f7-52aa9eb96c20', N'046dc72b-3057-ff4d-bbf3-a865de58b6a8', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'87e26cb6-04be-4e44-94fc-53415bfddded', N'c840d102-65e6-0847-b251-c854e12434bf', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'2e8317a7-6346-8343-b8e8-579bae3c7180', N'39774a40-f33b-8f41-b22e-bc50ca964779', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'6dc80e7b-ca30-754f-8d69-57a8bc9d47cc', N'74e74ba3-6285-c247-b588-62e0cd12e1e1', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'db3d0f9f-1ee8-024f-a39f-583177bd6929', N'c6eaf65e-9efd-404b-b5fb-fb5b59200f7b', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'417247e4-38dd-c04a-bec1-5881ddc2e163', N'cbf8ab37-e9bb-8b47-ae14-bfe98eb6a711', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'e797685c-5add-0841-8654-58d4ff093aea', N'e4cb0d64-a60f-134d-a0c8-cbc0c047f23f', N'c76f964d-6c71-4774-93d0-9143964315b4', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'f7e8c8af-0bdd-2c4b-8498-595ea0ad2db6', N'3289f7c5-cbc1-d244-a347-519a69bfeb76', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'f77f1b7a-79fc-a542-bedd-5b2dcbe19b5b', N'd2af8592-0420-854f-acdc-db812b0f4d8d', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'070ab799-46e5-244e-b666-5b7f574e027b', N'e5e235cd-17e0-8f41-9957-0e4e116df55a', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'345a723a-df84-6f47-9b3b-5cf179f38006', N'b55241cb-d126-8741-b3af-35b6f12e3cf1', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'05ab2731-c817-994a-a4a9-5e6a9bf72e66', N'7dd94f65-b809-924e-a9ca-3d24ff849c5a', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'df423e64-c616-4a47-bb63-5f6170ed5ae7', N'12300a57-8b03-2846-927e-6cfc2ae3b52e', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'848c331c-d1e7-7845-92c7-6561714d47d2', N'fcd2baf8-80ab-6e4b-92b8-d24377ead408', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'9a8b4b09-610e-414e-99c8-65e339ad402d', N'390ecab5-666a-8c49-8b8c-3c894b85ea05', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'2b895ce2-e564-0e4a-a250-662c8cdc56e5', N'8720d28f-5e84-7047-a0cd-886872b4dbc1', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'eb4bcad9-b8da-f64c-ba23-6638007a35e3', N'12300a57-8b03-2846-927e-6cfc2ae3b52e', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'4e8be9fe-8e40-0b4c-8d11-66e3141fcc1c', N'e5e235cd-17e0-8f41-9957-0e4e116df55a', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'7104889f-f3a2-a548-a2d8-68b0a3233c98', N'cbf8ab37-e9bb-8b47-ae14-bfe98eb6a711', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'dba40024-cdfd-234a-ae92-68dea5a4d891', N'cbf8ab37-e9bb-8b47-ae14-bfe98eb6a711', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'9e6739d1-8d21-1b4c-99be-6a8a7954b948', N'9e436193-c9ea-9941-85ca-96ada4db4bac', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'883132cf-3c66-1349-899a-6aeebde8b3b8', N'0480ba2e-fb54-3c43-8d0c-035be4710c56', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'a317298f-efb4-8941-9739-6cb35d1ae525', N'edca9d8a-9b70-4d49-9dd0-d145eda8805c', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'b4de459a-56f2-704d-bb00-6d1fede603c8', N'91a7f37b-8538-e24f-8299-a06bd6640095', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'3d31017f-b362-8d47-8a9c-6dc61a6ed053', N'7603d98a-9e2a-954e-aa88-bb7c598fca5e', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'6a66c30b-22bf-b14b-9b51-6ec944c52dbb', N'5032713b-839d-c746-914e-5c29004fc3c7', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'44ebc564-16cf-7f47-8135-6ee88f8dec50', N'6de72fc4-57ac-4e47-b37a-a5d31d05c5e9', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'56214e58-acb9-e440-abfb-6f96eb08c625', N'4a5281da-8e3c-f945-97ea-22ec86a3c01a', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'c4e7f5f2-6878-b544-93b9-72580cabb4f7', N'9a19ff2b-786c-a142-a356-403777815763', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
GO
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'7712f280-f8da-b248-827d-73d33706cc56', N'2e5e0e52-896b-304b-af47-c00efb474bb4', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'1b8588f6-0968-b44f-a436-73ef09da4395', N'8720d28f-5e84-7047-a0cd-886872b4dbc1', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'd087f7a0-f8d2-924b-8ba4-74b687104ba7', N'e4cb0d64-a60f-134d-a0c8-cbc0c047f23f', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'e0da3f52-8d34-1a41-b943-74ea3fa5c9d6', N'4a5281da-8e3c-f945-97ea-22ec86a3c01a', N'c76f964d-6c71-4774-93d0-9143964315b4', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'2d3fca44-01ad-044f-bbc1-74ee2529ca58', N'c9bc38bf-69d3-4f4f-9cd8-daead9d2270b', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'9cb04f42-2719-a64e-a902-759dad66459c', N'3923e251-e5b0-ac4a-a017-7b0379b9f242', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'3144f5f3-9d37-0c43-8bd9-7897d58d3c38', N'6049b327-e697-d74d-a3ab-bb1f7543b139', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 20, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'51c8a5c7-d5d2-0e43-9400-78bcfb478e6b', N'39774a40-f33b-8f41-b22e-bc50ca964779', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'f60f86c2-f3bb-c344-92db-7982d0d028ed', N'9e436193-c9ea-9941-85ca-96ada4db4bac', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'100bb884-dd87-604b-a7c9-7b5cc3917ff9', N'7603d98a-9e2a-954e-aa88-bb7c598fca5e', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'f093a40f-2934-3743-8ea8-7f835d615816', N'51e08f27-5698-704a-b703-a6dc5d16a3a8', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'7369cd91-3748-ae47-8f89-7f8f50b238ee', N'265c44e3-380b-c340-a940-4719e1b1ba86', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'2970515e-a753-5946-9953-8127920d3698', N'5032713b-839d-c746-914e-5c29004fc3c7', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'e2b86cc9-e15c-3645-b858-83c59e937bea', N'39774a40-f33b-8f41-b22e-bc50ca964779', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'61d2943f-9bba-9841-9fed-857974b67816', N'edca9d8a-9b70-4d49-9dd0-d145eda8805c', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'b7fcc3ea-be3b-b042-a06f-88c20609684d', N'7603d98a-9e2a-954e-aa88-bb7c598fca5e', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'b74078e8-2cfc-9144-88fe-89b3f9799893', N'a890b541-d210-7a42-bfa9-bb9ab4a73f60', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'8aa58aee-9c9d-9c46-85e7-8b731808a926', N'74e74ba3-6285-c247-b588-62e0cd12e1e1', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'9bbd41a2-97b2-9441-9877-8c6dad1c35c9', N'7e456810-1c13-a342-a811-2bc7c95c1a4d', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'b01b8ba8-4503-cb4c-9ccb-8e3a1a2a6d31', N'b5598739-ead0-9943-a729-2efb96fbc8c6', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'f605c7cd-6d95-8649-8239-8e543d0cee84', N'68767e95-effc-ee48-8d3e-d7693b44324f', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'141c0a98-ac1b-ea47-b396-8f1fc93a1fc6', N'e5e235cd-17e0-8f41-9957-0e4e116df55a', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'7a0362a5-9ed8-594f-bada-8f6f755e3be4', N'9a19ff2b-786c-a142-a356-403777815763', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'a486f683-22c2-8742-ab98-8fdc542e687c', N'8d1d8b62-1395-5d43-b474-ab88c5bd7a39', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'33951587-4f50-4f44-9a62-9016c8cc5236', N'194056c9-4dce-ad4c-9619-e6b9762d05a9', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'65c8dab7-9efc-104f-aa28-903a92d354f5', N'6942e418-c92f-d945-85ab-74a6a381a95d', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'942b84d9-8501-9d43-a158-9164488469ca', N'12300a57-8b03-2846-927e-6cfc2ae3b52e', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'5803a2c5-0ac8-0b49-9f66-91c3e09996c7', N'c6eaf65e-9efd-404b-b5fb-fb5b59200f7b', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'6d3578ef-8afe-0141-986d-9293fd521ff1', N'194056c9-4dce-ad4c-9619-e6b9762d05a9', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'ea9316c5-74d3-f845-a0eb-95cdd67aa0ce', N'7dd94f65-b809-924e-a9ca-3d24ff849c5a', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'66d0b20e-26bb-1142-89ce-975dc751ee40', N'7dd94f65-b809-924e-a9ca-3d24ff849c5a', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'cf1f13ad-8daf-9943-b0ad-9854912ff9d2', N'1fde7a06-9aed-ee40-a7ae-605296f1ce68', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'b66e390d-55ea-6543-84e5-9a23e58a0b6f', N'c6eaf65e-9efd-404b-b5fb-fb5b59200f7b', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'0e297e9c-35ca-4342-854a-9ab5b249e4c3', N'a890b541-d210-7a42-bfa9-bb9ab4a73f60', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'7d87dd1c-9f17-414a-a308-9b9182e12aa9', N'47d0e3c9-7e14-b446-af5d-747f9fc3d1c8', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'f99b4e6d-0f29-e346-a8e6-9c0f8b1c5f3d', N'a0e7fe26-c8e0-b348-ac67-6440f74b2b08', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'dd54aa8e-3f31-3641-b7b5-9ebf1059dbdd', N'265c44e3-380b-c340-a940-4719e1b1ba86', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'2c3903e3-dfa1-a24e-b281-a0272612231b', N'cbf8ab37-e9bb-8b47-ae14-bfe98eb6a711', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'a3c247c9-4365-f74d-bbe4-a28ccebcdd1e', N'f2e2785b-d430-aa4a-8439-187f202a73e6', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'ae07be15-6f5c-3a46-9b95-a2b0a603fe1b', N'c840d102-65e6-0847-b251-c854e12434bf', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'db9eb6f7-bd6f-ed4a-b248-a3a1fde29f28', N'a0e7fe26-c8e0-b348-ac67-6440f74b2b08', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'46fd89bb-b020-1241-aaa5-a5d38283089e', N'd94e2cc2-dbd9-4e4e-aa1a-488ece55e70d', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'968a2b01-3c1e-5547-b68b-a7428630adce', N'3af55b31-a9a6-e548-a8f7-6e598f233d98', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'4c10454b-dc2c-1b45-b739-a773e7234548', N'51e08f27-5698-704a-b703-a6dc5d16a3a8', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'17563b4e-1205-1346-b368-a7e4d5ca8600', N'7dd94f65-b809-924e-a9ca-3d24ff849c5a', N'c4c4b808-00c8-48fb-b043-2cc3240a71b3', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'2151fa86-8267-9642-8d1e-a830e9a7f146', N'3af55b31-a9a6-e548-a8f7-6e598f233d98', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'a988cb2c-05b5-6343-b84a-a91c62d0ce20', N'e6d07e36-852b-0448-adaf-da91ad40a604', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'a585270c-d26e-274b-8f4f-ad1b12b03231', N'194056c9-4dce-ad4c-9619-e6b9762d05a9', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'9d9ccb00-fb36-5b4e-9d08-ad1efd9061bd', N'e4cb0d64-a60f-134d-a0c8-cbc0c047f23f', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'4ffe0d29-8db0-994b-a8a5-ad62798263d2', N'12300a57-8b03-2846-927e-6cfc2ae3b52e', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'79320de3-2f04-a144-b4c0-ae20858673e5', N'7e456810-1c13-a342-a811-2bc7c95c1a4d', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'c9a7b02c-efba-4848-a146-b06d14c3d161', N'c840d102-65e6-0847-b251-c854e12434bf', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'f5fb2ff3-03be-2c42-ba57-b09af7cccec4', N'1fde7a06-9aed-ee40-a7ae-605296f1ce68', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'5880905f-489f-df41-a7b6-b09da34a1d36', N'8720d28f-5e84-7047-a0cd-886872b4dbc1', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'0d759325-abf7-3b47-8e22-b1da12371bd6', N'68767e95-effc-ee48-8d3e-d7693b44324f', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'c835d442-b6cd-df43-80c2-b31d4481d71f', N'fcd2baf8-80ab-6e4b-92b8-d24377ead408', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'0b6487a7-fd5b-4c4b-93c0-b3958f83095e', N'b55241cb-d126-8741-b3af-35b6f12e3cf1', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'cf666dd2-196e-e24d-95ee-b6063778e356', N'f2e2785b-d430-aa4a-8439-187f202a73e6', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'6ce10856-062b-0c4c-b84a-b621bce840ef', N'91a7f37b-8538-e24f-8299-a06bd6640095', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'2d3c22bb-4a63-7946-a0ef-b72de78ccea4', N'90545bde-c344-5949-aba2-c14c7d673bd2', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'c958491a-d00d-e74f-9bf2-b79732ad50e4', N'4a5281da-8e3c-f945-97ea-22ec86a3c01a', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'a5cba0b6-b5fa-9746-bb1f-b8aa880f9cdb', N'd94e2cc2-dbd9-4e4e-aa1a-488ece55e70d', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'e2ae71ed-f59b-d545-9f3a-bb3613e6a2e1', N'9a19ff2b-786c-a142-a356-403777815763', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'aa3fd224-00e6-784b-a4eb-bbd75c9c6bb9', N'6942e418-c92f-d945-85ab-74a6a381a95d', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'203451ac-0ed3-6541-a87f-bca2e3aadcb7', N'90545bde-c344-5949-aba2-c14c7d673bd2', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'9f0839c9-183a-9b4a-a4ab-be086e8eb0d7', N'b55241cb-d126-8741-b3af-35b6f12e3cf1', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'c2ea2d18-248c-9742-b575-bf104c459757', N'c9bc38bf-69d3-4f4f-9cd8-daead9d2270b', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'7ed7979e-bc9e-da4b-92ca-c0a351d1ddfd', N'd2af8592-0420-854f-acdc-db812b0f4d8d', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'cf7d7386-3f32-0644-818f-c2129ca7bc5e', N'97444936-0840-914b-9900-4d1bfe39ab40', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'70be7888-5899-d540-af4e-c3901098ec01', N'edca9d8a-9b70-4d49-9dd0-d145eda8805c', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'1cd12a9f-89e6-e443-a875-c3b964550795', N'f2e2785b-d430-aa4a-8439-187f202a73e6', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'9c38e0fe-e9ef-e340-a92d-c526f2896444', N'39774a40-f33b-8f41-b22e-bc50ca964779', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'13b81162-4ef4-364b-b61d-c54c4af7086f', N'97444936-0840-914b-9900-4d1bfe39ab40', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'df751732-33d2-a242-962e-c63ac0b79b7b', N'b5598739-ead0-9943-a729-2efb96fbc8c6', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'389545e6-ff5b-f44a-b7bd-c6a8a08ab956', N'c840d102-65e6-0847-b251-c854e12434bf', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'f7e4262b-d824-6246-b9d5-c9edee3c9461', N'e5e235cd-17e0-8f41-9957-0e4e116df55a', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'80521395-1285-9944-b882-cca86f248495', N'74e74ba3-6285-c247-b588-62e0cd12e1e1', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'af2b0ccf-e7a0-b54e-a36f-cd4f58fcf400', N'9e436193-c9ea-9941-85ca-96ada4db4bac', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'366fb13d-43d4-3643-afd4-cf929887fabf', N'74e74ba3-6285-c247-b588-62e0cd12e1e1', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'69d93d74-ccb5-7246-87c0-d06d383e37b4', N'a890b541-d210-7a42-bfa9-bb9ab4a73f60', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'88b7316c-2363-7e48-a8cf-d17b148694b8', N'feaf547d-1b1d-5141-b413-ced86da78e9f', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'f21e04f7-7e93-8e48-9d20-d1b6c8de52e0', N'1a254d4f-632c-c045-a84b-bbdb94e75eae', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'5eb8dace-839c-544e-80f8-d36756c96d2c', N'1fde7a06-9aed-ee40-a7ae-605296f1ce68', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'587f3295-5f85-b243-a457-d3c3f03e9a34', N'f2e2785b-d430-aa4a-8439-187f202a73e6', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'ba940f67-336d-274d-b86e-d3f2d63aaa49', N'2e5e0e52-896b-304b-af47-c00efb474bb4', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'18a7e55f-3d9c-824f-bfad-d47d61a244e1', N'74e74ba3-6285-c247-b588-62e0cd12e1e1', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'25dfb3d5-5408-2949-8f45-d4f891947807', N'3289f7c5-cbc1-d244-a347-519a69bfeb76', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'4f6181b1-8334-ec4a-9021-d51e6f4f632e', N'e5e235cd-17e0-8f41-9957-0e4e116df55a', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'4158b8bd-a2f9-fa49-a6cc-d59712ba0dad', N'3af55b31-a9a6-e548-a8f7-6e598f233d98', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'6212b1d6-f347-c146-bd7b-d616b9ded342', N'046dc72b-3057-ff4d-bbf3-a865de58b6a8', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'73fe8b4a-6527-544a-9fb8-d6cd2b604e90', N'97444936-0840-914b-9900-4d1bfe39ab40', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'82862e86-1ccb-c24d-a3bc-d90cae8af3a2', N'e4cb0d64-a60f-134d-a0c8-cbc0c047f23f', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'ad51cb25-eba7-7646-9f5d-dc0bcc1349b1', N'3923e251-e5b0-ac4a-a017-7b0379b9f242', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'a09ccfec-7e82-7944-b7c5-dd2eae9ae822', N'e6d07e36-852b-0448-adaf-da91ad40a604', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'7b1b043f-e6e7-3844-98a8-dd5b32c399b6', N'265c44e3-380b-c340-a940-4719e1b1ba86', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'8cec2584-4e3a-c549-8c9a-ddb9e9f31dcc', N'046dc72b-3057-ff4d-bbf3-a865de58b6a8', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'50c303a8-0f8b-7548-9934-de210be976e2', N'5032713b-839d-c746-914e-5c29004fc3c7', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'11c06a24-67af-a143-bba3-df0cffc55279', N'e6d07e36-852b-0448-adaf-da91ad40a604', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'7108e602-39ef-3c48-8723-e0d99047c98a', N'6942e418-c92f-d945-85ab-74a6a381a95d', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'01b358fd-2375-274d-8f3c-e29cda95e512', N'2e5e0e52-896b-304b-af47-c00efb474bb4', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
GO
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'473e78e3-dbff-3048-9097-e3bf187274a8', N'3289f7c5-cbc1-d244-a347-519a69bfeb76', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'78497481-c607-8948-8855-e64ced4bce83', N'7603d98a-9e2a-954e-aa88-bb7c598fca5e', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'fda35a92-4907-ff4d-99a4-e7e0bc6eea48', N'f2e2785b-d430-aa4a-8439-187f202a73e6', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'53dc3125-51d8-1e47-bbd4-e8677540370f', N'1fde7a06-9aed-ee40-a7ae-605296f1ce68', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'39a3809c-f855-e443-bab2-e86dd237dac2', N'265c44e3-380b-c340-a940-4719e1b1ba86', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'20222bcb-f156-8140-95ce-e892b06efb31', N'b5598739-ead0-9943-a729-2efb96fbc8c6', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'5fd12ed3-199a-1d4c-8794-ea4ea9cda4f8', N'c6eaf65e-9efd-404b-b5fb-fb5b59200f7b', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'7a4d843e-0cb6-c745-8f21-ebf080eaa8e0', N'c840d102-65e6-0847-b251-c854e12434bf', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'71ca8ba6-54e7-4b41-8409-ec4a71a0bc74', N'edca9d8a-9b70-4d49-9dd0-d145eda8805c', N'c4c4b808-00c8-48fb-b043-2cc3240a71b3', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'4c053785-99ee-924a-ae17-ecced5593e07', N'97444936-0840-914b-9900-4d1bfe39ab40', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'09115038-93de-f048-b74c-ef37b5596998', N'c9bc38bf-69d3-4f4f-9cd8-daead9d2270b', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'fd113c8b-5b73-3546-bccd-f0d24a2e550e', N'68767e95-effc-ee48-8d3e-d7693b44324f', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'7fd24d76-0c78-314e-8d0e-f13b86afffbe', N'1a254d4f-632c-c045-a84b-bbdb94e75eae', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'16de0720-d1f4-8b43-b14b-f5a394ce9c24', N'e4cb0d64-a60f-134d-a0c8-cbc0c047f23f', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'1452c75c-5ec1-144e-a8a1-f6cf54be15a4', N'3289f7c5-cbc1-d244-a347-519a69bfeb76', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'7b95ec2f-1cb4-9e46-aa66-f76831542538', N'0480ba2e-fb54-3c43-8d0c-035be4710c56', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'b4f29ffa-03bc-3c4a-9803-f79846664e27', N'fcd2baf8-80ab-6e4b-92b8-d24377ead408', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'23e6cfe2-f8a2-074c-96c3-f86505a1e9c0', N'903fac14-7083-364c-8c2d-144a4f41712b', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'8270809c-76ee-7242-b036-f97bc7c92d03', N'3923e251-e5b0-ac4a-a017-7b0379b9f242', N'9ae7954e-5c93-4df6-a431-6786c0b2ced6', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'3d9713e3-debd-4543-aecf-fbd0bef981ad', N'6942e418-c92f-d945-85ab-74a6a381a95d', N'f72d2327-32e8-4ae4-9e21-2c72f847fe50', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'43c65294-0320-a241-b963-fd250ccccbd2', N'd94e2cc2-dbd9-4e4e-aa1a-488ece55e70d', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'66e9cc20-d0cd-974c-af62-fe419f7d09b0', N'194056c9-4dce-ad4c-9619-e6b9762d05a9', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'6ad1f56d-3fb5-7045-bb82-fe9fcdbfe3ea', N'd2af8592-0420-854f-acdc-db812b0f4d8d', N'6e878efe-1924-424c-b578-844dcc22a1b8', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'62460513-a095-fa44-a936-ff9aa16ca7fd', N'0934530d-5de9-9d4e-be5a-347ac95a971f', N'6a438602-1aad-4431-be21-62bafbc2d838', 10, 1)
INSERT [dbo].[giay_chi_tiet] ([id], [id_giay], [id_kich_co], [so_luong_ton], [trangthai]) VALUES (N'3df8a12b-86a6-fc4b-b992-ffa03ea466bc', N'9e436193-c9ea-9941-85ca-96ada4db4bac', N'3dc52f6d-c256-4d92-8a1a-3c0febfcc56a', 10, 1)
GO
INSERT [dbo].[gio_hang_chi_tiet] ([id], [id_gio_hang], [id_giay_chi_tiet], [so_luong], [ghi_chu], [trangthai]) VALUES (N'3a2e4da9-49e0-4a2c-9cd7-1531f08c007c', N'd40a87d4-f3ff-4f92-8394-f793f75b1639', N'e4f458cf-98d0-f847-bb59-07ae707f25bd', 3, N'1', 1)
INSERT [dbo].[gio_hang_chi_tiet] ([id], [id_gio_hang], [id_giay_chi_tiet], [so_luong], [ghi_chu], [trangthai]) VALUES (N'b897ac00-69f7-4e49-8595-89124cc42e45', N'd40a87d4-f3ff-4f92-8394-f793f75b1639', N'0f034cf9-8d35-1846-bbfb-087be7ab5dc0', 5, N'1', 1)
GO
INSERT [dbo].[chuong_trinh_giam_gia_hoa_don] ([id], [ma], [ten], [dieu_kien], [so_tien_giam_max], [ngay_bat_dau], [ngay_ket_thuc], [phan_tram_giam], [so_luong], [trangthai]) VALUES (N'fa64aca7-fa74-444b-ba3c-240bb4557ffc', N'GGHD02', N'Giảm 9%', CAST(2000000 AS Decimal(18, 0)), CAST(200000 AS Decimal(18, 0)), CAST(N'2023-11-06' AS Date), CAST(N'2023-12-12' AS Date), 9, 20, 1)
INSERT [dbo].[chuong_trinh_giam_gia_hoa_don] ([id], [ma], [ten], [dieu_kien], [so_tien_giam_max], [ngay_bat_dau], [ngay_ket_thuc], [phan_tram_giam], [so_luong], [trangthai]) VALUES (N'a05db32f-15a7-4684-9cff-59700bc5404b', N'GGHD05', N'Giảm 3%', CAST(2000000 AS Decimal(18, 0)), CAST(200000 AS Decimal(18, 0)), CAST(N'2023-11-06' AS Date), CAST(N'2023-12-12' AS Date), 3, 20, 1)
INSERT [dbo].[chuong_trinh_giam_gia_hoa_don] ([id], [ma], [ten], [dieu_kien], [so_tien_giam_max], [ngay_bat_dau], [ngay_ket_thuc], [phan_tram_giam], [so_luong], [trangthai]) VALUES (N'1e6af3a2-918f-4c8d-86eb-8685ecd53a57', N'GGHD01', N'Giảm 10%', CAST(5000000 AS Decimal(18, 0)), CAST(500000 AS Decimal(18, 0)), CAST(N'2023-11-06' AS Date), CAST(N'2023-12-12' AS Date), 10, 20, 1)
INSERT [dbo].[chuong_trinh_giam_gia_hoa_don] ([id], [ma], [ten], [dieu_kien], [so_tien_giam_max], [ngay_bat_dau], [ngay_ket_thuc], [phan_tram_giam], [so_luong], [trangthai]) VALUES (N'b8f5b69e-14af-45ac-82ac-a6802430c7ed', N'GGHD04', N'Giảm 8%', CAST(1000000 AS Decimal(18, 0)), CAST(200000 AS Decimal(18, 0)), CAST(N'2023-11-06' AS Date), CAST(N'2023-12-12' AS Date), 8, 20, 1)
INSERT [dbo].[chuong_trinh_giam_gia_hoa_don] ([id], [ma], [ten], [dieu_kien], [so_tien_giam_max], [ngay_bat_dau], [ngay_ket_thuc], [phan_tram_giam], [so_luong], [trangthai]) VALUES (N'0b4a4dc3-8ff0-41ac-85e3-ab76a3238611', N'GGHD03', N'Giảm 5%', CAST(1000000 AS Decimal(18, 0)), CAST(100000 AS Decimal(18, 0)), CAST(N'2023-11-06' AS Date), CAST(N'2023-12-12' AS Date), 5, 20, 1)
GO
INSERT [dbo].[gio_hang_san_pham_yeu_thich_chi_tiet] ([id], [id_gio_hang_san_pham_yeu_thich], [id_giay], [ngay_tao], [trangthai]) VALUES (N'fc1c9ba0-01e9-b044-9e51-8ceee4eb96c5', N'72ae816c-5fe7-6b42-8971-4fd24759445c', N'c00d80a0-1adb-7845-9f3b-9ac42c2e2d1c', CAST(N'2023-11-10' AS Date), 1)
GO
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'92cc91fd-da54-474f-8221-00ab9ac84be6', N'puma1.jpg', N'c9bc38bf-69d3-4f4f-9cd8-daead9d2270b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'6befe52e-0787-7d47-a00a-019abb94a6e3', N'gucci5.jpg', N'b5598739-ead0-9943-a729-2efb96fbc8c6', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'bdb0f627-dda5-534c-8b7f-033124a32003', N'versace3_3.jpg', N'194056c9-4dce-ad4c-9619-e6b9762d05a9', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'8e9b2a91-b3cd-884b-8851-04852a356fc8', N'nike4_1.jpg', N'fcd2baf8-80ab-6e4b-92b8-d24377ead408', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'8dd3f598-9327-0c41-a276-04d1dcd9000b', N'Balenciaga3.jpg', N'8d1d8b62-1395-5d43-b474-ab88c5bd7a39', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'75ce077e-b9e9-f141-863c-0565ed2c9d9f', N'Balenciaga4_2.jpg', N'3289f7c5-cbc1-d244-a347-519a69bfeb76', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'48b293e6-29d4-5f4a-93ea-05c88a09cdf3', N'gucci2.jpg', N'7603d98a-9e2a-954e-aa88-bb7c598fca5e', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'43dcc593-656a-314b-a21e-05e51546954c', N'mlb1_1.jpg', N'3f71490b-511f-7b44-84c9-3fe4413b3188', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'35835b1c-f3aa-2f47-b345-06686f446557', N'Adidas5.jpg', N'cbf8ab37-e9bb-8b47-ae14-bfe98eb6a711', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'ce383cb9-7b42-5a47-8168-089b491e6799', N'Adidas5_3.jpg', N'cbf8ab37-e9bb-8b47-ae14-bfe98eb6a711', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'9c081ffb-26a7-8945-bf9b-0a62da0fc9bb', N'ysl2_2.jpg', N'47d0e3c9-7e14-b446-af5d-747f9fc3d1c8', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'cd01b26d-219e-434f-890e-0bccb17282a8', N'ysl1_1.jpg', N'903fac14-7083-364c-8c2d-144a4f41712b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'1fcbd661-7220-2e4b-bea0-0d098241e140', N'puma1_3.jpg', N'c9bc38bf-69d3-4f4f-9cd8-daead9d2270b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'7a186630-a50a-7a40-8730-0d7de0ab16fa', N'versace4.jpg', N'3923e251-e5b0-ac4a-a017-7b0379b9f242', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'65c03615-c32c-5245-98ef-0dfe264e5c94', N'dior2_3.jpg', N'fc4c744b-aede-b645-b118-7ea9140b8249', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'cf539836-46b1-2341-93a6-10cb4e78ada1', N'versace3.jpg', N'194056c9-4dce-ad4c-9619-e6b9762d05a9', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'2ffc11f1-990e-4e46-bd05-11b87a2fae45', N'Balenciaga2_2.jpg', N'edca9d8a-9b70-4d49-9dd0-d145eda8805c', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'268b8369-3dee-5a4b-af2c-11f8a9d34951', N'Balenciaga1_1.jpg', N'1fde7a06-9aed-ee40-a7ae-605296f1ce68', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'051ae573-d42f-4f4a-bb5d-121712c52e6a', N'ysl4.jpg', N'91a7f37b-8538-e24f-8299-a06bd6640095', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'e279aede-5abb-9e49-b757-1230416b1125', N'Adidas4_1.jpg', N'97444936-0840-914b-9900-4d1bfe39ab40', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'b35ae07c-639d-804b-b193-1417ca1e0db3', N'Adidas2_2.jpg', N'6942e418-c92f-d945-85ab-74a6a381a95d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'0d3129ed-222b-7a48-9d7a-151204759c26', N'gucci1_1.jpg', N'046dc72b-3057-ff4d-bbf3-a865de58b6a8', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'e8b6f27a-a5e2-524d-b74b-15e2105e104f', N'lacoste3_2.jpg', N'd94e2cc2-dbd9-4e4e-aa1a-488ece55e70d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'e57f42a7-bd9d-b843-85c4-1784f9f37ec1', N'versace5.jpg', N'39774a40-f33b-8f41-b22e-bc50ca964779', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'6d68bcc8-42b5-c44a-a634-17dc20b321cc', N'mlb3_2.jpg', N'597a9673-6929-d342-96dc-0a4c47e3cc7b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'bac5375e-63f4-a84a-938d-184b008e5766', N'versace5_1.jpg', N'39774a40-f33b-8f41-b22e-bc50ca964779', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'd80f5f3f-55c8-9b4d-810b-18567e1f978f', N'Adidas3_1.jpg', N'74e74ba3-6285-c247-b588-62e0cd12e1e1', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'21810c32-3ad9-124d-924e-185d9ffad035', N'gucci1_3.jpg', N'046dc72b-3057-ff4d-bbf3-a865de58b6a8', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'ff76ad15-61cc-ea47-8443-19277755d92f', N'ck4.jpg', N'a0e7fe26-c8e0-b348-ac67-6440f74b2b08', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'7330a255-fc25-9b43-87cc-19460b7acb77', N'ysl5_1.jpg', N'1a254d4f-632c-c045-a84b-bbdb94e75eae', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'b8a05068-c245-ee48-9d4f-1abe942dbf13', N'lacoste2.jpg', N'c6eaf65e-9efd-404b-b5fb-fb5b59200f7b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'6750007b-1ebd-8446-9f08-1ea639c1c65a', N'versace2_2.jpg', N'd2af8592-0420-854f-acdc-db812b0f4d8d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'84f809ae-4c3a-ad46-bed1-1ed5bed48592', N'ck5_1.jpg', N'7e456810-1c13-a342-a811-2bc7c95c1a4d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'8a9adbfb-2c8d-0c4a-bde7-207c944b4dcb', N'lacoste5.jpg', N'a890b541-d210-7a42-bfa9-bb9ab4a73f60', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'd7085a53-b06f-6b43-8fc8-21f05b62514e', N'Chanel2_1.jpg', N'390ecab5-666a-8c49-8b8c-3c894b85ea05', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'ce1d2db8-38a1-564b-8b52-22c35dc4669f', N'Adidas3.jpg', N'74e74ba3-6285-c247-b588-62e0cd12e1e1', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'343d54b3-a08b-c64b-941f-22c9317333ec', N'Adidas4_2.jpg', N'97444936-0840-914b-9900-4d1bfe39ab40', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'321765cf-96ca-1947-912e-2367638ebc18', N'dior5.jpg', N'0480ba2e-fb54-3c43-8d0c-035be4710c56', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'7bc66e07-c052-8c40-bd79-23760a74a4da', N'lacoste5_3.jpg', N'a890b541-d210-7a42-bfa9-bb9ab4a73f60', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'56f23bf0-55a1-c944-9749-23e0b1804808', N'nike4_2.jpg', N'fcd2baf8-80ab-6e4b-92b8-d24377ead408', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'c67a828f-7469-c34b-a269-25e5274a7706', N'Adidas2_3.jpg', N'6942e418-c92f-d945-85ab-74a6a381a95d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'24a0e4a5-99aa-484f-a94a-278a690d4bf4', N'Adidas1_3.jpg', N'90545bde-c344-5949-aba2-c14c7d673bd2', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'97582014-9066-7947-9fd1-28fe28b930a4', N'nike3_3.jpg', N'c840d102-65e6-0847-b251-c854e12434bf', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'9532d1f3-0f08-ae42-8744-2b1498034ec1', N'lacoste4_2.jpg', N'265c44e3-380b-c340-a940-4719e1b1ba86', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'f9d0bc42-3f0d-7941-917f-2c3606a6b3df', N'Chanel1.jpg', N'8720d28f-5e84-7047-a0cd-886872b4dbc1', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'4a48334d-9600-f847-aaaf-2c5282a0df87', N'ck5.jpg', N'7e456810-1c13-a342-a811-2bc7c95c1a4d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'5c84ff68-3b51-9249-8cc0-2d5c85f56199', N'ysl1_2.jpg', N'903fac14-7083-364c-8c2d-144a4f41712b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'0ff8bef3-ae25-3149-93ac-2f82ed93605b', N'nike5.jpg', N'e6d07e36-852b-0448-adaf-da91ad40a604', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'35d76444-f483-2342-b490-304eabe362bc', N'Adidas3_3.jpg', N'74e74ba3-6285-c247-b588-62e0cd12e1e1', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'261eddc6-f1cc-b040-9e3f-305037d65874', N'puma5.jpg', N'b55241cb-d126-8741-b3af-35b6f12e3cf1', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'114e6155-70e3-b54c-a325-3133b4e682af', N'ck5_2.jpg', N'7e456810-1c13-a342-a811-2bc7c95c1a4d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'46171dec-dc76-2347-b110-31fb4fb5e5bb', N'versace4_2.jpg', N'3923e251-e5b0-ac4a-a017-7b0379b9f242', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'746250ac-5af5-bd4c-b149-33325a489f0c', N'ysl1.jpg', N'903fac14-7083-364c-8c2d-144a4f41712b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'571aa6c4-edd2-eb42-97d4-34f93c3f5a30', N'nike1_1.jpg', N'9a19ff2b-786c-a142-a356-403777815763', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'b8462199-2655-7e47-b1bb-36a635e6259a', N'ck2.jpg', N'4a5281da-8e3c-f945-97ea-22ec86a3c01a', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'63cba24c-01c7-274d-addc-36a81b1f87fb', N'Balenciaga2.jpg', N'edca9d8a-9b70-4d49-9dd0-d145eda8805c', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'a6c1d803-4b7a-2c4f-8eb0-36be867d43ca', N'versace4_1.jpg', N'3923e251-e5b0-ac4a-a017-7b0379b9f242', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'9c4acdc4-6bd3-8b4a-b2c0-36cdeae8ef5b', N'versace1_1.jpg', N'feaf547d-1b1d-5141-b413-ced86da78e9f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'fc140a96-0fea-544e-b007-375ea99d51b6', N'gucci4_1.jpg', N'e4cb0d64-a60f-134d-a0c8-cbc0c047f23f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'412027ea-65e0-c940-8b24-37de6d030650', N'mlb5.jpg', N'6de72fc4-57ac-4e47-b37a-a5d31d05c5e9', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'c6a38b12-0f78-9646-ad0f-38a489857aa9', N'ysl4_1.jpg', N'91a7f37b-8538-e24f-8299-a06bd6640095', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'b79ff3d1-10af-724c-8dfe-39db5b7d835b', N'puma4.jpg', N'9e436193-c9ea-9941-85ca-96ada4db4bac', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'e019f672-cb29-9b40-b7d5-3a6fe9952b42', N'versace3_2.jpg', N'194056c9-4dce-ad4c-9619-e6b9762d05a9', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'07174de2-01cb-374e-9804-3c0eaaa3df56', N'versace4_3.jpg', N'3923e251-e5b0-ac4a-a017-7b0379b9f242', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'e53dbbe6-ecb5-c140-8843-3faeedc1088d', N'nike3_2.jpg', N'c840d102-65e6-0847-b251-c854e12434bf', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'0d592582-1e77-6541-ba02-40778a28fe9d', N'puma1_2.jpg', N'c9bc38bf-69d3-4f4f-9cd8-daead9d2270b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'379bffcb-44e2-a94a-89ce-4078cc17ce68', N'Chanel2_3.jpg', N'390ecab5-666a-8c49-8b8c-3c894b85ea05', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'be8826ac-f917-dd4c-a14a-44e1a04c9510', N'puma2.jpg', N'f2e2785b-d430-aa4a-8439-187f202a73e6', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'cf1c6b16-8e35-6c45-953c-455107422820', N'lacoste3_3.jpg', N'd94e2cc2-dbd9-4e4e-aa1a-488ece55e70d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'de75597c-6929-7240-ab82-462cd8570c52', N'nike2.jpg', N'12300a57-8b03-2846-927e-6cfc2ae3b52e', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'07d95558-cfe8-7b40-b862-46427cb2ba99', N'Balenciaga3_2.jpg', N'8d1d8b62-1395-5d43-b474-ab88c5bd7a39', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'f05e49f4-6b62-6947-b5f1-467e52b2c983', N'lacoste2_2.jpg', N'c6eaf65e-9efd-404b-b5fb-fb5b59200f7b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'e05f144f-cfbf-eb41-8c60-471cf0fd93e0', N'lacoste1.jpg', N'2e5e0e52-896b-304b-af47-c00efb474bb4', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'd4fe4b19-afbe-724e-a3c6-491de6663dd8', N'lacoste4.jpg', N'265c44e3-380b-c340-a940-4719e1b1ba86', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'd2c428c7-d6bc-4746-bd39-4df01d40b457', N'ck1_2.jpg', N'68767e95-effc-ee48-8d3e-d7693b44324f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'2588612f-a282-2946-b5ab-4e572fa0d687', N'puma2_1.jpg', N'f2e2785b-d430-aa4a-8439-187f202a73e6', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'614f91a2-6202-834d-b3cc-4eceec8eddb1', N'nike3.jpg', N'c840d102-65e6-0847-b251-c854e12434bf', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'74abd14c-d585-4d46-b0b9-51165896c797', N'mlb4.jpg', N'909849bf-6822-8242-b385-5bdc8953d684', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'8273080d-ffa8-5445-851d-54ff2410db16', N'puma3_2.jpg', N'51e08f27-5698-704a-b703-a6dc5d16a3a8', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'5b39a0ed-d628-8d42-b83c-55f36c53c056', N'mlb1_3.jpg', N'3f71490b-511f-7b44-84c9-3fe4413b3188', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'e3b59d17-c1bf-8f41-948b-570be5259af3', N'ck3_3.jpg', N'5032713b-839d-c746-914e-5c29004fc3c7', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'6e60bf49-2ed8-5840-a65d-586b347ab66d', N'gucci5_3.jpg', N'b5598739-ead0-9943-a729-2efb96fbc8c6', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'34157c78-847d-5644-ba17-5973a0932434', N'puma2_2.jpg', N'f2e2785b-d430-aa4a-8439-187f202a73e6', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'40283ab8-9a70-eb42-a030-5a1f4781ba20', N'lacoste2_3.jpg', N'c6eaf65e-9efd-404b-b5fb-fb5b59200f7b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'1164cb3c-624d-2a4e-aa03-5a6ce983eb9a', N'gucci4_3.jpg', N'e4cb0d64-a60f-134d-a0c8-cbc0c047f23f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'a27a0646-de8c-d94f-acfe-5bd60e8aeb61', N'gucci3_2.jpg', N'e5e235cd-17e0-8f41-9957-0e4e116df55a', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'323db015-a59b-2747-a0b8-5caf63a7ca33', N'mlb2_2.jpg', N'6049b327-e697-d74d-a3ab-bb1f7543b139', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'1de45e46-e213-6146-92fd-5ffd07a59889', N'nike4_3.jpg', N'fcd2baf8-80ab-6e4b-92b8-d24377ead408', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'76f535f0-c649-3543-a0bd-6099480fda2c', N'ck2_3.jpg', N'4a5281da-8e3c-f945-97ea-22ec86a3c01a', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'2b2fd2a6-0514-ae4c-8f08-6123cb3b18f6', N'Adidas3_2.jpg', N'74e74ba3-6285-c247-b588-62e0cd12e1e1', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'43338a0e-3136-2c4a-82b5-620cbee0cbca', N'nike4.jpg', N'fcd2baf8-80ab-6e4b-92b8-d24377ead408', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'86add7ac-35ae-b648-9e33-631189f9f017', N'versace1_2.jpg', N'feaf547d-1b1d-5141-b413-ced86da78e9f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'00da545d-158c-464b-9777-64cc4e899fe5', N'ysl3_3.jpg', N'3af55b31-a9a6-e548-a8f7-6e598f233d98', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'ccfef771-9b20-3946-8132-66710a637d2e', N'Chanel3_2.jpg', N'7dd94f65-b809-924e-a9ca-3d24ff849c5a', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'd86847e5-39fa-e24d-9ccb-67d4b81d1173', N'Chanel2.jpg', N'390ecab5-666a-8c49-8b8c-3c894b85ea05', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'c47a549c-f32d-5046-8133-69bce4d0d655', N'dior5_2.jpg', N'0480ba2e-fb54-3c43-8d0c-035be4710c56', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'30a1ca32-3fe3-834a-ae61-69f99e961228', N'puma5_1.jpg', N'b55241cb-d126-8741-b3af-35b6f12e3cf1', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'ff2ee3a4-16be-c840-8a8d-6ab8f05e94f5', N'Adidas1_1.jpg', N'90545bde-c344-5949-aba2-c14c7d673bd2', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'7af34468-06f3-0b43-b1f3-6d4e601e9eda', N'ysl2_1.jpg', N'47d0e3c9-7e14-b446-af5d-747f9fc3d1c8', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'589122ef-b8da-f143-ad48-6ea13101e1ff', N'mlb2_3.jpg', N'6049b327-e697-d74d-a3ab-bb1f7543b139', NULL)
GO
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'3cebe2c6-849c-d54e-b500-6f2143996abf', N'Chanel2_2.jpg', N'390ecab5-666a-8c49-8b8c-3c894b85ea05', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'3c8db31a-1c2e-504a-92ff-6f9aea57dc5e', N'nike2_2.jpg', N'12300a57-8b03-2846-927e-6cfc2ae3b52e', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'31b71e1b-bda5-1b4d-926f-70ab72e920df', N'mlb3_1.jpg', N'597a9673-6929-d342-96dc-0a4c47e3cc7b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'a8397607-1484-4045-a380-70e740f0acdb', N'ck1.jpg', N'68767e95-effc-ee48-8d3e-d7693b44324f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'e16145fc-98be-8d4e-b641-71917aa51aed', N'ysl5_3.jpg', N'1a254d4f-632c-c045-a84b-bbdb94e75eae', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'a7a18237-871b-384d-bb01-7203994e8779', N'mlb3_3.jpg', N'597a9673-6929-d342-96dc-0a4c47e3cc7b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'4db50df5-db78-8e4a-ab11-731f9f84d0da', N'gucci5_2.jpg', N'b5598739-ead0-9943-a729-2efb96fbc8c6', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'bdb83835-35d5-2b47-9a49-75595ac66dea', N'Chanel3_1.jpg', N'7dd94f65-b809-924e-a9ca-3d24ff849c5a', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'4af6b850-e88c-214e-bed2-7645dd949d64', N'lacoste4_3.jpg', N'265c44e3-380b-c340-a940-4719e1b1ba86', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'7d63e58d-c438-d44c-9a6f-7652534f1ff7', N'dior4_2.jpg', N'0934530d-5de9-9d4e-be5a-347ac95a971f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'f3a515cf-4385-b94c-ac3a-7676511f38e5', N'versace2_1.jpg', N'd2af8592-0420-854f-acdc-db812b0f4d8d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'97a040a0-e43a-a743-8a3f-76ea28c2c512', N'Adidas4_3.jpg', N'97444936-0840-914b-9900-4d1bfe39ab40', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'1672a7b0-62b5-a54e-91db-78fef925f750', N'Balenciaga1_2.jpg', N'1fde7a06-9aed-ee40-a7ae-605296f1ce68', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'ac46ece5-1a93-6144-9173-7a2af8901395', N'mlb4_2.jpg', N'909849bf-6822-8242-b385-5bdc8953d684', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'97ae6291-829a-9342-b973-7ab99fc0f27a', N'gucci4_2.jpg', N'e4cb0d64-a60f-134d-a0c8-cbc0c047f23f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'1b976682-4e21-1a4b-a9ec-7c78c64f3f5d', N'ysl2.jpg', N'47d0e3c9-7e14-b446-af5d-747f9fc3d1c8', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'bca2284b-8df3-3f46-b6c2-7c893fe71a2d', N'versace1.jpg', N'feaf547d-1b1d-5141-b413-ced86da78e9f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'4035d017-2ecf-b94a-8c15-7e0c67d89720', N'gucci3_1.jpg', N'e5e235cd-17e0-8f41-9957-0e4e116df55a', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'9c5a7d02-201f-b548-b50b-7ed78593f051', N'mlb4_3.jpg', N'909849bf-6822-8242-b385-5bdc8953d684', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'bfbc2a3f-55db-9b41-9a70-7feeb60f2e94', N'nike1_3.jpg', N'9a19ff2b-786c-a142-a356-403777815763', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'accc402f-f2df-ab42-ae54-81f3993b9cbc', N'gucci1.jpg', N'046dc72b-3057-ff4d-bbf3-a865de58b6a8', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'156e73d4-1d55-4c4f-9786-84381aeeb5b9', N'gucci3.jpg', N'e5e235cd-17e0-8f41-9957-0e4e116df55a', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'1653972b-542f-8749-a150-8482bab48511', N'ck5_3.jpg', N'7e456810-1c13-a342-a811-2bc7c95c1a4d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'bd2b987d-f2a6-fc46-8471-85a39924eeb8', N'puma3_1.jpg', N'51e08f27-5698-704a-b703-a6dc5d16a3a8', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'd1c8ddf5-ad1b-d442-8012-866c107e7401', N'ysl4_2.jpg', N'91a7f37b-8538-e24f-8299-a06bd6640095', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'0422d0b5-051a-8c4a-895a-872efe6a9a9b', N'mlb5_3.jpg', N'6de72fc4-57ac-4e47-b37a-a5d31d05c5e9', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'fe858255-aa75-8149-8984-8aec017fcdc6', N'Balenciaga2_3.jpg', N'edca9d8a-9b70-4d49-9dd0-d145eda8805c', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'6a2f5f0d-014f-7c4c-a713-8b49c4cfcf7a', N'mlb1.jpg', N'3f71490b-511f-7b44-84c9-3fe4413b3188', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'40c07d4a-a32c-d548-b444-8e019d6ec10d', N'ck3_2.jpg', N'5032713b-839d-c746-914e-5c29004fc3c7', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'a4f069f9-4938-9f4c-95e8-8e98a297bb8c', N'ck3.jpg', N'5032713b-839d-c746-914e-5c29004fc3c7', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'73debc12-e7b7-8547-8749-9077f4fcbe1a', N'mlb5_2.jpg', N'6de72fc4-57ac-4e47-b37a-a5d31d05c5e9', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'14e993eb-ec51-f94a-ac6e-90a2f6fe4840', N'lacoste3.jpg', N'd94e2cc2-dbd9-4e4e-aa1a-488ece55e70d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'71f21b0b-f149-ef42-8fd3-9113c290a146', N'ysl5.jpg', N'1a254d4f-632c-c045-a84b-bbdb94e75eae', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'96590692-1f38-9447-8bc4-919d586831ac', N'puma3.jpg', N'51e08f27-5698-704a-b703-a6dc5d16a3a8', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'73d1249d-acba-824a-9108-9274d664a88e', N'Balenciaga1.jpg', N'1fde7a06-9aed-ee40-a7ae-605296f1ce68', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'bfa6f289-2f1a-7b43-a12b-93db2a6eac56', N'nike2_1.jpg', N'12300a57-8b03-2846-927e-6cfc2ae3b52e', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'898ea3c0-16e3-c64f-a1af-96a1d06891f1', N'ck3_1.jpg', N'5032713b-839d-c746-914e-5c29004fc3c7', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'a4a83593-cbdf-114d-a951-9948cd459089', N'ck1_3.jpg', N'68767e95-effc-ee48-8d3e-d7693b44324f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'a346282d-0c98-9548-acd2-9a924c578c35', N'puma3_3.jpg', N'51e08f27-5698-704a-b703-a6dc5d16a3a8', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'23c3af4d-42ad-854b-bafd-9c89569a4fc5', N'ysl1_3.jpg', N'903fac14-7083-364c-8c2d-144a4f41712b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'5ab5446e-14d4-1745-9803-9e7757def5ff', N'dior3_1.jpg', N'cb626472-c91c-ec4a-9369-7b0942e94edd', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'c8c6aae9-82f8-a245-ab9e-9e799a290ced', N'lacoste4_1.jpg', N'265c44e3-380b-c340-a940-4719e1b1ba86', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'beddd94f-b5c2-0143-9368-9f1672a0348a', N'dior1_3.jpg', N'c00d80a0-1adb-7845-9f3b-9ac42c2e2d1c', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'ba6a6797-b451-ec44-91a6-9fadd719b344', N'nike3_1.jpg', N'c840d102-65e6-0847-b251-c854e12434bf', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'af50260d-a0a5-e84a-aae6-9fd728e746d5', N'Adidas2_1.jpg', N'6942e418-c92f-d945-85ab-74a6a381a95d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'20f72bb8-40ee-264e-a17d-9fdd5f9bdd2d', N'Balenciaga4_3.jpg', N'3289f7c5-cbc1-d244-a347-519a69bfeb76', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'143cb0e4-72eb-7c43-b5d0-a37e5ef143cd', N'mlb2_1.jpg', N'6049b327-e697-d74d-a3ab-bb1f7543b139', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'cd08cddc-d110-d84d-bc34-a39030e27dd9', N'dior4_1.jpg', N'0934530d-5de9-9d4e-be5a-347ac95a971f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'da585b75-9c72-6e43-a198-a4ab458da907', N'puma5_2.jpg', N'b55241cb-d126-8741-b3af-35b6f12e3cf1', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'4e6ef2af-65ff-7d42-80ea-a60306d2ee75', N'dior3_3.jpg', N'cb626472-c91c-ec4a-9369-7b0942e94edd', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'b53c5061-35c8-7b40-8e08-a6f1239b04b3', N'Balenciaga4_1.jpg', N'3289f7c5-cbc1-d244-a347-519a69bfeb76', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'8937fbce-548b-9044-99fb-a7a1ddf3832e', N'Adidas1_2.jpg', N'90545bde-c344-5949-aba2-c14c7d673bd2', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'bb8776c2-5e70-cb4a-83e1-a7eea7c093a9', N'versace3_1.jpg', N'194056c9-4dce-ad4c-9619-e6b9762d05a9', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'37a37e60-6fc5-ba4a-80de-a8d6ad0e2522', N'gucci5_1.jpg', N'b5598739-ead0-9943-a729-2efb96fbc8c6', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'c9554a1e-f5bc-2844-a548-a8eb28ca0d5d', N'dior3.jpg', N'cb626472-c91c-ec4a-9369-7b0942e94edd', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'2ee3caed-a6eb-194e-91e3-aa1dc7831684', N'Adidas4.jpg', N'97444936-0840-914b-9900-4d1bfe39ab40', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'644a0116-9e7e-4c4c-a72d-ab2bb710a135', N'ysl4_3.jpg', N'91a7f37b-8538-e24f-8299-a06bd6640095', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'217b614b-ae43-4741-bc2d-ab7fbf4803c4', N'dior1_1.jpg', N'c00d80a0-1adb-7845-9f3b-9ac42c2e2d1c', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'00834394-75e6-b24d-8368-ac5698810afe', N'puma1_1.jpg', N'c9bc38bf-69d3-4f4f-9cd8-daead9d2270b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'5908dbb4-232c-c243-8a27-acef2646ce91', N'Balenciaga2_1.jpg', N'edca9d8a-9b70-4d49-9dd0-d145eda8805c', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'c7f1a240-e310-5e4e-86e2-ad5f034719ec', N'Balenciaga3_1.jpg', N'8d1d8b62-1395-5d43-b474-ab88c5bd7a39', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'56d3b0fe-7549-e14e-abcf-af1fa469bc64', N'versace2_3.jpg', N'd2af8592-0420-854f-acdc-db812b0f4d8d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'cd6dd750-4d6d-9c4c-93c8-af43a2bdc599', N'Adidas5_1.jpg', N'cbf8ab37-e9bb-8b47-ae14-bfe98eb6a711', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'ee7a683e-9f3e-b74a-b9f5-af6c81dea471', N'gucci4.jpg', N'e4cb0d64-a60f-134d-a0c8-cbc0c047f23f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'1187c027-7b7c-c14c-87f8-b15164f32018', N'lacoste2_1.jpg', N'c6eaf65e-9efd-404b-b5fb-fb5b59200f7b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'1819a128-4cce-e547-b31c-b460d21726aa', N'dior2.jpg', N'fc4c744b-aede-b645-b118-7ea9140b8249', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'a7f652dd-e556-b047-81d3-b743e61a3bfd', N'Balenciaga4.jpg', N'3289f7c5-cbc1-d244-a347-519a69bfeb76', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'b0c8b0ff-8c7b-8446-9095-b885078eac3a', N'gucci2_2.jpg', N'7603d98a-9e2a-954e-aa88-bb7c598fca5e', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'a19762c9-53c0-a84a-8e9c-be4173b7bdfb', N'Adidas5_2.jpg', N'cbf8ab37-e9bb-8b47-ae14-bfe98eb6a711', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'03e20fd9-689d-0e4f-9c32-bf7ea826328a', N'ck2_2.jpg', N'4a5281da-8e3c-f945-97ea-22ec86a3c01a', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'2ad119f8-a72b-2f43-9d2f-c0efa83c928a', N'Chanel3.jpg', N'7dd94f65-b809-924e-a9ca-3d24ff849c5a', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'3cb23777-6ad9-8843-8624-c204b14be97a', N'dior3_2.jpg', N'cb626472-c91c-ec4a-9369-7b0942e94edd', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'e702dac9-5a9e-3045-9e43-c3610ccea343', N'versace2.jpg', N'd2af8592-0420-854f-acdc-db812b0f4d8d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'3aea4d8c-ffac-f740-b7cf-c4e28a88dbc0', N'lacoste5_2.jpg', N'a890b541-d210-7a42-bfa9-bb9ab4a73f60', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'c2f1dde3-fc5b-d24f-b03b-c51c6963b4ff', N'ck1_1.jpg', N'68767e95-effc-ee48-8d3e-d7693b44324f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'4d31334a-c9cf-084c-989c-c5deb831cad1', N'ck2_1.jpg', N'4a5281da-8e3c-f945-97ea-22ec86a3c01a', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'fbe4c610-dd3b-d542-be98-c7254d6f7a6d', N'ysl5_2.jpg', N'1a254d4f-632c-c045-a84b-bbdb94e75eae', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'92335c6c-412a-ae40-9209-c755b1fe8d66', N'Adidas1.jpg', N'90545bde-c344-5949-aba2-c14c7d673bd2', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'2836acfb-3814-544c-80f0-c826cc3e04b9', N'mlb1_2.jpg', N'3f71490b-511f-7b44-84c9-3fe4413b3188', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'b5e11f1b-cc85-a34d-a235-c8ae31f6a672', N'nike1.jpg', N'9a19ff2b-786c-a142-a356-403777815763', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'd9905f7e-a0c2-6d46-8d31-cb06df808db6', N'dior1.jpg', N'c00d80a0-1adb-7845-9f3b-9ac42c2e2d1c', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'3b2b14cd-9d13-db48-88e2-cc36a9eb67a0', N'gucci1_2.jpg', N'046dc72b-3057-ff4d-bbf3-a865de58b6a8', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'd400a956-1a3f-a64e-80b9-d03f27ce2e64', N'dior5_3.jpg', N'0480ba2e-fb54-3c43-8d0c-035be4710c56', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'09866dd6-2229-a240-a2fa-d0b1c3098396', N'ysl2_3.jpg', N'47d0e3c9-7e14-b446-af5d-747f9fc3d1c8', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'531053fb-1251-da4f-82c4-d2e18d5d6946', N'ysl3.jpg', N'3af55b31-a9a6-e548-a8f7-6e598f233d98', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'56d28baa-603c-0a4c-9f5b-d2f8e9e02282', N'nike5_1.jpg', N'e6d07e36-852b-0448-adaf-da91ad40a604', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'f1752ff9-6944-ef49-beab-d55ae8053667', N'lacoste3_1.jpg', N'd94e2cc2-dbd9-4e4e-aa1a-488ece55e70d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'd7afe6f5-74df-ec4a-8fc5-d573e169ffeb', N'versace1_3.jpg', N'feaf547d-1b1d-5141-b413-ced86da78e9f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'7b3f57cd-cca6-6f49-b9a4-d7ce3cb6cc64', N'Balenciaga1_3.jpg', N'1fde7a06-9aed-ee40-a7ae-605296f1ce68', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'cf0e52fb-87c5-6a41-ac43-dc1748715994', N'mlb4_1.jpg', N'909849bf-6822-8242-b385-5bdc8953d684', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'72256b89-e82d-1d48-b6aa-dc20a5517a85', N'nike1_2.jpg', N'9a19ff2b-786c-a142-a356-403777815763', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'5378feec-8cfe-4c44-af33-de57e5e1f9a7', N'gucci2_1.jpg', N'7603d98a-9e2a-954e-aa88-bb7c598fca5e', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'7abb5c3f-ebca-1343-841a-e02e92c2b6e8', N'dior5_1.jpg', N'0480ba2e-fb54-3c43-8d0c-035be4710c56', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'e75965a5-010f-b947-8c96-e402948e4791', N'puma5_3.jpg', N'b55241cb-d126-8741-b3af-35b6f12e3cf1', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'ce87eca4-8ac4-ea46-b795-e466d53facfb', N'nike5_3.jpg', N'e6d07e36-852b-0448-adaf-da91ad40a604', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'398446a0-e7eb-a74d-9055-e6934febbec2', N'mlb2.jpg', N'6049b327-e697-d74d-a3ab-bb1f7543b139', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'90244a28-4e7e-3240-a9c6-e93776d779f3', N'dior2_2.jpg', N'fc4c744b-aede-b645-b118-7ea9140b8249', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'03f7df82-98cd-5447-9b1c-eb3fcdf2ff48', N'dior4.jpg', N'0934530d-5de9-9d4e-be5a-347ac95a971f', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'62112f0c-6c94-0447-a102-eb6facc843ec', N'ysl3_1.jpg', N'3af55b31-a9a6-e548-a8f7-6e598f233d98', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'fb6d978e-441f-f54f-866b-ed6d7cdadb1d', N'dior4_3.jpg', N'0934530d-5de9-9d4e-be5a-347ac95a971f', NULL)
GO
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'0a7c8469-63f9-8541-9711-ee9d99f61fc5', N'Chanel3_3.jpg', N'7dd94f65-b809-924e-a9ca-3d24ff849c5a', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'981b8930-1a25-9a43-b66f-ef79bb832569', N'nike5_2.jpg', N'e6d07e36-852b-0448-adaf-da91ad40a604', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'3cf42f0f-d99d-7640-af56-f252ff890115', N'gucci3_3.jpg', N'e5e235cd-17e0-8f41-9957-0e4e116df55a', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'a3c776be-43d5-6b40-9c67-f2d049913b51', N'gucci2_3.jpg', N'7603d98a-9e2a-954e-aa88-bb7c598fca5e', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'ed1d6660-85db-214f-8130-f43413031404', N'puma2_3.jpg', N'f2e2785b-d430-aa4a-8439-187f202a73e6', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'c071a3c4-aaa8-1d42-88d2-f536b093862c', N'dior2_1.jpg', N'fc4c744b-aede-b645-b118-7ea9140b8249', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'5f928697-2f3e-de40-bd7c-f66e69899fb3', N'mlb5_1.jpg', N'6de72fc4-57ac-4e47-b37a-a5d31d05c5e9', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'482e73cc-0327-374c-af21-f7dfe4f38f32', N'dior1_2.jpg', N'c00d80a0-1adb-7845-9f3b-9ac42c2e2d1c', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'845d57c3-0f44-bb47-982c-f7fbd8c5b0e7', N'mlb3.jpg', N'597a9673-6929-d342-96dc-0a4c47e3cc7b', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'2286f461-eda8-9f4a-9589-f971ea7fd26b', N'lacoste5_1.jpg', N'a890b541-d210-7a42-bfa9-bb9ab4a73f60', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'c9132e1f-80e1-8946-945f-fdc44e8194a8', N'Adidas2.jpg', N'6942e418-c92f-d945-85ab-74a6a381a95d', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'b28d3cfa-9f19-9a49-9dd4-fe0abcf42cf9', N'nike2_3.jpg', N'12300a57-8b03-2846-927e-6cfc2ae3b52e', NULL)
INSERT [dbo].[anh_giay] ([id], [ten_url], [id_giay], [trangthai]) VALUES (N'07267569-3361-5a47-ad78-fecfd9c220e4', N'ysl3_2.jpg', N'3af55b31-a9a6-e548-a8f7-6e598f233d98', NULL)
GO

select * from gio_hang