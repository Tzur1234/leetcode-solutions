package leetcode.solutions.graphs;

import java.util.*;

public class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // step one create the adjacent map
        Map<String, List<String>> map = new HashMap<>();
        wordList.add(beginWord);

        for (String word : wordList){
            List<String> variations = createVariations(word);
            addVariationsToMap(map, variations, word);
        }

        // step 2 : BFS over the adjacent map
        int levels = 1;
        Queue<String> queue = new ArrayDeque<>();
        queue.add(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);

        while (!queue.isEmpty()){
            int qSize = queue.size();
            for (int i = 0; i < qSize; i++) {
                String word = queue.poll();
                if(word.equals(endWord)) return levels;
                List<String> neighbours = findNeighbours(map, word, visited);
                queue.addAll(neighbours);

            }
            levels++;
        }

        return 0;
    }


    private List<String> findNeighbours(Map<String, List<String>> map,
                                String word, Set<String> visited){
        // search for all the neighours of the 'word'
        // do not add words that was already visited
        // once it found a neighbours - remove the 'word' itself
        Set<String> neighbours = new HashSet<>();
        List<String> variations = createVariations(word);

        for (String var : variations){
            neighbours.addAll(map.get(var));
        }

        neighbours.remove(word);
        neighbours.removeIf(item -> visited.contains(item));// remove all visited
        visited.addAll(neighbours); // mark the neighbours as visited
        return new ArrayList<>(neighbours);
    }

    private List<String> createVariations(String word){
        List<String> variations = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            StringBuilder var = new StringBuilder(word);
            var.setCharAt(i, '*');
            variations.add(var.toString());
        }
        return variations;
    }

    private void addVariationsToMap(Map<String, List<String>> map, List<String> varitatios, String word){
        for (String var : varitatios){
            if(!map.containsKey(var)) map.put(var, new ArrayList<>());
            map.get(var).add(word);
        }
    }

}
