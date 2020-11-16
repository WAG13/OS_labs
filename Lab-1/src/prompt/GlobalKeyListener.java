package prompt;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    private Runnable onEscapePressed;

    public GlobalKeyListener(Runnable onEscapePressed) {
        this.onEscapePressed = onEscapePressed;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            onEscapePressed.run();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) { }

    public void nativeKeyTyped(NativeKeyEvent e) { }
}