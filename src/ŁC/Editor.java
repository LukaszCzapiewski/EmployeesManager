package ŁC;



import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;


public class Editor extends JFrame {

    private JFrame frame;
    private static int size;
    private Employee currentEmployee;
    private int index;
    private boolean loaded;
    private List<ByteArrayWrapper> wrappedFaces;
    private FaceContainer faceContainer;
    private final JPanel picture;
    Thread[] threads = new Thread[4];
    Random ran = new Random();
    File file = new File(System.getProperty("user.dir"));
    EmployeesL employeesAL = new EmployeesL("EmployesFile");
    JTextField id_field = new JTextField(20);
    JTextField fname_field = new JTextField(20);
    JTextField lname_field = new JTextField(20);
    JTextField adress_field = new JTextField(20);
    JTextField birth_field = new JTextField(20);
    JTextField employer_field = new JTextField(20);
    JTextField empdate_field = new JTextField(20);
    JTextField salary_field = new JTextField(20);
    String[] jobs = {"Agent Kredytowy", "Automatyk", "Astronom", "Recepcjonistka", "Rewident Taboru Kolejowego", "Rybak Srodladowy", "Nanotechnik", "Organista", "Opiekunka Osob Starszych"};
    JComboBox<String> jobscombo = new JComboBox<>(jobs);
    ExecutorService executor;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    ArrayBlockingQueue<Employee> players = new ArrayBlockingQueue<>(1000);
    int money;


    public Editor(){

        JPanel game = new JPanel(new BorderLayout());
        JButton start = new JButton("Start");
        JTextArea area = new JTextArea();
        JScrollPane pane = new JScrollPane (area);
        area.setEditable ( false );
        JScrollPane scroll = new JScrollPane ( area );
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

        game.add ( scroll );



        game.add(start,BorderLayout.PAGE_START);
;
        JFrame frame = new JFrame("Employee editor - Łukasz Czapiewski");
        frame.setSize(500,600);
        frame.setVisible(true);
        index = 0;
        loaded = false;
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        picture = new PicturePanel();
        JPanel cPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("Edit employee", null, cPanel, null);
        tabbedPane.addTab("Picture", null, picture, null);
        tabbedPane.addTab("Game", null,game, null);

        JPanel przyciski = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("Add");
        JButton save = new JButton("Save");
        JButton write = new JButton("Write");
        JButton find = new JButton("Find");
        przyciski.add(add);
        przyciski.add(save);


        start.addActionListener( e ->  { //obsługa gry

                    if(start.getLabel().equals("Stop")){
                        start.setLabel("Start");

                            threads[0].interrupt();
                            threads[1].interrupt();
                            threads[2].interrupt();
                            threads[3].interrupt();
                    }
                    else {
                        start.setLabel("Stop");

                        Employee[] winners = new Employee[size];
                        money = 0;
                        for(int i=0;i<size;i++) {
                            winners[i] = employeesAL.employeesList.get(i);
                            money=winners[i].getMoney()+money;
                        }
                        area.append("PIENIĄDZE W GRZE: " + money);
                        players.addAll(Arrays.asList(winners));
                        nextRound(size,area);




                    }
                }
        );
        write.addActionListener( e -> write()
        );
        save.addActionListener( e -> save()
        );


        przyciski.add(write);
        przyciski.add(find);
        String[] combo = { "Id", "First name", "Last name","Adress", "Birth date","Employer name","Employment date","Salary","Job title"};
        JComboBox<String> combobox = new JComboBox<>(combo);
        przyciski.add(combobox);
        cPanel.add(przyciski, BorderLayout.PAGE_START);




        JPanel etykiety = new JPanel(new GridLayout(9,1));
        JLabel id = new JLabel("     Id");
        JLabel fname = new JLabel("     First name");
        JLabel lname = new JLabel("     Last name");
        JLabel adress = new JLabel("     Adress");
        JLabel birth = new JLabel("     Birth date");
        JLabel employer = new JLabel("     Employer name");
        JLabel empdate = new JLabel("     Employment date");
        JLabel salary = new JLabel("     Salary");
        JLabel job = new JLabel("     Job title");
        etykiety.add(id);
        etykiety.add(fname);
        etykiety.add(lname);
        etykiety.add(adress);
        etykiety.add(birth);
        etykiety.add(employer);
        etykiety.add(empdate);
        etykiety.add(salary);
        etykiety.add(job);
        cPanel.add(etykiety, BorderLayout.LINE_START);





        JPanel polaTekstowe = new JPanel(new GridLayout(9,1,0,10));

        polaTekstowe.add(id_field);
        polaTekstowe.add(fname_field);
        polaTekstowe.add(lname_field);
        polaTekstowe.add(adress_field);
        polaTekstowe.add(birth_field);
        polaTekstowe.add(employer_field);
        polaTekstowe.add(empdate_field);
        polaTekstowe.add(salary_field);
        polaTekstowe.add(jobscombo);
        cPanel.add(polaTekstowe, BorderLayout.CENTER);








        JMenuBar nMenuBar = new JMenuBar();
        frame.setJMenuBar(nMenuBar);
        JMenu fileMenu = new JMenu("File");
        nMenuBar.add(fileMenu);
        JMenuItem LoadMenu = new JMenuItem("Load");
        LoadMenu.addActionListener( e -> openLoadWindow()

        );
        fileMenu.add(LoadMenu);



        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener( e -> System.exit(JFrame.EXIT_ON_CLOSE)
        );
        fileMenu.add(exitMenuItem);



        JPanel przewijanie = new JPanel(new FlowLayout());
        JButton forward1 = new JButton(">");
        forward1.addActionListener( e -> {
                    if(loaded&&index<size-1)
                        index =index+1;
                    update_fields(index);
                    update_image();
                }
        );
        JButton forward2 = new JButton(">>");
        forward2.addActionListener( e -> {
                    if(loaded)
                        index = size-1;
                    update_fields(index);
                    update_image();

                }
        );
        JButton back1 = new JButton("<");
        back1.addActionListener( e -> {
                    if(loaded&&index>0)
                        index =index-1;
                    update_fields(index);
                    update_image();
                }
        );
        JButton back2 = new JButton("<<");
        back2.addActionListener( e -> {
                    if(loaded)
                        index = 0;
                    update_fields(index);
                    update_image();
                }
        );
        przewijanie.add(back2);
        przewijanie.add(back1);
        przewijanie.add(forward1);
        przewijanie.add(forward2);
        contentPane.add(przewijanie, BorderLayout.PAGE_END);




    }

