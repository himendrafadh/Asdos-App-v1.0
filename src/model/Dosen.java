package model;

public class Dosen {

    private String nip; 
    private String nama;
    private String email;

  
    public Dosen(String nip, String nama, String email) {
        this.nip = nip;
        this.nama = nama;
        this.email = email;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}