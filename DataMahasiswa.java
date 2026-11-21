import java.util.ArrayList;

public class DataMahasiswa implements Operasi {
    private ArrayList<Mahasiswa> daftar = new ArrayList<Mahasiswa>();

    public void tambah(Mahasiswa m) {
        daftar.add(m);
    }

    public void ubah(int index, Mahasiswa m) {
        daftar.set(index, m);
    }

    public void hapus(int index) {
        daftar.remove(index);
    }

    public Mahasiswa get(int index) {
        return daftar.get(index);
    }

    public int getJumlah() {
        return daftar.size();
    }

    public boolean nimSudahAda(String nim) {
        for (int i = 0; i < daftar.size(); i++) {
            if (daftar.get(i).getNim().equals(nim)) {
                return true;
            }
        }
        return false;
    }
}