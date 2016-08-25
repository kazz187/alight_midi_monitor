package zone.kaz.alight_midi_monitor;

import processing.core.PApplet;

public class Main extends PApplet {

    private static int PORT = 7890;
    private static Networking networking = new Networking(PORT);
    private static int X_NUM = 8;
    private static int Y_NUM = 50;
    private static int WIDTH = 800;
    private static int HEIGHT = 400;

    public static void main(String[] args) {
        networking.start();
        PApplet.main(new String[]{Main.class.getName()});
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        background(0);
        frameRate(60);
    }

    public void draw() {
        clear();
        char[] buffer = networking.getBuffer();
        for (int i = 0; i < X_NUM; i++) {
            for (int j = 0; j < Y_NUM; j++) {
                int index = i * 64 + j;
                int color = 0;
                if (buffer != null && buffer.length > index * 3) {
                    color = color(buffer[index*3], buffer[index*3+1], buffer[index*3+2]);
                }
                fill(color);
                ellipse((int) ((i + 0.5) * width / X_NUM), height - (int) ((j + 0.5) * height / Y_NUM), 10, 10);
            }
        }

    }

}
