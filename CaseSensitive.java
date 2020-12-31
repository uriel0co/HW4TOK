import java.io.File;
import java.util.*;
/**
 * this class creat a single object of type CaseSensitive, builds the sensitive case inverted index and answer to a
 * given query.
 * @param - caseSensitiveInvertedIndex - the single object created by the function getInstance.
 */
public class CaseSensitive extends AbstractInvertedIndex {
    private static HashMap<String, TreeSet> invertedIndexMap = new HashMap<>();
    private static CaseSensitive caseSensitiveInvertedIndex;
    private CaseSensitive(){}

    /**
     * this function creats a single object of type CaseSensitive.
     *
     * @return caseSensitiveInvertedIndex - an object of type CaseSensitive.
     */
    public static CaseSensitive getInstance(){
        if(caseSensitiveInvertedIndex == null){
            caseSensitiveInvertedIndex = new CaseSensitive();
            System.out.println("New CaseSensitive index is created");
        } else{
            System.out.println("You already have a CaseSensitive index");
        }
        return caseSensitiveInvertedIndex;
    }
    /**
     * this function builds the sensitive case inverted index.
     *
     * @parm invertedIndexMap - a hashmap that its keys are the words in the documents,
     * the value of a key is a treeset with the names of the documents that contain the key.
     * @parm flag - true if the line is part of the text, else otherwise.
     **/

    public void buildInvertedIndex(File[] listOfFiles) {
        // create an empty hash map
        boolean flag = false;
        String fileName = null;
        for (File file : listOfFiles) {
            for (String line : Utils.readLines(file)) {
                if (line.length() > 6 && line.substring(0, 7).equals("<DOCNO>")) {
                    fileName = Utils.substringBetween(line, "<DOCNO> ", " </DOCNO>");
                }
                if (line.equals("</TEXT>")) {
                    flag = false;
                }
                if (flag == true){
                    for (String word : Utils.splitBySpace(line)){
                        if (invertedIndexMap.containsKey(word)){
                            invertedIndexMap.get(word).add(fileName);
                        }
                        else {
                            TreeSet ts = new TreeSet();
                            ts.add(fileName);
                            invertedIndexMap.put(word, ts);
                        }
                    }
                }
                if (line.equals("<TEXT>")) {
                    flag = true;
                }
            }
        }
    }
    /** this function returns the answer to the query.
     *
     * @param query - a String with requirements to the content of the files.
     * @param a,b -the two last treesets that we put in the stack.
     * @return a - a treeset of the names of the files that match the requirements in a given  query.
     */
    public TreeSet<String> runQuery(String query) {
        int wordCount = 0;
        List <String> opertors = new ArrayList<>();
        TreeSet <String> a = new TreeSet<>();
        TreeSet <String> b;
        opertors.addAll(Arrays.asList("AND","OR","NOT"));
        Stack <TreeSet> stack = new Stack<>();
        for(String queryOrOperator : Utils.splitBySpace(query)){
            if(!opertors.contains(queryOrOperator)){
                wordCount ++;
                stack.push(invertedIndexMap.get(queryOrOperator));
            }
            else{
                b = new TreeSet<String>(stack.pop());
                a = new TreeSet<String>(stack.pop());
                String operator = queryOrOperator;
                switch (operator){
                    case "NOT":
                        a.removeAll(b);
                        break;
                    case "AND":
                        a.retainAll(b);
                        break;
                    case "OR":
                        a.addAll(b);
                        break;
                }
                stack.push(a);
            }
        }
        if(wordCount == 1){
            a = new TreeSet<String>(stack.pop());
        }
        return a;
    }
}