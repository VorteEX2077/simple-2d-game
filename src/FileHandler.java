import java.io.*;

public class FileHandler {
    File fileObj;
    public int scoreFromFile;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    FileHandler(){
            readFile();
    }

    public void highScoreToFile(int hScore){
        System.out.println("writing to file:" + hScore  + " " + scoreFromFile);
        if (hScore > scoreFromFile){
            try {
                bufferedWriter = new BufferedWriter(new FileWriter("safe_file.txt"));
                bufferedWriter.write("" + hScore);
                bufferedWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // TODO: Write high score to the file.
        // TODO: save settings to file
    }

    public int getHighScore() {
        readFile();
        return scoreFromFile;
    }

    private void readFile(){
        try {
            bufferedReader = new BufferedReader(new FileReader("safe_file.txt"));
            String line = bufferedReader.readLine();
            if(line == null){
                scoreFromFile = 0;
            }
            else{
                scoreFromFile = Integer.parseInt(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
