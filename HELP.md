# Read Me First

To run this application, in your terminal navigate to the project and run 
"mvn clean compile -DskipTests" followed by "mvn spring-boot:run"

#### There are 3 rest apis available to call:
* http://localhost:8080/countingwords/highest-frequency
* http://localhost:8080/countingwords/frequency-for-word
* http://localhost:8080/countingwords/most-frequent-n-words

sample requests
* highest-frequency - {"text": "The sun shines over the lake"}
* frequency-for-word - {"text": "The sun sun will shine on us","word": "SUN"}
* most-frequent-n-words - {"text": "The sun will shine on us,the","amountOfWords": 2}