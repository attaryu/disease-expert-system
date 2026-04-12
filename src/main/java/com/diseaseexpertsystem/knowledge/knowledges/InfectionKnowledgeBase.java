package com.diseaseexpertsystem.knowledge.knowledges;

import java.util.List;
import java.util.Map;

import com.diseaseexpertsystem.knowledge.DiseaseKnowledgeBaseAbstract;
import com.diseaseexpertsystem.knowledge.Evidence;

public class InfectionKnowledgeBase extends DiseaseKnowledgeBaseAbstract {
  public InfectionKnowledgeBase() {
    initKnowledgeBase();
  }

  @Override
  public Map<String, List<Evidence>> getKnowledge() {
    return knowledgeGraph;
  }

  @Override
  public Map<String, String> getSymptoms() {
    return masterSymptoms;
  }

  private void initKnowledgeBase() {
    masterSymptoms.put("G001", "Pilek (hidung tersumbat, bersin berulang)");
    masterSymptoms.put("G002", "Batuk (kering/berdahak ringan, >3x/jam)");
    masterSymptoms.put("G003", "Sakit Tenggorokan (nyeri menelan, faring merah)");
    masterSymptoms.put("G004", "Demam (37.5-39°C, bertahap, menggigil ringan)");
    masterSymptoms.put("G005", "Demam Tinggi (>39°C mendadak, pola 2-7 hari)");
    masterSymptoms.put("G006", "Nyeri Sendi (otot dan tulang/breakbone fever)");
    masterSymptoms.put("G007", "Ruam Kulit (bintik merah, torniket positif)");
    masterSymptoms.put("G008", "Mual (muntah >2x/hari, nafsu makan turun)");
    masterSymptoms.put("G009", "Sering Buang Air Kecil (Poliuria, terutama malam)");
    masterSymptoms.put("G010", "Sering Haus (Polidipsia, minum >3 liter/hari)");
    masterSymptoms.put("G011", "Mudah Lelah (energi habis, gula darah >200 mg/dL)");
    masterSymptoms.put("G012", "Luka Sulit Sembuh (infeksi berulang, >2 minggu)");
    masterSymptoms.put("G013", "Tekanan Darah (konsisten >= 140/90 mmHg)");
    masterSymptoms.put("G014", "Sakit Kepala (pagi hari, berdenyut di tengkuk/belakang)");
    masterSymptoms.put("G015", "Pusing (terasa berputar/ringan saat ubah posisi)");
    masterSymptoms.put("G016", "Penglihatan Kabur (retina menyempit)");
    masterSymptoms.put("G017", "Mimisan (perdarahan hidung)");

    // Level 1: Probabilitas Kunjungan Pasien (Total = 1.0)
    knowledgeGraph.put("Root", List.of(
        new Evidence("Penyakit Infeksi", 0.6),
        new Evidence("Penyakit Non-Infeksi", 0.4)));

    // Level 2: Prevalensi Spesifik Penyakit (Total = 1.0)
    knowledgeGraph.put("Penyakit Infeksi", List.of(
        new Evidence("Influenza", 0.7),
        new Evidence("Demam Berdarah", 0.3)));

    knowledgeGraph.put("Penyakit Non-Infeksi", List.of(
        new Evidence("Hipertensi", 0.6),
        new Evidence("Diabetes", 0.4)));

    // Level 3: Nilai Diagnostik/Prediktif Gejala (Total bobot per penyakit = 1.0)
    knowledgeGraph.put("Influenza", List.of(
        new Evidence("G001", 0.4), // Pilek dominan
        new Evidence("G002", 0.3), // Batuk
        new Evidence("G004", 0.2), // Demam
        new Evidence("G003", 0.1))); // Sakit tenggorokan

    knowledgeGraph.put("Demam Berdarah", List.of(
        new Evidence("G005", 0.5), // Demam pola pelana sangat khas DB
        new Evidence("G007", 0.3), // Ruam kulit/Torniket
        new Evidence("G006", 0.15), // Nyeri sendi
        new Evidence("G008", 0.05))); // Mual (gejala umum)

    knowledgeGraph.put("Diabetes", List.of(
        new Evidence("G009", 0.4), // Poliuria utama
        new Evidence("G010", 0.4), // Polidipsia utama
        new Evidence("G012", 0.15), // Luka sulit sembuh
        new Evidence("G011", 0.05))); // Lelah

    knowledgeGraph.put("Hipertensi", List.of(
        new Evidence("G013", 0.7), // Tekanan darah tinggi adalah kunci mutlak
        new Evidence("G014", 0.15), // Sakit kepala tengkuk
        new Evidence("G015", 0.1), // Pusing
        new Evidence("G016", 0.03), // Penglihatan kabur
        new Evidence("G017", 0.02))); // Mimisan
  }
}