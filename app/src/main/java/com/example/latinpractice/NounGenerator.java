package com.example.latinpractice;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

enum NounCase {
    NominativeSingular, AccusativeSingular, DativeSingular, AblativeSingular, GenitiveSingular,
    NominativePlural, AccusativePlural, DativePlural, AblativePlural, GenitivePlural,
}

public class NounGenerator extends ExerciseGenerator {
    private ArrayList<Integer> declensions;
    private Random rand;
    private HashMap<NounCase, String> exampleSentences;
    private HashMap<Integer, List<String>> exampleWords;
    boolean singularEnabled;
    boolean pluralEnabled;
    public NounGenerator(ArrayList<Integer> declensions, boolean singular, boolean plural) {
        this.declensions = declensions;
        this.rand = new Random();

        singularEnabled = singular;
        pluralEnabled = plural;

        exampleSentences = new HashMap<>();
        exampleSentences.put(NounCase.NominativeSingular, "%s servum salutat");
        exampleSentences.put(NounCase.GenitiveSingular, "Sacculus %s magnum est");
        exampleSentences.put(NounCase.DativeSingular, "Cornelia rosam %s dat");
        exampleSentences.put(NounCase.AccusativeSingular, "dominus %s salutat");
        exampleSentences.put(NounCase.AblativeSingular, "Cum %s");
        exampleSentences.put(NounCase.NominativePlural, "%s servum salutat");
        exampleSentences.put(NounCase.GenitivePlural, "Sacculus %s magnum est");
        exampleSentences.put(NounCase.DativePlural, "Cornelia multas rosas %s dat");
        exampleSentences.put(NounCase.AccusativePlural, "dominus %s salutat");
        exampleSentences.put(NounCase.AblativePlural, "Cum %s");

        exampleWords = new HashMap<>();
        exampleWords.put(1, Arrays.asList("domina", "ancilla", "puella"));
        exampleWords.put(2, Arrays.asList("dominus", "servus", "templum", "bellum"));
        exampleWords.put(3, Arrays.asList("rēx|regis", "pastor|pastoris", "iītus"));
        exampleWords.put(4, Arrays.asList("ovis", "urbs"));
        exampleWords.put(5, Arrays.asList("mare|mari", "animal|animāle"));

    }

    private String getCaseText(int c) {
        if (c >= 5) c -= 5;
        switch (c) {
            case 0: return "Nominative";
            case 1: return "Accusative";
            case 2: return "Genitive";
            case 3: return "Dative";
            case 4: return "Ablative";
            default: return "Unknown";
        }
    }

    private NounCase getCaseOrdinal(int c) {
        switch(c) {
            case 0: return NounCase.NominativeSingular;
            case 1: return NounCase.AccusativeSingular;
            case 2: return NounCase.GenitiveSingular;
            case 3: return NounCase.DativeSingular;
            case 4: return NounCase.AblativeSingular;
            case 5: return NounCase.NominativePlural;
            case 6: return NounCase.AccusativePlural;
            case 7: return NounCase.GenitivePlural;
            case 8: return NounCase.DativePlural;
            case 9: return NounCase.AblativePlural;
        }
        return null;
    }

    public String filterWord(String word){
        if(word.contains("|")) {
            String[] tokens = word.split("\\|");
            return tokens[0];
        }
        return word;
    }

    public String findStem(String word, int declension) {
        if(word.contains("|")) {
            String[] tokens = word.split("\\|");
            word = tokens[1];
        }

        if (declension == 1)
            return word.substring(0, word.length() - 1);
        else if (declension == 2)
            return word.substring(0, word.length() - 2);
        else if (declension == 3)
            return word.substring(0, word.length() - 2);
        else if (declension == 4)
            if (word.substring(word.length() - 2).equals("is"))
                return word.substring(0, word.length() - 2);
            else
                return word.substring(0, word.length() - 1);
        else if (declension == 5)
            return word.substring(0, word.length() - 1);
        else
            return "";
    }

