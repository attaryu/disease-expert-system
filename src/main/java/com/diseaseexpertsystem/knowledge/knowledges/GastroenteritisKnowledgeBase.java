package com.diseaseexpertsystem.knowledge.knowledges;

import java.util.List;
import java.util.Map;

import com.diseaseexpertsystem.knowledge.DiseaseKnowledgeBaseAbstract;
import com.diseaseexpertsystem.knowledge.Evidence;
import com.diseaseexpertsystem.knowledge.Rule;

public class GastroenteritisKnowledgeBase extends DiseaseKnowledgeBaseAbstract {
  public GastroenteritisKnowledgeBase() {
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
    masterSymptoms.put("G001", "Apakah anda sering mengalami buang air besar (lebih dari 2 kali)?");
    masterSymptoms.put("G002", "Apakah anda mengalami berak encer?");
    masterSymptoms.put("G003", "Apakah anda mengalami berak berdarah?");
    masterSymptoms.put("G004", "Apakah anda merasa lesu dan tidak bergairah?");
    masterSymptoms.put("G005", "Apakah anda tidak selera makan?");
    masterSymptoms.put("G006", "Apakah anda merasa mual dan sering muntah (lebih dari 1 kali)?");
    masterSymptoms.put("G007", "Apakah anda merasa sakit di bagian perut?");
    masterSymptoms.put("G008", "Apakah tekanan darah anda rendah?");
    masterSymptoms.put("G009", "Apakah anda merasa pusing?");
    masterSymptoms.put("G010", "Apakah anda mengalami pingsan?");
    masterSymptoms.put("G011", "Apakah suhu badan anda tinggi?");
    masterSymptoms.put("G012", "Apakah anda mengalami luka di bagian tertentu?");
    masterSymptoms.put("G013", "Apakah anda tidak dapat menggerakkan anggota badan tertentu?");
    masterSymptoms.put("G014", "Apakah anda pernah memakan sesuatu?");
    masterSymptoms.put("G015", "Apakah anda memakan daging?");
    masterSymptoms.put("G016", "Apakah anda memakan jamur?");
    masterSymptoms.put("G017", "Apakah anda memakan makanan kaleng?");
    masterSymptoms.put("G018", "Apakah anda membeli susu?");
    masterSymptoms.put("G019", "Apakah anda meminum susu?");
  }

  private void initWeightedKnowledgeBase() {
    /* level 1 : Prevalensi Umum Gastroenteritis (Total = 1.0) */
    knowledgeGraph.put("Root", List.of(
        new Evidence("Keracunan Staphylococcus aureus", 0.3),
        new Evidence("Keracunan Salmonellae", 0.3),
        new Evidence("Keracunan Campylobacte", 0.2),
        new Evidence("Keracunan jamur beracun", 0.1),
        new Evidence("Keracunan Clostridium botulinum", 0.1)));

    /* level 2 : Relasi Penyakit ke Grup Gejala (Total per Penyakit = 1.0) */
    knowledgeGraph.put("Keracunan Staphylococcus aureus", List.of(
        new Evidence("Makan daging", 0.4),
        new Evidence("Mencret", 0.2),
        new Evidence("Muntah", 0.2),
        new Evidence("Sakit perut", 0.15),
        new Evidence("Darah rendah", 0.05)));

    knowledgeGraph.put("Keracunan jamur beracun", List.of(
        new Evidence("Makan jamur", 0.6),
        new Evidence("Koma", 0.2),
        new Evidence("Muntah", 0.1),
        new Evidence("Sakit perut", 0.1)));

    knowledgeGraph.put("Keracunan Salmonellae", List.of(
        new Evidence("Makan daging", 0.4),
        new Evidence("Demam", 0.2),
        new Evidence("Mencret", 0.2),
        new Evidence("Sakit perut", 0.1),
        new Evidence("Septicaemia", 0.1)));

    knowledgeGraph.put("Keracunan Clostridium botulinum", List.of(
        new Evidence("Makan makanan kaleng", 0.6),
        new Evidence("Lumpuh", 0.3),
        new Evidence("Muntah", 0.1)));

    knowledgeGraph.put("Keracunan Campylobacte", List.of(
        new Evidence("Minum susu", 0.4),
        new Evidence("Mencret berdarah", 0.3),
        new Evidence("Demam", 0.2),
        new Evidence("Sakit perut", 0.1)));

    /* level 3 : Relasi Grup Gejala ke Pertanyaan Fisik (Total per Grup = 1.0) */
    knowledgeGraph.put("Mencret", List.of(
        new Evidence("G001", 0.6),
        new Evidence("G002", 0.4)));

    knowledgeGraph.put("Muntah", List.of(
        new Evidence("G006", 0.8),
        new Evidence("G005", 0.2)));

    knowledgeGraph.put("Sakit perut", List.of(
        new Evidence("G007", 0.8),
        new Evidence("G004", 0.2)));

    knowledgeGraph.put("Darah rendah", List.of(
        new Evidence("G008", 0.7),
        new Evidence("G009", 0.3)));

    knowledgeGraph.put("Makan daging", List.of(
        new Evidence("G015", 0.8),
        new Evidence("G014", 0.2)));

    knowledgeGraph.put("Koma", List.of(
        new Evidence("G010", 0.8),
        new Evidence("G004", 0.2)));

    knowledgeGraph.put("Makan jamur", List.of(
        new Evidence("G016", 0.8),
        new Evidence("G014", 0.2)));

    knowledgeGraph.put("Demam", List.of(
        new Evidence("G011", 0.8),
        new Evidence("G009", 0.2)));

    knowledgeGraph.put("Septicaemia", List.of(
        new Evidence("G012", 0.8),
        new Evidence("G011", 0.2)));

    knowledgeGraph.put("Lumpuh", List.of(
        new Evidence("G013", 0.9),
        new Evidence("G004", 0.1)));

    knowledgeGraph.put("Makan makanan kaleng", List.of(
        new Evidence("G017", 0.8),
        new Evidence("G014", 0.2)));

    knowledgeGraph.put("Mencret berdarah", List.of(
        new Evidence("G003", 0.8),
        new Evidence("G001", 0.1),
        new Evidence("G002", 0.1)));

    knowledgeGraph.put("Minum susu", List.of(
        new Evidence("G019", 0.7),
        new Evidence("G018", 0.3)));
  }

  private void initRuleBasedKnowledgeBase() {
    rules.add(new Rule("Keracunan Staphylococcus aureus",
        List.of("G014", "G015", "G001", "G002", "G005", "G006", "G004", "G007", "G008",
            "G009")));

    rules.add(new Rule("Keracunan jamur beracun",
        List.of("G014", "G016", "G010", "G004", "G005", "G006", "G007")));

    rules.add(new Rule("Keracunan Salmonellae",
        List.of("G014", "G015", "G009", "G011", "G001", "G002", "G004", "G007", "G012")));
    rules.add(new Rule("Keracunan Clostridium botulinum",
        List.of("G014", "G017", "G004", "G013", "G005", "G006")));

    rules.add(new Rule("Keracunan Campylobacte",
        List.of("G018", "G019", "G001", "G002", "G003", "G009", "G011", "G004", "G007")));
  }
}
