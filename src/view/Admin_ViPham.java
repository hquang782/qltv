/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ViPhamController;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ViPham;

/**
 *
 * @author OS
 */
public class Admin_ViPham extends javax.swing.JInternalFrame {

    /**
     * Creates new form Admin_ViPham
     */
    public Admin_ViPham() {
        initComponents();
        loadViPham();
        setStatus(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void loadViPham() {
        tblBang.setModel(new DefaultTableModel(null, new String[]{"Mã Vi Phạm", "Mã SV", "Lỗi Vi Phạm", "Phạt"}));
        ArrayList<ViPham> list = new ViPhamController().danhSachTatCaViPham();
        DefaultTableModel model = (DefaultTableModel) tblBang.getModel();
        Object rowData[] = new Object[4];
        ViPhamController vpController = new ViPhamController();
        for (ViPham item : list) {
             rowData[0] = item.getMaViPham();
             rowData[1] = item.getMaSinhVien();
             rowData[2] = item.getTen();
             rowData[3] = item.getPhat();
             model.addRow(rowData);
        }
    }
    private void insertViPham(){
        ViPham vp = new ViPham();
        vp.setMaViPham(txtMaViPham.getText());
        vp.setMaSinhVien(txtMaSinhVien.getText());
        vp.setTen(txtTenLoiViPham.getText());
        vp.setPhat(textPhat.getText());
        
        
        ViPhamController vpc = new ViPhamController();
        if (vpc.insert(vp) != 0) {
            JOptionPane.showMessageDialog(null, "Thêm vi phạm thành công thành công");
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi thêm");
        }
    }
    
    
    private void SuaViPham(){
        ViPham vp = new ViPham();
        vp.setMaViPham(txtMaViPham.getText());
        vp.setMaSinhVien(txtMaSinhVien.getText());
        vp.setTen(txtTenLoiViPham.getText());
        vp.setPhat(textPhat.getText());
        
        
        ViPhamController vpc = new ViPhamController();
        if (vpc.update(vp) > 0) {
             JOptionPane.showMessageDialog(null, "cập nhật thành công");
            clearFormInput();
            tblBang.setSelectionMode(0);
        } else {
            JOptionPane.showMessageDialog(null, "cập nhật thất bại");
        }
        
    }
    
    public void deleteViPham() {
        int index = tblBang.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 Vi Phạm trong bảng để xóa", "Thông Báo", 1);
            return;
        }
        ViPhamController vpc = new ViPhamController();

        int tk = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
        if (tk == JOptionPane.YES_OPTION) {
            if (vpc.delete(txtMaViPham.getText()) != 0) {
                JOptionPane.showMessageDialog(this, "Xóa Vi Phạm thành công", "Thông Báo", 1);

            } else {
                JOptionPane.showMessageDialog(this, "Lỗi hệ thống", "Thông Báo", 0);
            }

        } else {
            return;

        }
    }
    
    public void loadViPhamTheoTen() {
        ViPhamController vpc = new ViPhamController();
//        ArrayList<ViPham> listvp = vpc.SearchTen(txtSearch.getText());
        ArrayList<ViPham> listvp = new ArrayList<>();

        DefaultTableModel model = (DefaultTableModel) tblBang.getModel();
        model.setRowCount(0);
//        ViPhamController vpController = new ViPhamController();
        ArrayList<ViPham> listvpTheoMa = vpc.SearchTen(txtSearch.getText());
        for (ViPham vipham : listvpTheoMa) {
            listvp.add(vipham);
//            System.out.println(vipham.getMaViPham());
        }
        for (ViPham vp : listvp) {
            Object[] row = new Object[]{
                vp.getMaViPham(),
                vp.getMaSinhVien(),
                vp.getTen(),
                vp.getPhat()
            };
            model.addRow(row);
        }
    }
    
    public void clearFormInput() {
        txtMaViPham.setText(null);
        txtMaSinhVien.setText(null);
        txtTenLoiViPham.setText(null);
        textPhat.setText(null);
    }
    
    public void setEditForm(Boolean b) {
        txtMaSinhVien.setEditable(b);
        txtMaViPham.setEditable(b);
        txtTenLoiViPham.setEditable(b);
        textPhat.setEditable(b);
    }
    
    public void displayFormInput() {
        ViPhamController vpController = new ViPhamController();
        ArrayList<ViPham> danhSachTatCavp = vpController.danhSachTatCaViPham();
        int selectedRow = tblBang.getSelectedRow();
        ViPham vp = danhSachTatCavp.get(selectedRow);
        txtMaViPham.setText(vp.getMaViPham());
        txtMaSinhVien.setText(vp.getMaSinhVien());
        txtTenLoiViPham.setText(vp.getTen());
        textPhat.setText(vp.getPhat());
    }
    
    public void setStatus(boolean insertable) {
//        btnThem.setEditable(insertable);
        btnSua.setEnabled(insertable);
        btnThem.setEnabled(insertable);
        btnTaoMoi.setEnabled(insertable);
        btnXoa.setEnabled(insertable);
    }
    
    public boolean valiform() {
        if (txtMaViPham.getText().equals("")) {
            txtMaViPham.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa nhập Mã Vi Phạm");
            return false;
        }
        else if (txtMaSinhVien.getText().equals("")) {
            txtMaSinhVien.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa nhập Mã Sinh Viên");
            return false;
        }
        else if (txtTenLoiViPham.getText().equals("")) {
            txtTenLoiViPham.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa nhập Tên Lỗi Vi Phạm");
            return false;
        } else if (textPhat.getText().equals("")) {
            textPhat.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa nhập Phạt");
            return false;
        }
        else{
            return true;
        }
          
       
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaViPham = new javax.swing.JTextField();
        txtMaSinhVien = new javax.swing.JTextField();
        lblMaViPham = new javax.swing.JLabel();
        lblMaSV = new javax.swing.JLabel();
        lblTenViPham = new javax.swing.JLabel();
        txtTenLoiViPham = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        textPhat = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        search = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBang = new javax.swing.JTable();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnTaoMoi = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(1000, 650));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Mã Vi Phạm");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Mã Sinh Viên");

