package org.es.leipl.tercafeira.grupob.tools.gui.swingCalendar;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;

public abstract class Calendar extends JComponent {
    protected static final LocalTime START_TIME = LocalTime.of(8, 0);
    protected static final LocalTime END_TIME = LocalTime.of(22, 30);

    protected static final int MIN_WIDTH = 600;
    protected static final int MIN_HEIGHT = 300;

    protected static final int HEADER_HEIGHT = 30;
    protected static final int TIME_COL_WIDTH = 50;

    // An estimate of the width of a single character (not exact but good
    // enough)
    public static final int FONT_LETTER_PIXEL_WIDTH = 5;
    private ArrayList<CalendarEvent> events;
    private double timeScale;
    public double dayWidth;
    public Graphics2D g2;

    private EventListenerList listenerList = new EventListenerList();

    public Calendar() {
        this(new ArrayList<>());
    }

    Calendar(ArrayList<CalendarEvent> events) {
        this.events = events;
        setupEventListeners();
        setupTimer();
    }

    public static LocalTime roundTime(LocalTime time, int minutes) {
        LocalTime t = time;

        if (t.getMinute() % minutes > minutes / 2) {
            t = t.plusMinutes(minutes - (t.getMinute() % minutes));
        } else if (t.getMinute() % minutes < minutes / 2) {
            t = t.minusMinutes(t.getMinute() % minutes);
        }

        return t;
    }

