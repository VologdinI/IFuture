package net.proselyte.bookmanager.logic;

import java.io.File;

public class TextSearcherFileIsString {
    File file;
    boolean isContainsRequiredString;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isContainsRequiredString() {
        return isContainsRequiredString;
    }

    public void setContainsRequiredString(boolean containsRequiredString) {
        isContainsRequiredString = containsRequiredString;
    }
}
