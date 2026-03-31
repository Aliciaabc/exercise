import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class ParenthesesChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入括号字符串: ");
        String input = scanner.nextLine();
        scanner.close();

        if (isBalanced(input)) {
            System.out.println("括号平衡且配对。");
        } else {
            System.out.println("括号不平衡或未正确配对。");
        }
    }

    /**
     * 检查括号字符串是否平衡且配对
     * @param str 输入的括号字符串
     * @return true 表示平衡且配对，false 表示不平衡或未配对
     */
    public static boolean isBalanced(String str) {
        // 使用 Deque 接口和 ArrayDeque 类作为栈
        Deque<Character> stack = new ArrayDeque<>();

        // 遍历字符串中的每个字符
        for (char ch : str.toCharArray()) {
            // 如果是开括号，压入栈
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            }
            // 如果是闭括号，进行匹配检查
            else if (ch == ')' || ch == ']' || ch == '}') {
                // 如果栈为空，说明没有对应的开括号
                if (stack.isEmpty()) {
                    return false;
                }
                // 弹出栈顶元素
                char top = stack.pop();
                // 检查弹出的开括号是否与当前闭括号匹配
                if (!isMatchingPair(top, ch)) {
                    return false;
                }
            }
            // 忽略其他字符（根据题目要求，输入应只包含括号，但加上此处理可提高健壮性）
        }
        // 所有字符处理完后，栈应为空，否则有未匹配的开括号
        return stack.isEmpty();
    }

    /**
     * 判断一对括号是否匹配
     * @param open 开括号
     * @param close 闭括号
     * @return 匹配返回 true，否则 false
     */
    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '[' && close == ']') ||
               (open == '{' && close == '}');
    }
}
