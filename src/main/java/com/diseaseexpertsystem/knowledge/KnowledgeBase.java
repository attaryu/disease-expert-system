package com.diseaseexpertsystem.knowledge;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KnowledgeBase {
  private Map<String, List<Evidence>> graph = new LinkedHashMap<>();

  public KnowledgeBase() {
    initKnowledgeBase();
  }

  public Map<String, List<Evidence>> getGraph() {
    return graph;
  }

  public Set<String> getUniqueLeaf() {
    Set<String> allNodes = new HashSet<>();
    Set<String> parents = graph.keySet();

    for (List<Evidence> evidences : graph.values()) {
      for (Evidence evidence : evidences) {
        if (!parents.contains(evidence.name)) {
          allNodes.add(evidence.name);
        }
      }
    }

    return allNodes;
  }

  private void initKnowledgeBase() {
    // Level 1: Probabilitas Kunjungan Pasien (Prevalensi Umum)
    graph.put("Root", List.of(
        new Evidence("Penyakit Infeksi", 0.6),
        new Evidence("Penyakit Non-Infeksi", 0.4)));

    // Level 2: Prevalensi Spesifik Penyakit
    graph.put("Penyakit Infeksi", List.of(
        new Evidence("Influenza", 0.85),
        new Evidence("Demam Berdarah", 0.15)));

    graph.put("Penyakit Non-Infeksi", List.of(
        new Evidence("Hipertensi", 0.7),
        new Evidence("Diabetes", 0.3)));

    // Level 3: Nilai Diagnostik/Prediktif Gejala (Total bobot per penyakit = 1.0)

    graph.put("Influenza", List.of(
        new Evidence("Pilek (hidung tersumbat, bersin berulang)", 0.4),
        new Evidence("Batuk (kering/berdahak ringan, >3x/jam)", 0.3),
        new Evidence("Sakit Tenggorokan (nyeri menelan, faring merah)", 0.15),
        new Evidence("Demam (37.5-39°C, bertahap, menggigil ringan)", 0.15)));

    graph.put("Demam Berdarah", List.of(
        new Evidence("Demam Tinggi (>39°C mendadak, pola 2-7 hari)", 0.45),
        new Evidence("Nyeri Sendi (otot dan tulang/breakbone fever)", 0.25),
        new Evidence("Ruam Kulit (bintik merah, torniket positif)", 0.2),
        new Evidence("Mual (muntah >2x/hari, nafsu makan turun)", 0.1)));

    graph.put("Diabetes", List.of(
        new Evidence("Sering Buang Air Kecil (Poliuria, terutama malam)", 0.35),
        new Evidence("Sering Haus (Polidipsia, minum >3 liter/hari)", 0.3),
        new Evidence("Mudah Lelah (energi habis, gula darah >200 mg/dL)", 0.2),
        new Evidence("Luka Sulit Sembuh (infeksi berulang, >2 minggu)", 0.15)));

    graph.put("Hipertensi", List.of(
        new Evidence("Tekanan Darah (konsisten >= 140/90 mmHg)", 0.6),
        new Evidence("Sakit Kepala (pagi hari, berdenyut di tengkuk/belakang)", 0.25),
        new Evidence("Pusing (terasa berputar/ringan saat ubah posisi)", 0.1),
        new Evidence("Penglihatan Kabur (retina menyempit)", 0.03),
        new Evidence("Mimisan (perdarahan hidung)", 0.02)));
  }
}