package com.countingwords.word;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service(value = "wordFrequencyAnalyzer")
public class WordFrequencyAnalyzerImpl implements WordFrequencyAnalyzer {

    @Override
    public int calculateHighestFrequency(String text) {

        Map<String, Long> counter = new HashMap<>();
        //stream the list of words and group them in a map containing the word and their number of occurrences
        getWordList(text).stream().collect(Collectors.groupingBy(word -> word.toLowerCase(), () -> counter, Collectors.counting()));

        //return the word with the most occurrences
        return Collections.max(counter.values()).intValue();
    }

    @Override
    public int calculateFrequencyForWord(String text, String word) {

        Multiset<String> counter = HashMultiset.create();
        //for each word, add it to a multiset which will keep tract of each word and their occurrence
        getWordList(text).forEach(w -> counter.add(w.toLowerCase()));

        //return the number of occurrences for a specific word
        return counter.count(word.toLowerCase());
    }

    @Override
    public WordFrequency[] calculateMostFrequentNWords(String text, int n) {

        Map<String, Long> counter = new TreeMap<>();
        //stream the list of words and group them in a tree map containing the word and their number of occurrences.
        //this is to sort it in alphabetical order
        getWordList(text).stream().collect(Collectors.groupingBy(w -> w.toLowerCase(), () -> counter, Collectors.counting()));

        //sort the map by number of occurrences from most to least and limit it based on the number of words you want.
        //store this in a new map
        Map<String, Long> orderedCounter = counter.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(n).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        //from the ordered list, add all the items to a list of WordFrequency objects
        List<WordFrequency> wordFrequencyList = new ArrayList<>();
        orderedCounter.entrySet().stream().forEach(e -> wordFrequencyList.add(new WordFrequencyImpl(e.getKey(), e.getValue().intValue())));

        return wordFrequencyList.toArray(WordFrequency[]::new);
    }

    public List<String> getWordList(String text) {
        //split text string and store in a list of strings
        List<String> words = new ArrayList<String>(Arrays.asList(text.trim().split("[^a-zA-Z]")));
        //remove any unwanted empty items caused by a space following a fullstop
        words.removeAll(Collections.singleton(""));

        return words;
    }

}
