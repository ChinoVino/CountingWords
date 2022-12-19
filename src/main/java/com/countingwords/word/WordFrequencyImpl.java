package com.countingwords.word;

public class WordFrequencyImpl implements WordFrequency {

    private String word;
    private int frequency;

    public WordFrequencyImpl(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public int getFrequency() {
        return frequency;
    }

    //toString method to display word and frequency easier
    @Override
    public String toString() {
        return word + "-" + frequency;
    }
}
