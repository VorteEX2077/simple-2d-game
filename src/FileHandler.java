import java.io.*;

public class FileHandler {
    File fileObj;
    private int score2;
    BufferedReader bufferedReader;
    FileHandler(){
        try {
            fileObj = new File("safe_file.txt");
            bufferedReader = new BufferedReader(new FileReader(fileObj));

            if(fileObj.exists()){
                readFile();
            }
            else{
                boolean isFileCreated = fileObj.createNewFile();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void highScoreToFile(){
        // TODO: Write hight scroe to the file.
        // TODO: save settings to file
    }

    public int getHighScore() {
        return score2;
    }

    private void readFile(){
        try {
        score2 = Integer.parseInt(bufferedReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
