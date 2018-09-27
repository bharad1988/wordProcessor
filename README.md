# Word Processor with a brain
A dictionary with machine learning -
- adds words to dictionary
- lookup words.
- if not found suggests best ranked words (like autocomplete or did you mean way) upto 4 words(configurable )
- learns about words that are searched and ranks the words. 
suggests best ranked words related to the searches. As the dictionary gets looked up for more and more words, the ranking improves for each searched word. 
The basis of the projected is Trie data structure with dynamically increasing children on each node(as as when the new characters get added). 

Autocomplete simulation:
If the words are partially typed and entered, the best suggestions based on the history will be made to complete the word.
The dictionary is constantly being curated to update the rankings.
Top 3-4 words will be suggested each time a word is not found. (Based on the availability of words)

The data is regularly written on to a pesistant store from which the dictionary will be loaded on start..

*** ToDo ***
Automated curation running in back ground.
Automated write back to persistant store.
Better file handling. At the monemt serializing and deserializing not the most efficient.

## Rudimentary interface 
### Examples 

```
Enter an option
1. To Add a word
2. To lookup a word
3. To Exit
1
Enter a word to be added
fantastic
Enter the meaning to be added
extraordinarily good or attractive
1. To Add a word
2. To lookup a word
3. To Exit
2
Enter a word to be looked up
fantas
"fantas": Not Found..
Try the following: 
fantastic
1. To Add a word
2. To lookup a word
3. To Exit
2
Enter a word to be looked up
fantastic
fantastic : extraordinarily good or attractive
1. To Add a word
2. To lookup a word
3. To Exit
2
Enter a word to be looked up
be
"be": Not Found..
Try the following: 
bezos
bent
being
bear
1. To Add a word
2. To lookup a word
3. To Exit
2
Enter a word to be looked up
bezos
bezos : amazon guy
1. To Add a word
2. To lookup a word
3. To Exit
```



