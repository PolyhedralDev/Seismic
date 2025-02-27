package com.dfsek.seismic.algorithms.string;

public class StringAlgorithms {
    /**
     * Converts a camelCase or PascalCase method name to snake_case.
     * <p>
     * Special cases:
     * <ul>
     *   <li>If the method name starts with 'i' followed by an uppercase letter, the 'i' is not separated by an underscore.</li>
     *   <li>If an uppercase letter is preceded by an uppercase letter and followed by a lowercase letter, an underscore is inserted
     *   before the uppercase letter.</li>
     *   <li>If an uppercase letter is followed by another uppercase letter, no underscore is inserted.</li>
     * </ul>
     * <p>
     * Examples:
     * <pre>
     * {@code
     * methodNameToSnakeCase("iPow10") -> "ipow10"
     * methodNameToSnakeCase("IEEEremainder") -> "ieee_remainder"
     * methodNameToSnakeCase("invSqrt") -> "inv_sqrt"
     * }
     * </pre>
     *
     * @param str the method name in camelCase or PascalCase.
     *
     * @return the method name in snake_case.
     */
    public static String methodNameToSnakeCase(String str) {
        StringBuilder snakeStr = new StringBuilder(str.length());
        char[] chars = str.toCharArray();
        int charLen = chars.length;
        snakeStr.append(Character.toLowerCase(chars[0]));
        for(int i = 1; i < charLen; i++) {
            char aChar = chars[i];
            if(Character.isUpperCase(aChar)) {
                char prevChar = chars[i - 1];
                if(Character.isLetter(prevChar)) {
                    if(Character.isLowerCase(prevChar)) {
                        if(i == 1 && chars[0] == 'i') {
                            snakeStr.append(Character.toLowerCase(aChar));
                        } else {
                            snakeStr.append('_');
                            snakeStr.append(Character.toLowerCase(aChar));
                        }
                    } else if(charLen > i + 1 && Character.isLowerCase(chars[i + 1])) {
                        snakeStr.append(Character.toLowerCase(aChar));
                        snakeStr.append('_');
                    } else {
                        snakeStr.append(Character.toLowerCase(aChar));
                    }
                } else {
                    snakeStr.append(Character.toLowerCase(aChar));
                }
            } else {
                snakeStr.append(aChar);
            }
        }

        return snakeStr.toString();
    }
}
