package com.countingwords;

import com.countingwords.rest.CountingWordsController;
import com.countingwords.word.WordFrequency;
import com.countingwords.word.WordFrequencyAnalyzer;
import com.countingwords.word.WordFrequencyAnalyzerImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;

import java.util.Arrays;

@SpringBootTest
class CountingWordsApplicationTests {

    @Autowired
    private CountingWordsController countingWordsController;

    @Autowired
    WordFrequencyAnalyzer wordFrequencyAnalyzer;

    @Test
    void contextLoads() {
        //Test that the app starts.
        Assert.assertTrue(ObjectUtils.isNotEmpty(countingWordsController));
    }

}
