package sample;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class Vocabulary {
    String file;

    /**
     * Read the dictionary file
     * @param file
     */
    public Vocabulary(String file) {
        this.file = file;
    }

    /**
     * Store the dictionary using data Structure Trie node
     * @param parent
     */
    public void vocabInTrie(TrieNode parent) {
        InputStream path = this.getClass().getResourceAsStream(file);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(path));
            String line = reader.readLine();
            while(line != null) {
                if(!line.isEmpty()) {
                    parent.insert(parent, line);
                }
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}

