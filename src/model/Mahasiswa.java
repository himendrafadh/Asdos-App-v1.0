package model; 

public class Mahasiswa {
    private String nim;
    private String nama;
    private String angkatan; 
    private String prodi;
    private String email;


    public Mahasiswa(String nim, String nama, String angkatan, String prodi, String email) {
        this.nim = nim;
        this.nama = nama;
        this.angkatan = angkatan;
        this.prodi = prodi;
        this.email = email;
    }


    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}