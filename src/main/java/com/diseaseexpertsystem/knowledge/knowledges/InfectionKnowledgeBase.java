package com.diseaseexpertsystem.knowledge.knowledges;

import java.util.List;
import java.util.Map;

import com.diseaseexpertsystem.knowledge.DiseaseKnowledgeBaseAbstract;
import com.diseaseexpertsystem.knowledge.Evidence;
import com.diseaseexpertsystem.knowledge.Rule;

public class InfectionKnowledgeBase extends DiseaseKnowledgeBaseAbstract {
  public InfectionKnowledgeBase() {
    initMasterSymptoms();
    initWeightedKnowledgeBase();
    initRuleBasedKnowledgeBase();
  }

  @Override
  public Map<String, List<Evidence>> getKnowledge() {
    return knowledgeGraph;
  }

  @Override
  public Map<String, String> getSymptoms() {
    return masterSymptoms;
  }

  @Override
  public List<Rule> getRules() {
    return rules;
  }

  private void initMasterSymptoms() {
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
  }

  private void initWeightedKnowledgeBase() {
    // Level 1: Prevalensi Spesifik Penyakit
    knowledgeGraph.put("Root", List.of(
        new Evidence("Influenza", 0.2),
        new Evidence("Demam Berdarah", 0.2),
        new Evidence("Hipertensi", 0.2),
        new Evidence("Diabetes", 0.2)));

    // Level 2: Nilai Diagnostik/Prediktif Gejala (Total bobot per penyakit = 1.0)
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

  private void initRuleBasedKnowledgeBase() {
    rules.add(new Rule("Influenza", List.of("G001", "G002", "G003", "G004")));
    rules.add(new Rule("Demam Berdarah", List.of("G005", "G006", "G007", "G008")));
    rules.add(new Rule("Diabetes", List.of("G009", "G010", "G011", "G012")));
    rules.add(new Rule("Hipertensi", List.of("G013", "G014", "G015", "G016", "G017")));
  }
}