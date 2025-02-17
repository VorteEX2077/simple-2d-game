import java.io.*;

public class FileHandler {
    public int scoreFromFile;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    String fileToResolution;
    String fileToSkin;
    int heightFromFile;
    int widthFromFile;

    FileHandler() {
        heightFromFile = 600;
        widthFromFile = 800;
        readFile();
    }

    public void highScoreToFile(int hScore) {
        //System.out.println("writing to file:" + hScore  + " " + scoreFromFile);
        if (hScore > scoreFromFile) {
            try {
                bufferedWriter = new BufferedWriter(new FileWriter("save_file.txt"));
                bufferedWriter.write("" + hScore + "," + fileToResolution);
                bufferedWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // TODO: save settings to file
        System.out.println("data written to file");
    }

    public void writeToFile(String res, String skin) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("save_file.txt"));
            bufferedWriter.write(getHighScore() + "," + res + "," + skin);
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public int getHighScore() {
        return scoreFromFile;
    }

    public void readFile() {
        String[] fromFile;
        File fileObj = new File("save_file.txt");
        if (fileObj.exists()) {
            /* READING THE FILE */
            try {
                FileReader fileReader = new FileReader(fileObj);
                bufferedReader = new BufferedReader(fileReader);
                String line = bufferedReader.readLine();
                if (line == null) {
                    scoreFromFile = 0;
                } else {
                    fromFile = line.split(",");
                    scoreFromFile = Integer.parseInt(fromFile[0]);
                    if (fromFile.length > 1)
                        fileToResolution = fromFile[1];
                    if (fromFile.length > 2)
                        fileToSkin = fromFile[2].trim();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (fileToResolution == null) fileToResolution = "600 x 800";
            if (fileToResolution.equals("1920 x 1080")) {
                widthFromFile = 1920;
                heightFromFile = 1080;
            } else if (fileToResolution.equals("1280 x 720")) {
                heightFromFile = 720;
                widthFromFile = 1280;
            }
        }
    }
}
