package bamika.misc;

public class SaveData {
    public static SaveData saveData = null;

    public int tmp;

    public SaveData() {
        tmp = 0;
    }

    public static SaveData save() {
        if (saveData == null)
            saveData = new SaveData();

        return saveData;
    }

    public static void load(SaveData saveData) {
        if (saveData == null) SaveData.saveData = new SaveData();
        else SaveData.saveData = saveData;
    }
}