        txtMaViPham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMaViPhamKeyReleased(evt);
            }
        });

        txtMaSinhVien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMaSinhVienKeyReleased(evt);
            }
        });

        lblMaViPham.setForeground(new java.awt.Color(255, 0, 0));

        lblMaSV.setForeground(new java.awt.Color(255, 0, 0));

        lblTenViPham.setForeground(new java.awt.Color(255, 0, 0));

        txtTenLoiViPham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenLoiViPhamKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Lỗi Vi Phạm");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Phạt:");

        textPhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textPhatActionPerformed(evt);
            }
        });
        textPhat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textPhatKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTenViPham, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaViPham, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaViPham, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                    .addComponent(txtMaSinhVien)
                    .addComponent(txtTenLoiViPham)
                    .addComponent(textPhat))
                .addGap(182, 182, 182))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaViPham, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(lblMaViPham, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenLoiViPham, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTenViPham, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(textPhat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm Mã Vi Phạm hoặc Mã Sinh Viên", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(search)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        tblBang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã Vi Phạm", "Mã Sinh Viên", "Lỗi Vi Phạm", "Phạt"
            }
        ));
        tblBang.setRowHeight(30);
        tblBang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBang);

        btnSua.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnThem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnTaoMoi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnTaoMoi.setText("Tạo Mới");
        btnTaoMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoMoiActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Quản Lý Vi Phạm");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnTaoMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(424, 424, 424)
                        .addComponent(jLabel1)))
                .addContainerGap(123, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(116, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1154, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtMaViPhamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaViPhamKeyReleased
        if(!txtMaViPham.getText().equals("")){
            lblMaViPham.setText(null);
        }
    }//GEN-LAST:event_txtMaViPhamKeyReleased

    private void txtMaSinhVienKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaSinhVienKeyReleased
        if(!txtMaSinhVien.getText().equals("")){
            lblMaSV.setText(null);
        }
    }//GEN-LAST:event_txtMaSinhVienKeyReleased

    private void txtTenLoiViPhamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenLoiViPhamKeyReleased
        // TODO add your handling code here:
        if(!txtTenLoiViPham.getText().equals("")){
            lblTenViPham.setText(null);
        }
    }//GEN-LAST:event_txtTenLoiViPhamKeyReleased

    private void tblBangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangMouseClicked
        displayFormInput();
        setStatus(true);
        setEditForm(true);
    }//GEN-LAST:event_tblBangMouseClicked

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
//        btnTaoMoi.setText("Hủy");
//        btnXoa.setEnabled(false);
//        btnThem.setEnabled(false);
//        btnLuu.setEnabled(true);
//        btnSua.setEnabled(false);
//        setEditForm(true);
          SuaViPham();
          loadViPham();
          clearFormInput();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deleteViPham();
        clearFormInput();
        loadViPham();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
       insertViPham();
       clearFormInput();
       loadViPham();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnTaoMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoMoiActionPerformed
       if (btnTaoMoi.getText().equals("Hủy")) {
            btnTaoMoi.setText("Tạo Mới");
        }
        clearFormInput();
        tblBang.setSelectionMode(0);
        setEditForm(true);
    }//GEN-LAST:event_btnTaoMoiActionPerformed

    private void searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchMouseClicked
            loadViPhamTheoTen();
    }//GEN-LAST:event_searchMouseClicked

    private void textPhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPhatActionPerformed

    }//GEN-LAST:event_textPhatActionPerformed

    private void textPhatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textPhatKeyReleased
           if(!textPhat.getText().equals("")){
            
        }
    }//GEN-LAST:event_textPhatKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTaoMoi;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMaSV;
    private javax.swing.JLabel lblMaViPham;
    private javax.swing.JLabel lblTenViPham;
    private javax.swing.JLabel search;
    private javax.swing.JTable tblBang;
    private javax.swing.JTextField textPhat;
    private javax.swing.JTextField txtMaSinhVien;
    private javax.swing.JTextField txtMaViPham;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTenLoiViPham;
    // End of variables declaration//GEN-END:variables
}
