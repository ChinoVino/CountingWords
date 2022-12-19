package com.countingwords.rest;

import com.countingwords.model.*;
import com.countingwords.word.WordFrequency;
import com.countingwords.word.WordFrequencyAnalyzer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/countingwords")
public class CountingWordsController {
    private static Logger log = Logger.getLogger(CountingWordsController.class.getName());
    private WordFrequencyAnalyzer wordFrequencyAnalyzer;

    public CountingWordsController(WordFrequencyAnalyzer wordFrequencyAnalyzer) {
        this.wordFrequencyAnalyzer = wordFrequencyAnalyzer;
    }

    @PostMapping(path= "/highest-frequency", consumes = "application/json", produces = "application/json")
    public HighestFrequencyResponse calculateHighestFrequency(@RequestBody HighestFrequencyRequest request) {
        log.log(Level.INFO, "calculateHighestFrequency request");
        Integer frequency = wordFrequencyAnalyzer.calculateHighestFrequency(request.getText());

        HighestFrequencyResponse response = new HighestFrequencyResponse();
        response.setFrequency(frequency);
        return response;
    }

    @PostMapping(path= "/frequency-for-word", consumes = "application/json", produces = "application/json")
    public WordFrequencyResponse calculateFrequencyForWord(@RequestBody WordFrequencyRequest request) {
        log.log(Level.INFO, "calculateFrequencyForWord request");
        Integer frequency = wordFrequencyAnalyzer.calculateFrequencyForWord(request.getText(), request.getWord());

        WordFrequencyResponse response = new WordFrequencyResponse();
        response.setFrequency(frequency);
        return response;
    }

    @PostMapping(path= "/most-frequent-n-words", consumes = "application/json", produces = "application/json")
    public MostFrequentNWordsResponse calculateMostFrequentNWords(@RequestBody MostFrequentNWordsRequest request) {
        log.log(Level.INFO, "calculateMostFrequentNWords request");
        WordFrequency[] wordFrequencyList = wordFrequencyAnalyzer.calculateMostFrequentNWords(request.getText(), request.getAmountOfWords());

        MostFrequentNWordsResponse response = new MostFrequentNWordsResponse();
        response.setWordFrequency(Arrays.stream(wordFrequencyList).map(m -> new com.countingwords.model.WordFrequency().word(m.getWord()).frequency(m.getFrequency())).collect(Collectors.toList()));
        return response;
    }
}
