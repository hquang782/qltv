/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.LopController;
import controller.PhieuMuonController;
import controller.SachController;
import controller.SinhVienController;
import controller.ViPhamController;
import controller.theLoaiSachController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.PhieuMuon;
import model.Sach;
import model.SinhVien;
import model.TheLoaiSach;
import model.ViPham;

/**
 *
 * @author OS
 */
public class jFSinhVien1 extends javax.swing.JFrame {

    ArrayList<SinhVien> listsv;
    ArrayList<Sach> lists;
    ArrayList<TheLoaiSach> listtls;
    ArrayList<PhieuMuon> listpm;
    ArrayList<ViPham> lisvp;
    Login login = new Login();
    String sdt = login.getSDT();

    /**
     * Creates new form SinhVien
     */
    public jFSinhVien1() {
        initComponents();
        loadSinhVien();
        lblWelcome.setText("Welcome: " + txtHoTen.getText());
        DSloadPhieuMuon();
        DSloadSach();
        DSloadTheloaisach();
        DSloadViPham();

    }

    public void loadSinhVien() {
        SinhVienController sinhVienController = new SinhVienController();
        SinhVien sinhVien = sinhVienController.LoadSvTheoSdt(sdt);
        txtMaSV.setText(sinhVien.getMaSv());
        LopController lopController = new LopController();

        txtLop.setText(lopController.layTenTheoMaLop(sinhVien.getMaLop()));
        System.out.println(sinhVien.getMaLop());
                
        System.out.println(lopController.layTenTheoMaLop(sinhVien.getMaLop()));
                
        txtHoTen.setText(sinhVien.getHoTen());
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse((String) sinhVien.getNgaySinh());
            dateNgaySinh.setDate(date);
        } catch (ParseException ex) {
            Logger.getLogger(jFSinhVien1.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (sinhVien.getGioiTinh()) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }

        txtSDT.setText(sinhVien.getSoDienThoai());
        txtDiaChi.setText(sinhVien.getDiaChi());
        txtEmail.setText(sinhVien.getEmail());

    }



    public void updatesv() {
        SinhVien sv = new SinhVien();
        sv.setMaSv(txtMaSV.getText());
        LopController lcl = new LopController();
        sv.setPassword(txtPass.getText());
        sv.setMaLop(lcl.layMaLopTheoTen(txtLop.getText()));
        sv.setHoTen(txtHoTen.getText());
        Date date = dateNgaySinh.getDate();
        String df = new SimpleDateFormat("yyyy-MM-dd").format(date);
        sv.setNgaySinh(df);
        boolean gt;
        if (rdoNam.isSelected()) {
            gt = true;
        } else {
            gt = false;
        }
        sv.setGioiTinh(gt);
        sv.setDiaChi(txtDiaChi.getText());
        sv.setSoDienThoai(txtSDT.getText());
        sv.setEmail(txtEmail.getText());
        SinhVienController svc = new SinhVienController();
        if (txtPass.getText().equals("")) {
            if (svc.updateNoPass(sv) > 0) {
                JOptionPane.showMessageDialog(null, "cập nhật thành công");

            } else {
                JOptionPane.showMessageDialog(null, "cập nhật thất bại");
            }
        } else {
            if (svc.update(sv) > 0) {
                JOptionPane.showMessageDialog(null, "cập nhật thành công");

            } else {
                JOptionPane.showMessageDialog(null, "cập nhật thất bại");
            }
        }
    }
    
    
        public void DSloadPhieuMuon(){
        tblPhieuMuon.setModel(new DefaultTableModel(null,new String[]{"Loan ID", "Book ID", "Quantity", "Borrow Date", "Return Date"}));
        ArrayList<PhieuMuon> list = new PhieuMuonController().SearchMaSVMaPhieu(txtMaSV.getText());
        DefaultTableModel model = (DefaultTableModel) tblPhieuMuon.getModel();   
        Object rowData[] = new Object[5];
        PhieuMuonController pmcoController = new PhieuMuonController();
        for (PhieuMuon item : list) {
             rowData[0] = item.getMaPhieuMuon();
             rowData[1] = item.getMaSach();
             rowData[2] = item.getSoLuong();
             rowData[3] = item.getNgayMuon();
             rowData[4] = item.getNgayHetHan();

             model.addRow(rowData);
        }
    }
        
