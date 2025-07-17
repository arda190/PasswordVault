import java.io.*;
import java.security.*;
import java.util.*;


public class FolderIntegrity {
    static String userHome=System.getProperty("user.home");
    static String outputPath=userHome+File.separator+"myapp"+File.separator+"integrity_snapshot.txt";

    public static void fileIntegritySave(String folderPath){
        try{
            File file=new File(folderPath);
            File[] files=file.listFiles();
            File writeFile=new File(outputPath);
            writeFile.getParentFile().mkdirs();
            FileWriter fw=new FileWriter(outputPath,false);
            String output="";

            for(File f:files){
                FileReader fr=new FileReader(f);
                int c;
                StringBuilder sb=new StringBuilder();
                while((c= fr.read())!=-1){
                    sb.append((char)c);
                }
                MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
                messageDigest.update(sb.toString().getBytes());
                byte[] digest=messageDigest.digest();
                StringBuilder sdigest=new StringBuilder();
                for(byte b:digest){
                    sdigest.append(String.format("%02x",b));
                }
                output+=f.getName()+"\t"+sdigest.toString()+"\n";
                sb.setLength(0);
                sdigest.setLength(0);
            }
            fw.write(output);
            fw.close();


        }catch(Exception e){
            System.out.println("Exception occured.");
        }
    }

    public static void fileIntegrityChecker(String folderPath){
        try {
            HashMap<String, String> hashValues = fileIntegrityLoad(outputPath);
            ArrayList<String> changes = new ArrayList<>();
            File file = new File(folderPath);
            File[] files = file.listFiles();
            FileReader fr = null;
            for (File f : files) {
                if (hashValues.containsKey(f.getName())) {
                    fr = new FileReader(f);
                    int c;
                    StringBuilder sb = new StringBuilder();
                    while((c= fr.read())!=-1){
                        sb.append((char)c);
                    }
                    MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
                    messageDigest.update(sb.toString().getBytes());
                    byte[] digest=messageDigest.digest();
                    StringBuilder sdigest=new StringBuilder();
                    for(byte b:digest){
                        sdigest.append(String.format("%02x",b));
                    }
                    if(!hashValues.get(f.getName()).equals(sdigest.toString())){
                        changes.add("FILE MODIFIED:"+f.getName());
                    }
                }
                else{
                    changes.add("NEW FILE:"+f.getName());
                }
            }
            if(changes.isEmpty()){
                System.out.println("All files are same with snapshot");
            }
            else{
                System.out.println("Integrity Check Failed.The Following Changes Were Detected:");
                for(String change:changes){
                    System.out.println(change);
                }
            }


        }catch(Exception e){
            System.out.println("Exception occured");
        }

    }

    private static HashMap<String,String> fileIntegrityLoad(String folderPath){
        HashMap<String,String> hashValues=new HashMap<>();
        try{
            Scanner sc=new Scanner(new File(folderPath));
            while(sc.hasNext()){
                String line=sc.nextLine();
                String[] parts=line.split("\t");
                hashValues.put(parts[0],parts[1]);
            }
            sc.close();
        }catch (Exception e){
            System.out.println("Exception occured.");
        }
        return hashValues;
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String folderPath;
        System.out.println("Welcome to Folder Integrity Checker");
        while(true){
            System.out.println("\nPlease choose an option:");
            System.out.println("1.Create a new integrity snapshot");
            System.out.println("2.Verify files against existing snapshot");
            System.out.println("Exit");
            System.out.println("What is your choice?");
            String choice=scanner.nextLine();

            switch (choice){
                case "1":
                    System.out.println("Enter the full path of the folder to snapshot:");
                    folderPath=scanner.nextLine();
                    fileIntegritySave(folderPath);
                    break;
                case "2":
                    System.out.println("Enter the full path of the folder to verify:");
                    folderPath=scanner.nextLine();
                    fileIntegrityChecker(folderPath);
                    break;
                case "3":
                    System.out.println("Thank you for using Integrity Checker.Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option");
            }


        }

    }



}