    private void setupEventListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!checkCalendarEventClick(e.getPoint())) {
                    checkCalendarEmptyClick(e.getPoint());
                }
            }
        });
    }

    protected abstract boolean dateInRange(LocalDate date);

    private boolean checkCalendarEventClick(Point p) {
        double x0, x1, y0, y1;
        for (CalendarEvent event : events) {
            if (!dateInRange(event.getDate())) continue;

            x0 = dayToPixel(event.getDate().getDayOfWeek());
            y0 = timeToPixel(event.getStart());
            x1 = dayToPixel(event.getDate().getDayOfWeek()) + dayWidth;
            y1 = timeToPixel(event.getEnd());

            if (p.getX() >= x0 && p.getX() <= x1 && p.getY() >= y0 && p.getY() <= y1) {
                fireCalendarEventClick(event);
                return true;
            }
        }
        return false;
    }

    private boolean checkCalendarEmptyClick(Point p) {
        final double x0 = dayToPixel(getStartDay());
        final double x1 = dayToPixel(getEndDay()) + dayWidth;
        final double y0 = timeToPixel(START_TIME);
        final double y1 = timeToPixel(END_TIME);

        if (p.getX() >= x0 && p.getX() <= x1 && p.getY() >= y0 && p.getY() <= y1) {
            LocalDate date = getDateFromDay(pixelToDay(p.getX()));
            fireCalendarEmptyClick(LocalDateTime.of(date, pixelToTime(p.getY())));
            return true;
        }
        return false;
    }

    protected abstract LocalDate getDateFromDay(DayOfWeek day);

    // CalendarEventClick methods

    public void addCalendarEventClickListener(CalendarEventClickListener l) {
        listenerList.add(CalendarEventClickListener.class, l);
    }

    public void removeCalendarEventClickListener(CalendarEventClickListener l) {
        listenerList.remove(CalendarEventClickListener.class, l);
    }

    // Notify all listeners that have registered interest for
    // notification on this event type.
    private void fireCalendarEventClick(CalendarEvent calendarEvent) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        CalendarEventClickEvent calendarEventClickEvent;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == CalendarEventClickListener.class) {
                calendarEventClickEvent = new CalendarEventClickEvent(this, calendarEvent);
                ((CalendarEventClickListener) listeners[i + 1]).calendarEventClick(calendarEventClickEvent);
            }
        }
    }

    // CalendarEmptyClick methods

    public void addCalendarEmptyClickListener(CalendarEmptyClickListener l) {
        listenerList.add(CalendarEmptyClickListener.class, l);
    }

    public void removeCalendarEmptyClickListener(CalendarEmptyClickListener l) {
        listenerList.remove(CalendarEmptyClickListener.class, l);
    }

    private void fireCalendarEmptyClick(LocalDateTime dateTime) {
        Object[] listeners = listenerList.getListenerList();
        CalendarEmptyClickEvent calendarEmptyClickEvent;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == CalendarEmptyClickListener.class) {
                calendarEmptyClickEvent = new CalendarEmptyClickEvent(this, dateTime);
                ((CalendarEmptyClickListener) listeners[i + 1]).calendarEmptyClick(calendarEmptyClickEvent);
            }
        }
    }

    private void calculateScaleVars() {
        int width = getWidth();
        int height = getHeight();

        if (width < MIN_WIDTH) {
            width = MIN_WIDTH;
        }

        if (height < MIN_HEIGHT) {
            height = MIN_HEIGHT;
        }

        // Units are pixels per second
        timeScale = (double) (height - HEADER_HEIGHT) / (END_TIME.toSecondOfDay() - START_TIME.toSecondOfDay());
        dayWidth = (width - TIME_COL_WIDTH) / numDaysToShow();
    }

    protected abstract int numDaysToShow();

    // Gives x val of left most pixel for day col
    protected abstract double dayToPixel(DayOfWeek dayOfWeek);

    public double timeToPixel(LocalTime time) {
        return ((time.toSecondOfDay() - START_TIME.toSecondOfDay()) * timeScale) + HEADER_HEIGHT;
    }

    private LocalTime pixelToTime(double y) {
        return LocalTime.ofSecondOfDay((int) ((y - HEADER_HEIGHT) / timeScale) + START_TIME.toSecondOfDay()).truncatedTo(ChronoUnit.MINUTES);
    }

    private DayOfWeek pixelToDay(double x) {
        double pixel;
        DayOfWeek day;
        for (int i = getStartDay().getValue(); i <= getEndDay().getValue(); i++) {
            day = DayOfWeek.of(i);
            pixel = dayToPixel(day);
            if (x >= pixel && x < pixel + dayWidth) {
                return day;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        calculateScaleVars();
        g2 = (Graphics2D) g;

        // Rendering hints try to turn anti-aliasing on which improves quality
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set background to white
        g2.setColor(Color.white);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Set paint colour to black
        g2.setColor(Color.black);

        drawDayHeadings();
        drawTodayShade();
        drawGrid();
        drawTimes();
        drawEvents();
        drawCurrentTimeLine();
    }

    protected abstract DayOfWeek getStartDay();

    protected abstract DayOfWeek getEndDay();

    public void drawDayHeadings() {
        int y = 20;
        int x;
        LocalDate day;
        DayOfWeek dayOfWeek;

        for (int i = getStartDay().getValue(); i <= getEndDay().getValue(); i++) {
            dayOfWeek = DayOfWeek.of(i);
            day = getDateFromDay(dayOfWeek);

            String text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + day.getDayOfMonth() + "/" + day.getMonthValue();
            x = (int) (dayToPixel(DayOfWeek.of(i)) + (dayWidth / 2) - (FONT_LETTER_PIXEL_WIDTH * text.length() / 2));
            g2.drawString(text, x, y);
        }
    }

    public void drawGrid() {
        // Save the original colour
        final Color ORIG_COLOUR = g2.getColor();

        // Set colour to grey with half alpha (opacity)
        Color alphaGray = new Color(128, 128, 128, 128);
        Color alphaGrayLighter = new Color(200, 200, 200, 128);
        g2.setColor(alphaGray);

        // Draw vertical grid lines
        double x;
        for (int i = getStartDay().getValue(); i <= getEndDay().getValue(); i++) {
            x = dayToPixel(DayOfWeek.of(i));
            g2.draw(new Line2D.Double(x, HEADER_HEIGHT, x, timeToPixel(END_TIME)));
        }

        // Draw horizontal grid lines
        double y;
        int x1;
        for (LocalTime time = START_TIME; time.compareTo(END_TIME) <= 0; time = time.plusMinutes(30)) {
            y = timeToPixel(time);
            if (time.getMinute() == 0) {
                g2.setColor(alphaGray);
                x1 = 0;
            } else {
                g2.setColor(alphaGrayLighter);
                x1 = TIME_COL_WIDTH;
            }
            g2.draw(new Line2D.Double(x1, y, dayToPixel(getEndDay()) + dayWidth, y));
        }

        // Reset the graphics context's colour
        g2.setColor(ORIG_COLOUR);
    }

    public void drawTodayShade() {
        LocalDate today = LocalDate.now();

        // Check that date range being viewed is current date range
        if (!dateInRange(today)) return;

        final double x = dayToPixel(today.getDayOfWeek());
        final double y = timeToPixel(START_TIME);
        final double width = dayWidth;
        final double height = timeToPixel(END_TIME) - timeToPixel(START_TIME);

        final Color origColor = g2.getColor();
        Color alphaGray = new Color(200, 200, 200, 64);
        g2.setColor(alphaGray);
        g2.fill(new Rectangle2D.Double(x, y, width, height));
        g2.setColor(origColor);
    }

    public void drawCurrentTimeLine() {
        LocalDate today = LocalDate.now();

        // Check that date range being viewed is current date range
        if (!dateInRange(today)) return;

        final double x0 = dayToPixel(today.getDayOfWeek());
        final double x1 = dayToPixel(today.getDayOfWeek()) + dayWidth;
        final double y = timeToPixel(LocalTime.now());

        final Color origColor = g2.getColor();
        final Stroke origStroke = g2.getStroke();

        g2.setColor(new Color(255, 127, 110));
        g2.setStroke(new BasicStroke(2));
        g2.draw(new Line2D.Double(x0, y, x1, y));

        g2.setColor(origColor);
        g2.setStroke(origStroke);
    }

    private void drawTimes() {
        int y;
        for (LocalTime time = START_TIME; time.compareTo(END_TIME) <= 0; time = time.plusHours(1)) {
            y = (int) timeToPixel(time) + 15;
            g2.drawString(time.toString(), TIME_COL_WIDTH - (FONT_LETTER_PIXEL_WIDTH * time.toString().length()) - 5, y);
        }
    }

    private void drawEvents() {
        double x;
        double y0;

        for (CalendarEvent event : events) {
            if (!dateInRange(event.getDate())) continue;

            x = dayToPixel(event.getDate().getDayOfWeek());
            y0 = timeToPixel(event.getStart());

            Rectangle2D rect = new Rectangle2D.Double(x, y0, dayWidth, (timeToPixel(event.getEnd()) - timeToPixel(event.getStart())));
            x=rect.getMinX();
            y0=rect.getMinY();
            Font origFont = g2.getFont();
            float fontSize = origFont.getSize() - 1.6F;

            Color origColor = g2.getColor();
            g2.setColor(event.getColor());
            g2.fill(rect);
            g2.setColor(origColor);

            //Going to parse the event to display.
            String[] eventText = event.getText().split(";");
            String uc = eventText[0];
            String sala = eventText[1];
            String cursos = eventText[2];
            String turmas = eventText[3];

            int xText = (int) x+5;
            int yText = (int) y0 +11;
            // Store the current font state


            // Create a new font with same properties but bold
            Font newFont = origFont.deriveFont(Font.BOLD, fontSize);
            g2.setFont(newFont);

            // Unbolden
            g2.setFont(origFont.deriveFont(fontSize));

            // Draw the event's UC
            FontMetrics fm = g2.getFontMetrics();
            int stringWidth = fm.stringWidth(uc);
            int stringHeight = fm.getHeight();
//            System.out.println("stringwidth : " + stringWidth  + " dayWidth : " + dayWidth);
            if(stringWidth > dayWidth){
               String[] uc_parts = uc.split(" ");
                uc = "";
               for(String s : uc_parts){
                   String regex = "\\b(de|da|dos|do|e|das)\\b";
                   if(s.matches(regex)){
                       continue;
                   }//I want to add a popUp to this string i'm adding.
                   if(s.matches("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")){
                       uc+=s;
                       continue;
                   }
                   uc += String.valueOf(s.charAt(0)).toUpperCase();

               }
            }
            //even tought is big to fit the rect (...)
            if(fm.stringWidth(uc) > dayWidth){
                uc = "...";
            }
            g2.drawString(uc, xText, yText);
            yText = yText + stringHeight;
            g2.drawString("Sala: " + sala, xText, (yText));
            yText = yText + stringHeight;
            if(fm.stringWidth("Cursos: " + cursos) > dayWidth){
                cursos = "(...)";
            }
            g2.drawString("Cursos: " + cursos, xText, (yText));
            yText = yText + stringHeight;
            if(fm.stringWidth("turmas: " + turmas) > dayWidth){
                turmas = "(...)";
            }
            g2.drawString("Turmas: " + turmas, xText, (yText));

            // Reset font
            g2.setFont(origFont);
        }
    }

    protected double getDayWidth() {
        return dayWidth;
    }

    // Repaints every minute to update the current time line
    private void setupTimer() {
        Timer timer = new Timer(1000*60, e -> repaint());
        timer.start();
    }

    protected abstract void setRangeToToday();

    public void goToToday() {
        setRangeToToday();
        repaint();
    }

    public void addEvent(CalendarEvent event) {
        events.add(event);
        repaint();
    }

    public boolean removeEvent(CalendarEvent event) {
        boolean removed = events.remove(event);
        repaint();
        return removed;
    }

    public void setEvents(ArrayList<CalendarEvent> events) {
        this.events = events;
        repaint();
    }
}
