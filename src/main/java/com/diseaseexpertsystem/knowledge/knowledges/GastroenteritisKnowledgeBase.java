package com.diseaseexpertsystem.knowledge.knowledges;

import java.util.List;
import java.util.Map;

import com.diseaseexpertsystem.knowledge.DiseaseKnowledgeBaseAbstract;
import com.diseaseexpertsystem.knowledge.Evidence;

public class GastroenteritisKnowledgeBase extends DiseaseKnowledgeBaseAbstract {
  public GastroenteritisKnowledgeBase() {
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
    masterSymptoms.put("G001", "Apakah anda sering mengalami buang air besar (lebih dari  kali)?");
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

    /* level 1 */
    knowledgeGraph.put("Root", List.of(
        new Evidence("Keracunan Staphylococcus aureus", 1),
        new Evidence("Keracunan jamur beracun", 1),
        new Evidence("Keracunan Salmonellae", 1),
        new Evidence("Keracunan Clostridium botulinum", 1),
        new Evidence("Keracunan Campylobacte", 1)));

    /* level 2 */
    knowledgeGraph.put("Keracunan Staphylococcus aureus", List.of(
        new Evidence("Mencret", 0.2),
        new Evidence("Muntah", 0.2),
        new Evidence("Sakit perut", 0.2),
        new Evidence("Darah rendah", 0.2),
        new Evidence("Makan daging", 0.2)));

    knowledgeGraph.put("Keracunan jamur beracun", List.of(
        new Evidence("Mencret", 0.2),
        new Evidence("Muntah", 0.2),
        new Evidence("Sakit perut", 0.2),
        new Evidence("Koma", 0.2),
        new Evidence("Makan jamur", 0.2)));

    knowledgeGraph.put("Keracunan Salmonellae", List.of(
        new Evidence("Mencret", 0.1),
        new Evidence("Muntah", 0.1),
        new Evidence("Sakit perut", 0.2),
        new Evidence("Demam", 0.2),
        new Evidence("Septicaemia", 0.2),
        new Evidence("Makan daging", 0.2)));

    knowledgeGraph.put("Keracunan Clostridium botulinum", List.of(
        new Evidence("Muntah", 0.3),
        new Evidence("Lumpuh", 0.3),
        new Evidence("Makan makanan kaleng", 0.4)));

    knowledgeGraph.put("Keracunan Campylobacte", List.of(
        new Evidence("Mencret berdarah", 0.2),
        new Evidence("Sakit perut", 0.3),
        new Evidence("Demam", 0.2),
        new Evidence("Minum susu", 0.3)));

    /* level 3 */
    // 20
    knowledgeGraph.put("Mencret", List.of(
        new Evidence("G001", 0.2),
        new Evidence("G002", 0.2),
        new Evidence("G004", 0.2),
        new Evidence("G005", 0.3)));

    // 21
    knowledgeGraph.put("Muntah", List.of(
        new Evidence("G004", 0.2),
        new Evidence("G005", 0.2),
        new Evidence("G006", 0.2)));

    // 22
    knowledgeGraph.put("Sakit perut", List.of(
        new Evidence("G004", 0.5),
        new Evidence("G007", 0.5)));

    // 23
    knowledgeGraph.put("Darah rendah", List.of(
        new Evidence("G004", 0.3),
        new Evidence("G008", 0.3),
        new Evidence("G009", 0.4)));

    // 29
    knowledgeGraph.put("Makan daging", List.of(
        new Evidence("G014", 0.5),
        new Evidence("G015", 0.5)));

    // 24
    knowledgeGraph.put("Koma", List.of(
        new Evidence("G004", 0.5),
        new Evidence("G010", 0.5)));

    // 30
    knowledgeGraph.put("Makan jamur", List.of(
        new Evidence("G014", 0.5),
        new Evidence("G016", 0.5)));

    // 25
    knowledgeGraph.put("Demam", List.of(
        new Evidence("G004", 0.2),
        new Evidence("G005", 0.2),
        new Evidence("G009", 0.3),
        new Evidence("G011", 0.3)));

    // 26
    knowledgeGraph.put("Septicaemia", List.of(
        new Evidence("G004", 0.2),
        new Evidence("G008", 0.2),
        new Evidence("G011", 0.3),
        new Evidence("G012", 0.3)));

    // 27
    knowledgeGraph.put("Lumpuh", List.of(
        new Evidence("G004", 0.3),
        new Evidence("G013", 0.7)));

    // 31
    knowledgeGraph.put("Makan makanan kaleng", List.of(
        new Evidence("G014", 0.5),
        new Evidence("G017", 0.5)));

    // 28
    knowledgeGraph.put("Mencret berdarah", List.of(
        new Evidence("G001", 0.2),
        new Evidence("G002", 0.2),
        new Evidence("G003", 0.2),
        new Evidence("G004", 0.2),
        new Evidence("G005", 0.2)));

    // 32
    knowledgeGraph.put("Minum susu", List.of(
        new Evidence("G018", 0.5),
        new Evidence("G019", 0.5)));
  }
}
