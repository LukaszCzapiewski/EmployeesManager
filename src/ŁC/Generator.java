package ŁC;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;



class Generator {
    private static EmployeesL employeesAL;
    private static EmployeesL employeesALcopy;
    private static FaceContainer faceContainer;
    private static BufferedImage bufferedImage;

    static void uniqueAddFace(int n) throws IOException {

        BufferedImage generatedFace;

        for (int i = 0; i < n; i++) {
            generatedFace = createGraphics();
            faceContainer.getBufferedImages().add(generatedFace);
        }

        faceContainer.enbufferAll();

    }
    static private int DEFAULT_WIDTH = 450, DEFAULT_HEIGHT = 500;
    private static int eyeSize;
    private static int eyeHeight;
    private static int eyeWidth;
    private static int p1x;
    private static int p1y;
    private static int p2x;
    private static int p2y;
    private static int p3x;
    private static int p3y;
    private static int mountY;
    private static int mountWidth;
    private static int startAngle;
    private static int hair1x;
    private static int hair1y;
    private static int hair2x;
    private static int hair2y;
    private static int prob;
    private static int col;
    private static int earsSize;
    private static int earsX;
    private static int earsY;

    private static Random r = new Random();

    static void randomize() {
        
        eyeSize = r.nextInt(15) + 10;
        eyeHeight = r.nextInt(20) + 100;
        eyeWidth = r.nextInt(15);

        p1x = r.nextInt(10);
        p1y = r.nextInt(10);

        p2x = r.nextInt(10);
        p2y = r.nextInt(10);

        p3x = r.nextInt(10);
        p3y = r.nextInt(10);

        mountY = r.nextInt(30);
        startAngle = r.nextInt(5) == 1 ? r.nextInt(30) : r.nextInt(30) + 160;
        mountWidth = r.nextInt(20) - 10;

        col = r.nextInt(20);

        earsSize = r.nextInt(10);
        earsX = r.nextInt(10);
        earsY = r.nextInt(10);

    }
    public static BufferedImage createGraphics() {
        randomize();

        BufferedImage bufferedImage = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        double leftX = 70;
        double topY = 20;
        double width = 280;
        double height = 330;
        var backRect = new Rectangle2D.Double(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        g2.setColor(Color.WHITE);
        g2.fill(backRect);
        var rect = new Rectangle2D.Double(leftX, topY, width, height);
        g2.setColor(new Color(255 - col, 207 - col, 160 - col));
        var ellipse = new Ellipse2D.Double();
        ellipse.setFrame(rect);
        g2.fill(ellipse);
        g2.fillOval(330 + earsX, 150 + earsY, 40 + earsSize, 70 + earsSize);
        g2.fillOval(50 - earsX, 150 + earsY, 40 + earsSize, 70 + earsSize);

        g2.setColor(Color.BLACK);
        g2.draw(ellipse);

        g2.setColor(Color.WHITE);
        g2.fillOval(Double.valueOf(leftX).intValue() + 100 - eyeWidth - eyeSize / 2, Double.valueOf(topY).intValue() - eyeSize / 2 + eyeHeight, eyeSize * 2, eyeSize * 2);
        g2.fillOval(Double.valueOf(leftX).intValue() + 170 + eyeWidth - eyeSize / 2, Double.valueOf(topY).intValue() - eyeSize / 2 + eyeHeight, eyeSize * 2, eyeSize * 2);
        g2.setColor(Color.BLACK);

        g2.fillOval(Double.valueOf(leftX).intValue() + 100 - eyeWidth, Double.valueOf(topY).intValue() + eyeHeight, eyeSize, eyeSize);
        g2.fillOval(Double.valueOf(leftX).intValue() + 170 + eyeWidth, Double.valueOf(topY).intValue() + eyeHeight, eyeSize, eyeSize);

        g2.setStroke(new BasicStroke(3));
        g2.draw(new Line2D.Float(210 + p1x, 140 + p1y, 190 + p2x, 220 + p2y));

        g2.drawArc(Double.valueOf(leftX).intValue() + 100, Double.valueOf(topY).intValue() + 220 + mountY, 80 + mountWidth, 20, startAngle, 160);

        int xdiff = 0;
        int hairLenght;
        prob = r.nextInt(8);

        if (prob != 0) {
            hairLenght = r.nextInt(30) - 20;

            for (int i = 0; i < 14; i++) {

                hair1x = r.nextInt(5);
                hair1y = r.nextInt(5);

                hair1x = r.nextInt(5);
                hair2y = r.nextInt(10);

                g2.draw(new Line2D.Float(140 + xdiff + hair1x, 40 + hair1y, 140 + xdiff + hair2x, 10 + hair2y + hairLenght));
                xdiff += 10;
            }

        }
        g2.dispose();


        return bufferedImage;


       
        
        

    }

    public static void testInputOutput(EmployeesL empL){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employeesAL1.dat"))) {
            out.writeObject(empL);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        EmployeesL employeesALcopy = new EmployeesL("testinputstream");
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("employeesAL.dat"))) {
            employeesALcopy = (EmployeesL) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




    public static Long sortArray(EmployeesArr empArr){
        Instant start;
        Instant finish;
        Long timeElapsed;


        start = Instant.now();
        Arrays.sort(empArr.employeesArray, new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                int c;
                c = o1.getEmploymentDate().compareTo(o2.getEmploymentDate());
                if(c == 0)
                    c = o1.getSalary().compareTo(o2.getSalary());
                if(c == 0)
                    c = o1.getName().compareTo(o2.getName());
                return c;
            }
        });
        finish = Instant.now();
        timeElapsed = Duration.between(start,finish).toMillis();
        return timeElapsed;
    }

    public static Long sortList(EmployeesL empL, int i){
        Instant start;
        Instant finish;
        Long timeElapsed;

        if(i == 1){
            start = Instant.now();
            Collections.sort(empL.employeesList, new Comparator<Employee>() {
                @Override
                public int compare(Employee e1, Employee e2) {
                    int c;
                    c = e1.getEmploymentDate().compareTo(e2.getEmploymentDate());
                    if(c == 0)
                        c = e1.getSalary().compareTo(e2.getSalary());
                    if(c == 0)
                        c = e1.getName().compareTo(e2.getName());
                    return c;
                }
            });
            finish = Instant.now();
            timeElapsed = Duration.between(start,finish).toMillis();
            return timeElapsed;
        }

        if(i == 2){
            start = Instant.now();
            Collections.sort(empL.employeesArrayList, new Comparator<Employee>() {
                @Override
                public int compare(Employee e1, Employee e2) {
                    int c;
                    c = e1.getEmploymentDate().compareTo(e2.getEmploymentDate());
                    if(c == 0)
                        c = e1.getSalary().compareTo(e2.getSalary());
                    if(c == 0)
                        c = e1.getName().compareTo(e2.getName());
                    return c;
                }
            });
            finish = Instant.now();
            timeElapsed = Duration.between(start,finish).toMillis();
            return timeElapsed;
        }

        else return null;
    }



    public static void main(String[] args) {
        String[] name = new String[]{"Harry", "Ross",
                "Bruce", "Cook",
                "Carolyn", "Morgan",
                "Albert", "Walker",
                "Randy", "Reed",
                "Larry", "Barnes",
                "Lois", "Wilson",
                "Jesse", "Campbell",
                "Ernest", "Rogers",
                "Theresa", "Patterson",
                "Henry", "Simmons",
                "Michelle", "Perry",
                "Frank", "Butler",
                "Shirley"};
        String[] lname = new String[]{"Brooks",
                "Rachel", "Edwards",
                "Christopher", "Perez",
                "Thomas", "Baker",
                "Sara", "Moore",
                "Chris", "Bailey",
                "Roger", "Johnson",
                "Marilyn", "Thompson",
                "Anthony", "Evans",
                "Julie", "Hall",
                "Paula", "Phillips",
                "Annie", "Hernandez",
                "Dorothy", "Murphy",
                "Alice", "Howard"};

        String[] city = new String[]{"09-530 Gabin", "95-030 Gadka", "98-405 Galewice", "81-222 Gdynia", "59-180 Gaworzyce", " 08-400 Garwolin", "42-277 Garnek"};
        String[] street = new String[]{"Licealna", "Biskupia", "Turmoncka", "Izabelli", "Atenska", "Na Grobli", "Klimatyczna", "Aleja Stanow Zjednoczonych", "Karkonoszy", "Zielone Zacisze"};
        String[] employer = new String[]{"Trendspoca", "Nandl", "Pennicon Enterprises", "Zengeotec", "Wallenbeam", "Komputer Engineering", "Robitlog", "Worldpower"};
        String[] job = new String[]{"Agent Kredytowy", "Automatyk", "Astronom", "Recepcjonistka", "Rewident Taboru Kolejowego", "Rybak Srodladowy", "Nanotechnik", "Organista", "Opiekunka Osob Starszych"};
        Random random = new Random();
        EmployeesL empL = new EmployeesL("Ubezpieczyciel1");
        EmployeesL empLArr = new EmployeesL("Ubezpieczyciel2");
        EmployeesArr empArr = new EmployeesArr("Ubezpieczyciel3");


        for(int i=0;i<16;i++)
        {

            Employee emp = new Employee(
                    i,
                    name[random.nextInt(name.length)]+" "+lname[random.nextInt(lname.length)],
                    city[random.nextInt(city.length)]+" "+ street[random.nextInt(street.length)]+" " + random.nextInt(100)+"/"+random.nextInt(12),
                    LocalDate.of(1960,1,1).plusYears(random.nextInt(30)).plusMonths( random.nextInt(12)).plusDays( random.nextInt(31)),
                    employer[random.nextInt(employer.length)],
                    LocalDate.of(1990,1,1).plusYears(random.nextInt(30)).plusMonths( random.nextInt(12)).plusDays( random.nextInt(31)),
                    new BigDecimal(random.nextInt(12000)+3000),
                    job[random.nextInt(job.length)]);
            empL.employeesList.add(emp);
            empLArr.employeesArrayList.add(emp);
            empArr.employeesArray[i]=emp;


        }
        faceContainer = new FaceContainer();
        try {
            uniqueAddFace(4);
        } catch (IOException e) {
            e.printStackTrace();
        }

        empL.setFaceContainer(faceContainer);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("pracownicy"))) {
            out.writeObject(empL);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }







    }
}