//this class builds an object of tye CaseSensitive.
public class CaseSensitiveFactory extends AbstractInvertedIndexFactory {
    /**
     * this function builds an object of type CaseSensitive.
    * @return - an object of type CaseSensitive.
     **/
    @Override
    public CaseSensitive createInvertedIndex(){
        return CaseSensitive.getInstance();
    }
}