package main;

import java.util.ArrayList;

public class KeyboardContainer {
	private ArrayList<Keyboard> keyboards;

    private KeyboardContainer() {
    	keyboards = new ArrayList<>();
    }

    private static KeyboardContainer instance;

    public static KeyboardContainer getInstance() {
        if (instance == null) {
            instance = new KeyboardContainer();
        }
        return instance;
    }

    public void addKeyboard(String name, String price, String stock, String description) {
        Keyboard keyboard = new Keyboard(name, price, stock, description);
        keyboards.add(keyboard);
    }

    public ArrayList<Keyboard> getKeyboards() {
        return keyboards;
    }
}
