package db;

import java.util.Date;

public interface Trackable {
    Date creationDate = null; // لازم نیست private باشه؟
    Date lastModificationDate = null;

    void setCreationDate(Date date);
    Date getCreationDate();

    void setLastModificationDate(Date date);
    Date getLastModificationDate();
}