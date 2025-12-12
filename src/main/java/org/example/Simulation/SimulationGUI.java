package org.example.Simulation;

import org.example.Events.Event;
import org.example.Events.EventType;
import org.example.GeoCore.Location;
import org.example.Resources.States.ActionState;
import org.example.Resources.States.ReturningState;
import org.example.Resources.States.TravelingState;
import org.example.Resources.Unit;
import org.example.Resources.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimulationGUI extends JFrame {
    private final SimulationConfig config;
    private final MapPanel mapPanel;

    public SimulationGUI(SimulationConfig config) {
        this.config = config;
        this.setTitle("System Wspomagania Decyzji PSP - Kraków");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mapPanel = new MapPanel();
        this.add(mapPanel);

        Timer timer = new Timer(33, e -> mapPanel.repaint());
        timer.start();

        this.setVisible(true);
    }

    private class MapPanel extends JPanel {

        private final double minLat = 49.95855025648944;
        private final double maxLat = 50.154564013341734;
        private final double minLon = 19.688292482742394;
        private final double maxLon = 20.02470275868903;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            g2d.setColor(new Color(240, 240, 240));
            g2d.fillRect(0, 0, w, h);
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawRect(0, 0, w - 1, h - 1);

            for (Unit unit : config.units) {
                Point p = convertGeoToPixel(unit.getLocation(), w, h);
                g2d.setColor(Color.BLUE);
                g2d.fillRect(p.x - 6, p.y - 6, 12, 12);
                g2d.setColor(Color.BLACK);
                g2d.drawString(unit.getName(), p.x - 10, p.y - 10);
            }


            try {
                config.skkm.activeEvents.removeIf(event -> !event.isActive());
                List<Event> events = config.skkm.activeEvents;
                for (Event event : events) {
                    Point p = convertGeoToPixel(event.getLocation(), w, h);
                    if (event.getType() == EventType.PZ) {
                        g2d.setColor(new Color(255, 50, 50, 180)); // Czerwony przezroczysty
                    } else {
                        g2d.setColor(new Color(100, 100, 255, 180)); // Niebieski (MZ)
                    }
                    g2d.fillOval(p.x - 8, p.y - 8, 16, 16);
                }
            } catch (Exception e) {
                // Ignorujemy błędy współbieżności przy rysowaniu
            }

            for (Unit unit : config.units) {
                for (Vehicle v : unit.getVehicles()) {
                    drawVehicle(g2d, v, unit, w, h);
                }
            }

        }

        private void drawVehicle(Graphics2D g, Vehicle v, Unit parent, int w, int h) {
            Location loc = null;
            Color color = Color.GRAY;

            if (v.getState() instanceof org.example.Resources.States.FreeState) {
                return;
            } else if (v.getState() instanceof TravelingState) {
                loc = ((TravelingState) v.getState()).getCurrentLocation();
                color = Color.ORANGE; // W drodze
            } else if (v.getState() instanceof ActionState) {
                loc = ((ActionState) v.getState()).getLocation();
                color = Color.RED; // W akcji
            } else if (v.getState() instanceof ReturningState) {
                loc = ((ReturningState) v.getState()).getCurrentLocation();
                color = Color.GREEN; // Powrót
            }

            if (loc != null) {
                Point p = convertGeoToPixel(loc, w, h);

                g.setColor(color);
                g.fillOval(p.x - 4, p.y - 4, 8, 8);

                if (!(v.getState() instanceof org.example.Resources.States.FreeState)) {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial", Font.BOLD, 10));


                    int yOffset = (Math.abs(v.getId().hashCode()) % 6) * 12;

                    g.drawString(v.getId(), p.x + 8, p.y - 10 + yOffset);
                }
            }
        }

        private Point convertGeoToPixel(Location loc, int width, int height) {
            double x = (loc.getLongitude() - minLon) / (maxLon - minLon) * width;
            double y = (maxLat - loc.getLatitude()) / (maxLat - minLat) * height;
            return new Point((int) x, (int) y);
        }

    }
}