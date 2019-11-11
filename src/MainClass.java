import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

class MainClass {
    public static void main(String[] args) throws Exception {
        System.out.println(Objects.requireNonNull(new File(args[0]).listFiles())[0]);

        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";

        File dir = new File(args[0]);

        Long r = Long.parseLong(args[1].substring(0,2), 16);
        Long g = Long.parseLong(args[1].substring(2,4),16);
        Long b = Long.parseLong(args[1].substring(4,6),16);
        double rd = Double.parseDouble(r.toString())/255;
        double gd = Double.parseDouble(g.toString())/255;
        double bd = Double.parseDouble(b.toString())/255;

        System.out.println(ANSI_YELLOW + "colors will be replaced with: " + rd + "," + gd + "," + bd +",1" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "files found in dir " + dir.getPath() + ANSI_RESET);

        for(String fileNames : Objects.requireNonNull(dir.list())) System.out.println(fileNames);

        int i = 1;
        for (File file : Objects.requireNonNull(dir.listFiles())) {

            System.out.print("convert " + file.getName() + " " + ANSI_RED + i + "/" + Objects.requireNonNull(dir.listFiles()).length + ANSI_RESET);
            Scanner s = new Scanner(file);
            byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));

            String st = new String(encoded, StandardCharsets.UTF_8);
            FindColor findcolor = new FindColor(file);
            Set<String> colorArray = findcolor.find(findcolor.jsonObject);
            colorArray.remove("[1,1,1,1]"); //exclude more colors here
            System.out.println("Colorarray: " + colorArray);

            for(String color : colorArray) {
                st = st.replace(color, "["+rd+","+gd+","+bd+",1]");
            }
            FileWriter writer = new FileWriter(file.getName());
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(st);
            bw.close();
            s.close();
            System.out.print(ANSI_GREEN + " ...complete" + ANSI_RESET + "\n");
            i++;
            FindColor.out.clear();
            }
        }
    }