package model;

public class Nilai {
    private String nim;
    private String kodeMK;
    private String nip; 
    private double uts;
    private double uas;
    private double tugas;
    private double akhir;
    private String grade;

    public Nilai(String nim, String kodeMK, String nip, double uts, double uas, double tugas, double akhir, String grade) {
        this.nim = nim;
        this.kodeMK = kodeMK;
        this.nip = nip;
        this.uts = uts;
        this.uas = uas;
        this.tugas = tugas;
        this.akhir = akhir;
        this.grade = grade;
    }



    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getKodeMK() {
        return kodeMK;
    }

    public void setKodeMK(String kodeMK) {
        this.kodeMK = kodeMK;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public double getUts() {
        return uts;
    }

    public void setUts(double uts) {
        this.uts = uts;
    }

    public double getUas() {
        return uas;
    }

    public void setUas(double uas) {
        this.uas = uas;
    }

    public double getTugas() {
        return tugas;
    }

    public void setTugas(double tugas) {
        this.tugas = tugas;
    }

    public double getAkhir() {
        return akhir;
    }

    public void setAkhir(double akhir) {
        this.akhir = akhir;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}