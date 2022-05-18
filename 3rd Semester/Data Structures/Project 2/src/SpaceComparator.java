

import java.util.Comparator;

public class SpaceComparator implements Comparator<Disk> {
    @Override
    public int compare(Disk d1, Disk d2) {
        return d1.getFreeSpace() - d2.getFreeSpace();
    }
}
