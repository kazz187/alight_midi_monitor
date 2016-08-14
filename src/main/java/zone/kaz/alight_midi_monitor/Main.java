package zone.kaz.alight_midi_monitor;

import processing.core.PApplet;

public class Main extends PApplet {

    private static int PORT = 7890;

    public static void main(String[] args) {
        Networking networking = new Networking(PORT);
        networking.start();
        PApplet.main(new String[]{Main.class.getName()});
    }

    public void settings() {
        size(800, 400);
    }

    public void setup() {
        background(0);
        frameRate(20);
    }

    public void draw() {
        clear();
        ellipse(random(800), 200, 10, 10);

    }

}
