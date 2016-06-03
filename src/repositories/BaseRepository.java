package repositories;

import models.BaseModel;

import java.io.*;
import java.util.ArrayList;

public abstract class BaseRepository<T extends BaseModel>  {
    protected final Class<T> tClass;
    protected  String filePath;

    protected BaseRepository(String filePath, Class<T> tClass) {
        this.filePath = filePath;
        this.tClass = tClass;
    }

    protected abstract void readItem(BufferedReader br, T item);
    protected  abstract void writeItem(PrintWriter pw, T item);

    public T getById(int id) {
        return this.getAll().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }

    public ArrayList<T> getAll() {
        ArrayList<T> items = new ArrayList<T>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))){
            String line;

            while((line = br.readLine()) != null) {
                T item = getInstance();
                item.setId(Integer.parseInt(line));

                readItem(br, item);
                items.add(item);
            }
        } catch (FileNotFoundException fex) {
            createFile();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return items;
    }

    public void insert(T item) {
        item.setId(getNextId());

        try (PrintWriter pw = new PrintWriter(new FileWriter(this.filePath, true))) {
            writeItem(pw, item);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void update(T item) {
        String tempFilePath = "temp-" + this.filePath;
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(tempFilePath, true))) {
                String line;

                while ((line = br.readLine()) != null) {
                    T currentItem = getInstance();
                    currentItem.setId(Integer.parseInt(line));
                    readItem(br, currentItem);

                    if (currentItem.getId() == item.getId()) {
                        writeItem(pw, item);
                    } else {
                        writeItem(pw, currentItem);
                    }
                }
            }
        } catch (FileNotFoundException fex) {
            createFile();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        File oldFile = new File(this.filePath);
        oldFile.delete();

        File newFile = new File(tempFilePath);
        newFile.renameTo(oldFile);
    }

    public void delete(T item) {
        String tempFilePath = "temp-" + this.filePath;
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(tempFilePath, true))) {
                String line;

                while ((line = br.readLine()) != null) {
                    T currentItem = getInstance();
                    currentItem.setId(Integer.parseInt(line));
                    readItem(br, currentItem);

                    if (currentItem.getId() != item.getId()) {
                       writeItem(pw, currentItem);
                    }
                }
            }
        } catch (FileNotFoundException fex) {
            createFile();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        File oldFile = new File(this.filePath);
        oldFile.delete();

        File newFile = new File(tempFilePath);
        newFile.renameTo(oldFile);
    }

    private void createFile() {
        File file = new File(this.filePath);

        try {
            file.createNewFile();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        }
    }

    private int getNextId() {
        int id = 0;
        ArrayList<T> items = this.getAll();

        for (T item : items) {
            id = item.getId();
        }

        return id + 1;
    }

    private T getInstance() {
        try {
            return tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}