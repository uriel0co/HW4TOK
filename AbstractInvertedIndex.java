import java.io.File;
import java.util.TreeSet;
//this class builds the inverted index and return the answer to the query.
public abstract class AbstractInvertedIndex {
    abstract void buildInvertedIndex(File[] files);
    abstract TreeSet<String> runQuery(String query);
}
