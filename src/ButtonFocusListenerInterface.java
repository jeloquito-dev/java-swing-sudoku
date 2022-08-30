import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

interface ButtonFocusListenerInterface extends FocusListener {

    void removeFocus(FocusEvent e);

    @Override
    default void focusGained(FocusEvent e) {

    }

    @Override
    default void focusLost(FocusEvent e) {
        removeFocus(e);
    }
}
