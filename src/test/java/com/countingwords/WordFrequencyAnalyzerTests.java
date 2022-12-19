package com.countingwords;

import com.countingwords.model.*;
import com.countingwords.word.WordFrequencyAnalyzer;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CountingWordsApplication.class)
@WebAppConfiguration
public class WordFrequencyAnalyzerTests{

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    WordFrequencyAnalyzer wordFrequencyAnalyzer;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    //Test for highest frequency
    @Test
    public void calculateHighestFrequency() {

        HighestFrequencyRequest request = new HighestFrequencyRequest().text("THe sun shines. The on us.");
        HighestFrequencyResponse response = new HighestFrequencyResponse();

        try {
            //REST call countingwords/highest-frequency endpoint
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/countingwords/highest-frequency")
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(request))).andReturn();

            String responseString = result.getResponse().getContentAsString();
            System.out.println(responseString);

            //map the response string to the actual rest call response
            response = mapFromJson(responseString, HighestFrequencyResponse.class);
        }catch(Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(response.getFrequency() == 2);
    }

    //Test for frequency of a specific word
    @Test
    public void calculateFrequencyForWord(){

        WordFrequencyRequest request = new WordFrequencyRequest().text("THe sun shines. The on us.").word("thE");
        WordFrequencyResponse response = new WordFrequencyResponse();

        try {
            //REST call countingwords/frequency-for-word endpoint
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/countingwords/frequency-for-word")
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(request))).andReturn();

            String responseString = result.getResponse().getContentAsString();
            System.out.println(responseString);

            //map response string to rest call response object
            response = mapFromJson(responseString, WordFrequencyResponse.class);
        }catch(Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(response.getFrequency() == 2);
    }

    //Test for frequency of n number of words
    @Test
    public void calculateMostFrequentNWords(){

        MostFrequentNWordsRequest request = new MostFrequentNWordsRequest().text("THe sun shines. The on us.").amountOfWords(2);
        MostFrequentNWordsResponse response = new MostFrequentNWordsResponse();

        try {
            //REST call countingwords/most-frequent-n-words endpoint
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/countingwords/most-frequent-n-words")
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(request))).andReturn();

            String responseString = result.getResponse().getContentAsString();
            System.out.println(responseString);

            //map response string to rest call response object
            response = mapFromJson(responseString, MostFrequentNWordsResponse.class);
        }catch(Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(!response.getWordFrequency().isEmpty());
    }

    protected <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

}
