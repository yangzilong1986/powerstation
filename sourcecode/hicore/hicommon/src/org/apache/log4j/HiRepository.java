package org.apache.log4j;

import java.util.Enumeration;

public class HiRepository extends Hierarchy {
    public HiRepository(Logger root) {
        super(root);
    }

    public void closeLogger(String name) {
        CategoryKey key = new CategoryKey(name);
        Object o = this.ht.get(key);
        if (!(o instanceof Logger)) return;
        Enumeration enumeration = ((Logger) o).getAllAppenders();
        while ((enumeration != null) && (enumeration.hasMoreElements())) {
            Appender a = (Appender) enumeration.nextElement();
            a.close();
        }

        this.ht.remove(key);
    }

    public int getLoggerCount() {
        return this.ht.size();
    }
}