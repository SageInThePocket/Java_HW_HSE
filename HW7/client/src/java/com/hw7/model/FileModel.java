package com.hw7.model;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileModel implements Serializable {
    private long size;
    private String name;
    private Date editDate;
    private String type;
    //Image

    public FileModel(File file) {
        if (!file.isFile())
            throw new IllegalArgumentException();

        name = file.getName();
        editDate = new Date(file.lastModified());
        size = file.length();
        type = getType(name);
    }

    public FileModel(String name, Date editDate, long size) {
        String[] splitName = name.split("\\.");
        this.name = splitName[0];
        this.editDate = editDate;
        this.size = size;
        if (splitName.length > 1)
            type = splitName[splitName.length - 1];
        else
            type = "Unknown";
    }

    public FileModel(String[] data) {
        if (!checkData(data))
            throw new IllegalArgumentException("Invalid data");

        String[] splitName = data[0].split("\\.");
        this.name = splitName[0];
        this.editDate = new Date(Long.parseLong(data[1]));
        this.size = Long.parseLong(data[2]);
        if (splitName.length > 1)
            type = splitName[splitName.length - 1];
        else
            type = "Unknown";
    }

    private static boolean checkData(String[] data) {
        return data.length == 3 && data[0] != null &&
                data[1] != null && data[2] != null &&
                TryParseToLong(data[1]) && TryParseToLong(data[2]);
    }

    private static boolean TryParseToLong(String num) {
        try {
            Long.parseLong(num);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    private static String getType(String name) {
        String[] splitArr = name.split("\\.");
        return splitArr[splitArr.length - 1];
    }

    public long getSizeBytes() {
        return size;
    }

    public Double getSizeKbs() {
        return getSizeBytes() / 1024.0;
    }

    public Double getSizeMbs() {
        return getSizeKbs() / 1024.0;
    }

    public Double getSizeGbs() {
        return getSizeMbs() / 1024.0;
    }

    public String getType() {
        return type;
    }

    public Date getEditDate() {
        return editDate;
    }

    public String getName() {
        return name;
    }

    public String getSizeWithUnits() {
        if (getSizeKbs() < 1)
            return String.format("%d B", size);
        if (getSizeMbs() < 1)
            return String.format("%d Kb", Math.round(getSizeKbs()));
        if (getSizeGbs() < 1)
            return String.format("%d Mb", Math.round(getSizeMbs()));
        else
            return String.format("%d Gb", Math.round(getSizeGbs()));
    }

    public String getStringDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        return format.format(editDate);
    }

    public String getFullName() {
        return name + "." + type;
    }
}