    void openLoadWindow() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            loadWithProgressBar(frame,fileChooser.getSelectedFile().toString());

    }


    private void loadWithProgressBar(Component parent, String path) {

        File file = new File(path);


        new Thread(() -> {
            ProgressMonitorInputStream pMonitorInputStream;
            ProgressMonitor progressMonitor;
            try{



                ObjectInputStream bis = new ObjectInputStream(new BufferedInputStream(
                        pMonitorInputStream = new ProgressMonitorInputStream(
                                parent,
                                "Reading " + file.getName(),
                                new FileInputStream(file))));{
                    progressMonitor = pMonitorInputStream.getProgressMonitor();
                    progressMonitor.setMillisToDecideToPopup(100);
                    progressMonitor.setMillisToPopup(100);
                }


                employeesAL = (EmployeesL) bis.readObject();
                size = employeesAL.employeesList.size();
                faceContainer = employeesAL.getFaceContainer();
                wrappedFaces = faceContainer.getWrappers();
                loaded = true;
                update_fields(0);
                update_image();







            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();



    }

    int nextRound(int size2,JTextArea area){
        Runnable[] worker = new Runnable[size2/2];
        threads = new Thread[size2/2];
        for (int i = 0; i < size2/2; i++) {
            try {
                worker[i] = new WorkerThread(players.take(), players.take() ,area,players);

            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        for (int i = 0; i < size2/2; i++){
            threads[i] = new Thread(worker[i]);
            threads[i].start();
            i++;
            threads[i] = new Thread(worker[i]);
            threads[i].start();
        }
        size2=size2/2;
        area.append("\n\n next Round");
    return size2; }
    
    void update_fields(int index){
        try {
            currentEmployee = employeesAL.employeesList.get(index);
            String[] arr = currentEmployee.getName().split(" ");
            id_field.setText(String.valueOf(currentEmployee.getId()));
            fname_field.setText(arr[0]);
            lname_field.setText(arr[1]);
            adress_field.setText(currentEmployee.getAdressLine());
            birth_field.setText(String.valueOf(currentEmployee.getBirthDate()));
            employer_field.setText(currentEmployee.getEmployerName());
            empdate_field.setText(String.valueOf(currentEmployee.getEmploymentDate()));
            salary_field.setText((currentEmployee.getSalary()) + " zł");
            jobscombo.setSelectedItem(currentEmployee.getJobtitle());
        }catch (IndexOutOfBoundsException ignored){

        }
    }
    void save() {
        currentEmployee.setId(Integer.parseInt(id_field.getText()));
        currentEmployee.setName(fname_field.getText()+" "+lname_field.getText());
        currentEmployee.setAdressLine(adress_field.getText());
        //currentEmployee.setBirthDate(LocalDate.parse(String.valueOf(birth_field), formatter));
        currentEmployee.setEmployerName(employer_field.getText());
        //currentEmployee.setEmploymentDate(LocalDate.parse(empdate_field.getText(), formatter));

    }
    void write() {


        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getName()))) {
            out.writeObject(employeesAL);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    void update_image() {

        BufferedImage pictureToShow = null;

        if (employeesAL.getEmployeeList().contains(currentEmployee)) {
            int index = employeesAL.getEmployeeList().indexOf(currentEmployee);

            try {
                if (index>wrappedFaces.size()) {pictureToShow = FaceContainer.debuffer(wrappedFaces.get(ran.nextInt(wrappedFaces.size())).getImage());}
                else
                pictureToShow = FaceContainer.debuffer(wrappedFaces.get(index).getImage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else
            pictureToShow = new BufferedImage(450, 450, BufferedImage.TYPE_INT_RGB);

        ((PicturePanel) picture).setBufferedImage(pictureToShow);
        picture.revalidate();
        picture.repaint();

    }








    public static void main(String[] args) {
        EventQueue.invokeLater(Editor::new);
    }
}

