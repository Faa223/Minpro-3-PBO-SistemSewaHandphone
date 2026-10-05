/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.Handphone;
import model.Pelanggan;
import model.Sewa;
import model.SewaHarian;
import model.SewaMingguan;

/**
 *
 * @author ASUS
 */
public class Controller implements CrudHandphone, CrudPelanggan {
    private final ArrayList<Handphone> daftarHandphone;
    private final ArrayList<Pelanggan> daftarPelanggan;
    private final ArrayList<Sewa> daftarSewa;
    private int nomorHP;
    private int nomorPelanggan;
    private int nomorSewa;

    public Controller() {
        daftarHandphone = new ArrayList<>();
        daftarPelanggan = new ArrayList<>();
        daftarSewa = new ArrayList<>();
        nomorHP = 1;
        nomorPelanggan = 1;
        nomorSewa = 1;
        isiDataAwal();
    }

    private void isiDataAwal() {
        tambahHandphone("Samsung", "Galaxy S23", 100000);
        tambahHandphone("Apple", "iPhone 14", 150000);
        tambahHandphone("Xiaomi", "Redmi Note 13", 75000);
        tambahPelanggan("Pompom", "081234567890", "6472010101010001");
        tambahPelanggan("Yahya SOTM", "082345678901", "6472010202020002");
    }

    private String generateKodeHP() {
        String kode = String.format("HP%03d", nomorHP);
        nomorHP++;
        return kode;
    }

    private String generateIdPelanggan() {
        String id = String.format("P%03d", nomorPelanggan);
        nomorPelanggan++;
        return id;
    }

    private String generateIdSewa() {
        String id = String.format("SW%03d", nomorSewa);
        nomorSewa++;
        return id;
    }
    
    @Override
    public ArrayList<Handphone> getDaftarHandphone() {
        return daftarHandphone;
    }
    @Override
    public ArrayList<Pelanggan> getDaftarPelanggan() {
        return daftarPelanggan;
    }

    public ArrayList<Sewa> getDaftarSewa() {
        return daftarSewa;
    }
    
    @Override
    public Handphone tambahHandphone(String merk, String tipe, double harga) {
        if (!Handphone.validasiMerk(merk) || !Handphone.validasiTipe(tipe) || !Handphone.validasiHargaSewa(harga)) {
            return null;
        }

        Handphone hp = new Handphone(generateKodeHP(), merk, tipe, harga);
        daftarHandphone.add(hp);
        return hp;
    }
    
        @Override
    public Handphone cariHandphone(String kodeHP) {
        if (kodeHP == null) {
            return null;
        }

        for (Handphone hp : daftarHandphone) {
            if (hp.getKodeHP().equalsIgnoreCase(kodeHP.trim())) {
                return hp;
            }
        }

        return null;
    }
    
    @Override
    public boolean ubahHandphone(String kodeHP, String merk, String tipe, double harga) {
    Handphone hp = cariHandphone(kodeHP);

    if (hp == null) {
        return false;
    }

    if (!Handphone.validasiMerk(merk)
            || !Handphone.validasiTipe(tipe)
            || !Handphone.validasiHargaSewa(harga)) {
        return false;
    }

    hp.setMerk(merk);
    hp.setTipe(tipe);
    hp.setHargaSewa(harga);

    return true;
}
    
    public boolean handphonePunyaRiwayat(String kodeHP) {
        for (Sewa sewa : daftarSewa) {
            if (sewa.getHandphone().getKodeHP().equalsIgnoreCase(kodeHP)) {
                return true;
            }
        }

        return false;
    }
    @Override
    public boolean hapusHandphone(String kodeHP) {
        Handphone hp = cariHandphone(kodeHP);

        if (hp == null) {
            return false;
        }

        if (handphonePunyaRiwayat(kodeHP)) {
            return false;
        }

        daftarHandphone.remove(hp);
        return true;
    }
    @Override
    public Pelanggan tambahPelanggan(String nama, String noHP, String nik) {
        if (!Pelanggan.validasiNama(nama) || !Pelanggan.validasiNoHP(noHP) || !Pelanggan.validasiNIK(nik)) {
            return null;
        }

        if (noHPSudahAda(noHP) || nikSudahAda(nik)) {
            return null;
        }

        Pelanggan pelanggan = new Pelanggan(generateIdPelanggan(), nama, noHP, nik);
        daftarPelanggan.add(pelanggan);
        return pelanggan;
    }
    @Override
    public Pelanggan cariPelanggan(String idPelanggan) {
        if (idPelanggan == null) {
            return null;
        }
        
        for (Pelanggan pelanggan : daftarPelanggan) {
            if (pelanggan.getIdPelanggan().equalsIgnoreCase(idPelanggan)) {
                return pelanggan;
            }
        }

        return null;
    }

