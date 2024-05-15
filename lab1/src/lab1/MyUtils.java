package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MyUtils {
    public static String[] readFile(String fileName) {
        ArrayList<String> wordList = new ArrayList<>();

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            // 使用正则表达式替换非字母字符为空格，并且分割成单词
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().toLowerCase();
                line = line.replaceAll("[^a-zA-Z]", " ");
                String[] words = line.split("\\s+");
                wordList.addAll(Arrays.asList(words));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return wordList.toArray(new String[0]);
    }

    public static void writeWalkToFile(String walk, String path) {
        try {
            File file = new File(path);
            java.io.PrintWriter output = new java.io.PrintWriter(file);
            output.print(walk);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 检查文件夹是否存在，若不存在则创建。
    public static void checkAndCreateFolder(String path) {
        File outputFolder = new File(path);
        if (!outputFolder.exists()) {
            boolean created = outputFolder.mkdirs();
            if (created) {
                System.out.println("文件夹\"" + path + "\"已成功创建。");
            } else {
                System.out.println("文件夹\"" + path + "\"创建失败。");
            }
        } else {
            System.out.println("文件夹\""+ path + "已存在。");
        }
    }
}