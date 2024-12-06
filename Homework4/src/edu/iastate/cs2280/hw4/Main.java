package edu.iastate.cs2280.hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * @author Daniel Habiger
 *
 */

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the filename to decode:");
        
        String filePath = scanner.nextLine();
        scanner.close();
        
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath))).trim();
        
        int position = fileContent.lastIndexOf('\n');
        
        Set<Character> uniqueCharacters = new HashSet<>();
        
        String encodingPattern = fileContent.substring(0, position); 
        String binaryMessage = fileContent.substring(position).trim(); 
        
        for (char ch : encodingPattern.toCharArray()) {
            if (ch != '^') {
                uniqueCharacters.add(ch);
            }
        }
        
        String characterSet = uniqueCharacters.stream().map(String::valueOf).collect(Collectors.joining());
        MsgTree treeRoot = new MsgTree(encodingPattern);
        
        MsgTree.printCharacterCodes(treeRoot, characterSet);
        
        treeRoot.decode(treeRoot, binaryMessage);
    }
}
