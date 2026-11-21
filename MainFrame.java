import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements ActionListener {
    private JTextField txtNim, txtNama;
    private JComboBox<String> cmbJurusan;
    private JRadioButton rbLaki, rbPerempuan;
    private JButton btnTambah, btnUbah, btnHapus, btnReset;
    private JTable tabel;
    private DefaultTableModel modelTabel;
    private DataMahasiswa data = new DataMahasiswa();

    public MainFrame() {
        setTitle("Aplikasi Data Mahasiswa");
        setSize(650, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(buatPanelForm(), BorderLayout.NORTH);
        add(buatPanelTabel(), BorderLayout.CENTER);
        add(buatPanelTombol(), BorderLayout.SOUTH);

        btnTambah.addActionListener(this);
        btnUbah.addActionListener(this);
        btnHapus.addActionListener(this);
        btnReset.addActionListener(this);
        tabel.addMouseListener(new KlikTabel());
    }

    private JPanel buatPanelForm() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        txtNim = new JTextField();
        txtNama = new JTextField();
        cmbJurusan = new JComboBox<String>(new String[]{
            "Teknik Informatika", "Teknik Mesin", "Teknik Elektro", "Teknik Sipil"
        });

        rbLaki = new JRadioButton("Laki-laki", true);
        rbPerempuan = new JRadioButton("Perempuan");
        ButtonGroup grup = new ButtonGroup();
        grup.add(rbLaki);
        grup.add(rbPerempuan);

        JPanel panelJk = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelJk.add(rbLaki);
        panelJk.add(rbPerempuan);

        panel.add(new JLabel("NIM"));
        panel.add(txtNim);
        panel.add(new JLabel("Nama"));
        panel.add(txtNama);
        panel.add(new JLabel("Jurusan"));
        panel.add(cmbJurusan);
        panel.add(new JLabel("Jenis Kelamin"));
        panel.add(panelJk);

        return panel;
    }

    private JScrollPane buatPanelTabel() {
        modelTabel = new DefaultTableModel(
            new String[]{"NIM", "Nama", "Jurusan", "Jenis Kelamin"}, 0);
        tabel = new JTable(modelTabel);
        tabel.setDefaultEditor(Object.class, null);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        return scroll;
    }

    private JPanel buatPanelTombol() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");

        panel.add(btnTambah);
        panel.add(btnUbah);
        panel.add(btnHapus);
        panel.add(btnReset);

        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        Object sumber = e.getSource();

        if (sumber == btnTambah) {
            tambahData();
        } else if (sumber == btnUbah) {
            ubahData();
        } else if (sumber == btnHapus) {
            hapusData();
        } else if (sumber == btnReset) {
            resetForm();
        }
    }

    private class KlikTabel extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            isiFormDariTabel();
        }
    }

    private void isiFormDariTabel() {
        int i = tabel.getSelectedRow();
        if (i >= 0) {
            Mahasiswa m = data.get(i);
            txtNim.setText(m.getNim());
            txtNama.setText(m.getNama());
            cmbJurusan.setSelectedItem(m.getJurusan());
            if (m.getJenisKelamin().equals("Laki-laki")) {
                rbLaki.setSelected(true);
            } else {
                rbPerempuan.setSelected(true);
            }
            txtNim.setEnabled(false);
        }
    }

    private void tambahData() {
        if (!validasiInput()) {
            return;
        }

        String nim = txtNim.getText().trim();
        if (data.nimSudahAda(nim)) {
            JOptionPane.showMessageDialog(this, "NIM sudah terdaftar!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Mahasiswa m = new Mahasiswa(nim, txtNama.getText().trim(),
            (String) cmbJurusan.getSelectedItem(), getJenisKelamin());
        data.tambah(m);

        refreshTabel();
        resetForm();
        JOptionPane.showMessageDialog(this,
            "Data " + m.getPeran() + " berhasil ditambahkan.");
    }

    private void ubahData() {
        int i = tabel.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel terlebih dahulu!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validasiInput()) {
            return;
        }

        Mahasiswa baru = new Mahasiswa(data.get(i).getNim(), txtNama.getText().trim(),
            (String) cmbJurusan.getSelectedItem(), getJenisKelamin());
        data.ubah(i, baru);

        refreshTabel();
        resetForm();
        JOptionPane.showMessageDialog(this, "Data berhasil diubah.");
    }

    private void hapusData() {
        int i = tabel.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel terlebih dahulu!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int pilihan = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            data.hapus(i);
            refreshTabel();
            resetForm();
        }
    }

    private boolean validasiInput() {
        if (txtNim.getText().trim().isEmpty() || txtNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM dan Nama tidak boleh kosong!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private String getJenisKelamin() {
        if (rbLaki.isSelected()) {
            return "Laki-laki";
        }
        return "Perempuan";
    }

    private void refreshTabel() {
        modelTabel.setRowCount(0);
        for (int i = 0; i < data.getJumlah(); i++) {
            Mahasiswa m = data.get(i);
            modelTabel.addRow(new Object[]{
                m.getNim(), m.getNama(), m.getJurusan(), m.getJenisKelamin()
            });
        }
    }

    private void resetForm() {
        txtNim.setText("");
        txtNama.setText("");
        cmbJurusan.setSelectedIndex(0);
        rbLaki.setSelected(true);
        txtNim.setEnabled(true);
        tabel.clearSelection();
    }
}