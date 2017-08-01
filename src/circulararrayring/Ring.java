package circulararrayring;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Galvin on 2/26/2015.
 */
public interface Ring<T> extends Collection<T> {
    public T get(int index) throws IndexOutOfBoundsException;

    public Iterator<T> iterator();

    public int size();


}
