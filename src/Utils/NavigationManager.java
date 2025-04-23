package Utils;

import javafx.scene.Parent;

import java.util.Stack;

public class NavigationManager {
    private static final Stack<Parent> history = new Stack<>();

    public static void pushView(Parent view) {
        history.push(view);
    }

    public static Parent popPreviousView() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        return null;
    }

    public static void clear() {
        history.clear();
    }
}