    @Override
public boolean ubahPelanggan(String idPelanggan, String nama, String noHP, String nik) {
    Pelanggan pelanggan = cariPelanggan(idPelanggan);

    if (pelanggan == null) {
        return false;
    }

    if (!Pelanggan.validasiNama(nama)
            || !Pelanggan.validasiNoHP(noHP)
            || !Pelanggan.validasiNIK(nik)) {
        return false;
    }

    if (noHPSudahAdaSelain(noHP, idPelanggan)
            || nikSudahAdaSelain(nik, idPelanggan)) {
        return false;
    }

    pelanggan.setNama(nama);
    pelanggan.setNoHP(noHP);
    pelanggan.setNik(nik);

    return true;
}
    
    public boolean noHPSudahAda(String noHP) {
        for (Pelanggan pelanggan : daftarPelanggan) {
            if (pelanggan.getNoHP().equals(noHP)) {
                return true;
            }
        }

        return false;
    }

    public boolean noHPSudahAdaSelain(
            String noHP,
            String idPelanggan) {

        for (Pelanggan pelanggan : daftarPelanggan) {
            if (!pelanggan.getIdPelanggan().equalsIgnoreCase(idPelanggan) && pelanggan.getNoHP().equals(noHP)) {
                return true;
            }
        }

        return false;
    }

    public boolean nikSudahAda(String nik) {
        for (Pelanggan pelanggan : daftarPelanggan) {
            if (pelanggan.getNik().equals(nik)) {
                return true;
            }
        }

        return false;
    }

    public boolean nikSudahAdaSelain(String nik, String idPelanggan) {
        for (Pelanggan pelanggan : daftarPelanggan) {
            if (!pelanggan.getIdPelanggan().equalsIgnoreCase(idPelanggan) && pelanggan.getNik().equals(nik)) {
                return true;
            }
        }
        
        return false;
    }

    public boolean pelangganPunyaRiwayat(String idPelanggan) {
        for (Sewa sewa : daftarSewa) {
            if (sewa.getPelanggan().getIdPelanggan().equalsIgnoreCase(idPelanggan)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean hapusPelanggan(String idPelanggan) {
        Pelanggan pelanggan = cariPelanggan(idPelanggan);

        if (pelanggan == null) {
            return false;
        }

        if (pelangganPunyaRiwayat(idPelanggan)) {
            return false;
        }

        daftarPelanggan.remove(pelanggan);
        return true;
    }

    public Sewa buatSewaHarian(String idPelanggan, String kodeHP, int jumlahHari) {
        Pelanggan pelanggan = cariPelanggan(idPelanggan);
        Handphone hp = cariHandphone(kodeHP);

        if (pelanggan == null) {
            return null;
        }

        if (hp == null) {
            return null;
        }

        if (!hp.getStatus().equals("TERSEDIA")) {
            return null;
        }

        if (!SewaHarian.validasiJumlahHari(jumlahHari)) {
            return null;
        }

        SewaHarian sewa = new SewaHarian(generateIdSewa(), pelanggan, hp, jumlahHari);
        daftarSewa.add(sewa);
        hp.setStatus("DISEWA");
        return sewa;
    }

    public Sewa buatSewaMingguan(String idPelanggan, String kodeHP, int jumlahMinggu) {
        Pelanggan pelanggan = cariPelanggan(idPelanggan);
        Handphone hp = cariHandphone(kodeHP);

        if (pelanggan == null) {
            return null;
        }

        if (hp == null) {
            return null;
        }

        if (!hp.getStatus().equals("TERSEDIA")) {
            return null;
        }
        
        if (!SewaMingguan.validasiJumlahMinggu(jumlahMinggu)) {
            return null;
        }

        SewaMingguan sewa = new SewaMingguan(generateIdSewa(), pelanggan, hp, jumlahMinggu);
        daftarSewa.add(sewa);
        hp.setStatus("DISEWA");
        return sewa;
    }

    public Sewa cariSewa(String idSewa) {
        if (idSewa == null) {
            return null;
        }
        
        for (Sewa sewa : daftarSewa) {
            if (sewa.getIdSewa().equalsIgnoreCase(idSewa)) {
                return sewa;
            }
        }

        return null;
    }

    public boolean kembalikanHandphone(String idSewa) {
        Sewa sewa = cariSewa(idSewa);

        if (sewa == null) {
            return false;
        }

        if (!sewa.getStatus().equals("AKTIF")) {
            return false;
        }

        sewa.setStatus("SELESAI");
        sewa.getHandphone().setStatus("TERSEDIA");
        return true;
    }

    public int hitungHPTersedia() {
        int jumlah = 0;

        for (Handphone hp : daftarHandphone) {
            if (hp.getStatus().equals("TERSEDIA")) {
                jumlah++;
            }
        }

        return jumlah;
    }

    public int hitungHPDisewa() {
        int jumlah = 0;

        for (Handphone hp : daftarHandphone) {
            if (hp.getStatus().equals("DISEWA")) {
                jumlah++;
            }
        }
        return jumlah;
    }

    public int hitungSewaAktif() {
        int jumlah = 0;

        for (Sewa sewa : daftarSewa) {
            if (sewa.getStatus().equals("AKTIF")) {
                jumlah++;
            }
        }
        return jumlah;
    }
}
