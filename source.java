import java.io.IOException;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;


public class source {

    public static boolean boo = true;

    public static List<Block> blockchain = new ArrayList<>();
    public static int prefix = 4;
    public static String prefixString = new String(new char[prefix]).replace('\0', '0');

    public static class Block {
        public String hash;
        public String previousHash;
        public String data;
        public long timeStamp;
        public int nonce;
    }
        
    public static Block Block(String data, String previousHash, long timeStamp)  {
        Block b = new Block();
        b.data = data;
        b.previousHash = previousHash;
        b.timeStamp = timeStamp;
        b.nonce = 0;
        b.hash = calculateBlockHash(b);
        return b;
    }
    
    public static String mineBlock(int prefix, Block bl)  throws IOException, InterruptedException  {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        //Block post = blockchain.get(blockchain.size() - 1);
        while (!bl.hash.substring(0, prefix).equals(prefixString)) {
            bl.nonce+=1;
            String temp = calculateBlockHash(bl);
            bl.hash = temp;
            //System.out.println(temp);
            //console();    
        }
        //System.out.println(bl.nonce);
        return bl.hash;
    }

    public static String calculateBlockHash(Block b) {
        String dataToHash = b.previousHash + Long.toString(b.timeStamp) + Integer.toString(b.nonce) + b.data;
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes("UTF8"));
            //System.out.println("Try");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            //System.out.println("error");
        }
        StringBuffer buffer = new StringBuffer();
        for (byte bb : bytes) {
            buffer.append(String.format("%02x", bb));
        }
        //System.out.println(buffer);
        return buffer.toString();

    }
    

    public static void newBlock(String name)  throws IOException, InterruptedException  {
        String data = name;
        String previous_Hash = blockchain.get(blockchain.size() - 1).hash;
        long date = new Date().getTime();

        Block newBlock = Block(data, previous_Hash, date);

        //System.out.println("\nPré:"+newBlock.hash);
        //System.out.println("Previous Hash:::: ->" + previous_Hash + "------");
        newBlock.hash = mineBlock(prefix, newBlock);
        
        //System.out.println("Post:"+newBlock.hash+"\n");

        assertTrue(newBlock.hash.substring(0, prefix).equals(prefixString));
        verify();
        blockchain.add(newBlock);
        //System.out.println(" Hash::::: ->" +blockchain.get(blockchain.size() - 1).hash);
        //System.out.println("---------------------------------------");
    }

    public static void afflist() {
        for(int i = 0; i<blockchain.size(); i+=1) {
            affBlock(blockchain.get(i));
            System.out.println();
        }
    }

    public static void affStr(int n, String t[]) {
        for(int i = 0; i<n; i+=1) {
            System.out.println(t[i]);
        }
    }
    
    public static void verify() {
        boolean verify = true;
        int lastblockIndex = blockchain.size() -1;
        for (int i = 0; i < blockchain.size()-1; i++) {
            verify = blockchain.get(i).hash.compareTo(blockchain.get(lastblockIndex).hash) != 0;
            verify = blockchain.get(i).previousHash.compareTo(blockchain.get(lastblockIndex).previousHash) != 0;
        }
        verify = blockchain.get(lastblockIndex).hash.substring(0, prefix).equals(prefixString);
        boo = verify;
        //assertTrue(verify);
    }

    public static void assertTrue(boolean equals) {
        if(!equals) System.exit(0);
    }

    public static void affBlock(Block b) {
        System.out.println("data: " + b.data + "\nhash: " + b.hash + "\nPreviousHash: " + b.previousHash + "\nDate: " + b.timeStamp + "\nnonce: " + b.nonce);
    }

    public static void launch(String name, String hash)  throws IOException, InterruptedException  {
        long date = new Date().getTime();
        Block bl = Block(name, hash, date);
        bl.hash = mineBlock(prefix, bl);
        blockchain.add(bl);
    }
    
    public static void clear() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    /**
     * 
     * @throws Exception temps d'attente entre deux clear
     */
    public static void console() throws IOException, InterruptedException {
        //System.out.println("cls");
        clear();
        System.out.flush();
        Thread.sleep(1);
    }

    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws IOException, InterruptedException {
        launch("Launching Block", "00002af0050b62a7ab036034e39d2c5f9228b921a28d6ea36003e11b3a2ed4e2");
        System.out.println("Start");
        int i = 0;
        //String t[] = new String[1_000];
        while(boo){//&& i<t.length) {
            //System.out.println("data of the new block:");
            //String dataToEnter = sc.nextLine();
            Thread.sleep(500);
            String dataToEnter = "aaaa";
            newBlock(dataToEnter);
            //t[i] = blockchain.get(i).hash;
            affBlock(blockchain.get(i));
            i+=1;
            System.out.println("Number of block: " + i);
            //if(i%100 == 0) {
            //    System.out.println(i);
            //}
            //System.out.println("New block chain: ");
            //afflist();
        }
        //affStr(t.length, t);
        //System.out.println("end");

        //givenBlockchain_whenValidated_thenSuccess();
        //String hash = "";
        //String previousHash = "3fac2af0050b62a7ab036034e39d2c5f9228b921a28d6ea36003e11b3a2ed4e2";
        /*for(int i = 0; i<100; i+=1) {
            hash = calculateBlockHash(previousHash, 1235L, 2, "hello");
            System.out.println(hash);
            previousHash = hash;
        }*/

    }
}