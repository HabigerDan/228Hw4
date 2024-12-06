package edu.iastate.cs2280.hw4;

import java.util.Stack;

/**
 * Represents a binary tree for decoding messages.
 * 
 * @author Daniel Habiger
 */
public class MsgTree {
    public MsgTree leftNode;  
    public MsgTree rightNode;
    public char data;         

    /**
     * Constructs a tree from the encoding string.
     * 
     * @param encodingString The preorder encoding string.
     */
    public MsgTree(String encodingString) {
        if (encodingString == null || encodingString.length() < 2) {
            return;
        }
        Stack<MsgTree> nodeStack = new Stack<>();
        nodeStack.push(this);
        int index = 0;
        MsgTree currentNode = this;
        this.data = encodingString.charAt(index++);
        String lastOperation = "insert";

        while (index < encodingString.length()) {
            MsgTree newNode = new MsgTree(encodingString.charAt(index++));
            if (!lastOperation.equals("insert")) {
                currentNode.rightNode = newNode;
                if (newNode.data == '^') {
                    currentNode = nodeStack.push(newNode);
                    lastOperation = "insert";
                } else {
                    if (!nodeStack.isEmpty()) {
                        currentNode = nodeStack.pop();
                    }
                    lastOperation = "pop";
                }
            } else {
                currentNode.leftNode = newNode;
                if (newNode.data == '^') {
                    currentNode = nodeStack.push(newNode);
                    lastOperation = "insert";
                } else {
                    if (!nodeStack.isEmpty()) {
                        currentNode = nodeStack.pop();
                    }
                    lastOperation = "pop";
                }
            }
        }
    }

    /**
     * Constructs a tree node with the specified character.
     * 
     * @param charData The character stored in the node.
     */
    public MsgTree(char charData) {
        this.leftNode = null;
        this.rightNode = null;
        this.data = charData;
    }

    /**
     * Decodes the binary string using the tree.
     * 
     * @param treeRoot The root of the encoding tree.
     * @param binaryMessage The binary string to decode.
     */
    public void decode(MsgTree treeRoot, String binaryMessage) {
        MsgTree currentNode = treeRoot;
        System.out.println("MESSAGE:");
        StringBuilder decodedMessage = new StringBuilder();
        for (int i = 0; i < binaryMessage.length(); i++) {
            char bit = binaryMessage.charAt(i);
            currentNode = (bit == '0') ? currentNode.leftNode : currentNode.rightNode;
            if (currentNode.data != '^') {
                decodedMessage.append(currentNode.data);
                findCode(treeRoot, currentNode.data, binaryCode = "");
                currentNode = treeRoot;
            }
        }
        System.out.println(decodedMessage);
    }

    /**
     * Finds the binary code for a specific character.
     * 
     * @param root The root of the tree.
     * @param target The character to find.
     * @param path The binary path to the character.
     * @return True if the character is found, false otherwise.
     */
    private static boolean findCode(MsgTree root, char target, String path) {
        if (root == null) {
            return false;
        }

        if (root.data == target) {
            binaryCode = path;
            return true;
        }

        return findCode(root.leftNode, target, path + "0") || findCode(root.rightNode, target, path + "1");
    }

    /**
     * Prints the character codes for the tree.
     * 
     * @param treeRoot The root of the tree.
     * @param characters The string of characters to encode.
     */
    public static void printCharacterCodes(MsgTree treeRoot, String characters) {
        System.out.println("character code\n-------------------------");
        for (char character : characters.toCharArray()) {
            findCode(treeRoot, character, binaryCode = "");
            System.out.println("    " + (character == '\n' ? "\\n" : character + " ") + "    " + binaryCode);
        }
    }

    private static String binaryCode; 
}