        public void DSloadViPham() {
        tblViPham.setModel(new DefaultTableModel(null, new String[]{"Violation ID", "Violation Error", "Fine"}));
        ArrayList<ViPham> list = new ViPhamController().SearchTen(txtMaSV.getText());
        DefaultTableModel model = (DefaultTableModel) tblViPham.getModel();
        Object rowData[] = new Object[3];
        ViPhamController vpController = new ViPhamController();
        for (ViPham item : list) {
             rowData[0] = item.getMaViPham();
//             rowData[1] = item.getMaSinhVien();
             rowData[1] = item.getTen();
             rowData[2] = item.getPhat();
             model.addRow(rowData);
        }
    }
        
    
         public void DSloadTheloaisach(){
        tblTheLoaiSach.setModel(new DefaultTableModel(null,new String[]{"Category ID", "Category Name", "Position"}));
        theLoaiSachController controller = new theLoaiSachController();
        ArrayList<TheLoaiSach> list = controller.ListTheLoai();
        DefaultTableModel model = (DefaultTableModel) tblTheLoaiSach.getModel();
       
        Object rowData[] = new Object[3];
        for(TheLoaiSach item : list){
            rowData[0] = item.getMaTheLoai();
            rowData[1] = item.getTenTheLoai();
            rowData[2] = item.getViTri();
            model.addRow(rowData);
        }
    }
         
        public void timkiemtheloai() {
        theLoaiSachController scl = new theLoaiSachController();
        ArrayList<TheLoaiSach> lists = new ArrayList<>();

        DefaultTableModel model = (DefaultTableModel) tblTheLoaiSach.getModel();
        model.setRowCount(0);
        ArrayList<TheLoaiSach> listsTheoMa = scl.Search(txtSearchTLS.getText());
        for (TheLoaiSach tls : listsTheoMa) {
            lists.add(tls);
        }
        theLoaiSachController tlsc = new theLoaiSachController();
        for (TheLoaiSach s : lists) {
            Object[] row = new Object[]{
                s.getMaTheLoai(),
                s.getTenTheLoai(),
                s.getViTri()  
            };
            model.addRow(row);
        }
    }     
         
         
        private void DSloadSach() {
        tblSach.setModel(new DefaultTableModel(null, new String[]{"Book ID", "Book Title", "Genre", "Author", "Quantity", "Publisher", "Date of Entry", "Description"}));
        ArrayList<Sach> list = new SachController().danhSachTatCaSach();
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        Object rowData[] = new Object[9];
        theLoaiSachController lsc = new theLoaiSachController();
        for (Sach item : list) {
            rowData[0] = item.getMaSach();
            rowData[1] = item.getTenSach();
            rowData[2] = lsc.layTenTheoMa(item.getMaTheLoai());
            rowData[3] = item.getTacGia();
            rowData[4] = item.getSoLuong();
            rowData[5] = item.getMaNXB();
            rowData[6] = item.getNgay();
            rowData[7] = item.getNoiDung();
            model.addRow(rowData);
        }

    }
        
