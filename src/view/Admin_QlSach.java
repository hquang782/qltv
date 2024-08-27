/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

//import controller.LopController;
import controller.SachController;
import controller.theLoaiSachController;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Sach;

/**
 *
 * @author OS
 */
public class Admin_QlSach extends javax.swing.JInternalFrame {

    /**
     * Creates new form Admin_QlSach
     */
    public Admin_QlSach() {
        initComponents();
        loadSach();
        loadComboBoxQlSach();
        setStatus(true);

    }
    
    private void loadComboBoxQlSach() {
        try {
            String sql = "Select * from theloaisach";

            Connection conn = util.ConnectDB.connectSQLServer();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            SachController sachController = new SachController();
            while (rs.next()) {
                cbbTheLoai.addItem(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Admin_QlSach.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertSach() {
        Sach s = new Sach();
        s.setMaSach(txtMaSach.getText());
        s.setTenSach(txtTenSach.getText());
        s.setMaNXB(txtNXB.getText());
        theLoaiSachController sachController = new theLoaiSachController();
        s.setMaTheLoai(sachController.layMaTheoTen(cbbTheLoai.getSelectedItem().toString()));
        s.setTacGia(txtTacGia.getText());
        s.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        System.out.println(s.getSoLuong());
        Date date = txtdate.getDate();
        String df = new SimpleDateFormat("yyyy-MM-dd").format(date);
        s.setNgay(df);
        s.setNoiDung(txtNoiDung.getText());
        SachController scl = new SachController();
        if (scl.insert(s) != 0) {
            JOptionPane.showMessageDialog(null, "Thêm Sách thành công");
        } else {
            JOptionPane.showMessageDialog(null, "Mã Sách [ " + txtMaSach.getText() + " ] đã tồn tại không thể thêm");
        }
    }
    

    public void updateSach() {
        Sach s = new Sach();
        s.setMaSach(txtMaSach.getText());
        s.setTenSach(txtTenSach.getText());
        s.setMaNXB(txtNXB.getText());
        theLoaiSachController sc = new theLoaiSachController();
        String tLoai = sc.layMaTheoTen((String) cbbTheLoai.getSelectedItem());
        s.setMaTheLoai(tLoai);
        s.setTacGia(txtTacGia.getText());
        s.setSoLuong(Integer.parseInt(txtSoLuong.getText()));

        Date date = txtdate.getDate();
        String df = new SimpleDateFormat("yyyy-MM-dd").format(date);
        s.setNgay(df);
        s.setNoiDung(txtNoiDung.getText());
        SachController sachc = new SachController();
        if (sachc.update(s) > 0) {
            JOptionPane.showMessageDialog(null, "cập nhật thành công");
            clearFormInput();
            tblQuanLySach.setSelectionMode(0);
        } else {
            JOptionPane.showMessageDialog(null, "cập nhật thất bại");
        }

    }

    private void loadSach() {
        tblQuanLySach.setModel(new DefaultTableModel(null, new String[]{"Mã sách", "Tên sách", "TL sách", "Tác giả", "Số lượng", "NXB", "Ngày nhập", "ND"}));
        ArrayList<Sach> list = new SachController().danhSachTatCaSach();
        DefaultTableModel model = (DefaultTableModel) tblQuanLySach.getModel();
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

    public void deleteSach() {
        int index = tblQuanLySach.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sách trong bảng để xóa", "Thông Báo", 1);
            return;
        }
        SachController scl = new SachController();

        int tk = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
        if (tk == JOptionPane.YES_OPTION) {
            if (scl.delete(txtMaSach.getText()) != 0) {
                JOptionPane.showMessageDialog(this, "Xóa Sách thành công", "Thông Báo", 1);

            } else {
                JOptionPane.showMessageDialog(this, "Lỗi hệ thống", "Thông Báo", 0);
            }

        } else {
            return;

        }
    }
    
    public void loadSachTimTheoTen() {
        SachController scl = new SachController();
        ArrayList<Sach> lists = new ArrayList<>();

        DefaultTableModel model = (DefaultTableModel) tblQuanLySach.getModel();
        model.setRowCount(0);
        ArrayList<Sach> listsTheoMa = scl.SearchTen(txtSearch.getText());
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
    

    public boolean valiform() {
        if (txtMaSach.getText().equals("")) {
            txtMaSach.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa nhập Mã Sách");
            return false;
        } else if (txtTenSach.getText().equals("")) {
            txtTenSach.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa nhập Tên Sách");
            return false;
        } else if (!(txtTenSach.getText().matches("\\D*"))) {
            txtTenSach.requestFocus();
            JOptionPane.showMessageDialog(this, "Tên sách phải là chữ");
            return false;
        } else if (!(txtTacGia.getText().matches("\\D*"))) {
            txtTacGia.requestFocus();
            JOptionPane.showMessageDialog(this, "Tên tác giả phải là chữ");
            return false;
        } else if (txtNoiDung.getText().equals("")) {
            txtNoiDung.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa nhập nội dung");
            return false;
        } else if (txtSoLuong.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập số lượng");
            txtSoLuong.requestFocus();
            return false;
        } else if (txtSoLuong.getText().equals(0)) {
            txtSoLuong.requestFocus();
            JOptionPane.showMessageDialog(this, "Số lượng mượn ít nhất là 1 cuốn ");
            return false;
        } else {
            return true;
        }
    }

    public void clearFormInput() {
        txtMaSach.setText(null);
        txtTenSach.setText(null);
        txtNXB.setText(null);
        cbbTheLoai.setSelectedIndex(0);
        txtTacGia.setText(null);
        txtSoLuong.setText(null);
        txtNoiDung.setText(null);
        setStatus(true);
    }

    public void setEditForm(Boolean b) {
        txtMaSach.setEditable(b);
        txtTenSach.setEditable(b);
        txtNXB.setEditable(b);
        txtdate.setEnabled(b);
        cbbTheLoai.setEnabled(b);
        txtTacGia.setEnabled(b);
        txtSoLuong.setEditable(b);
        txtNoiDung.setEditable(b);
    }
    
    public void displayFormInput() {
        SachController sach = new SachController();
        ArrayList<Sach> danhsachtatcasach = sach.danhSachTatCaSach();
        int selectedRow = tblQuanLySach.getSelectedRow();
        Sach s = danhsachtatcasach.get(selectedRow);
        txtMaSach.setText(s.getMaSach());
        txtTenSach.setText(s.getTenSach());
        txtNXB.setText(s.getMaNXB());

        theLoaiSachController sachController = new theLoaiSachController();
        cbbTheLoai.setSelectedItem(sachController.layTenTheoMa(s.getMaTheLoai()));
        txtTacGia.setText(s.getTacGia());
        txtSoLuong.setText(Integer.toString(s.getSoLuong()));

        try {
            DefaultTableModel model = (DefaultTableModel) tblQuanLySach.getModel();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String) model.getValueAt(selectedRow, 6));
            txtdate.setDate(date);
            
        } catch (ParseException ex) {
        }
        txtNoiDung.setText(s.getNoiDung());


    }

    public void setStatus(boolean insertable) {
        txtMaSach.setEditable(insertable);
        btnLuu.setEnabled(insertable);
        btnSua.setEnabled(insertable);
        btnXoa.setEnabled(insertable);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        pnlThongTinSach = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        txtTenSach = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtNoiDung = new javax.swing.JTextField();
        txtdate = new com.toedter.calendar.JDateChooser();
        txtNXB = new javax.swing.JTextField();
        txtTacGia = new javax.swing.JTextField();
        cbbTheLoai = new javax.swing.JComboBox<>();
        lblTitle = new javax.swing.JLabel();
        pnlSearchSach = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JLabel();
        ScrollPaneQLSach = new javax.swing.JScrollPane();
        tblQuanLySach = new javax.swing.JTable();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnTaoMoi = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setTitle("Quản Lý Sách");
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setMaximumSize(new java.awt.Dimension(1200, 750));
        setMinimumSize(new java.awt.Dimension(1200, 750));
        setName(""); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        pnlThongTinSach.setBackground(new java.awt.Color(255, 255, 255));
        pnlThongTinSach.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel1.setText("Mã Sách :");

        jLabel2.setText("Tên Sách :");

        jLabel3.setText("Thể Loại :");

        jLabel4.setText("NXB :");

        jLabel5.setText("Tác Giả :");

        jLabel6.setText("Số lượng :");

        jLabel7.setText("Ngày Nhập :");

        jLabel8.setText("Nội Dung :");

        txtNoiDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoiDungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinSachLayout = new javax.swing.GroupLayout(pnlThongTinSach);
        pnlThongTinSach.setLayout(pnlThongTinSachLayout);
        pnlThongTinSachLayout.setHorizontalGroup(
            pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaSach))
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenSach))
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(cbbTheLoai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtdate, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtNoiDung))
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtSoLuong))
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtTacGia))
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtNXB)))
                .addContainerGap())
        );

        pnlThongTinSachLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8});

        pnlThongTinSachLayout.setVerticalGroup(
            pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbbTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtNoiDung, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlThongTinSachLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtMaSach, txtSoLuong, txtTenSach});

        lblTitle.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblTitle.setText("Quản Lý Sách");

        pnlSearchSach.setBackground(new java.awt.Color(255, 255, 255));
        pnlSearchSach.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm Kiếm Theo Mã hoặc Tên"));

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlSearchSachLayout = new javax.swing.GroupLayout(pnlSearchSach);
        pnlSearchSach.setLayout(pnlSearchSachLayout);
        pnlSearchSachLayout.setHorizontalGroup(
            pnlSearchSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSearchSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );
        pnlSearchSachLayout.setVerticalGroup(
            pnlSearchSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSearchSachLayout.createSequentialGroup()
                .addGroup(pnlSearchSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSearchSachLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tblQuanLySach.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblQuanLySach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sách", "Tên Sách", "Mã Thể Loại", "Tác Giả", "Số Lượng", "Mã Nhà Xuất Bản", "Ngày xuất bản", "Nội Dung"
            }
        ));
        tblQuanLySach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQuanLySachMouseClicked(evt);
            }
        });
        ScrollPaneQLSach.setViewportView(tblQuanLySach);

        btnXoa.setBackground(new java.awt.Color(24, 133, 127));
        btnXoa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLuu.setBackground(new java.awt.Color(24, 133, 127));
        btnLuu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(255, 255, 255));
        btnLuu.setText("Lưu");
        btnLuu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnTaoMoi.setBackground(new java.awt.Color(24, 133, 127));
        btnTaoMoi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnTaoMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoMoi.setText("Tạo Mới");
        btnTaoMoi.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTaoMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoMoiActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(24, 133, 127));
        btnSua.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTaoMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ScrollPaneQLSach)
                        .addGap(33, 33, 33))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlSearchSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlThongTinSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(450, 450, 450)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblTitle)
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnlThongTinSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(pnlSearchSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(ScrollPaneQLSach, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNoiDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoiDungActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoiDungActionPerformed

    private void btnTaoMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoMoiActionPerformed
        if (btnTaoMoi.getText().equals("Hủy")) {
            btnTaoMoi.setText("Tạo Mới");
        }
        
        tblQuanLySach.setSelectionMode(0);
        clearFormInput();
        setEditForm(true);
    }//GEN-LAST:event_btnTaoMoiActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        if (btnTaoMoi.getText().equals("Tạo Mới")) {
            // TAO MOI
            if (valiform() == true) {
                insertSach();
            }
        } else {
            if (valiform() == true) {
                updateSach();
            }
        }
        loadSach();
    }//GEN-LAST:event_btnLuuActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed


    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deleteSach();
        clearFormInput();
        loadSach();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        btnTaoMoi.setText("Hủy");
        btnXoa.setEnabled(false);
        btnLuu.setEnabled(true);
        btnSua.setEnabled(false);
        setEditForm(true);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void tblQuanLySachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQuanLySachMouseClicked
        // TODO add your handling code here:
        displayFormInput();
        setStatus(true);
        setEditForm(false);
    }//GEN-LAST:event_tblQuanLySachMouseClicked

    private void btnSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseClicked
        // TODO add your handling code here:
        loadSachTimTheoTen();
    }//GEN-LAST:event_btnSearchMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPaneQLSach;
    private javax.swing.JButton btnLuu;
    private javax.swing.JLabel btnSearch;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTaoMoi;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbbTheLoai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlSearchSach;
    private javax.swing.JPanel pnlThongTinSach;
    private javax.swing.JTable tblQuanLySach;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtNXB;
    private javax.swing.JTextField txtNoiDung;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTacGia;
    private javax.swing.JTextField txtTenSach;
    private com.toedter.calendar.JDateChooser txtdate;
    // End of variables declaration//GEN-END:variables
}