    public List<String> findConjugations(String word, int declension) {
        if(declension == 1) {
            return Arrays.asList("a", "am", "ae", "ae", "ā", "ae", "ās", "ārum", "īs", "īs");
        }
        else if (declension == 2) {
            if (word.substring(word.length() - 2).equals("us"))
                return Arrays.asList("us", "um", "ī", "ō", "ō", "ī", "ōs", "ōrum", "īs", "īs");
            else
                return Arrays.asList("um", "um", "ī", "ō", "ō", "a", "a", "ōrum", "īs", "īs");
        }
        else if (declension == 3) { // Male and Neutral
            if (word.substring(word.length() - 2).equals("is"))
                return Arrays.asList("", "em", "is", "ī", "e", "ēs", "ēs", "um", "ibus", "ibus");
            else
                return Arrays.asList("", "", "is", "ī", "e", "a", "a", "um", "ibus", "ibus");
        }
        else if (declension == 4) { // Feminines
            if (word.substring(word.length() - 2).equals("is"))
                return Arrays.asList("is", "em", "is", "ī", "e", "ēs", "ēs", "ium", "ibus", "ibus");
            else
                return Arrays.asList("s", "em", "is", "ī", "e", "ēs", "ēs", "ium", "ibus", "ibus");
        }
        else if (declension == 5) { // Neutrals
            if (word.substring(word.length() - 1).equals("i"))
                return Arrays.asList("", "", "is", "ī", "ī", "ia", "ia", "ium", "ibus", "ibus");
            else
                return Arrays.asList("", "", "is", "ī", "e", "ia", "ia", "ium", "ibus", "ibus");
        }
        else return null;
    }

    public String declineWord(int casus, int declension, String word) {
        String base = filterWord(word);
        String stem = findStem(word, declension);
        List<String> forms = findConjugations(word, declension);


        return forms.get(casus).equals("") ? base : stem + forms.get(casus);
    }

    public String[] findAlternatives(int casus, int declension, String word) {
        ArrayList<String> alternatives = new ArrayList<>();

        int attempts = 0;
        while(alternatives.size() < 4) {
            String alt = "";
            String stem = findStem(word, declension);
            int shuffle = rand.nextInt(2);
            if(shuffle == 0) {
                // Pick correct conjugation from different declension
                alt = declineWord(casus, rand.nextInt(5) + 1, word);
            } else if (shuffle == 1) {
                // Pick wrong conjugation from same declension
                alt = declineWord(rand.nextInt(5) + (casus >= 5 ? 5 : 0), declension, word);
            }

            if(!alternatives.contains(alt))
                alternatives.add(alt);

            attempts += 1;
            if (attempts > 1000) {
                Log.wtf("LatinPractice", "Cannot find 4 alternatives");
                break;
            }
        }

        return new String[] {alternatives.get(0), alternatives.get(1), alternatives.get(2), alternatives.get(3)};
    }

    @Override
    public Exercise GetExercise() {

        int randCaseMin = singularEnabled ? 0 : 5;
        int randCaseMax = pluralEnabled ? 10 : 5;
        int declension = declensions.get(rand.nextInt(declensions.size()));
        int casus = rand.nextInt(randCaseMax - randCaseMin) + randCaseMin;

        String word = exampleWords.get(declension).get(rand.nextInt(exampleWords.get(declension).size()));
        String answer = declineWord(casus, declension, word);
        String[] alternatives = findAlternatives(casus, declension, word);

        String sentence = String.format(exampleSentences.get(getCaseOrdinal(casus)), "______");
        boolean answerFound = false;
        for(int i = 0; i < 4; i++)
            if(alternatives[i].equals(answer))
                answerFound = true;

        if(!answerFound)
            alternatives[rand.nextInt(4)] = answer;

        String extra = filterWord(word) + " → " + getCaseText(casus) + " " + (casus >= 5 ? "Plural" : "Singular");

        return new Exercise(sentence, answer, alternatives, extra);
    }
}
