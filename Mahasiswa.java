public class Mahasiswa extends Orang {
    private String nim;
    private String jurusan;
    private String jenisKelamin;

    public Mahasiswa(String nim, String nama, String jurusan, String jenisKelamin) {
        super(nama);
        this.nim = nim;
        this.jurusan = jurusan;
        this.jenisKelamin = jenisKelamin;
    }

    public String getNim() {
        return nim;
    }

    public String getJurusan() {
        return jurusan;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public String getPeran() {
        return "Mahasiswa";
    }
}