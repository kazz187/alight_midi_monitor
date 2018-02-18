package zone.kaz.alight_midi_monitor;

import processing.core.PApplet;

public class Main2 extends PApplet {

    private static int PORT = 7890;
    private static Networking networking = new Networking(PORT);
    private static int X1_NUM = 8;
    private static int Y1_NUM = 54;
    private static int X2_NUM = 4;
    private static int Y2_NUM = 60;
    private static int WIDTH = 800;
    private static int HEIGHT = 500;

    public static void main(String[] args) {
        networking.start();
        PApplet.main(new String[]{Main2.class.getName()});
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
        for (int i = 0; i < X1_NUM; i++) {
            for (int j = 0; j < Y1_NUM; j++) {
                int index = i * 64 + j;
                int color = 0;
                if (buffer != null && buffer.length > index * 3) {
                    color = color(buffer[index*3], buffer[index*3+1], buffer[index*3+2]);
                }
                fill(color);
                ellipse((int) ((i + 0.5) * width / X1_NUM * 0.5 + width * 0.5 / 2),
                        height - (int) ((j + 0.5) * height / Y1_NUM * 0.4),
                        5, 5);
            }
        }
        for (int k = 0; k < 2; k++) {
            for (int l = 0; l < X2_NUM; l++) {
                int x0 = (int) ((X2_NUM - l) * width / 2.0 / 10.0);
                int x1 = (int) (width / 2.0 - l * width / 2.0 / 10.0);
                int y0 = (int) (height * 0.6 - (l+1) * height * 0.11);
                int y1 = (int) (height * 0.6 - (l+1) * height * 0.05);
                for (int m = 0; m < Y2_NUM; m++) {
                    int index = (l*2+k) * 64 + m + 512;
                    int color = 0;
                    if (buffer != null && buffer.length > index * 3) {
                        color = color(buffer[index*3], buffer[index*3+1], buffer[index*3+2]);
                    }
                    fill(color);
                    int x = (int) (x0 + (double) (x1 - x0) / Y2_NUM * m);
                    int y = (int) (y0 + (double) (y1 - y0) / Y2_NUM * m);
                    if (k != 0) {
                        x *= -1;
                        x += width;
                    }
                    ellipse(x, y, 5, 5);
                }
            }
        }
    }

}