        public void SachTim() {
        SachController scl = new SachController();
        ArrayList<Sach> lists = new ArrayList<>();

        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);
        ArrayList<Sach> listsTheoMa = scl.SearchTen(txtSearchS.getText());
        for (Sach sach : listsTheoMa) {
            lists.add(sach);
        }
        theLoaiSachController tlsc = new theLoaiSachController();
        for (Sach s : lists) {
            Object[] row = new Object[]{
                s.getMaSach(),
                s.getTenSach(),
                tlsc.layTenTheoMa(s.getMaTheLoai()),
                s.getTacGia(),
                s.getSoLuong(),
                s.getMaNXB(),
                s.getNgay(),
                s.getNoiDung()              
            };
            model.addRow(row);
        }
    } 
    
        
     public void searchPhieuMuon(){
//        ArrayList<PhieuMuon> listpm = new ArrayList<>();
        PhieuMuonController pmc = new PhieuMuonController();
        DefaultTableModel model = (DefaultTableModel) tblPhieuMuon.getModel();
        model.setRowCount(0);
//        LopController lopController = new LopController();
        ArrayList<PhieuMuon> listsvTheoMa = pmc.SearchMaSVMaPhieu(txtPhieuMuon.getText());
//        for (PhieuMuon i : listsvTheoMa) {
//            listpm.add(i);
//        }
        for (PhieuMuon pm : listsvTheoMa) {
//            String tenLop = pmc.layTenTheoMaLop(sv.getMaLop());
                Object[] row = new Object[]{
                pm.getMaPhieuMuon(),
//                pm.getMaSinhVien(),
                pm.getMaSach(),
                pm.getSoLuong(),
                pm.getNgayMuon(),
                pm.getNgayHetHan(),
            };
            model.addRow(row);
        }
    }
     
     
        public void loadViPhamTheoTen() {
        ViPhamController vpc = new ViPhamController();
//        ArrayList<ViPham> listvp = vpc.SearchTen(txtSearch.getText());
        ArrayList<ViPham> listvp = new ArrayList<>();

        DefaultTableModel model = (DefaultTableModel) tblViPham.getModel();
        model.setRowCount(0);
//        ViPhamController vpController = new ViPhamController();
        ArrayList<ViPham> listvpTheoMa = vpc.SearchTen(txtViPham.getText());
        for (ViPham vipham : listvpTheoMa) {
            listvp.add(vipham);
//            System.out.println(vipham.getMaViPham());
        }
        for (ViPham vp : listvp) {
            Object[] row = new Object[]{
                vp.getMaViPham(),
//                vp.getMaSinhVien(),
                vp.getTen(),
                vp.getPhat()
            };
            model.addRow(row);
        }
    }
    
        
        
        
       /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        pnlForm = new javax.swing.JPanel();
        lblWelcome = new javax.swing.JLabel();
        btnDangXuat = new javax.swing.JButton();
        pnl1 = new javax.swing.JPanel();
        tab = new javax.swing.JTabbedPane();
        tabSV = new javax.swing.JTabbedPane();
        pnl2 = new javax.swing.JPanel();
        lblTitleSV = new javax.swing.JLabel();
        lblMaSV = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblHoTen = new javax.swing.JLabel();
        lblNgaySinh = new javax.swing.JLabel();
        lblGioiTinh = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblDiaChi = new javax.swing.JLabel();
        lblSDT = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtPass = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        btnCapNhat = new javax.swing.JButton();
        dateNgaySinh = new com.toedter.calendar.JDateChooser();
        lblPassword1 = new javax.swing.JLabel();
        lblHoTen1 = new javax.swing.JLabel();
        lblDiaChi1 = new javax.swing.JLabel();
        lblSDT1 = new javax.swing.JLabel();
        lblEmail1 = new javax.swing.JLabel();
        txtLop = new javax.swing.JTextField();
        lblMaSV1 = new javax.swing.JLabel();
        txtMaSV = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        tabSach = new javax.swing.JTabbedPane();
        pnl3 = new javax.swing.JPanel();
        lblTitleSach = new javax.swing.JLabel();
        pnl4 = new javax.swing.JPanel();
        txtSearchS = new javax.swing.JTextField();
        lblIconSearch = new javax.swing.JLabel();
        timkiemsach = new javax.swing.JLabel();
        pnl5 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        pnl9 = new javax.swing.JPanel();
        lblTitleTLS = new javax.swing.JLabel();
        pnl10 = new javax.swing.JPanel();
        txtSearchTLS = new javax.swing.JTextField();
        lblIconSearchTLS = new javax.swing.JLabel();
        timkiemtheloai = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTheLoaiSach = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        tabVP = new javax.swing.JTabbedPane();
        pnl12 = new javax.swing.JPanel();
        lblTitlePM1 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblViPham = new javax.swing.JTable();
        lblDanhSachPM1 = new javax.swing.JLabel();
        lblMaSach2 = new javax.swing.JLabel();
        lblSoLuong2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtViPham = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        timkiem1 = new javax.swing.JLabel();
        tabPM = new javax.swing.JTabbedPane();
        pnl11 = new javax.swing.JPanel();
        lblTitlePM = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblPhieuMuon = new javax.swing.JTable();
        lblDanhSachPM = new javax.swing.JLabel();
        lblMaSach1 = new javax.swing.JLabel();
        lblSoLuong1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        timkiem = new javax.swing.JLabel();
        txtPhieuMuon = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlForm.setBackground(new java.awt.Color(1, 128, 129));
        pnlForm.setForeground(new java.awt.Color(0, 51, 255));
        pnlForm.setMinimumSize(new java.awt.Dimension(1000, 600));

        lblWelcome.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(255, 255, 255));
        lblWelcome.setText("Welcome: ");

        btnDangXuat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logout.png"))); // NOI18N
        btnDangXuat.setText("Logout");
        btnDangXuat.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        pnl1.setBackground(new java.awt.Color(1, 128, 129));

        tab.setBackground(new java.awt.Color(255, 255, 255));
        tab.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        tab.setToolTipText("");
        tab.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tab.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        tabSV.setBackground(new java.awt.Color(255, 255, 255));
        tabSV.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabSV.setPreferredSize(new java.awt.Dimension(890, 420));

        pnl2.setBackground(new java.awt.Color(255, 255, 255));

        lblTitleSV.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblTitleSV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/member.png"))); // NOI18N
        lblTitleSV.setText("Student Information");
        lblTitleSV.setToolTipText("");

        lblMaSV.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblMaSV.setText("Student code:");

        lblPassword.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblPassword.setText("Password:");

        lblHoTen.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblHoTen.setText("Fullname");

        lblNgaySinh.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblNgaySinh.setText("Date of birth");

        lblGioiTinh.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblGioiTinh.setText("Gender");

        lblEmail.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEmail.setText("Email:");

        lblDiaChi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblDiaChi.setText("Address:");

        lblSDT.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblSDT.setText("Phone:");

        txtEmail.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtPass.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtDiaChi.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtSDT.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        rdoNam.setText("Male");

        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        rdoNu.setText("Female");

        btnCapNhat.setBackground(new java.awt.Color(1, 128, 129));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/update.png"))); // NOI18N
        btnCapNhat.setText("Update");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        dateNgaySinh.setDateFormatString("dd-MM-yyyy");

        lblPassword1.setForeground(new java.awt.Color(255, 0, 0));

        lblHoTen1.setForeground(new java.awt.Color(255, 0, 0));

        lblDiaChi1.setForeground(new java.awt.Color(255, 0, 0));

        lblSDT1.setForeground(new java.awt.Color(255, 0, 0));

        lblEmail1.setForeground(new java.awt.Color(255, 0, 0));

        txtLop.setEditable(false);
        txtLop.setBackground(new java.awt.Color(255, 255, 255));
        txtLop.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lblMaSV1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblMaSV1.setText("Class:");

        txtMaSV.setEditable(false);
        txtMaSV.setBackground(new java.awt.Color(255, 255, 255));
        txtMaSV.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtMaSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSVActionPerformed(evt);
            }
        });

        txtHoTen.setEditable(false);
        txtHoTen.setBackground(new java.awt.Color(255, 255, 255));
        txtHoTen.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        javax.swing.GroupLayout pnl2Layout = new javax.swing.GroupLayout(pnl2);
        pnl2.setLayout(pnl2Layout);
        pnl2Layout.setHorizontalGroup(
            pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl2Layout.createSequentialGroup()
                        .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl2Layout.createSequentialGroup()
                                .addComponent(lblNgaySinh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl2Layout.createSequentialGroup()
                                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPassword)
                                    .addComponent(lblMaSV, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblHoTen))
                                .addGap(18, 18, 18)
                                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHoTen1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(lblEmail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblEmail1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtEmail)))
                            .addGroup(pnl2Layout.createSequentialGroup()
                                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnl2Layout.createSequentialGroup()
                                        .addComponent(lblDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl2Layout.createSequentialGroup()
                                        .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblMaSV1, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblSDT, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addGap(18, 18, 18)))
                                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtLop, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDiaChi1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSDT1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(146, 146, 146))
                    .addGroup(pnl2Layout.createSequentialGroup()
                        .addComponent(lblGioiTinh)
                        .addGap(18, 18, 18)
                        .addComponent(rdoNam)
                        .addGap(18, 18, 18)
                        .addComponent(rdoNu)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(pnl2Layout.createSequentialGroup()
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl2Layout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(lblTitleSV, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl2Layout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtDiaChi, txtEmail, txtPass, txtSDT});

        pnl2Layout.setVerticalGroup(
            pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblTitleSV, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMaSV1)
                        .addComponent(txtLop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMaSV)
                        .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPassword)
                        .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDiaChi))
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiaChi1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSDT))
                    .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblHoTen)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHoTen1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSDT1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl2Layout.createSequentialGroup()
                        .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNgaySinh))
                        .addGap(18, 18, 18)
                        .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblGioiTinh)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu)))
                    .addGroup(pnl2Layout.createSequentialGroup()
                        .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmail))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(67, 67, 67)
                .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabSV.addTab("", pnl2);

        tab.addTab("Update information", null, tabSV, "");

        tabSach.setBackground(new java.awt.Color(255, 255, 255));
        tabSach.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabSach.setPreferredSize(new java.awt.Dimension(890, 420));

        pnl3.setBackground(new java.awt.Color(255, 255, 255));
        pnl3.setPreferredSize(new java.awt.Dimension(890, 420));

        lblTitleSach.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblTitleSach.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleSach.setText("Book");

        pnl4.setBackground(new java.awt.Color(255, 255, 255));
        pnl4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search by Book Code, Book Name, Genre Code", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N
        pnl4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSearchS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pnl4.add(txtSearchS, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 20, 580, 30));
        pnl4.add(lblIconSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 19, -1, 30));

        timkiemsach.setBackground(new java.awt.Color(204, 204, 255));
        timkiemsach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        timkiemsach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                timkiemsachMouseClicked(evt);
            }
        });
        pnl4.add(timkiemsach, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, -1, -1));

        pnl5.setBackground(new java.awt.Color(197, 197, 197));
        pnl5.setLayout(new java.awt.CardLayout());

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sách", "Tên Sách", "Mã Thể Loại", "Tác Giả", "Số Lượng", "NXB", "Ngày Nhập", "NDTT", "Hình"
            }
        ));
        tblSach.setRowHeight(30);
        jScrollPane6.setViewportView(tblSach);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("List Book");

        javax.swing.GroupLayout pnl3Layout = new javax.swing.GroupLayout(pnl3);
        pnl3.setLayout(pnl3Layout);
        pnl3Layout.setHorizontalGroup(
            pnl3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl3Layout.createSequentialGroup()
                .addGap(306, 306, 306)
                .addComponent(jLabel3)
                .addContainerGap(423, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitleSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6)
                    .addComponent(pnl4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl3Layout.setVerticalGroup(
            pnl3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitleSach, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnl5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(532, 532, 532))
        );

        pnl4.getAccessibleContext().setAccessibleName("Tìm Kiếm theo Mã Sách , Tên Sách, Mã TL");

        tabSach.addTab("books", pnl3);

        pnl9.setBackground(new java.awt.Color(255, 255, 255));
        pnl9.setMaximumSize(new java.awt.Dimension(3274, 3276));

        lblTitleTLS.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblTitleTLS.setText("BOOK CATEGORY");

        pnl10.setBackground(new java.awt.Color(255, 255, 255));
        pnl10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search by all attributes", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        txtSearchTLS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblIconSearchTLS.setBackground(new java.awt.Color(255, 255, 255));

        timkiemtheloai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        timkiemtheloai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                timkiemtheloaiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnl10Layout = new javax.swing.GroupLayout(pnl10);
        pnl10.setLayout(pnl10Layout);
        pnl10Layout.setHorizontalGroup(
            pnl10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearchTLS, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(timkiemtheloai)
                .addGap(131, 131, 131)
                .addComponent(lblIconSearchTLS, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        pnl10Layout.setVerticalGroup(
            pnl10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl10Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(pnl10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(timkiemtheloai)
                    .addComponent(txtSearchTLS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIconSearchTLS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblTheLoaiSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Thể Loại", "Tên Thể Loại Sách", "Vị Trí"
            }
        ));
        tblTheLoaiSach.setRowHeight(30);
        jScrollPane2.setViewportView(tblTheLoaiSach);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("LIST CATEGORIES");

        javax.swing.GroupLayout pnl9Layout = new javax.swing.GroupLayout(pnl9);
        pnl9.setLayout(pnl9Layout);
        pnl9Layout.setHorizontalGroup(
            pnl9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnl9Layout.createSequentialGroup()
                .addGroup(pnl9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(pnl9Layout.createSequentialGroup()
                        .addGap(307, 307, 307)
                        .addComponent(lblTitleTLS)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pnl9Layout.createSequentialGroup()
                .addGap(302, 302, 302)
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl9Layout.setVerticalGroup(
            pnl9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl9Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lblTitleTLS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(184, Short.MAX_VALUE))
        );

        tabSach.addTab("category", pnl9);

        tab.addTab("Books", null, tabSach, "");

        tabVP.setBackground(new java.awt.Color(255, 255, 255));
        tabVP.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabVP.setPreferredSize(new java.awt.Dimension(890, 420));

        pnl12.setBackground(new java.awt.Color(255, 255, 255));

        lblTitlePM1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblTitlePM1.setText("VIOLATION TICKET");

        tblViPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã Vi Phạm", "Lỗi Vi Phạm", "Phạt"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblViPham.setRowHeight(30);
        jScrollPane5.setViewportView(tblViPham);

        lblDanhSachPM1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDanhSachPM1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDanhSachPM1.setText("list of violations");

        lblMaSach2.setForeground(new java.awt.Color(255, 0, 0));

        lblSoLuong2.setForeground(new java.awt.Color(255, 0, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(0, 0, 153));

        txtViPham.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtViPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtViPhamActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Search by ticket code");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(166, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(txtViPham)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(txtViPham, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jPanel3.setBackground(new java.awt.Color(24, 133, 127));
        jPanel3.setForeground(new java.awt.Color(255, 204, 204));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("FINE");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Rách sách, vẽ bậy: 20.000 (vnđ)");
        jLabel7.setToolTipText("");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Mất trang, hỏng bìa: 30.000 (vnđ)");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Mất sách: Giá của sách ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel6))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(0, 23, Short.MAX_VALUE))
        );

        timkiem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        timkiem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                timkiem1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnl12Layout = new javax.swing.GroupLayout(pnl12);
        pnl12.setLayout(pnl12Layout);
        pnl12Layout.setHorizontalGroup(
            pnl12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl12Layout.createSequentialGroup()
                .addGroup(pnl12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl12Layout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(lblTitlePM1))
                    .addGroup(pnl12Layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(lblDanhSachPM1, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnl12Layout.createSequentialGroup()
                .addGroup(pnl12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 801, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSoLuong2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl12Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timkiem1)
                        .addGap(177, 177, 177)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(lblMaSach2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl12Layout.setVerticalGroup(
            pnl12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl12Layout.createSequentialGroup()
                .addGroup(pnl12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnl12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl12Layout.createSequentialGroup()
                            .addGap(138, 138, 138)
                            .addComponent(lblMaSach2))
                        .addGroup(pnl12Layout.createSequentialGroup()
                            .addGap(61, 61, 61)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl12Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(lblTitlePM1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnl12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl12Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl12Layout.createSequentialGroup()
                                .addComponent(timkiem1)
                                .addGap(18, 18, 18)))))
                .addGap(26, 26, 26)
                .addComponent(lblDanhSachPM1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(pnl12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSoLuong2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(115, Short.MAX_VALUE))
        );

        tabVP.addTab("violation tickets", pnl12);

        tab.addTab("violation tickets", null, tabVP, "");

        tabPM.setBackground(new java.awt.Color(255, 255, 255));
        tabPM.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabPM.setPreferredSize(new java.awt.Dimension(890, 420));

        pnl11.setBackground(new java.awt.Color(255, 255, 255));

        lblTitlePM.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblTitlePM.setText("BORROW TICKET");

        tblPhieuMuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã Phiếu Mượn", "Số Lượng Mượn", "Ngày Mượn", "Ngày Hẹn Trả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhieuMuon.setRowHeight(30);
        jScrollPane4.setViewportView(tblPhieuMuon);

        lblDanhSachPM.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDanhSachPM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDanhSachPM.setText("list of borrow tickets");

        lblMaSach1.setForeground(new java.awt.Color(255, 0, 0));

        lblSoLuong1.setForeground(new java.awt.Color(255, 0, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(0, 0, 153));

        timkiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        timkiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                timkiemMouseClicked(evt);
            }
        });

        txtPhieuMuon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Search by code");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(timkiem))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timkiem))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout pnl11Layout = new javax.swing.GroupLayout(pnl11);
        pnl11.setLayout(pnl11Layout);
        pnl11Layout.setHorizontalGroup(
            pnl11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl11Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(lblMaSach1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl11Layout.createSequentialGroup()
                        .addGroup(pnl11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl11Layout.createSequentialGroup()
                                .addGap(128, 128, 128)
                                .addComponent(lblDanhSachPM, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnl11Layout.createSequentialGroup()
                .addGap(285, 285, 285)
                .addComponent(lblTitlePM)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl11Layout.setVerticalGroup(
            pnl11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl11Layout.createSequentialGroup()
                .addGroup(pnl11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnl11Layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(lblMaSach1)
                        .addGap(22, 22, 22))
                    .addGroup(pnl11Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lblTitlePM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(pnl11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(505, 505, 505))
                    .addGroup(pnl11Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblDanhSachPM, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel1.getAccessibleContext().setAccessibleName("Tìm kiếm theo mã phiếu\n");
        jPanel1.getAccessibleContext().setAccessibleDescription("");

        tabPM.addTab("Borrow tickets", pnl11);

        tab.addTab("Borrow tickets", null, tabPM, "");

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnl1Layout = new javax.swing.GroupLayout(pnl1);
        pnl1.setLayout(pnl1Layout);
        pnl1Layout.setHorizontalGroup(
            pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl1Layout.createSequentialGroup()
                .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 961, Short.MAX_VALUE)
                .addGap(42, 42, 42)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator1)
        );
        pnl1Layout.setVerticalGroup(
            pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl1Layout.createSequentialGroup()
                .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl1Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(lblWelcome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addComponent(pnl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblWelcome, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDangXuat, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(pnl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, 971, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        txtMaSV.setEditable(false);
        txtLop.setEditable(false);
        txtHoTen.setEditable(false);
        dateNgaySinh.setEnabled(false);
    }//GEN-LAST:event_formWindowOpened

    private void timkiemsachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timkiemsachMouseClicked
        // TODO add your handling code here:
        SachTim();
       
    }//GEN-LAST:event_timkiemsachMouseClicked

    private void timkiemtheloaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timkiemtheloaiMouseClicked
        // TODO add your handling code here:
        timkiemtheloai();
    }//GEN-LAST:event_timkiemtheloaiMouseClicked

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        updatesv();
        loadSinhVien();
        //        if (valiformSV() == true) {
            //
            //
            //        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void timkiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timkiemMouseClicked
        // TODO add your handling code here:
        searchPhieuMuon();
    }//GEN-LAST:event_timkiemMouseClicked

    private void timkiem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timkiem1MouseClicked
        loadViPhamTheoTen();
    }//GEN-LAST:event_timkiem1MouseClicked

    private void txtMaSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSVActionPerformed

    private void txtViPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtViPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtViPhamActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jFSinhVien1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jFSinhVien1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jFSinhVien1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jFSinhVien1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jFSinhVien1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private com.toedter.calendar.JDateChooser dateNgaySinh;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblDanhSachPM;
    private javax.swing.JLabel lblDanhSachPM1;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblDiaChi1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEmail1;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblHoTen1;
    private javax.swing.JLabel lblIconSearch;
    private javax.swing.JLabel lblIconSearchTLS;
    private javax.swing.JLabel lblMaSV;
    private javax.swing.JLabel lblMaSV1;
    private javax.swing.JLabel lblMaSach1;
    private javax.swing.JLabel lblMaSach2;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPassword1;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblSDT1;
    private javax.swing.JLabel lblSoLuong1;
    private javax.swing.JLabel lblSoLuong2;
    private javax.swing.JLabel lblTitlePM;
    private javax.swing.JLabel lblTitlePM1;
    private javax.swing.JLabel lblTitleSV;
    private javax.swing.JLabel lblTitleSach;
    private javax.swing.JLabel lblTitleTLS;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel pnl1;
    private javax.swing.JPanel pnl10;
    private javax.swing.JPanel pnl11;
    private javax.swing.JPanel pnl12;
    private javax.swing.JPanel pnl2;
    private javax.swing.JPanel pnl3;
    private javax.swing.JPanel pnl4;
    private javax.swing.JPanel pnl5;
    private javax.swing.JPanel pnl9;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTabbedPane tabPM;
    private javax.swing.JTabbedPane tabSV;
    private javax.swing.JTabbedPane tabSach;
    private javax.swing.JTabbedPane tabVP;
    private javax.swing.JTable tblPhieuMuon;
    private javax.swing.JTable tblSach;
    private javax.swing.JTable tblTheLoaiSach;
    private javax.swing.JTable tblViPham;
    private javax.swing.JLabel timkiem;
    private javax.swing.JLabel timkiem1;
    private javax.swing.JLabel timkiemsach;
    private javax.swing.JLabel timkiemtheloai;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtLop;
    private javax.swing.JTextField txtMaSV;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtPhieuMuon;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSearchS;
    private javax.swing.JTextField txtSearchTLS;
    private javax.swing.JTextField txtViPham;
    // End of variables declaration//GEN-END:variables
}
