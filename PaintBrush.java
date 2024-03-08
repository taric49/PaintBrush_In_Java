package depo1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class PaintBrush extends JFrame implements MouseInputListener {

    JPanel colorPanel;

    JPanel buttonPanel;

    JPanel bigPanel;

    ArrayList<Cisim> cisimler = new ArrayList<>();
    ArrayList<Cisim> liftedObjs = new ArrayList<>();
    String feature; // wich button is pressed

    int x; // mouses coordinates updatet whenever mouse is dragged
    int y;

    int prevX;
    int prevY;

    int pressedX;
    int pressedY; // mouses coordinates when mouse pressed

    boolean dragged = false;

    Color currColor = Color.BLACK;

    Cisim liftedObj = null; // Currently lifted object

    JPanel blueLine;

    class Cisim {
        int x;
        int y;
        int oldx;
        int oldy;
        int wide;
        int height;
        Color color;
        boolean moveable = true; // if it is false then its drawing and cannot movable

        public Cisim(int a, int b, int w, int h, Color c) {
            x = a;
            y = b;
            wide = w;
            height = h;
            color = c;
        }

        public Cisim(int a, int b, int olda, int oldb, int w, int h, Color c) {
            x = a;
            y = b;
            oldx = olda;
            oldy = oldb;
            wide = w;
            height = h;
            color = c;
        }

    }

    public class RectangleClass extends Cisim {

        public RectangleClass(int a, int b, int w, int h, Color c) {
            super(a, b, w, h, c);
        }

        public RectangleClass(int a, int b, int olda, int oldb, int w, int h, Color c) {
            super(a, b, olda, oldb, w, h, c);
        }
    }

    public class Oval extends Cisim {

        public Oval(int a, int b, int w, int h, Color c) {
            super(a, b, w, h, c);
        }
    }

    public PaintBrush() {
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        colorPanel = new JPanel();
        bigPanel = new JPanel();
        buttonPanel = new JPanel();

        colorPanel.setLayout(new FlowLayout());
        buttonPanel.setLayout(new FlowLayout());

        bigPanel.setLayout(new BorderLayout());
        bigPanel.add(colorPanel, BorderLayout.NORTH);
        bigPanel.add(buttonPanel, BorderLayout.CENTER);
        blueLine = new JPanel();
        blueLine.setBackground(Color.blue);
        bigPanel.add(blueLine, BorderLayout.SOUTH);

        add(bigPanel, BorderLayout.NORTH);

        JButton recButton = new JButton("Dikdortgen ciz");
        recButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                feature = "recButton";
            }

        });
        JButton ovButton = new JButton("Oval ciz");
        ovButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                feature = "ovButton";
            }

        });
        JButton draw = new JButton("Kalemle ciz");
        draw.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                feature = "draw";
            }

        });
        JButton lift = new JButton("Tasi");
        lift.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                feature = "lift";
            }

        });

        buttonPanel.add(recButton);
        buttonPanel.add(ovButton);
        buttonPanel.add(draw);
        buttonPanel.add(lift);

        colorPanel.add(new JPanel());

        JPanel blue = new JPanel();
        blue.setPreferredSize(new Dimension(50, 40)); // deifne a constant size I didnt need to use this but i use it
                                                      // for aestetic
        blue.setBackground(Color.BLUE);
        blue.addMouseListener(new MouseAdapter() { // define action when this panel clicked
            public void mouseClicked(MouseEvent e) {
                currColor = Color.BLUE;
            }
        });
        JPanel red = new JPanel();
        red.setPreferredSize(new Dimension(50, 40));
        red.setBackground(Color.RED);
        red.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                currColor = Color.RED;
            }
        });
        JPanel green = new JPanel();
        green.setPreferredSize(new Dimension(50, 40));
        green.setBackground(Color.GREEN);
        green.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                currColor = Color.GREEN;
            }
        });
        JPanel yellow = new JPanel();
        yellow.setPreferredSize(new Dimension(50, 40));
        yellow.setBackground(Color.YELLOW);
        yellow.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                currColor = Color.YELLOW;
            }
        });
        JPanel orange = new JPanel();
        orange.setPreferredSize(new Dimension(50, 40));
        orange.setBackground(Color.ORANGE);
        orange.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                currColor = Color.ORANGE;
            }
        });
        JPanel purple = new JPanel();
        purple.setPreferredSize(new Dimension(50, 40));
        purple.setBackground(new Color(127, 0, 127));
        purple.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                currColor = new Color(127, 0, 127);
            }
        });
        JPanel black = new JPanel();
        black.setPreferredSize(new Dimension(50, 40));
        black.setBackground(Color.BLACK);
        black.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                currColor = Color.BLACK;
            }
        });

        colorPanel.add(blue);
        colorPanel.add(red);
        colorPanel.add(green);
        colorPanel.add(yellow);
        colorPanel.add(orange);
        colorPanel.add(purple);
        colorPanel.add(black);
        colorPanel.add(new JPanel());
        getContentPane().setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);

    }

    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < cisimler.size(); i++) {
            Cisim c = cisimler.get(i);
            if (c instanceof RectangleClass) {
                g.setColor(c.color);
                if (c.moveable) {
                    g.fillRect(c.x, c.y, c.wide, c.height);
                } else {
                    g.drawLine(c.oldx, c.oldy, c.x, c.y);

                }
            } else if (c instanceof Oval) {
                g.setColor(c.color);
                g.fillOval(c.x, c.y, c.wide, c.height);
            }
        }

        for (int j = 0; j < liftedObjs.size(); j++) {
            Cisim c = liftedObjs.get(j);
            if (c instanceof RectangleClass) {
                g.setColor(c.color);
                g.fillRect(c.x, c.y, c.wide, c.height);
            } else if (c instanceof Oval) {
                g.setColor(c.color);
                g.fillOval(c.x, c.y, c.wide, c.height);
            }
        }

        if (liftedObj != null && liftedObj instanceof RectangleClass) { // draws last lifted Object because it has to be
                                                                        // the top
            g.setColor(liftedObj.color);
            g.fillRect(liftedObj.x, liftedObj.y, liftedObj.wide, liftedObj.height);
        } else if (liftedObj != null && liftedObj instanceof Oval) {
            g.setColor(liftedObj.color);
            g.fillOval(liftedObj.x, liftedObj.y, liftedObj.wide, liftedObj.height);
        }
        g.setColor(currColor);

        if (feature.equals("draw")) { // lastly draws currently adjusting objects and lines
            Cisim c = new RectangleClass(x, y, prevX, prevY, 3, 3, currColor);
            c.moveable = false;
            cisimler.add(c);
            g.drawLine(c.oldx, c.oldy, c.x, c.y);
        } else if (feature.equals("recButton") && pressedY > blueLine.getY() + 45) {
            g.fillRect(Math.min(x, pressedX), Math.min(y, pressedY), Math.abs(x - pressedX), Math.abs(y - pressedY));
        } else if (feature.equals("ovButton") && pressedY > blueLine.getY() + 45) {
            g.fillOval(Math.min(x, pressedX), Math.min(y, pressedY), Math.abs(x - pressedX), Math.abs(y - pressedY));
        } else if (feature.equals("lift")) {
            if (liftedObj != null && dragged && liftedObj.moveable) {
                liftedObj.x = x;
                liftedObj.y = y;
            }
        }

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

        pressedX = e.getX();
        pressedY = e.getY();

        if (e.getY() > blueLine.getY() + 45) {
            x = e.getX();
            y = e.getY();
        } else {
            x = e.getX();
            y = blueLine.getY() + 45;
        }

        if (feature.equals("lift") && e.getY() > blueLine.getY() + 45) {
            for (int i = 0; i < cisimler.size(); i++) {
                Cisim c = cisimler.get(i);

                if (pressedX >= c.x && pressedX <= c.x + c.wide && pressedY >= c.y && pressedY <= c.y + c.height
                        && c.moveable) {
                    if (c instanceof RectangleClass) {
                        liftedObj = c;
                        liftedObjs.add(liftedObj);
                    } else if (c instanceof Oval) {
                        double centerX = c.x + c.wide / 2;
                        double centerY = c.y + c.height / 2;
                        double xx = (pressedX - centerX) / (c.wide / 2);
                        double yy = (pressedY - centerY) / (c.height / 2);
                        if (xx * xx + yy * yy <= 1) {
                            liftedObj = c;
                            liftedObjs.add(liftedObj);
                        }

                    }

                }
            }
        }

    }

    public void mouseReleased(MouseEvent e) {
        if (feature.equals("recButton") && pressedY > blueLine.getY() + 45) {
            cisimler.add(new RectangleClass(Math.min(x, pressedX), Math.min(y, pressedY), Math.abs(x - pressedX),
                    Math.abs(y - pressedY), currColor));
            liftedObjs.add(cisimler.get(cisimler.size() - 1));
            // When mouse released we add new Object
        } else if (feature.equals("ovButton") && pressedY > blueLine.getY() + 45) {
            cisimler.add(new Oval(Math.min(x, pressedX), Math.min(y, pressedY), Math.abs(x - pressedX),
                    Math.abs(y - pressedY), currColor));
            liftedObjs.add(cisimler.get(cisimler.size() - 1));
        } else if (feature.equals("lift")) {
            liftedObj = null;
        } else if (feature.equals("draw")) {

        }

    }

    public void mouseDragged(MouseEvent e) {
        if (e.getY() > blueLine.getY() + 45) {
            dragged = true;
            prevX = x;
            prevY = y;
            x = e.getX();
            y = e.getY();
            repaint();
        } else {
            dragged = false;
        }

    }

    public void mouseMoved(MouseEvent e) {
    }

    public static void main(String[] args) {
        PaintBrush b = new PaintBrush();
        b.setVisible(true);
    }
}
