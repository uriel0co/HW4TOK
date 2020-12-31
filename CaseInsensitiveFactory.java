//this class builds an object of tye CaseInsensitive. 
public class CaseInsensitiveFactory extends AbstractInvertedIndexFactory {
    /**
     * this function builds an object of type CaseInsensitive.
     * @return - an object of type CaseInsensitive.
     */
    @Override
    public CaseInsensitive createInvertedIndex(){
        return CaseInsensitive.getInstance();
    }
}