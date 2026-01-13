module Asdos { // Nama modul Anda mungkin berbeda
    requires java.desktop;
    requires java.sql;
    
    // Tambahkan 'requires' untuk setiap modul Apache POI yang dibutuhkan
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.poi.ooxml.schemas; // <-- INI YANG KURANG
    requires org.apache.commons.collections4;
	requires com.formdev.flatlaf;
    // Anda mungkin perlu menambahkan modul lain seperti commons.compress, dll.
}