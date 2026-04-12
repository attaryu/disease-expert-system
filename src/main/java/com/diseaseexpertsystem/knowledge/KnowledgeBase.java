package com.diseaseexpertsystem.knowledge;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KnowledgeBase {
  private Map<String, String> masterSymptoms = new LinkedHashMap<>();
  private Map<String, List<Evidence>> knowledgeGraph = new LinkedHashMap<>();

  public KnowledgeBase() {
    initInfectionKnowledgeBase();
  }

  public Map<String, List<Evidence>> getKnowledge() {
    return knowledgeGraph;
  }

  public Map<String, String> getSymptoms() {
    return masterSymptoms;
  }

  private void initInfectionKnowledgeBase() {
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

    // Level 1: Probabilitas Kunjungan Pasien (Prevalensi Umum)
    knowledgeGraph.put("Root", List.of(
        new Evidence("Penyakit Infeksi", 0.6),
        new Evidence("Penyakit Non-Infeksi", 0.4)));

    // Level 2: Prevalensi Spesifik Penyakit
    knowledgeGraph.put("Penyakit Infeksi", List.of(
        new Evidence("Influenza", 0.85),
        new Evidence("Demam Berdarah", 0.15)));

    knowledgeGraph.put("Penyakit Non-Infeksi", List.of(
        new Evidence("Hipertensi", 0.7),
        new Evidence("Diabetes", 0.3)));

    // Level 3: Nilai Diagnostik/Prediktif Gejala (Total bobot per penyakit = 1.0)

    knowledgeGraph.put("Influenza", List.of(
        new Evidence("G001", 0.4),
        new Evidence("G002", 0.3),
        new Evidence("G003", 0.15),
        new Evidence("G004", 0.15)));

    knowledgeGraph.put("Demam Berdarah", List.of(
        new Evidence("G005", 0.45),
        new Evidence("G006", 0.25),
        new Evidence("G007", 0.2),
        new Evidence("G008", 0.1)));

    knowledgeGraph.put("Diabetes", List.of(
        new Evidence("G009", 0.35),
        new Evidence("G010", 0.3),
        new Evidence("G011", 0.2),
        new Evidence("G012", 0.15)));

    knowledgeGraph.put("Hipertensi", List.of(
        new Evidence("G013", 0.6),
        new Evidence("G014", 0.25),
        new Evidence("G015", 0.1),
        new Evidence("G016", 0.03),
        new Evidence("G017", 0.02)));
  }
}