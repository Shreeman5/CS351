package sample;

import java.util.ArrayList;

public class TrieNode {

    int size = 26;
    TrieNode[] parent = new TrieNode[size];
    boolean validity;
    ArrayList<String> words = new ArrayList<>();


    /**
     * initiate TrieNode
     */
    public TrieNode() {
        validity = false;
        for(int i = 0; i < size; i ++) {
            parent[i] = null;
        }
    }

    /**
     * Store the words in the dictionary
     * @param root
     * @param word
     */
    public void insert(TrieNode root, String word) {
        TrieNode trieNode = root;
        for(int i = 0; i < word.length(); i++) {
            int index = Character.toLowerCase(word.charAt(i)) - 'a';

            if(trieNode.parent[index] == null) {
                trieNode.parent[index] = new TrieNode();
            }

            trieNode = trieNode.parent[index];
        }
        trieNode.validity = true;
    }

    /**
     * Check if entered word is true
     * @param word
     * @param parent
     * @return
     */
    public boolean validity(String word, TrieNode parent) {
        TrieNode trieNode = parent;
        for(int i = 0; i < word.length(); i ++) {
            int letter = Character.toLowerCase(word.charAt(i)) - 'a';

            if(trieNode.parent[letter] == null) {
                return false;
            }
            trieNode = trieNode.parent[letter];
        }

        if(trieNode.validity) {
            words.add(word);
            return true;
        }
        return false;
    }
}
